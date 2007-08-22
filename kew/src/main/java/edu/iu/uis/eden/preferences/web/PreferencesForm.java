/*
 * Copyright 2005-2006 The Kuali Foundation.
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
package edu.iu.uis.eden.preferences.web;

import org.apache.struts.action.ActionForm;

import edu.iu.uis.eden.preferences.Preferences;

/**
 * Struts ActionForm for {@link PreferencesAction}.
 * 
 * @see PreferencesAction
 *
 * @author rkirkend
 */
public class PreferencesForm extends ActionForm {
    
    private static final long serialVersionUID = 4536869031291955777L;
	private Preferences preferences;
    private String methodToCall = "";
    private String returnMapping;
    
    public String getReturnMapping() {
        return returnMapping;
    }
    public void setReturnMapping(String returnMapping) {
        this.returnMapping = returnMapping;
    }
    public PreferencesForm() {
        preferences = new Preferences();
    }
    public String getMethodToCall() {
        return methodToCall;
    }
    public void setMethodToCall(String methodToCall) {
        this.methodToCall = methodToCall;
    }
    public Preferences getPreferences() {
        return preferences;
    }
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}

/*
 * Copyright 2003 The Trustees of Indiana University. All rights reserved. This file is part of the EDEN software package. For license information, see the LICENSE file in the top level directory of the EDEN source distribution.
 */
