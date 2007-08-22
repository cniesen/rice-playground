/*
 * Copyright 2005-2006 The Kuali Foundation.
 *
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.iu.uis.eden.actions;

import org.apache.log4j.MDC;

import edu.iu.uis.eden.EdenConstants;
import edu.iu.uis.eden.KEWServiceLocator;
import edu.iu.uis.eden.actionrequests.ActionRequestFactory;
import edu.iu.uis.eden.actionrequests.ActionRequestValue;
import edu.iu.uis.eden.engine.node.RouteNodeInstance;
import edu.iu.uis.eden.exception.EdenUserNotFoundException;
import edu.iu.uis.eden.exception.InvalidActionTakenException;
import edu.iu.uis.eden.exception.WorkflowException;
import edu.iu.uis.eden.routeheader.DocumentRouteHeaderValue;
import edu.iu.uis.eden.user.WorkflowUser;
import edu.iu.uis.eden.util.Utilities;

/**
 * Saves a document.  Puts the document in the persons action list that saved the document.
 * This can currently only be done by the initiator of the document.
 *
 * @author rkirkend
 * @author ewestfal
 * @author seiffert
 *
 */
public class SaveActionEvent extends ActionTakenEvent {

  private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SaveActionEvent.class);

  private static final String RESPONSIBILITY_DESCRIPTION = "Initiator needs to complete document.";

  public SaveActionEvent(DocumentRouteHeaderValue routeHeader, WorkflowUser user) {
    super(routeHeader, user);
    setActionTakenCode(EdenConstants.ACTION_TAKEN_SAVED_CD);
  }

  public SaveActionEvent(DocumentRouteHeaderValue routeHeader, WorkflowUser user, String annotation) {
      super(routeHeader, user, annotation);
      setActionTakenCode(EdenConstants.ACTION_TAKEN_SAVED_CD);
  }

  /* (non-Javadoc)
   * @see edu.iu.uis.eden.actions.ActionTakenEvent#requireInitiatorCheck()
   */
  @Override
  protected boolean requireInitiatorCheck() {
      return routeHeader.getDocumentType().getInitiatorMustSavePolicy().getPolicyValue().booleanValue();
  }

  /* (non-Javadoc)
   * @see edu.iu.uis.eden.actions.ActionTakenEvent#isActionCompatibleRequest(java.util.List)
   */
  @Override
  public String validateActionRules() throws EdenUserNotFoundException {
      String superError = super.validateActionTakenRules();
      if (!Utilities.isEmpty(superError)) {
          return superError;
      }
      if (!getRouteHeader().isValidActionToTake(getActionPerformedCode())) {
          return "Document is not in a state to be approved";
      }
      return "";
  }

  public void recordAction() throws InvalidActionTakenException, EdenUserNotFoundException {
    MDC.put("docId", getRouteHeader().getRouteHeaderId());
    checkLocking();
    updateSearchableAttributesIfPossible();

    if (annotation == null) {
      annotation = "";
    }

    LOG.debug("Checking to see if the action is legal");
    String errorMessage = validateActionRules();
    if (!Utilities.isEmpty(errorMessage)) {
        throw new InvalidActionTakenException(errorMessage);
    }

//    if (getRouteHeader().isValidActionToTake(getActionTakenCode())) {
      if (getRouteHeader().isStateInitiated()) {
        LOG.debug("Record the save action");
        saveActionTaken();
        getRouteHeader().getActionRequests().add(generateSaveRequest());
        LOG.debug("Marking document saved");
        try {
          String oldStatus = getRouteHeader().getDocRouteStatus();
          getRouteHeader().markDocumentSaved();
          String newStatus = getRouteHeader().getDocRouteStatus();
          notifyStatusChange(newStatus, oldStatus);
          getRouteHeaderService().saveRouteHeader(routeHeader);
        } catch (WorkflowException ex) {
          LOG.warn(ex, ex);
          throw new InvalidActionTakenException(ex.getMessage());
        }
      }
//    } else {
//      LOG.warn("Document not in state to be saved.");
//      throw new InvalidActionTakenException("Document is not in a state to be saved");
//    }
  }

  protected ActionRequestValue generateSaveRequest() throws EdenUserNotFoundException {
	  RouteNodeInstance intialNode = (RouteNodeInstance) KEWServiceLocator.getRouteNodeService().getInitialNodeInstances(routeHeaderId).get(0);
	  ActionRequestFactory arFactory = new ActionRequestFactory(routeHeader, intialNode);
	  ActionRequestValue saveRequest =
		  arFactory.createActionRequest(EdenConstants.ACTION_REQUEST_COMPLETE_REQ,
			  new Integer(0),
			  getUser(),
			  RESPONSIBILITY_DESCRIPTION,
			  EdenConstants.SAVED_REQUEST_RESPONSIBILITY_ID,
			  Boolean.TRUE,
			  annotation);
//      this.getActionRequestService().saveActionRequest(saveRequest);
      this.getActionRequestService().activateRequest(saveRequest);
      return saveRequest;
  }

}