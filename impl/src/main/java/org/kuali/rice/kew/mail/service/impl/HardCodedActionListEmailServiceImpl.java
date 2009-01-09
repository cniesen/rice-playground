package org.kuali.rice.kew.mail.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.actionrequest.ActionRequestValue;
import org.kuali.rice.kew.doctype.bo.DocumentType;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.DTOConverter;
import org.kuali.rice.kew.dto.RouteHeaderDTO;
import org.kuali.rice.kew.mail.CustomEmailAttribute;
import org.kuali.rice.kew.mail.EmailBody;
import org.kuali.rice.kew.mail.EmailSubject;
import org.kuali.rice.kew.routeheader.DocumentRouteHeaderValue;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.user.UserService;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.util.Utilities;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.KNSConstants;


/**
 * Old hardcoded implementation for unit testing purposes only
 * @deprecated This is the original hardcoded actionlistemailservice implementation
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class HardCodedActionListEmailServiceImpl extends ActionListEmailServiceImpl {
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(HardCodedActionListEmailServiceImpl.class);

	private static final String DEFAULT_EMAIL_FROM_ADDRESS = "workflow@indiana.edu";

	private static final String ACTION_LIST_REMINDER = "Action List Reminder";

	private String deploymentEnvironment;

	public String getDocumentTypeEmailAddress(DocumentType documentType) {
		String fromAddress = (documentType == null ? null : documentType
				.getNotificationFromAddress());
		if (Utilities.isEmpty(fromAddress)) {
			fromAddress = getApplicationEmailAddress();
		}
		return fromAddress;
	}

	public String getApplicationEmailAddress() {
		// first check the configured value
		String fromAddress = Utilities.getKNSParameterValue(KEWConstants.DEFAULT_KIM_NAMESPACE, KNSConstants.DetailTypes.MAILER_DETAIL_TYPE, KEWConstants.EMAIL_REMINDER_FROM_ADDRESS);
		// if there's no value configured, use the default
		if (Utilities.isEmpty(fromAddress)) {
			fromAddress = DEFAULT_EMAIL_FROM_ADDRESS;
		}
		return fromAddress;
	}

	public EmailSubject getEmailSubject() {
		return new EmailSubject(ACTION_LIST_REMINDER);
	}

	public EmailSubject getEmailSubject(String customSubject) {
		return new EmailSubject(ACTION_LIST_REMINDER + " " + customSubject);
	}

	public void sendImmediateReminder(Person user, ActionItem actionItem) {
		if (sendActionListEmailNotification()) {
			DocumentRouteHeaderValue document = KEWServiceLocator
					.getRouteHeaderService().getRouteHeader(
							actionItem.getRouteHeaderId());
			StringBuffer emailBody = new StringBuffer(
					buildImmediateReminderBody(user, actionItem, document
							.getDocumentType()));
			StringBuffer emailSubject = new StringBuffer();
			try {
				CustomEmailAttribute customEmailAttribute = actionItem
						.getRouteHeader().getCustomEmailAttribute();
				if (customEmailAttribute != null) {
					RouteHeaderDTO routeHeaderVO = DTOConverter
							.convertRouteHeader(actionItem.getRouteHeader(),
									user.getPrincipalId());
					ActionRequestValue actionRequest = KEWServiceLocator
							.getActionRequestService().findByActionRequestId(
									actionItem.getActionRequestId());
					ActionRequestDTO actionRequestVO = DTOConverter
							.convertActionRequest(actionRequest);
					customEmailAttribute.setRouteHeaderVO(routeHeaderVO);
					customEmailAttribute.setActionRequestVO(actionRequestVO);
					String customBody = customEmailAttribute
							.getCustomEmailBody();
					if (!Utilities.isEmpty(customBody)) {
						emailBody.append(customBody);
					}
					String customEmailSubject = customEmailAttribute
							.getCustomEmailSubject();
					if (!Utilities.isEmpty(customEmailSubject)) {
						emailSubject.append(customEmailSubject);
					}
				}
			} catch (Exception e) {
				LOG
						.error(
								"Error when checking for custom email body and subject.",
								e);
			}
			sendEmail(user, getEmailSubject(emailSubject.toString()),
					new EmailBody(emailBody.toString()), document
							.getDocumentType());
		}

	}

	public void sendDailyReminder() {
		if (sendActionListEmailNotification()) {
			Collection<Person> users = getUsersWithEmailSetting(KEWConstants.EMAIL_RMNDR_DAY_VAL);
			for (Iterator<Person> userIter = users.iterator(); userIter.hasNext();) {
				Person user =  userIter.next();
				try {
					Collection actionItems = getActionListService()
							.getActionList(user.getPrincipalId(), null);
					if (actionItems != null && actionItems.size() > 0) {
						sendReminder(user, actionItems,
								KEWConstants.EMAIL_RMNDR_DAY_VAL);
					}
				} catch (Exception e) {
					LOG.error(
							"Error sending daily action list reminder to user: "
									+ user.getEmailAddress(), e);
				}
			}
		}
		LOG.debug("Daily action list emails sent successful");
	}

	public void sendWeeklyReminder() {
		if (sendActionListEmailNotification()) {
			Collection<Person> users = getUsersWithEmailSetting(KEWConstants.EMAIL_RMNDR_WEEK_VAL);
			for (Iterator<Person> userIter = users.iterator(); userIter.hasNext();) {
				Person user = userIter.next();
				try {
					Collection actionItems = getActionListService()
							.getActionList(user.getPrincipalId(), null);
					if (actionItems != null && actionItems.size() > 0) {
						sendReminder(user, actionItems,
								KEWConstants.EMAIL_RMNDR_WEEK_VAL);
					}
				} catch (Exception e) {
					LOG.error(
							"Error sending weekly action list reminder to user: "
									+ user.getEmailAddress(), e);
				}
			}
		}
		LOG.debug("Weekly action list emails sent successful");
	}

	private void sendReminder(Person user, Collection actionItems,
			String emailSetting) {
		String emailBody = null;
		if (KEWConstants.EMAIL_RMNDR_DAY_VAL.equals(emailSetting)) {
			emailBody = buildDailyReminderBody(user, actionItems);
		} else if (KEWConstants.EMAIL_RMNDR_WEEK_VAL.equals(emailSetting)) {
			emailBody = buildWeeklyReminderBody(user, actionItems);
		}
		sendEmail(user, getEmailSubject(), new EmailBody(emailBody));
	}

	public String buildImmediateReminderBody(Person person,
			ActionItem actionItem, DocumentType documentType) {
		String docHandlerUrl = actionItem.getRouteHeader().getDocumentType()
				.getDocHandlerUrl();
		if (docHandlerUrl.indexOf("?") == -1) {
			docHandlerUrl += "?";
		} else {
			docHandlerUrl += "&";
		}
		docHandlerUrl += KEWConstants.ROUTEHEADER_ID_PARAMETER + "="
				+ actionItem.getRouteHeaderId();
		docHandlerUrl += "&" + KEWConstants.COMMAND_PARAMETER + "="
				+ KEWConstants.ACTIONLIST_COMMAND;
		StringBuffer sf = new StringBuffer();

		sf
				.append("Your Action List has an eDoc(electronic document) that needs your attention: \n\n");
		sf.append("Document ID:\t" + actionItem.getRouteHeaderId() + "\n");
		sf.append("Initiator:\t\t");
		try {
			sf.append(actionItem.getRouteHeader().getInitiatorUser()
					.getDisplayName()
					+ "\n");
		} catch (Exception e) {
			LOG.error("Error retrieving initiator for action item "
					+ actionItem.getRouteHeaderId());
			sf.append("\n");
		}
		sf.append("Type:\t\t" + "Add/Modify "
				+ actionItem.getRouteHeader().getDocumentType().getName()
				+ "\n");
		sf.append("Title:\t\t" + actionItem.getDocTitle() + "\n");
		sf.append("\n\n");
		sf.append("To respond to this eDoc: \n");
		sf.append("\tGo to " + docHandlerUrl + "\n\n");
		sf.append("\tOr you may access the eDoc from your Action List: \n");
		sf.append("\tGo to " + getActionListUrl()
				+ ", and then click on the numeric Document ID: "
				+ actionItem.getRouteHeaderId()
				+ " in the first column of the List. \n");
		sf.append("\n\n\n");
		sf
				.append("To change how these email notifications are sent(daily, weekly or none): \n");
		sf.append("\tGo to " + getPreferencesUrl() + "\n");
		sf.append("\n\n\n");
		sf.append(getHelpLink(documentType) + "\n\n\n");

		// for debugging purposes on the immediate reminder only
		if (!isProduction()) {
			try {
				sf.append("Action Item sent to " + actionItem.getPerson().getPrincipalName());
				if (actionItem.getDelegationType() != null) {
					sf.append(" for delegation type "
							+ actionItem.getDelegationType());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return sf.toString();
	}

	public String buildDailyReminderBody(Person person,
			Collection actionItems) {
		StringBuffer sf = new StringBuffer();
		sf.append(getDailyWeeklyMessageBody(actionItems));
		sf
				.append("To change how these email notifications are sent (immediately, weekly or none): \n");
		sf.append("\tGo to " + getPreferencesUrl() + "\n");
		// sf.append("\tSend as soon as you get an eDoc\n\t" +
		// getPreferencesUrl() + "\n\n");
		// sf.append("\tSend weekly\n\t" + getPreferencesUrl() + "\n\n");
		// sf.append("\tDo not send\n\t" + getPreferencesUrl() + "\n");
		sf.append("\n\n\n");
		sf.append(getHelpLink() + "\n\n\n");
		return sf.toString();
	}

	public String buildWeeklyReminderBody(Person person,
			Collection actionItems) {
		StringBuffer sf = new StringBuffer();
		sf.append(getDailyWeeklyMessageBody(actionItems));
		sf
				.append("To change how these email notifications are sent (immediately, daily or none): \n");
		sf.append("\tGo to " + getPreferencesUrl() + "\n");
		// sf.append("\tSend as soon as you get an eDoc\n\t" +
		// getPreferencesUrl() + "\n\n");
		// sf.append("\tSend daily\n\t" + getPreferencesUrl() + "\n\n");
		// sf.append("\tDo not send\n\t" + getPreferencesUrl() + "\n");
		sf.append("\n\n\n");
		sf.append(getHelpLink() + "\n\n\n");
		return sf.toString();
	}

	protected String getDailyWeeklyMessageBody(Collection actionItems) {
		StringBuffer sf = new StringBuffer();
		HashMap docTypes = getActionListItemsStat(actionItems);

		sf
				.append("Your Action List has "
						+ actionItems.size()
						+ " eDocs(electronic documents) that need your attention: \n\n");
		Iterator iter = docTypes.keySet().iterator();
		while (iter.hasNext()) {
			String docTypeName = (String) iter.next();
			sf.append("\t" + ((Integer) docTypes.get(docTypeName)).toString()
					+ "\t" + docTypeName + "\n");
		}
		sf.append("\n\n");
		sf.append("To respond to each of these eDocs: \n");
		sf
				.append("\tGo to "
						+ getActionListUrl()
						+ ", and then click on its numeric Document ID in the first column of the List.\n");
		sf.append("\n\n\n");
		return sf.toString();
	}

	private HashMap getActionListItemsStat(Collection actionItems) {
		HashMap docTypes = new HashMap();
		Iterator iter = actionItems.iterator();

		while (iter.hasNext()) {
			String docTypeName = ((ActionItem) iter.next()).getRouteHeader()
					.getDocumentType().getName();
			if (docTypes.containsKey(docTypeName)) {
				docTypes.put(docTypeName, new Integer(((Integer) docTypes
						.get(docTypeName)).intValue() + 1));
			} else {
				docTypes.put(docTypeName, new Integer(1));
			}
		}
		return docTypes;
	}

	public UserService getUserService() {
		return (UserService) KEWServiceLocator.getUserService();
	}

	public String getDeploymentEnvironment() {
		return deploymentEnvironment;
	}

	public void setDeploymentEnvironment(String deploymentEnvironment) {
		this.deploymentEnvironment = deploymentEnvironment;
	}

}