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
package org.kuali.rice.kew.quicklinks;

import java.util.List;

import org.kuali.rice.kew.quicklinks.dao.QuickLinksDAO;
import org.kuali.rice.kew.user.WorkflowUser;


public class QuickLinksServiceImpl implements QuickLinksService {

    private QuickLinksDAO quickLinksDAO;
    
    public List getActionListStats(WorkflowUser workflowUser) {
        return getQuickLinksDAO().getActionListStats(workflowUser);
    }

    public List getInitiatedDocumentTypesList(WorkflowUser workflowUser) {
        return getQuickLinksDAO().getInitiatedDocumentTypesList(workflowUser);
    }

    public List getNamedSearches(WorkflowUser workflowUser) {
        return getQuickLinksDAO().getNamedSearches(workflowUser);
    }

    public List getRecentSearches(WorkflowUser workflowUser) {
        return getQuickLinksDAO().getRecentSearches(workflowUser);
    }

    public List getWatchedDocuments(WorkflowUser workflowUser) {
        return getQuickLinksDAO().getWatchedDocuments(workflowUser);
    }

    // BELOW ARE SPRING MANAGED PROPERTIES OF THIS BEAN
    public QuickLinksDAO getQuickLinksDAO() {
        return quickLinksDAO;
    }
    public void setQuickLinksDAO(QuickLinksDAO quickLinksDAO) {
        this.quickLinksDAO = quickLinksDAO;
    }

}