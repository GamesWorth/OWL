package model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private String data_id;
    private String data_etag;
    private String id;
    private String subject;
    private String sender_name;
    private String sender_email;
    private String body;
    private List<String> ccRecipients;
    private List<String> bccRecipients;
    private String importance;
    private OffsetDateTime time_recieved;
    private boolean read;

    public Message(String id, String subject, String sender_name, String sender_email, String body, JSONArray ccRecipients, JSONArray bccRecipients, String importance, String time_recieved, boolean read) {
        this.id = id;
        this.subject = subject;
        this.sender_name = sender_name;
        this.sender_email = sender_email;
        this.body = pruneHtml(body);
        this.ccRecipients = buildRecList(ccRecipients);
        this.bccRecipients = buildRecList(bccRecipients);
        this.importance = importance;
        if(time_recieved == null) this.time_recieved = null;
        else this.time_recieved = OffsetDateTime.parse(time_recieved);
        this.read = read;
    }

    public String pruneHtml(String str){
        str = str.replaceAll("\\<.*?\\>", "");
        return str;
    }

    private ArrayList<String> buildRecList(JSONArray raw){
        ArrayList<String> list = new ArrayList<String>();
        if (raw != null) {
            int len = raw.length();
            for (int i=0;i<len;i++){
                list.add(raw.get(i).toString());
            }
        }
        return list;
    }

    public String getData_id() {return data_id;}

    public String getData_etag() {return data_etag;}

    public String getId() {return id;}

    public String getSubject() {return subject;}

    public String getSender_name() {return sender_name;}

    public String getSender_email() {return sender_email;}

    public String getBody() {return body;}

    public String getImportance() {return importance;}

    public OffsetDateTime getTime_recieved() {return time_recieved;}

    public List<String> getCcRecipients() {return ccRecipients;}

    public List<String> getBccRecipients() {return bccRecipients;}

    public boolean isRead() {return read;}

    @Override
    public String toString(){
        String rd;
        if (read) {rd = "Read";}
        else {rd = "Unread";}

        String timeStr;
        if (time_recieved == null) timeStr = "----";
        else timeStr = time_recieved.toString();

        return "From"+sender_email+"Subject :"+subject+"DateTime :"+ timeStr + " -" +rd;
    }
}
