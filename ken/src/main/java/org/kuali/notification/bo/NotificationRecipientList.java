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
package org.kuali.notification.bo;

/**
 * This class represents the data structure that will house a default recipient list for a notification channel.
 * @author Aaron Godert (ag266 at cornell dot edu)
 */
public class NotificationRecipientList {
    private Long id;
    private String recipientType;
    private String recipientId;
    
    private NotificationChannel channel;
    
    /**
     * Constructs a NotificationRecipientList.java instance.
     */
    public NotificationRecipientList() {
    }

    /**
     * Gets the channel attribute. 
     * @return Returns the channel.
     */
    public NotificationChannel getChannel() {
        return channel;
    }


    /**
     * Sets the channel attribute value.
     * @param channel The channel to set.
     */
    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
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
     * Gets the recipientId attribute. 
     * @return Returns the recipientId.
     */
    public String getRecipientId() {
        return recipientId;
    }

    /**
     * Sets the recipientId attribute value.
     * @param recipientId The recipientId to set.
     */
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    /**
     * Gets the recipientType attribute. 
     * @return Returns the recipientType.
     */
    public String getRecipientType() {
        return recipientType;
    }

    /**
     * Sets the recipientType attribute value.
     * @param recipientType The recipientType to set.
     */
    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
}
