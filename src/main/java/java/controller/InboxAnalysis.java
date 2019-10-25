package controller;

import model.Analytics;
import model.Inbox;
import model.InboxFolder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InboxAnalysis {
    private String token = "";
    List<JSONObject> rawInbox;
    Inbox locals;
    MailAnalytics analytics;

    public InboxAnalysis(){
        authenticate();
        System.out.println("Starting inbox");
        rawInbox = new ArrayList<>();
        getInbox();
        buildLocals();
        runAnalysis();
    }

    private void authenticate(){
        OAuthService authenticator = new OAuthService();
        authenticator.buildOAuth();
        boolean loop = false;
        //check for authentication
        while(!loop) {
            loop = authenticator.isAuth();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.token = authenticator.getAccess_token();
        System.out.println("Authentication success...");
    }

    private int CAP = 10;

    private void getInbox(){
        System.out.println("Getting inbox");
        MailRetrievalService mailRetriever = new MailRetrievalService(token);
        for(int i=0;i<CAP;i++){
            System.out.print(i+"/"+CAP+"\r");
            rawInbox.add(mailRetriever.getRawList());
        }
    }

    private void buildLocals(){
        locals = new Inbox();
        for (JSONObject jObj: rawInbox) {
            locals.build_inbox(jObj);
        }
        String str = locals.toString();
        System.out.println(str);
    }

    private void runAnalysis(){
        analytics = new MailAnalytics(locals);
        analytics.runFullAnalysis();
        System.out.println(analytics.dispalyResults());
    }
}
