package tests;

import general.MobileDriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.MapsPage;

public class MapsPageTest extends MobileDriverManager {

    private MapsPage mapsPage;

    @Test
    public void verifyMapPageLoads() {
        mapsPage = new MapsPage(getDriver());
        Assert.assertTrue(mapsPage.verifyLoads(), "[ERROR]    Maps Screen does not loaded");
        Assert.assertTrue(mapsPage.clickInSearchTextField(), "[ERROR]    The field was not clicked");
        Assert.assertTrue(mapsPage.typeSomeText(), "[ERROR]    The field was not filled");
    }

}
