import api_helper.JSONResponse;
import api_helper.Utils;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TwitterAPITest {

    private static final String TEST_STATUS_CREATED_AT = "Tue Jun 06 16:25:54 +0000 2017";
    private static final int TEST_STATUS_RETWEET_COUNT = 0;
    private static final String TEST_STATUS_TEXT = "Test";

    private static final String UPDATE_STATUS_TEXT = "TestPostStatus";

    @Test
    public void testHomeTimeline() throws Exception {
        JSONObject statusTimeLine = Utils.getHomeTimeLine().getJSONArray().getJSONObject(0);

        Assert.assertEquals(TEST_STATUS_CREATED_AT, statusTimeLine.getString("created_at"));
        Assert.assertEquals(TEST_STATUS_RETWEET_COUNT, statusTimeLine.getInt("retweet_count"));
        Assert.assertEquals(TEST_STATUS_TEXT, statusTimeLine.getString("text"));
    }

    @Test
    public void testPostStatus() throws Exception {
        JSONObject status = Utils.updateStatus(UPDATE_STATUS_TEXT).getJSONObject();
        long statusId = status.getLong("id");
        String statusText = status.getString("text");

        try{
            Assert.assertEquals(UPDATE_STATUS_TEXT, statusText);
        }
        finally {
            Utils.destroyStatus(statusId);
        }

    }

    @Test
    public void testDoublePostStatus() throws Exception {
        JSONObject status = Utils.updateStatus(UPDATE_STATUS_TEXT).getJSONObject();
        long statusId = status.getLong("id");

        JSONResponse statusDouble = Utils.updateStatus(UPDATE_STATUS_TEXT);

        try{
            Assert.assertEquals(403, statusDouble.getStatusCode());
        }
        finally {
            Utils.destroyStatus(statusId);
        }
    }


    @Test
    public void testDestroyStatus() throws Exception {
        JSONObject status = Utils.updateStatus(UPDATE_STATUS_TEXT).getJSONObject();
        long statusId = status.getLong("id");

        Utils.destroyStatus(statusId);

        JSONObject statusTimeLine = Utils.getHomeTimeLine().getJSONArray().getJSONObject(0); // loop

        Assert.assertNotEquals(statusId, statusTimeLine.getLong("id"));
    }
}