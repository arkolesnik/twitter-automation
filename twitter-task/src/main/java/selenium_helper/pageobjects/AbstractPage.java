package selenium_helper.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AbstractPage {

    public AbstractPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
