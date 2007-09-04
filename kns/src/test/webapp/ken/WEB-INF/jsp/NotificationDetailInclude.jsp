   <ul>
		<div><strong>From: </strong>
		<c:set var="sender_counter" value="0" />
		<c:forEach var="sender" items="${senders}">
			<c:if test="${sender_counter > 0}">, <c:out value="${sender.senderName}"/></c:if>
			<c:if test="${sender_counter == 0}"><c:out value="${sender.senderName}"/></c:if>
			<c:set var="sender_counter" value="${sender_counter + 1}" />
		</c:forEach></div>
		<div><strong>Recipients: </strong>
		<c:set var="recip_counter" value="0" />
		<c:forEach var="recipient" items="${recipients}">
			<c:if test="${recip_counter > 0}">
				, <c:out value="${recipient.recipientId}" />
			</c:if>
			<c:if test="${recip_counter == 0}">
				<c:out value="${recipient.recipientId}" />
			</c:if>
			<c:set var="recip_counter" value="${recip_counter + 1}" />
		</c:forEach>
		</div>
		<div><strong>Channel: </strong><c:out
			value="${notification.channel.name}" default="none" /></div>
		<div><strong>Producer: </strong><c:out
			value="${notification.producer.name}" default="none" /></div>
		<div><strong>Type: </strong><c:out
			value="${notification.deliveryType}" default="none" /></div>
		<div><strong>Priority: </strong><c:out
			value="${notification.priority.name}" default="none" /></div>
               <div><strong>Send Date: </strong><c:out
                   value="${notification.sendDateTime}" default="none" /></div>
               <div><strong>Removal Date: </strong><c:out
                   value="${notification.autoRemoveDateTime}" default="none" /></div>
		<br />
		<br />
        <div><strong>Title: </strong><c:out value="${notification.title}" escapeXml="false" /></div>
		<div><strong>Content:</strong></div>
		<div style="padding: 3px; border-style: double;">
			<c:out value="${contenthtml}" escapeXml="false" />
		</div>
		<div style="padding: 3px; align=center;">
        <%-- first test whether this notification is actionable by the current user --%>
        <c:if test="${actionable}">
            <c:if test="${notification.deliveryType == 'ACK' && ! empty docId}">
             
    			   <c:if test="${empty ackmessage}">
    			   <a href="<c:url value='AckNotification.form'>
                              <c:param name='docId' value='${docId}'/>
                              <c:param name='notifId' value='${notification.id}'/>
                              <c:param name='command' value='${command}'/>
                              <c:param name='standaloneWindow' value='${standaloneWindow}'/>
                              </c:url>"><img
    					src="images/buttonsmall_acknowledge.gif" border="0"
    					alt="acknowledge" /></a>
    			   </c:if>
    			   <c:if test="${! empty ackmessage}">
    				<strong><c:out value="${ackmessage}" /></strong>
    			   </c:if>
    		 </c:if>
             
             <c:if test="${notification.deliveryType == 'FYI' && ! empty docId}">
             
                <c:if test="${empty fyimessage}">
                <a href="<c:url value='FyiNotification.form'>
                              <c:param name='docId' value='${docId}'/>
                              <c:param name='notifId' value='${notification.id}'/>
                              <c:param name='command' value='${command}'/>
                              <c:param name='standaloneWindow' value='${standaloneWindow}'/>
                              </c:url>"><img
                   src="images/buttonsmall_fyi.gif" border="0"
                   alt="fyi" /></a>
                </c:if>
                <c:if test="${! empty fyimessage}">
                <strong><c:out value="${fyumessage}" /></strong>
                </c:if>
             </c:if>
         </c:if>
         
         <c:if test="${standaloneWindow eq 'true'}">
         	<a href="javascript:self.close()">
	         	<img src="images/buttonsmall_close.gif" border="0" alt="close" />
            </a>
         </c:if>
        </div>
	</ul>