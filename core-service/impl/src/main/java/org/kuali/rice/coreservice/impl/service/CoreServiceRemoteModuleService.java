/*
 * Copyright 2006-2012 The Kuali Foundation
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

package org.kuali.rice.coreservice.impl.service;

import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.service.impl.RemoteModuleServiceBase;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CoreServiceRemoteModuleService extends RemoteModuleServiceBase {

    @Override
    public <T extends ExternalizableBusinessObject> T getExternalizableBusinessObject(Class<T> businessObjectClass,
            Map<String, Object> fieldValues) {
        return null;  
    }

    @Override
    public <T extends ExternalizableBusinessObject> List<T> getExternalizableBusinessObjectsList(
            Class<T> businessObjectClass, Map<String, Object> fieldValues) {
        return Collections.emptyList();
    }

    @Override
    public boolean isExternalizableBusinessObjectLookupable(Class boClass) {
        return false;
    }

    @Override
    public boolean isExternalizableBusinessObjectInquirable(Class boClass) {
        return false;
    }
}
