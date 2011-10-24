/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.rice.krms.impl.validation;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.framework.engine.Action;
import org.kuali.rice.krms.framework.engine.BasicRule;
import org.kuali.rice.krms.framework.engine.Proposition;
import org.kuali.rice.krms.framework.type.ValidationRuleType;

import java.util.List;

/**
 *
 * A {@link org.kuali.rice.krms.framework.engine.Rule} that executes a {@link org.kuali.rice.krms.framework.engine.Action} when the {@link Proposition} is false,
 * as opposed to {@link BasicRule} which executes its action when the proposition is true.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class ValidationRule extends BasicRule {
    private ValidationRuleType type = null;

    public ValidationRule(ValidationRuleType type, String name, Proposition proposition, List<Action> actions) {
        super(name, proposition, actions);
        if (type == null) throw new IllegalArgumentException("type must not be null");
        this.type = type;
    }

    @Override
    protected boolean shouldExecuteAction(boolean ruleExecutionResult) {
        if (type == null || type.equals(ValidationRuleType.VALID)) {
            return !ruleExecutionResult;
        }
        return ruleExecutionResult;
    }
}
