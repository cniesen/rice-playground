/*
 * Copyright 2007 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.kuali.rice.test.runners;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.kuali.rice.test.lifecycles.PerTestDataLoaderLifecycle;

/**
 * A Runner which sets up Rice unit tests appropriately. 
 * 1) It invokes setName() on the Test (if the method exists) and sets it to the name of the test method being invoked. 
 * 2) It reads the test data annotations and bootstraps the database for test runs.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 * @since 0.9
 */
public class RiceUnitTestClassRunner extends JUnit4ClassRunner {

    private PerTestDataLoaderLifecycle perTestDataLoaderLifecycle;
    private Method currentMethod;
    
    public RiceUnitTestClassRunner(final Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected void invokeTestMethod(Method method, RunNotifier runNotifier) {
        this.currentMethod = method;
        try {
            perTestDataLoaderLifecycle = new PerTestDataLoaderLifecycle(method);
            super.invokeTestMethod(method, runNotifier);
        } finally {
            this.currentMethod = null;
        }
    }

    @Override
    protected Object createTest() throws Exception {
        Object test = super.createTest();
        setTestName(test, currentMethod);
        setTestPerTestDataLoaderLifecycle(test);
        return test;
    }

    protected void setTestPerTestDataLoaderLifecycle(final Object test) {
        try {
            final Method setPerTestDataLoaderLifecycle = MethodUtils.getAccessibleMethod(test.getClass(), "setPerTestDataLoaderLifecycle", new Class[]{PerTestDataLoaderLifecycle.class});
            setPerTestDataLoaderLifecycle.invoke(test, new Object[]{perTestDataLoaderLifecycle});
        } catch (final Exception e) {
            // no setPerTestDataLoaderLifecycle method or we failed to invoke it so we can't set the lifecycle
        }
    }

    protected void setTestName(final Object test, final Method testMethod) {
        try {
            String name = testMethod == null ? "" : testMethod.getName();
            final Method setNameMethod = MethodUtils.getAccessibleMethod(test.getClass(), "setName", new Class[]{String.class});
            if (setNameMethod != null) {
                setNameMethod.invoke(test, new Object[]{name});
            }
        } catch (final Exception e) {
            // no setName method or we failed to invoke it so we can't set the name
        }
    }

}
