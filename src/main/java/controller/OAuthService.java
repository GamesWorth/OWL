package controller;

import org.json.JSONObject;
import service.ParameterStringBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class OAuthService {
    private String access_token;

    public static String client_id = "0f11d13c-7fc5-4683-aa44-f0e20c325e5f";
    public static String response_type = "code";
    public static String scope = "Mail.Read";
    public static String uri = "http%3A%2F%2Flocalhost%3A4567%2Fresponse";
    public static String uri_sec = "http://localhost:4567/response";

    public static String client_secret = "Mv?Bo8NS/4F2fmbsC?R2XNllvVqx-/:T";
    public static String grant_type = "authorization_code";

    public static String url_str = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
    public static String token_url_str = "https://login.microsoftonline.com/common/oauth2/v2.0/token";

    private boolean Auth = false;

    public OAuthService() {
        staticFiles.location("/public");
        get("/response", (req, res) -> {
            //do here
            String code = req.queryParams("code");
            System.out.println("found code "+code);
            getToken(code);
            res.type("text/html");
            res.redirect("resPage_ok.html");
            return "OK";
        });
    }

    public void buildOAuth() {
        //create url
        String url = ""+url_str+"?client_id="+client_id+"&redirect_uri="+uri+"&response_type="+response_type+"&scope="+scope;
        String[] comm = new String[]{"cmd", "/c","start chrome \"" + url + "\""};
        System.out.println(url);
        try {
            Runtime.getRuntime().exec(comm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getToken(String code) throws IOException {
        URL url = new URL(token_url_str);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", client_id);
        parameters.put("client_secret", client_secret);
        parameters.put("code", code);
        parameters.put("redirect_uri", uri_sec);
        parameters.put("grant_type", "authorization_code");

        con.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        out.flush();
        out.close();
        con.disconnect();


        JSONObject res = new JSONObject(content.toString());
        access_token = res.getString("access_token");
        System.out.println(access_token);
        Auth = true;
    }

    public String getAccess_token() {
        return access_token;
    }

    public boolean isAuth() {
        return Auth;
    }
}

