<%@ include file="/kr/WEB-INF/jsp/tldHeader.jsp"%>

<c:set var="docNameAttributes" value="${DataDictionary.PersonDocumentName.attributes}" />
<kul:subtab lookedUpCollectionName="name" width="${tableWidth}" subTabTitle="Names">      
        <table cellpadding=0 cellspacing=0 summary="">
          	<tr>
          		<th><div align="left">&nbsp</div></th> 
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.nameTypeCode}" noColon="true" /></div></th>
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.title}" noColon="true" /></div></th>
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.firstName}" noColon="true" /></div></th>
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.lastName}" noColon="true" /></div></th>
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.suffix}" noColon="true" /></div></th>
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.dflt}" noColon="true" /></div></th>
          		<th><div align="center"><kul:htmlAttributeLabel attributeEntry="${docNameAttributes.active}" noColon="true" /></div></th>
           <c:if test="${not inquiry}">	
              	<kul:htmlAttributeHeaderCell literalLabel="Actions" scope="col"/>
          	</c:if>
          	</tr>     
          <c:if test="${not inquiry}">	
          	
             <tr>
				<th class="infoline">
					<c:out value="Add:" />
				</th>

                <td align="left" valign="middle" class="infoline">
                <div align="center">
                	<kul:htmlControlAttribute property="newName.nameTypeCode" attributeEntry="${docNameAttributes.nameTypeCode}"/>
	            </div>
				</td>
                <td class="infoline">
                <div align="center">
                	<kul:htmlControlAttribute property="newName.title" attributeEntry="${docNameAttributes.title}" />
                <div align="center">
                </td>
                <td class="infoline">   
                <div align="center">             	
                  <kul:htmlControlAttribute property="newName.firstName" attributeEntry="${docNameAttributes.firstName}" />
				</div>
				</td>
                
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="newName.lastName" attributeEntry="${docNameAttributes.lastName}"/>
                </div>
                </td>
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="newName.suffix" attributeEntry="${docNameAttributes.suffix}"/>
                </div>
                </td>
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="newName.dflt" attributeEntry="${docNameAttributes.dflt}"/>
                </div>
                </td>
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="newName.active" attributeEntry="${docNameAttributes.active}"/>
                </div>
                </td>
                
                
                <td class="infoline">
					<div align=center>
						<html:image property="methodToCall.addName.anchor${tabKey}"
						src='${ConfigProperties.kr.externalizable.images.url}tinybutton-add1.gif' styleClass="tinybutton"/>
					</div>
                </td>
       </tr>         
      </c:if>      
        	<c:forEach var="name" items="${KualiForm.document.names}" varStatus="status">
	             <tr>
					<th class="infoline">
						<c:out value="${status.index+1}" />
					</th>
	                <td align="left" valign="middle">
	                	<div align="center"> <kul:htmlControlAttribute property="document.names[${status.index}].nameTypeCode"  attributeEntry="${docNameAttributes.nameTypeCode}"  />
					</div>
					</td>
	                <td>
	                <div align="center"> <kul:htmlControlAttribute property="document.names[${status.index}].title" attributeEntry="${docNameAttributes.title}" />
	                </div>
	                </td>
	                <td>     
	                <div align="center">           	
	                  <kul:htmlControlAttribute property="document.names[${status.index}].firstName" attributeEntry="${docNameAttributes.firstName}" />
					</div>
					</td>

				<td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="document.names[${status.index}].lastName" attributeEntry="${docNameAttributes.lastName}"/>
                </div>
                </td>
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="document.names[${status.index}].suffix" attributeEntry="${docNameAttributes.suffix}"/>
                </div>
                </td>
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="document.names[${status.index}].dflt" attributeEntry="${docNameAttributes.dflt}"/>
                </div>
                </td>
                <td align="left" valign="middle" class="infoline">
                	<div align="center"><kul:htmlControlAttribute property="document.names[${status.index}].active" attributeEntry="${docNameAttributes.active}"/>
                </div>
                </td>
           <c:if test="${not inquiry}">	
					
					<td>
					<div align=center>&nbsp;
	        	     <c:choose>
	        	       <c:when test="${name.edit}">
	        	          <img class='nobord' src='${ConfigProperties.kr.externalizable.images.url}tinybutton-delete2.gif' styleClass='tinybutton'/>
	        	       </c:when>
	        	       <c:otherwise>
	        	          <html:image property='methodToCall.deleteName.line${status.index}.anchor${currentTabIndex}'
								src='${ConfigProperties.kr.externalizable.images.url}tinybutton-delete1.gif' styleClass='tinybutton'/>
	        	       </c:otherwise>
	        	     </c:choose>  
					</div>
	                </td>
	            </c:if>    
	            </tr>
        	</c:forEach>        

            
        </table>
</kul:subtab>
