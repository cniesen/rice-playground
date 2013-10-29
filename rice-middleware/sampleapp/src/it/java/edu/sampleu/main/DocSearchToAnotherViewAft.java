package edu.sampleu.main;

import junit.framework.Assert;
import org.junit.Test;
import org.kuali.rice.testtools.selenium.AutomatedFunctionalTestUtils;
import org.kuali.rice.testtools.selenium.WebDriverLegacyITBase;
import org.kuali.rice.testtools.selenium.WebDriverUtil;
import org.openqa.selenium.Alert;

/**
 * test that after doc search, navigating to people flow maintenance view does not cause Javascript errors
 * and therefore interfere with JS functionality like validation
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class DocSearchToAnotherViewAft extends WebDriverLegacyITBase {

    @Override
    public void fail(String message) {
        org.junit.Assert.fail(message);
    }

    @Override
    protected String getBookmarkUrl() {
        return AutomatedFunctionalTestUtils.PORTAL + "?channelTitle=Document%20Search&channelUrl=" + WebDriverUtil.getBaseUrlString()
                + "/kew/DocumentSearch.do?docFormKey=88888888&returnLocation=" + AutomatedFunctionalTestUtils.PORTAL_URL + AutomatedFunctionalTestUtils.HIDE_RETURN_LINK;
    }

	@Test
    /**
     * test that after doc search, navigating to people flow maintenance view does not cause Javascript errors
     * and therefore interfere with JS functionality like validation
     */
	public void testDocSearchToAnotherView() throws Exception {
		waitAndClick("img[alt=\"doc search\"]");
		waitForPageToLoad();
		selectFrame("iframeportlet");
		waitAndClick("td.infoline > input[name=\"methodToCall.search\"]");
		waitForPageToLoad();
	//	selectFrame("relative=top");
		driver.switchTo().defaultContent();
		waitAndClickByLinkText("Main Menu");
        waitForPageToLoad();
		//setSpeed("2000");
		waitAndClickByLinkText("People Flow");
		waitForPageToLoad();
		selectFrame("iframeportlet");
		waitAndClickByLinkText("Create New");
		waitForPageToLoad();
		fireEvent("document.documentHeader.documentDescription", "focus");
		waitAndTypeByName("document.documentHeader.documentDescription", "sample description");
		fireEvent("document.documentHeader.explanation", "focus");
		waitAndTypeByName("document.documentHeader.explanation", "sample explanation");		
//		((JavascriptExecutor)driver).executeScript("document.getElementById(\"uif-cancel\").focus();");
		waitAndClickByLinkText("Cancel");
		Thread.sleep(5000);
		final String text = "Form has unsaved data. Do you want to leave anyway?";
		Alert a=driver.switchTo().alert();
		Assert.assertTrue(a.getText().equals(text));
		a.dismiss();
        passed();
	}
}