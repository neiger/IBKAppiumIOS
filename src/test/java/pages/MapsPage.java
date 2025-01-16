package pages;

import general.BasePage;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MapsPage extends BasePage {

    @iOSXCUITFindBy(xpath="//XCUIElementTypeSearchField[@name=\"MapsSearchTextField\"]")
    private WebElement mapsSearchTxt;

    public MapsPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {return waitForElementToBeVisible(mapsSearchTxt);}

    public boolean clickInSearchTextField() {
        return tapElement(mapsSearchTxt);
    }

    public boolean typeSomeText(){
        return sendTextOnCleanElement(mapsSearchTxt, "Some Text");
    }
}
