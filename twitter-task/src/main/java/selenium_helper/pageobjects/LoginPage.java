package selenium_helper.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(id = "signin-email")
    private WebElement emailInput;

    @FindBy(id = "signin-password")
    private WebElement passwordInput;

    @FindBy(css = ".flex-table-secondary .submit")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginSuccess(String username, String password) {
        emailInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
