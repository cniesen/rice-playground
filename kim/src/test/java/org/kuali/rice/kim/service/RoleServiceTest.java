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
package org.kuali.rice.kim.service;

import org.kuali.rice.kim.test.KIMTestCase;
import org.kuali.rice.resourceloader.GlobalResourceLoader;

/**
 * Basic test to verify we can access the QualifiedRoleService through the GRL. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class RoleServiceTest extends KIMTestCase {
    private RoleService roleService;

    public void setUp() throws Exception {
        super.setUp();
        roleService = (RoleService) GlobalResourceLoader.getService("roleService");
    }
}
