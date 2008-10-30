/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.rice.kim.bo.entity.impl;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.rice.kim.bo.entity.EntityEmail;
import org.kuali.rice.kim.bo.reference.EmailType;
import org.kuali.rice.kim.bo.reference.impl.EmailTypeImpl;

/**
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Entity
@Table(name = "KRIM_ENTITY_EMAIL_T")
public class EntityEmailImpl extends DefaultableEntityDataBase implements EntityEmail {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ENTITY_EMAIL_ID")
	protected String entityEmailId;

	@Column(name = "ENTITY_ID")
	protected String entityId;

	@Column(name = "ENT_TYP_CD")
	protected String entityTypeCode;

	@Column(name = "EMAIL_TYP_CD")
	protected String emailTypeCode;

	@Column(name = "EMAIL_ADDR")
	protected String emailAddress;

	@ManyToOne(targetEntity=EmailTypeImpl.class, fetch = FetchType.EAGER, cascade = {})
	@JoinColumn(name = "EMAIL_TYP_CD", insertable = false, updatable = false)
	protected EmailType emailType;

	/**
	 * @see org.kuali.rice.kim.bo.entity.EntityEmail#getEmailAddress()
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.EntityEmail#getEmailTypeCode()
	 */
	public String getEmailTypeCode() {
		return emailTypeCode;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.EntityEmail#getEntityEmailId()
	 */
	public String getEntityEmailId() {
		return entityEmailId;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.EntityEmail#setEmailAddress(java.lang.String)
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.EntityEmail#setEmailTypeCode(java.lang.String)
	 */
	public void setEmailTypeCode(String emailTypeCode) {
		this.emailTypeCode = emailTypeCode;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.DefaultableEntityTypeData#getEntityTypeCode()
	 */
	public String getEntityTypeCode() {
		return entityTypeCode;
	}

	/**
	 * @see org.kuali.rice.kim.bo.entity.DefaultableEntityTypeData#setEntityTypeCode(java.lang.String)
	 */
	public void setEntityTypeCode(String entityTypeCode) {
		this.entityTypeCode = entityTypeCode;
	}

	/**
	 * @see org.kuali.rice.kns.bo.BusinessObjectBase#toStringMapper()
	 */
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();
		m.put("entityEmailId", entityEmailId);
		m.put("entityTypeCode", entityTypeCode);
		m.put("emailTypeCode", emailTypeCode);
		return m;
	}

	public String getEntityId() {
		return this.entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setEntityEmailId(String entityEmailId) {
		this.entityEmailId = entityEmailId;
	}

	public EmailType getEmailType() {
		return this.emailType;
	}

	public void setEmailType(EmailType emailType) {
		this.emailType = emailType;
	}

}
