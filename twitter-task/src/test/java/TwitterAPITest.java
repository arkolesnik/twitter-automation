import api_helper.JSONResponse;
import api_helper.Utils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TwitterAPITest {

    private static final String TEST_STATUS_CREATED_AT = "Wed Jun 07 16:28:17 +0000 2017";
    private static final int TEST_STATUS_RETWEET_NUMBER = 0;
    private static final String TEST_STATUS_TEXT = "MainTweet";

    private static final String UPDATE_STATUS_TEXT = "TestPostStatus";

    @Test
    public void testHomeTimeline() throws Exception {
        JSONObject statusTimeLine = Utils.getHomeTimeLine().getJSONArray().getJSONObject(0);
        StringBuilder error = new StringBuilder();
        String actualDate = statusTimeLine.getString("created_at");
        if (!TEST_STATUS_CREATED_AT.equalsIgnoreCase(actualDate)) {
            error.append("Expected tweet text is " + TEST_STATUS_CREATED_AT + ", but actual is " + actualDate + "\n");
        }
        int actualRetweetNumber = statusTimeLine.getInt("retweet_count");
        if (TEST_STATUS_RETWEET_NUMBER != actualRetweetNumber) {
            error.append("Expected retweet number " + TEST_STATUS_RETWEET_NUMBER + ", but actual is " + actualRetweetNumber + "\n");
        }
        String actualTweetText = statusTimeLine.getString("text");
        if (!TEST_STATUS_TEXT.equalsIgnoreCase(actualTweetText)) {
            error.append("Expected tweet text is " + TEST_STATUS_TEXT + ", but actual is " + actualTweetText + "\n");
        }
        Assert.assertTrue(error.toString().isEmpty(), error.toString());
    }

    @Test
    public void testPostStatus() throws Exception {
        JSONObject status = Utils.updateStatus(UPDATE_STATUS_TEXT).getJSONObject();
        long statusId = status.getLong("id");
        String statusText = status.getString("text");
        try {
            Assert.assertEquals(UPDATE_STATUS_TEXT, statusText);
        } finally {
            Utils.destroyStatus(statusId);
        }

    }

    @Test
    public void testDoublePostStatus() throws Exception {
        JSONObject status = Utils.updateStatus(UPDATE_STATUS_TEXT).getJSONObject();
        long statusId = status.getLong("id");
        JSONResponse statusDouble = Utils.updateStatus(UPDATE_STATUS_TEXT);
        try {
            Assert.assertEquals(403, statusDouble.getStatusCode());
        } finally {
            Utils.destroyStatus(statusId);
        }
    }


    @Test
    public void testDestroyStatus() throws Exception {
        JSONObject status = Utils.updateStatus(UPDATE_STATUS_TEXT).getJSONObject();
        long statusId = status.getLong("id");
        Utils.destroyStatus(statusId);
        JSONObject statusTimeLine = Utils.getHomeTimeLine().getJSONArray().getJSONObject(0);
        Assert.assertNotEquals(statusId, statusTimeLine.getLong("id"));
    }
}