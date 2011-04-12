/*
 * Copyright 2007-2009 The Kuali Foundation
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
package org.kuali.rice.kns.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.rice.kns.datadictionary.MaintainableSectionDefinition;
import org.kuali.rice.kns.datadictionary.MaintenanceDocumentEntry;
import org.kuali.rice.kns.service.BusinessObjectSerializerService;
import org.kuali.rice.kns.service.KNSServiceLocatorWeb;
import org.kuali.rice.kns.service.MaintenanceDocumentDictionaryService;
import org.kuali.rice.kns.util.documentserializer.AlwaysTruePropertySerializibilityEvaluator;
import org.kuali.rice.kns.util.documentserializer.MaintenanceDocumentPropertySerializibilityEvaluator;
import org.kuali.rice.kns.util.documentserializer.PropertySerializabilityEvaluator;
import org.kuali.rice.kns.util.documentserializer.SerializationState;

public class BusinessObjectSerializerServiceImpl extends SerializerServiceBase implements BusinessObjectSerializerService {

    /**
     * Serializes a document for routing
     * 
     * @see org.kuali.rice.kns.service.DocumentSerializerService#serializeDocumentToXml(org.kuali.rice.kns.document.Document)
     */
    public String serializeBusinessObjectToXml(Object businessObject) {
        PropertySerializabilityEvaluator propertySerizabilityEvaluator = getPropertySerizabilityEvaluator(businessObject);
        evaluators.set(propertySerizabilityEvaluator);
        SerializationState state = new SerializationState(); //createNewDocumentSerializationState(document);
        serializationStates.set(state);
        
        //Object xmlWrapper = null;//wrapDocumentWithMetadata(document);
        String xml;
        if (propertySerizabilityEvaluator instanceof AlwaysTruePropertySerializibilityEvaluator) {
            xml = getXmlObjectSerializerService().toXml(businessObject);
        }
        else {
            xml = xstream.toXML(businessObject);
        }
        
        evaluators.set(null);
        serializationStates.set(null);
        return xml;
    }

    public PropertySerializabilityEvaluator getPropertySerizabilityEvaluator(Object businessObject) {
        PropertySerializabilityEvaluator evaluator = null;
        
        MaintenanceDocumentDictionaryService maintenanceDocumentDictionaryService = 
        	KNSServiceLocatorWeb.getMaintenanceDocumentDictionaryService();
        String docTypeName = maintenanceDocumentDictionaryService.getDocumentTypeName(businessObject.getClass());
        MaintenanceDocumentEntry maintenanceDocumentEntry = maintenanceDocumentDictionaryService.getMaintenanceDocumentEntry(docTypeName);
        List<MaintainableSectionDefinition> maintainableSectionDefinitions = maintenanceDocumentEntry.getMaintainableSections();
        if(CollectionUtils.isEmpty(maintainableSectionDefinitions)) {
            evaluator = new AlwaysTruePropertySerializibilityEvaluator();
        }
        else {
            evaluator = new MaintenanceDocumentPropertySerializibilityEvaluator();
            evaluator.initializeEvaluator(businessObject);
        }
        
        return evaluator;
    }
}
