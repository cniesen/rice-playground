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
package edu.iu.uis.eden.routetemplate;

import java.util.List;

/**
 * Result of a {@link RuleExpression} evaluation 
 * @author Aaron Hamid (arh14 at cornell dot edu)
 */
public class RuleExpressionResult {
    /**
     * Whether the expression succeeded
     */
    private final boolean success;
    /**
     * Any responsibilities generated from a successful evaluation
     */
    private final List<RuleResponsibility> responsibilities;

    /**
     * Constructs a rule expression result with a success indicator but no responsibilities 
     * @param success whether the expression succeeded
     */
    public RuleExpressionResult(boolean success) {
        this.success = success;
        this.responsibilities = null;
    }

    /**
     * Constructs a rule expression result with both a success indicator and a list of responsibilities
     * @param success whether the expression succeeded
     * @param responsibilities any responsibilities generated from a successful evaluation
     */
    public RuleExpressionResult(boolean success, List<RuleResponsibility> responsibilities) {
        this.success = success;
        this.responsibilities = responsibilities;
    }

    /**
     * @return whether the evaluation was successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @return any responsibilities generated from a successful evaluation
     */
    public List<RuleResponsibility> getResponsibilities() {
        return responsibilities;
    }
}