/*
 * Copyright 2005-2007 The Kuali Foundation.
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.kew.DocumentRouteLevelChange;
import org.kuali.rice.kew.KEWServiceLocator;
import org.kuali.rice.kew.actionrequests.ActionRequestValue;
import org.kuali.rice.kew.clientapp.WorkflowDocument;
import org.kuali.rice.kew.doctype.DocumentType;
import org.kuali.rice.kew.dto.NetworkIdDTO;
import org.kuali.rice.kew.engine.node.RouteNodeInstance;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.util.Utilities;
import org.kuali.workflow.test.KEWTestCase;

import edu.iu.uis.eden.test.TestUtilities;

public class ReturnToPreviousNodeActionTest extends KEWTestCase {
    
	@Test public void testReturnToPreviousSequential() throws Exception {
        
        WorkflowDocument document = new WorkflowDocument(new NetworkIdDTO("ewestfal"), SequentialSetup.DOCUMENT_TYPE_NAME);
        document.routeDocument("");
        
        // approve the document to the third node (workflow document 2)
        document = new WorkflowDocument(new NetworkIdDTO("bmcgough"), document.getRouteHeaderId());
        assertTrue("bmcgough should have approve.", document.isApprovalRequested());
        document.approve("");
        document = new WorkflowDocument(new NetworkIdDTO("rkirkend"), document.getRouteHeaderId());
        assertTrue("rkirkend should have approve.", document.isApprovalRequested());
        document.approve("");
        
        // we should now be at workflow document 2 node with request to pmckown
        assertEquals("Should be at WorkflowDocument2.", SequentialSetup.WORKFLOW_DOCUMENT_2_NODE, document.getNodeNames()[0]);
        document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
        assertTrue("Document should be enroute.", document.stateIsEnroute());
        assertTrue("pmckown should have approve.", document.isApprovalRequested());
        
        // now return the document to the AdHoc node
        document.returnToPreviousNode("", SequentialSetup.ADHOC_NODE);
        
        // there should now be 1 requests, an APPROVE to the initiator, since pmckown took the "return" action, he will not get
        // an FYI generated to him
        List actionRequests = KEWServiceLocator.getActionRequestService().findAllActionRequestsByRouteHeaderId(document.getRouteHeaderId());
        boolean isApproveToEwestfal = false;
        for (Iterator iterator = actionRequests.iterator(); iterator.hasNext();) {
            ActionRequestValue request = (ActionRequestValue) iterator.next();
            if (request.getWorkflowUser().getAuthenticationUserId().getId().equals("ewestfal")) {
                assertEquals("Should be approve request.", KEWConstants.ACTION_REQUEST_APPROVE_REQ, request.getActionRequested());
                isApproveToEwestfal = true;
            }
        }
        assertTrue(isApproveToEwestfal);
        assertEquals("Should be 1 requests.", 1, actionRequests.size());

        // Route a new document, and test the notification requests
        document = new WorkflowDocument(new NetworkIdDTO("ewestfal"), SequentialSetup.DOCUMENT_TYPE_NAME);
        document.routeDocument("");
        // there should now be 2 requests, one to rkirkend and one to bmcgough
        actionRequests = KEWServiceLocator.getActionRequestService().findAllActionRequestsByRouteHeaderId(document.getRouteHeaderId());
        assertEquals("There should be 2 requests.", 2, actionRequests.size());
        
        // now return to the current node we are on, effectively refreshing it, initiate this action as rkirkend so that bmcgough gets an FYI
        document = new WorkflowDocument(new NetworkIdDTO("rkirkend"), document.getRouteHeaderId());
        RouteNodeInstance preReturnNodeInstance = (RouteNodeInstance)KEWServiceLocator.getRouteNodeService().getActiveNodeInstances(document.getRouteHeaderId()).iterator().next();
        document.returnToPreviousNode("", SequentialSetup.WORKFLOW_DOCUMENT_NODE);
        preReturnNodeInstance = KEWServiceLocator.getRouteNodeService().findRouteNodeInstanceById(preReturnNodeInstance.getRouteNodeInstanceId());
        RouteNodeInstance postReturnNodeInstance = (RouteNodeInstance)KEWServiceLocator.getRouteNodeService().getActiveNodeInstances(document.getRouteHeaderId()).iterator().next();
        
        // check the nodes
        assertFalse("Node instances should be different.", preReturnNodeInstance.getRouteNodeInstanceId().equals(postReturnNodeInstance.getRouteNodeInstanceId()));
        assertEquals("Route nodes should be equal.", preReturnNodeInstance.getRouteNode().getRouteNodeId(), postReturnNodeInstance.getRouteNode().getRouteNodeId());
        // check the relationship between the nodes
        assertEquals("Should have 1 next node.", 1, preReturnNodeInstance.getNextNodeInstances().size());
        assertEquals("Should have 1 previous node.", 1, postReturnNodeInstance.getPreviousNodeInstances().size());
        assertEquals("Should have 0 next node.", 0, postReturnNodeInstance.getNextNodeInstances().size());
        assertEquals("pre node's next node should be the post node.", postReturnNodeInstance.getRouteNodeInstanceId(), ((RouteNodeInstance)preReturnNodeInstance.getNextNodeInstances().iterator().next()).getRouteNodeInstanceId());
        assertEquals("post node's previous node should be the pre node.", preReturnNodeInstance.getRouteNodeInstanceId(), ((RouteNodeInstance)postReturnNodeInstance.getPreviousNodeInstances().iterator().next()).getRouteNodeInstanceId());
        
        // there should now be 3 requests, a new approve to rkirkend and bmcgough and an FYI
        actionRequests = KEWServiceLocator.getActionRequestService().findAllActionRequestsByRouteHeaderId(document.getRouteHeaderId());
        assertEquals("There should be 3 requests.", 3, actionRequests.size());
        boolean isApproveToRkirkend = false;
        boolean isApproveToBmcgough = false;
        boolean isFyiToBmcgough = false;
        for (Iterator iterator = actionRequests.iterator(); iterator.hasNext();) {
            ActionRequestValue request = (ActionRequestValue) iterator.next();
            String netId = request.getWorkflowUser().getAuthenticationUserId().getId(); 
            if (netId.equals("rkirkend")) {
                assertEquals("Should be approve request.", KEWConstants.ACTION_REQUEST_APPROVE_REQ, request.getActionRequested());
                isApproveToRkirkend = true;
            } else if (netId.equals("bmcgough")) {
                if (request.getActionRequested().equals(KEWConstants.ACTION_REQUEST_APPROVE_REQ)) {
                    isApproveToBmcgough = true;
                } else if (request.getActionRequested().equals(KEWConstants.ACTION_REQUEST_FYI_REQ)) {
                    isFyiToBmcgough = true;
                }
                
            }
        }
        assertTrue(isApproveToRkirkend);
        assertTrue(isApproveToBmcgough);
        assertTrue(isFyiToBmcgough);
               
    }
    /**
     * our routing all ignore previous:
     * 
     *          M
     *          S
     *  B1      B2      B3      
     *  B1.1    B2.1    
     *          J
     *          M.1
     *          M.2
     *          M.3
     * 
     * M-WorkflowDocument-bmcgough(A)/rkrirked(A)
     * 
     * S-Split
     * 
     * B1- WorkflowDocument2-B1 -pmckown(A)
     * B1.1- WorkflowDocument3-B1 -jitrue(A)
     * 
     * B2- WorkflowDocument3-B2 -jitrue(A)
     * B2.1- WorkflowDocument2-B2 -pmckown(A)
     * 
     * B3- WorkflowDocument4-B3 -jthomas(A)
     * 
     * J-Join
     * 
     * M.1- WorkflowDocumentFinal -xqi(A)
     * M.2- Acknowledge1 -temay(K)
     * M.3- Acknowledge2 -jhopf(K)
     */
    @Test public void testReturnToPreviousParallel() throws Exception {
    	// set up our own post processor
    	DocumentType docType = KEWServiceLocator.getDocumentTypeService().findByName(ParallelSetup.DOCUMENT_TYPE_NAME);
    	docType.setPostProcessorName(ReturnToPreviousPostProcessor.class.getName());
    	//side step the normal validation of the service
    	//((DocumentTypeDAO)KEWServiceLocator.getService("enDocumentTypeDAO")).save(docType);
    	KEWServiceLocator.getDocumentTypeService().save(docType);
    	
    	// route a document
        WorkflowDocument document = new WorkflowDocument(new NetworkIdDTO("ewestfal"), ParallelSetup.DOCUMENT_TYPE_NAME);
        document.routeDocument("");
                
        //M branch
        document = new WorkflowDocument(new NetworkIdDTO("bmcgough"), document.getRouteHeaderId());
        document.approve("");
        document = new WorkflowDocument(new NetworkIdDTO("rkirkend"), document.getRouteHeaderId());
        document.approve("");
        
        assertAtNodes(document.getRouteHeaderId(), new String[] { ParallelSetup.WORKFLOW_DOCUMENT_2_B1_NODE, ParallelSetup.WORKFLOW_DOCUMENT_3_B2_NODE, ParallelSetup.WORKFLOW_DOCUMENT_4_B3_NODE});
        assertInBranches(document.getRouteHeaderId(), new String[] { "B1", "B2", "B3" });
        assertAllBranchesSameParent(document.getRouteHeaderId(), "PRIMARY");
        
        // assert that the proper parties have the document
        document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
        assertTrue(document.isApprovalRequested());
        document = new WorkflowDocument(new NetworkIdDTO("jitrue"), document.getRouteHeaderId());
        assertTrue(document.isApprovalRequested());
        document = new WorkflowDocument(new NetworkIdDTO("jthomas"), document.getRouteHeaderId());
        assertTrue(document.isApprovalRequested());
        
        // Approve as pmckown on B1 to transition to next node in B1, this is where we'll return from
        document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
        document.approve("");
   
        // now assert that the document is in the proper state
        assertAtNodes(document.getRouteHeaderId(), new String[] { ParallelSetup.WORKFLOW_DOCUMENT_3_B1_NODE, ParallelSetup.WORKFLOW_DOCUMENT_3_B2_NODE, ParallelSetup.WORKFLOW_DOCUMENT_4_B3_NODE});
        assertInBranches(document.getRouteHeaderId(), new String[] { "B1", "B2", "B3" });
        assertAllBranchesSameParent(document.getRouteHeaderId(), "PRIMARY");
        
        // check the post processor and make sure it has the proper number of transitions
        assertEquals("Post processor should have transitioned 6 times.", 6, ReturnToPreviousPostProcessor.getRouteLevelChanges().size());
        ReturnToPreviousPostProcessor.clearRouteLevelChanges();
        
        // verify that pmckown no longer has approve request
        document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
        assertFalse("pmckown should not have an approve request", document.isApprovalRequested());
        
        //rollback from B2.1 to B2
        document = new WorkflowDocument(new NetworkIdDTO("jitrue"), document.getRouteHeaderId());
        assertTrue(document.isApprovalRequested());
        document.returnToPreviousNode("", ParallelSetup.WORKFLOW_DOCUMENT_2_B1_NODE);
        
        // now pmckown shold have the document again
        document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
        assertTrue("Document should be back to pmckown", document.isApprovalRequested());
        
        // check that the post processor was notified properly on the rollback
        assertEquals("Post processor should have been notified.", 1, ReturnToPreviousPostProcessor.getRouteLevelChanges().size());
        DocumentRouteLevelChange levelChangeEvent = (DocumentRouteLevelChange)ReturnToPreviousPostProcessor.getRouteLevelChanges().get(0);
        assertEquals("New node should be WorkflowDocument2-B1", ParallelSetup.WORKFLOW_DOCUMENT_2_B1_NODE, levelChangeEvent.getNewNodeName());
        assertEquals("Old node should be WorkflowDocument3-B1", ParallelSetup.WORKFLOW_DOCUMENT_3_B1_NODE, levelChangeEvent.getOldNodeName());
        
    }
    
    private void assertAtNodes(Long documentId, String[] nodeNames) {
    	List activeNodeInstances = KEWServiceLocator.getRouteNodeService().getActiveNodeInstances(documentId);
    	assertEquals("There should be " + nodeNames.length + " active nodes.", nodeNames.length, activeNodeInstances.size());
    	for (int index = 0; index < nodeNames.length; index++) {
			String nodeName = nodeNames[index];
			boolean foundNode = false;
			for (Iterator iterator = activeNodeInstances.iterator(); iterator.hasNext(); ) {
				RouteNodeInstance activeNodeInstance = (RouteNodeInstance) iterator.next();
				if (activeNodeInstance.getName().equals(nodeName)) {
					foundNode = true;
					break;
				}
			}
			assertTrue("Document is not currently at node " + nodeName, foundNode);
		}
    }
    
    private void assertInBranches(Long documentId, String[] branchNames) {
    	// let's look at where we are and make sure we are in the correct branches and that all 3 branches have
        // the same parent branch.  This is currently a limitation of the ReturnToPreviousNodeAction in that it
        // will only allow a return if all currently executing branches have the same parent.    	
        List nodeInstances = KEWServiceLocator.getRouteNodeService().getActiveNodeInstances(documentId);
        boolean foundB1 = false;
        boolean foundB2 = false;
        boolean foundB3 = false;
        for (Iterator iterator = nodeInstances.iterator(); iterator.hasNext(); ) {
			RouteNodeInstance nodeInstance = (RouteNodeInstance) iterator.next();
			if ("B1".equals(nodeInstance.getBranch().getName())) foundB1 = true;
			if ("B2".equals(nodeInstance.getBranch().getName())) foundB2 = true;
			if ("B3".equals(nodeInstance.getBranch().getName())) foundB3 = true;
		}
        assertTrue("Not in Branch B1.", foundB1);
        assertTrue("Not in Branch B2.", foundB2);
        assertTrue("Not in Branch B3.", foundB3);
    }
    
    private void assertAllBranchesSameParent(Long documentId, String parentBranchName) {
    	List nodeInstances = KEWServiceLocator.getRouteNodeService().getActiveNodeInstances(documentId);
    	for (Iterator iterator = nodeInstances.iterator(); iterator.hasNext(); ) {
			RouteNodeInstance nodeInstance = (RouteNodeInstance) iterator.next();
			String branchName = (nodeInstance.getBranch().getParentBranch() == null ? null : nodeInstance.getBranch().getParentBranch().getName());
			assertTrue("Parent branch should be '" + parentBranchName + "'.", Utilities.equals(parentBranchName, branchName));
		}
    }

    /**
     * This test was implemented to address issue KULWF-495.
     * 
     * Effectively, we want to ensure that we can return from a Final Approval node and then pass back
     * through it without blowing the Final Approval Policy and sending the document into exception routing.
     */
    @Test public void testReturnToPreviousFromFinalNode() throws Exception {
    	// the BlanketApproveMandatoryNodeTest document type defined in ActionsConfig.xml defines WorkflowDocument2
    	// as a final approval node.
    	WorkflowDocument document = new WorkflowDocument(new NetworkIdDTO("ewestfal"), "BlanketApproveMandatoryNodeTest");
    	// blanket approve to the final approver node
    	document.blanketApprove("", "WorkflowDocument2");
    	
    	// the document should now be routed to Sir pmckown
    	assertTrue("Document should be enroute.", document.stateIsEnroute());
    	TestUtilities.assertAtNode("Should be at ye old WorkflowDocument2 node.", document, "WorkflowDocument2");
    	document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
    	assertTrue("Document should be to pmckown.", document.isApprovalRequested());
    	List activeNodeInstances = KEWServiceLocator.getRouteNodeService().getActiveNodeInstances(document.getRouteHeaderId());
    	assertEquals(1, activeNodeInstances.size());
    	RouteNodeInstance nodeInstance = (RouteNodeInstance)activeNodeInstances.get(0);
    	assertTrue("Active node instance should be a final approval node.", nodeInstance.getRouteNode().getFinalApprovalInd().booleanValue());
    	
    	// return back to initial node, ewestfal should end up with a complete request
    	document.returnToPreviousNode("", "AdHoc");
    	assertTrue("Document should be enroute.", document.stateIsEnroute());
    	TestUtilities.assertAtNode("We should be at the AdHoc node.", document, "AdHoc");
    	document = new WorkflowDocument(new NetworkIdDTO("ewestfal"), document.getRouteHeaderId());
    	
    	// now blanket approve back to WorkflowDocument2 again, before the bug fix this is where the policy failure would happen
    	document.blanketApprove("", "WorkflowDocument2");
    	assertTrue("Document should be enroute.", document.stateIsEnroute());
    	TestUtilities.assertAtNode("Should be at ye old WorkflowDocument2 node.", document, "WorkflowDocument2");
    	document = new WorkflowDocument(new NetworkIdDTO("pmckown"), document.getRouteHeaderId());
    	assertTrue("Document should be to pmckown.", document.isApprovalRequested());
    	
    	// now return it back one node to WorkflowDocument, this should send approve requests to bmcgough and rkirkend
    	document.returnToPreviousNode("", "WorkflowDocument");
    	assertTrue("Document should be enroute.", document.stateIsEnroute());
    	TestUtilities.assertAtNode("Should be at ye old WorkflowDocument node.", document, "WorkflowDocument");
    	document = new WorkflowDocument(new NetworkIdDTO("bmcgough"), document.getRouteHeaderId());
    	assertTrue("Bmcgough should have an approve.", document.isApprovalRequested());
    	document = new WorkflowDocument(new NetworkIdDTO("rkirkend"), document.getRouteHeaderId());
    	assertTrue("Rkirkend should have an approve.", document.isApprovalRequested());
    	
    	// now blanket approve it to the end and the document should be processed
    	document.blanketApprove("");
    	assertTrue("Document should be processed.", document.stateIsProcessed());
    }
        
    protected void loadTestData() throws Exception {
        loadXmlFile("ActionsConfig.xml");
    }
    
    private class SequentialSetup {

        public static final String DOCUMENT_TYPE_NAME = "BlanketApproveSequentialTest";
        public static final String ADHOC_NODE = "AdHoc";
        public static final String WORKFLOW_DOCUMENT_NODE = "WorkflowDocument";
        public static final String WORKFLOW_DOCUMENT_2_NODE = "WorkflowDocument2";
        public static final String ACKNOWLEDGE_1_NODE = "Acknowledge1";
        public static final String ACKNOWLEDGE_2_NODE = "Acknowledge2";
                
    }
    
    private class ParallelSetup {
        
        public static final String DOCUMENT_TYPE_NAME = "BlanketApproveParallelTest";
        public static final String ADHOC_NODE = "AdHoc";
        public static final String WORKFLOW_DOCUMENT_NODE = "WorkflowDocument";
        public static final String WORKFLOW_DOCUMENT_2_B1_NODE = "WorkflowDocument2-B1";
        public static final String WORKFLOW_DOCUMENT_2_B2_NODE = "WorkflowDocument2-B2";
        public static final String WORKFLOW_DOCUMENT_3_B1_NODE = "WorkflowDocument3-B1";
        public static final String WORKFLOW_DOCUMENT_3_B2_NODE = "WorkflowDocument3-B2";
        public static final String WORKFLOW_DOCUMENT_4_B3_NODE = "WorkflowDocument4-B3";
        public static final String ACKNOWLEDGE_1_NODE = "Acknowledge1";
        public static final String ACKNOWLEDGE_2_NODE = "Acknowledge2";
        public static final String JOIN_NODE = "Join";
        public static final String SPLIT_NODE = "Split";
                
    }
    
    public static class ReturnToPreviousPostProcessor extends DefaultPostProcessor {

    	private static List routeLevelChanges = new ArrayList();
    	
		public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange levelChangeEvent) throws Exception {
			routeLevelChanges.add(levelChangeEvent);
			return new ProcessDocReport(true);
		}
		
		public static List getRouteLevelChanges() {
			return routeLevelChanges;
		}
		
		public static void clearRouteLevelChanges() {
			routeLevelChanges.clear();
		}
    	
    }
    
}
