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
package org.kuali.notification.util;

import java.io.IOException;
import java.io.StringReader;

import org.kuali.notification.bo.NotificationContentType;
import org.kuali.notification.service.NotificationContentTypeService;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Entity resolver that resolves content type XSD resources to XSDs define in
 * the respective content type's NotificationContentType record
 * @author Aaron Hamid (arh14 at cornell dot edu)
 */
public class ContentTypeEntityResolver extends ContentTypeResourceResolver implements EntityResolver {
    /**
     * Constructs a ContentTypeEntityResolver.java.
     * @param notificationContentTypeService
     */
    public ContentTypeEntityResolver(NotificationContentTypeService notificationContentTypeService) {
        super(notificationContentTypeService);
    }

    /**
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        LOG.debug("Resolving '" + publicId + "' / '" + systemId + "'");
        if (!systemId.startsWith(CONTENT_TYPE_PREFIX)) {
            LOG.warn("Cannot resolve non-ContentType resources");
            return null;
        }
        NotificationContentType notificationContentType = resolveContentType(systemId);
        if (notificationContentType == null) {
            LOG.error("Unable to resolve system id '" + systemId + "' locally...delegating to default resolution strategy.");
            return null;
        }
        LOG.debug("Resolved '" + systemId + "' to " + notificationContentType.getXsd());
        return new InputSource(new StringReader(notificationContentType.getXsd()));
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[ContentTypeEntityResolver]";
    }
}