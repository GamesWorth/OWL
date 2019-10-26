package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inbox {
    private List<InboxFolder> folders;

    public Inbox(){
        folders = new ArrayList<>();
    }

    public List<InboxFolder> getFolders() {
        return folders;
    }

    public void build_inbox(JSONObject raw){
        generate_folder(raw.getString("@odata.context"),raw.getJSONArray("value"));
    }

    private void generate_folder(String context, JSONArray messages){
        String cont = findContext(context);
        Boolean newFolder = true;
        for(InboxFolder f : folders){
            if(f.getContext().equals(cont)){
                for(int i = 0;i<messages.length();i++){
                    f.addMessage(buildMessage((JSONObject) messages.get(i)));
                }
                newFolder = false;
            }
        }
        if (newFolder){
            InboxFolder folder = new InboxFolder(cont);
            for(int i = 0;i<messages.length();i++){
                folder.addMessage(buildMessage((JSONObject) messages.get(i)));
            }
            folders.add(folder);
        }
    }

    private Message buildMessage(JSONObject mssg){
        String id = mssg.getString("id");
        String subject = mssg.getString("subject");
        String senderAddr = mssg.getJSONObject("sender").getJSONObject("emailAddress").getString("name");
        String senderName = mssg.getJSONObject("sender").getJSONObject("emailAddress").getString("address");
        String body = mssg.getJSONObject("body").getString("content");
        JSONArray ccRec = mssg.getJSONArray("ccRecipients");
        JSONArray bccRec = mssg.getJSONArray("bccRecipients");
        String importance = mssg.getString("importance");
        String recieved = mssg.get("receivedDateTime").toString();
        boolean read = mssg.getBoolean("isRead");
        return new Message(id,subject,senderAddr,senderName,body,ccRec,bccRec,importance,recieved,read);
    }

    private String findContext(String full){
        String[] arr = full.split("mailFolders",10);

        //split after mailFolders
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(arr[1]);
        String res = "";
        while(m.find()) {
            res = m.group(1);
        }
        res = res.replaceAll(" /[\\']/","");
        return res;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String temp;
        for (InboxFolder folder:folders) {
            temp = folder.toString();
            sb.append(temp);
            sb.append("\n");
        }
        return sb.toString();
    }
}
