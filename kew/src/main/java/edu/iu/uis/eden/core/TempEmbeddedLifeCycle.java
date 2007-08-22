/*
 * Copyright 2005-2007 The Kuali Foundation.
 *
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
package edu.iu.uis.eden.core;

import java.util.LinkedList;
import java.util.List;

import org.kuali.rice.core.Core;
import org.kuali.rice.lifecycle.BaseCompositeLifecycle;
import org.kuali.rice.lifecycle.Lifecycle;

import edu.iu.uis.eden.core.dependencylifecycles.SpringLifeCycle;
import edu.iu.uis.eden.core.dependencylifecycles.XmlPipelineLifeCycle;
import edu.iu.uis.eden.mail.EmailReminderLifecycle;

/**
 * A temporary lifecycle that lives in embedded space.  Will be removed when the embedded plugin is factored out.
 * @author ewestfal
 */
public class TempEmbeddedLifeCycle extends BaseCompositeLifecycle {

	/* (non-Javadoc)
	 * @see org.kuali.rice.lifecycle.BaseCompositeLifecycle#loadLifecycles()
	 */
	@Override
	protected List<Lifecycle> loadLifecycles() throws Exception {
		List<Lifecycle> lifecycles = new LinkedList<Lifecycle>();
		String springLocation = Core.getCurrentContextConfig().getAlternateSpringFile();
		if (springLocation == null) {
			springLocation = "org/kuali/workflow/resources/KewSpringBeans.xml";
		}
    	lifecycles.add(new SpringLifeCycle(springLocation));
    	lifecycles.add(new WebApplicationGlobalResourceLifecycle());
    	if (Core.getCurrentContextConfig().getRunningEmbeddedServerMode()) {
    		lifecycles.add(new XmlPipelineLifeCycle());
    		lifecycles.add(new EmailReminderLifecycle());
    	}
    	return lifecycles;
	}

}
