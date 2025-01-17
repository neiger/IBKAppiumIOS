package pages;

import general.BasePage;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MapsPage extends BasePage {

    @iOSXCUITFindBy(id="MapsSearchTextField")
    private WebElement mapsSearchTxt;

    @iOSXCUITFindBy(id="SettingsButton.Explore")
    private WebElement settingsButtonExplore;

    @iOSXCUITFindBy(id="UserLocationButton")
    private WebElement userLocationButton;

    @iOSXCUITFindBy(id="PlaceSummaryAccessoryViewImageView-PlaceSummaryTitleLabel-PlaceSummaryLabel-PlaceSummaryActionButtonsGrid")
    private WebElement firstResultOption;

    public MapsPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForElementToBeVisible(mapsSearchTxt)
                && waitForElementToBeVisible(settingsButtonExplore)
                && waitForElementToBeVisible(userLocationButton);
    }

    public boolean clickInSearchTextField() {
        return tapElement(mapsSearchTxt);
    }

    public boolean typeSomeText(){
        return sendTextOnCleanElement(mapsSearchTxt, "San Jose");
    }

    public boolean tapOnUserLocationButton(){
        return tapElement(firstResultOption);
    }

}
