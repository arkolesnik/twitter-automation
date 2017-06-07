package selenium_helper.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TweetPage extends AbstractPage {

    public static final String DUPLICATION_ERROR = "You have already sent this Tweet.";

    private By textContainer = By.className("js-tweet-text-container");
    private By dateLink = By.className("tweet-timestamp");
    private By retweetNumber = By.xpath("(//span[@data-tweet-stat-count])[2]");
    private By contextMenuButton = By.cssSelector(".ProfileTweet-action .IconContainer");
    private By deleteLink = By.cssSelector(".js-actionDelete button");

    @FindBy(id = "timeline")
    private WebElement tweetsTable;

    @FindBy(css = ".stream .stream-item")
    private List<WebElement> tweetsList;

    @FindBy(css = "#global-new-tweet-button .text")
    private WebElement tweetButtonGlobal;

    @FindBy(id = "global-tweet-dialog-dialog")
    private WebElement newTweetModal;

    @FindBy(id = "tweet-box-global")
    private WebElement tweetTextInput;

    @FindBy(css = ".modal-content .tweet-action")
    private WebElement tweetButtonModal;

    @FindBy(css = ".modal-footer .delete-action")
    private WebElement deleteButtonModal;

    @FindBy(id = "delete-tweet-dialog-dialog")
    private WebElement deleteModal;

    @FindBy(css = ".alert-messages .message-text")
    private WebElement messageTooltip;

    @FindBy(css = "#global-tweet-dialog-dialog .Icon--close.Icon--medium")
    private WebElement closeModalButton;

    public TweetPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getTweetsTable() {
        return tweetsTable;
    }

    public WebElement getLastTwit() {
        return tweetsList.get(0);
    }

    public WebElement getTweetButtonGlobal() {
        return tweetButtonGlobal;
    }

    public WebElement getTweetTextInput() {
        return tweetTextInput;
    }

    public WebElement getDeleteModal() {
        return deleteModal;
    }

    public WebElement getNewTweetModal() {
        return newTweetModal;
    }

    public String extractTwitText(WebElement tweet) {
        return tweet.findElement(textContainer).getText();
    }

    public String getDate(WebDriver driver, WebElement tweet) {
        WebElement link = tweet.findElement(dateLink);
        Actions actions = new Actions(driver);
        actions.moveToElement(link).build().perform();

        return link.getAttribute("data-original-title");
    }

    public String getRetweetNumber(WebElement tweet, WebDriverWait wait, WebDriver driver) {
        return tweet.findElement(retweetNumber).getAttribute("data-tweet-stat-count");
    }

    public void openAddTweetModal() {
        tweetButtonGlobal.click();
    }

    public void addNewTweet(String text) {
        tweetTextInput.clear();
        tweetTextInput.sendKeys(text);
        tweetButtonModal.click();
    }

    public void deleteTweet(WebElement tweet) {
        tweet.findElement(contextMenuButton).click();
        tweet.findElement(deleteLink).click();
        deleteButtonModal.click();
    }

    public boolean isDuplicationErrorShown(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(messageTooltip, DUPLICATION_ERROR));
        } catch (TimeoutException e) {
            return false;
        }
        return true;

    }

    public void closeTweetModal() {
        closeModalButton.click();
    }

}
