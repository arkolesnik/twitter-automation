package selenium_helper.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends AbstractPage {

    @FindBy(id = "user-dropdown")
    private WebElement userDropdown;

    @FindBy(className = "ProfileCardStats-statLabel")
    private WebElement tweetsLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public boolean isUserLoggedIn() {
        return userDropdown.isDisplayed();
    }

    public void clickTweetsLink() {
        tweetsLink.click();
    }

}
