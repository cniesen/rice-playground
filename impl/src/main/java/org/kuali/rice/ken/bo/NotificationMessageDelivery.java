/*
 * Copyright 2007-2008 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.ken.bo;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class represents and instance of a NotificationMessageDelivery.  A Notification gets delivered to 
 * recipients, possibly in various ways.  For each delivery type that a recipient gets sent to them, 
 * they have an instance of this entity.
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
@Entity
@Table(name="KREN_NTFCTN_MSG_DELIV_T")
public class NotificationMessageDelivery implements Lockable {
    @Id
	@Column(name="NTFCTN_MSG_DELIV_ID")
	private Long id;
    @Column(name="STAT_CD", nullable=false)
	private String messageDeliveryStatus;
    @Column(name="RECIP_ID", nullable=false)
	private String userRecipientId;
    @Column(name="SYS_ID", nullable=true)
	private String deliverySystemId;  // can hold an identifier from the endpoint delivery mechanism system (i.e. workflow id, SMS id, etc)
	@Column(name="LOCKD_DTTM", nullable=true)
	private Timestamp lockedDate;

    /**
     * Lock column for OJB optimistic locking
     */
    @Version
	@Column(name="VER_NBR")
	private Integer lockVerNbr;
    
    @OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
	@JoinColumn(name="NTFCTN_ID")
	private Notification notification;

    /**
     * Constructs a NotificationMessageDelivery instance.
     */
    public NotificationMessageDelivery() {
    }
    
    /**
     * Gets the id attribute. 
     * @return Returns the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id attribute value.
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Return value of lock column for OJB optimistic locking
     * @return value of lock column for OJB optimistic locking
     */
    public Integer getLockVerNbr() {
        return lockVerNbr;
    }

    /**
     * Set value of lock column for OJB optimistic locking
     * @param lockVerNbr value of lock column for OJB optimistic locking
     */
    public void setLockVerNbr(Integer lockVerNbr) {
        this.lockVerNbr = lockVerNbr;
    }

    /**
     * Gets the messageDeliveryStatus attribute. 
     * @return Returns the messageDeliveryStatus.
     */
    public String getMessageDeliveryStatus() {
        return messageDeliveryStatus;
    }

    /**
     * Sets the messageDeliveryStatus attribute value.
     * @param messageDeliveryStatus The messageDeliveryStatus to set.
     */
    public void setMessageDeliveryStatus(String deliveryStatus) {
        this.messageDeliveryStatus = deliveryStatus;
    }

    /**
     * Gets the userRecipientId attribute. 
     * @return Returns the userRecipientId.
     */
    public String getUserRecipientId() {
        return userRecipientId;
    }

    /**
     * Sets the userRecipientId attribute value.
     * @param userRecipientId The userRecipientId to set.
     */
    public void setUserRecipientId(String userRecipientId) {
        this.userRecipientId = userRecipientId;
    }

    /**
     * Gets the lockedDate attribute. 
     * @return Returns the lockedDate.
     */
    public Timestamp getLockedDate() {
        return lockedDate;
    }
    
    /**
     * Sets the lockedDate attribute value.
     * @param lockedDate The lockedDate to set.
     */
    public void setLockedDate(Timestamp lockedDate) {
        this.lockedDate = lockedDate;
    }

    /**
     * Gets the notification attribute. 
     * @return Returns the notification.
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * Sets the notification attribute value.
     * @param notification The notification to set.
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                       .append("id", id)
                       .append("messageDeliveryStatus", messageDeliveryStatus)
                       .append("userRecipientId", userRecipientId)
                       .append("deliverySystemId", deliverySystemId)
                       .append("lockedDate", lockedDate)
                       .append("notification", notification)
                       .toString();
    }

    /**
     * Gets the deliverySystemId attribute. 
     * @return Returns the deliverySystemId.
     */
    public String getDeliverySystemId() {
        return deliverySystemId;
    }

    /**
     * Sets the deliverySystemId attribute value.
     * @param deliverySystemId The deliverySystemId to set.
     */
    public void setDeliverySystemId(String deliverySystemId) {
        this.deliverySystemId = deliverySystemId;
    }
}