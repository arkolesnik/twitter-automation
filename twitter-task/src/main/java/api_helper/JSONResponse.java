package api_helper;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONResponse {
    private String content;
    private int statusCode;

    public JSONResponse(HttpResponse response) throws Exception {
        this.content = IOUtils.toString(response.getEntity().getContent());
        this.statusCode = response.getStatusLine().getStatusCode();
    }

    public JSONArray getJSONArray() {
        return new JSONArray(this.content );
    }

    public JSONObject getJSONObject() {
        return new JSONObject(this.content );
    }

    public int getStatusCode() {
        return statusCode;
    }
}
