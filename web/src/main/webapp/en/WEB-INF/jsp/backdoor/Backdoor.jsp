<%@ taglib uri="../../tld/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="../../tld/struts-bean-el.tld" prefix="bean-el" %>
<%@ taglib uri="../../tld/struts-logic-el.tld" prefix="logic-el"%>
<%@ taglib uri="../../tld/c.tld" prefix="c" %>
<%@ taglib uri="../../tld/fmt.tld" prefix="fmt" %>
<%@ taglib uri="../../tld/displaytag.tld" prefix="display-el" %>

<html>
<head>
<title>Workflow Backdoor</title>
<link href="css/screen.css" rel="stylesheet" type="text/css">

</head>
<c:if test="${BackdoorForm.backdoorLinksBackdoorLogin}">
<table width="100%" border=0 cellpadding=0 cellspacing=0 class="headercell1">
  <tr>
    <td><img src="images/wf-logo.gif" alt="Workflow" width=150 height=21 hspace=5 vspace=5></td>
  </tr>
</table>
</c:if>
<br>


<body>
<jsp:include page="../WorkflowMessages.jsp" flush="true" />
<c:if test="${BackdoorForm.backdoorLinksBackdoorLogin && BackdoorForm.showBackdoorLogin}">
 	<html-el:form action="Backdoor.do">

      	<table>
            <tr><td>
            	<html-el:hidden property="targetName"/>
            	<html-el:hidden property="backdoorLinksBackdoorLogin"/>
				&nbsp;&nbsp;&nbsp;&nbsp;Backdoor Id: &nbsp;&nbsp;<html-el:text property="backdoorId" />&nbsp;&nbsp;<html-el:image src="images/tinybutton-login.gif" align="absmiddle" property="methodToCall.login" tabindex="1"/>&nbsp;&nbsp;
	            <html-el:image src="images/tinybutton-logout.gif" align="absmiddle" property="methodToCall.logout" tabindex="2"/>
				</td>
            </tr>

        </table>

    </html-el:form>
    <br>
</c:if>
<table>
	<tr>
		<td><a href="ActionList.do" target="<c:out value="${BackdoorForm.targetName}" />">Action List</a></td>
	</tr>
	<tr>
		<td><a href="DocumentSearch.do" target="<c:out value="${BackdoorForm.targetName}" />">Document Search</a></td>
	</tr>
	<tr>
		<td><a href="Lookup.do?lookupableImplServiceName=WorkGroupLookupableImplService" target="<c:out value="${BackdoorForm.targetName}" />">Workgroup</a></td>
	</tr>
	<tr>
		<td><a href="Lookup.do?lookupableImplServiceName=RuleBaseValuesLookupableImplService" target="<c:out value="${BackdoorForm.targetName}" />">Rules</a></td>
	</tr>
	<tr>
		<td><a href="Lookup.do?lookupableImplServiceName=UserLookupableImplService" target="<c:out value="${BackdoorForm.targetName}" />">User Lookup</a></td>
	</tr>
	<tr>
	    <td><a href="RemoveReplace.do" target="<c:out value="${BackdoorForm.targetName}" />">Remove/Replace User</a></td>
	</tr>

	<tr>
		<td><a href="RoutingReport.do" target="<c:out value="${BackdoorForm.targetName}" />">Routing Report</a></td>
	</tr>
	<tr>
		<td><a href="QuickLinks.do" target="<c:out value="${BackdoorForm.targetName}" />">Workflow QuickLinks</a></td>
	</tr>
	<tr>
		<td><a href="RuleQuickLinks.do" target="<c:out value="${BackdoorForm.targetName}" />">Rule QuickLinks</a></td>
	</tr>

	<tr>
		<td><a href="Lookup.do?lookupableImplServiceName=EDocLiteLookupableService" target="<c:out value="${BackdoorForm.targetName}" />">EDocLites <b>OLD</b></a></td>
	</tr>
	<tr>
		<td><a href="../kr/lookup.do?businessObjectClassName=org.kuali.rice.kew.edl.bo.EDocLiteAssociation&docFormKey=88888888&returnLocation=../en/Portal.do&hideReturnLink=true" target="<c:out value="${BackdoorForm.targetName}" />">EDocLites <b>NEW</b></a></td>	
	</tr>
	<tr>
	  <td><a href="../kew/Rule.do?methodToCall=docHandler&command=initiate&docTypeName=EDENSERVICE-DOCS.RuleDocument" target="<c:out value="${BackdoorForm.targetName}"/>">Create Rule <b>NEW</b></a></td>
	</tr>
</table>

<c:if test="${BackdoorForm.backdoorLinksBackdoorLogin}">
	<jsp:include page="../BackdoorMessage.jsp" flush="true"/>
</c:if>

</body>
</html>