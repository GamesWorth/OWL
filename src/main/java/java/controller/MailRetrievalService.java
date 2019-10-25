package controller;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ParameterStringBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MailRetrievalService {

    private String url_string;
    private String token;

    public MailRetrievalService(String token){
        this.token = token;
        this.url_string = "https://graph.microsoft.com/v1.0/me/mailfolders" + "/inbox/messages";
    }

    public JSONObject getRawList(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url_string)
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .build();

        Response response = null;
        String body = " ";
        try {
            response = client.newCall(request).execute();
            body = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jObj = new JSONObject(body);
        this.url_string = jObj.getString("@odata.nextLink");
        return jObj;
    }

}
