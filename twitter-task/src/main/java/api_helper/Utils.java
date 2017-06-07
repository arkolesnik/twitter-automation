package api_helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Utils {

    private static final String HOME_TIMELINE_URL = "https://api.twitter.com/1.1/statuses/home_timeline.json";
    private static final String STATUSES_UPDATE_URL = "https://api.twitter.com/1.1/statuses/update.json?status=%s";
    private static final String STATUSES_DESTROY_URL = "https://api.twitter.com/1.1/statuses/destroy/%s.json";

    private static final String CONSUMER_KEY = "tjobeSiU5jNYsThxtdjiZsr3O";
    private static final String CONSUMER_SECRET = "lDomkAd3WmANt0LVcei8gMpm1rBhxEsiW9kPuMS7znJHGCQEZk";
    private static final String ACCESS_TOKEN = "869980605781561350-1kmZKk671xYpQzmYE4WcL0mx4W1PaoH";
    private static final String TOKEN_SECRET = "Xmvs4p7Dmg0Der5RSLmRqAqhQTzsKSfxwTRc6R9oDWguN";

    private static OAuthConsumer consumer;

    public static JSONResponse getHomeTimeLine() throws Exception {
        HttpGet request = new HttpGet(HOME_TIMELINE_URL);
        return performTwitterRequest(request);
    }

    public static JSONResponse updateStatus(String statusText) throws Exception {
        HttpPost request = new HttpPost(String.format(STATUSES_UPDATE_URL, statusText));
        return performTwitterRequest(request);
    }

    public static JSONResponse destroyStatus(Long statusId) throws Exception {
        HttpPost request = new HttpPost(String.format(STATUSES_DESTROY_URL, statusId));
        return performTwitterRequest(request);
    }

    private static OAuthConsumer getConsumer() {
        if (consumer == null) {
            consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
            consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        }
        return consumer;
    }

    private static JSONResponse performTwitterRequest(HttpUriRequest request) throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        getConsumer().sign(request);
        request.addHeader("accept", "application/json");
        HttpResponse response = client.execute(request);
        JSONResponse jsonResponse = new JSONResponse(response);
        EntityUtils.consume(response.getEntity());
        return jsonResponse;
    }
}
