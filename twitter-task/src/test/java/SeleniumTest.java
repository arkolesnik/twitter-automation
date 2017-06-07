import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import selenium_helper.driver_utils.Utils;
import selenium_helper.pageobjects.LoginPage;
import selenium_helper.pageobjects.MainPage;
import selenium_helper.pageobjects.TweetPage;

public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private LoginPage loginPage;
    private MainPage mainPage;
    private TweetPage tweetPage;

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        Utils.setOptionsForChrome(options);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);

        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        tweetPage = new TweetPage(driver);

        driver.get("https://twitter.com/");
    }

    @Test
    @Parameters({"username", "password"})
    public void login(String username, String password) {
        loginPage.loginSuccess(username, password);
        Assert.assertTrue(mainPage.isUserLoggedIn(), "User was probably not logged in \n");
    }

    @Test
    @Parameters({"text"})
    public void addDeleteTweet(String text) {
        tweetPage.openAddTweetModal();
        wait.until(ExpectedConditions.visibilityOf(tweetPage.getTweetTextInput()));
        tweetPage.addNewTweet(text);
        wait.until(ExpectedConditions.invisibilityOf(tweetPage.getTweetTextInput()));
        Assert.assertEquals(tweetPage.extractTwitText(tweetPage.getLastTwit()), text);

        tweetPage.deleteTweet(tweetPage.getLastTwit());
        wait.until(ExpectedConditions.invisibilityOf(tweetPage.getDeleteModal()));
        Assert.assertNotEquals(tweetPage.extractTwitText(tweetPage.getLastTwit()), text);
    }

    @Test
    @Parameters({"text"})
    public void duplicateTweet(String text) {
        try {
            for (int i = 0; i < 2; i++) {
                tweetPage.openAddTweetModal();
                wait.until(ExpectedConditions.visibilityOf(tweetPage.getTweetTextInput()));
                tweetPage.addNewTweet(text);
                if (i == 0) {
                    wait.until(ExpectedConditions.invisibilityOf(tweetPage.getTweetTextInput()));
                }
            }
            Assert.assertTrue(tweetPage.isDuplicationErrorShown(wait), "Error message about tweet duplication is not shown \n");

        } finally {
            tweetPage.closeTweetModal();
            wait.until(ExpectedConditions.invisibilityOf(tweetPage.getNewTweetModal()));
            tweetPage.deleteTweet(tweetPage.getLastTwit());
            wait.until(ExpectedConditions.invisibilityOf(tweetPage.getDeleteModal()));
        }
    }

    @Test
    @Parameters({"creationDate", "text", "retweetNumber"})
    public void checkTweet(String creationDate, String text, String retweetNumber) {
        mainPage.clickTweetsLink();
        wait.until(ExpectedConditions.visibilityOf(tweetPage.getTweetsTable()));

        StringBuilder error = new StringBuilder();

        String actualTweetText = tweetPage.extractTwitText(tweetPage.getLastTwit());
        if (!actualTweetText.equalsIgnoreCase(text)) {
            error.append("Expected tweet text is " + text + ", but actual is " + actualTweetText + "\n");
        }

        String actualDate = tweetPage.getDate(driver, tweetPage.getLastTwit());
        if (!actualDate.equalsIgnoreCase(creationDate)) {
            error.append("Expected tweet date is " + creationDate + ", but actual is " + actualDate + "\n");
        }

        String actualRetweetNumber = tweetPage.getRetweetNumber(tweetPage.getLastTwit(), wait, driver);
        if (!actualRetweetNumber.equalsIgnoreCase(retweetNumber)) {
            error.append("Expected retweet number " + retweetNumber + ", but actual is " + actualRetweetNumber + "\n");
        }
        Assert.assertTrue(error.toString().isEmpty(), error.toString());
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
