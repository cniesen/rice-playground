/**
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.rice.krad.demo.uif.library.clientresponsiveness;

import org.junit.Test;
import org.kuali.rice.testtools.selenium.WebDriverLegacyITBase;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class DemoClientResponsivenessProgressiveDisclosureAft extends WebDriverLegacyITBase {

    /**
     * /kr-krad/kradsampleapp?viewId=Demo-ProgressiveDisclosureView&methodToCall=start
     */
    public static final String BOOKMARK_URL = "/kr-krad/kradsampleapp?viewId=Demo-ProgressiveDisclosureView&methodToCall=start";

    @Override
    protected String getBookmarkUrl() {
        return BOOKMARK_URL;
    }
    
    /**
     * //div[@data-parent='Demo-ProgressiveDisclosure-Example9']/div[@data-role='disclosureContent']
     */
    private static final String CWGR_GENERIC_XPATH= "//div[@data-parent='Demo-ProgressiveDisclosure-Example9']/div[@data-role='disclosureContent']";

    @Override
    protected void navigate() throws Exception {
        waitAndClickById("Demo-LibraryLink", "");
        waitAndClickByLinkText("Client Responsiveness");
        waitAndClickByLinkText("Progressive Disclosure");
    }

    protected void testClientResponsivenessProgressiveDisclosure() throws Exception {
         //Scenario-1
        waitAndClickByLinkText("Default");
        assertIsNotVisibleByXpath("//input[@name='inputField1']", "Is Visible");
        waitAndClickByName("booleanField1");
        assertIsVisibleByXpath("//input[@name='inputField1']","Not Visible");
    }
    
    protected void testClientResponsivenessProgressiveDisclosureAjaxRetrieval() throws Exception {
        waitAndClickByLinkText("Ajax Retrieval");
        checkForIncidentReport("DemoClientResponsivenessProgressiveDisclosureAft Ajax Retrieval");
        assertIsNotVisibleByXpath("//input[@name='inputField18']", "element");
        waitAndClickByName("booleanField2");
        Thread.sleep(2000);
        assertIsVisibleByXpath("//input[@name='inputField18']", "element");
    }
    
    protected void testClientResponsivenessProgressiveDisclosureRefreshWhenShown() throws Exception {
        waitAndClickByLinkText("Refresh when Shown");
        assertIsNotVisibleByXpath("//input[@name='inputField5']", "element");
        waitAndClickByName("booleanField3");
        Thread.sleep(2000);
        assertIsVisibleByXpath("//input[@name='inputField5']", "element");
    }
    
    protected void testClientResonsivenessProgressiveDisclosureShowFieldThroughMatching() throws Exception {
    	waitAndClickByLinkText("Show Field Through Matching");
//    	assertElementPresentByXpath("//input[@name='inputField7' and @disabled]");
//    	assertElementPresentByXpath("//input[@name='inputField8' and @disabled]");
    	waitAndTypeByName("inputField6","A");
    	waitAndClickByLinkText("Documentation");
    	waitForElementPresentByXpath("//input[@name='inputField7']");
    	waitAndTypeByName("inputField6","B");
    	waitAndClickByLinkText("Documentation");
    	waitForElementPresentByXpath("//input[@name='inputField8']");
    }
    
    protected void testClientResonsivenessProgressiveDisclosureofGroup() throws Exception {
    	waitAndClickByLinkText("Progressive Disclosure of Groups");
    	waitForElementPresentByXpath("//input[@name='inputField10' and @disabled]");
    	waitForElementPresentByXpath("//input[@name='inputField11' and @disabled]");
    	waitForElementPresentByXpath("//input[@name='inputField12' and @disabled]");
    	waitAndClickByXpath("//input[@name='inputField9' and @value='show1']");
    	waitForElementPresentByXpath("//input[@name='inputField10']");
    	waitForElementPresentByXpath("//input[@name='inputField11']");
    	waitForElementPresentByXpath("//input[@name='inputField12']");
    	waitAndClickByXpath("//input[@name='inputField9' and @value='show2']");
    	waitForTextNotPresent("Loading...");
    	waitForElementPresentByXpath("//input[@name='inputField13']");
    	waitForElementPresentByXpath("//input[@name='inputField14']");
    }
    
    protected void testClientResonsivenessProgressiveDisclosureConditionalRefresh() throws Exception {
    	waitAndClickByXpath("//li[@data-tabfor='Demo-ProgressiveDisclosure-Example6']/a[contains(text(),'Conditional Refresh')]");
    	waitAndClickByXpath("//input[@name='inputField15' and @value='show1']");
    	waitForTextPresent("Loading...");
    	waitAndTypeByName("inputField16","Hello World!");
    	waitAndTypeByName("inputField17","Hello Deep!");
    	waitAndClickByXpath("//input[@name='inputField15' and @value='show2']");
        waitForTextNotPresent("Loading...");
    	waitForTextPresent("Hello Deep!");
    }
    
    protected void testClientResonsivenessProgressiveDisclosureConditionalOptions() throws Exception {
    	waitAndClickByLinkText("Conditional Options");
    	selectByName("inputField19","Apples");
    	waitAndClickButtonByText("Refresh Group");
        waitForTextNotPresent("Loading...");
    	Thread.sleep(10000);		
    	selectByName("inputField4","Vegetables");
    	// Test page gives exception after this step.
        waitAndClickButtonByText("Refresh Field");
        waitForTextNotPresent("Loading...");
        Thread.sleep(10000);
        waitAndClickButtonByText("Refresh Field but with Server Errors");
        waitForTextPresent("Field 1: Intended message with key: serverTestError not found. [+1 warning] [+1 message]");
        waitAndClickButtonByText("Refresh Page");
        waitForTextNotPresent("Field 1: Intended message with key: serverTestError not found. [+1 warning] [+1 message]");
    }
    
    protected void testClientResonsivenessProgressiveDisclosureRefreshBasedOnTimer() throws Exception {
    	waitAndClickByLinkText("Refresh Based on Timer");
    	//There are no component to perform test on the page.
        checkForIncidentReport();
    }
    
    protected void testClientResonsivenessProgressiveDisclosureCollectionWithGroupRefresh() throws Exception {
    	waitAndClickByLinkText("Collection Group With Refresh");
    	waitAndTypeByXpath(CWGR_GENERIC_XPATH+"/div/table/tbody/tr[2]/td/div/input","ref");
        fireEvent("focus", "collection1[0].field1");
        waitForTextNotPresent("Loading...");
    	//Test cannot be written ahead as there is a freemarker error in page
        checkForIncidentReport();
    }
    
    @Test
    public void testClientResponsivenessProgressiveDisclosureBookmark() throws Exception {
    	testClientResponsivenessProgressiveDisclosureAll();
        passed();
    }

    @Test
    public void testClientResponsivenessProgressiveDisclosureNav() throws Exception {
    	testClientResponsivenessProgressiveDisclosureAll();
        passed();
    }

    @Test
    public void testClientResponsivenessProgressiveDisclosureConditionalOptionsBookmark() throws Exception {
        testClientResonsivenessProgressiveDisclosureConditionalOptions();
        passed();
    }

    @Test
    public void testClientResponsivenessProgressiveDisclosureConditionalOptionsNav() throws Exception {
        testClientResonsivenessProgressiveDisclosureConditionalOptions();
        passed();
    }

    @Test
    public void testClientResponsivenessProgressiveDisclosureCollectionWithGroupRefreshBookmark() throws Exception {
        testClientResonsivenessProgressiveDisclosureCollectionWithGroupRefresh();
        passed();
    }

    @Test
    public void testClientResponsivenessProgressiveDisclosureCollectionWithGroupRefreshNav() throws Exception {
        testClientResonsivenessProgressiveDisclosureCollectionWithGroupRefresh();
        passed();
    }

    private void testClientResponsivenessProgressiveDisclosureAll() throws Exception {
    	testClientResponsivenessProgressiveDisclosureAjaxRetrieval();
        testClientResponsivenessProgressiveDisclosureRefreshWhenShown();
        testClientResponsivenessProgressiveDisclosure();
        testClientResonsivenessProgressiveDisclosureShowFieldThroughMatching();
        testClientResonsivenessProgressiveDisclosureofGroup();
        testClientResonsivenessProgressiveDisclosureConditionalRefresh();
        testClientResonsivenessProgressiveDisclosureRefreshBasedOnTimer();
//        testClientResonsivenessProgressiveDisclosureCollectionWithGroupRefresh();
//        testClientResonsivenessProgressiveDisclosureConditionalOptions();
    }
}
