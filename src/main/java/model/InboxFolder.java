package model;


import java.util.ArrayList;
import java.util.List;

public class InboxFolder {
    private String context;
    private List<Message> messages;

    public InboxFolder(String context) {
        this.context = context;
        this.messages = new ArrayList<Message>();
    }

    public String getContext() {return context;}

    public List<Message> getAllMessages() {return messages;}

    public void addMessage(Message message){messages.add(message);}

    public void displayList(){
        for(Message x : messages){

        }
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String temp;
        sb.append(context);
        sb.append("\n");
        for (Message mssg:messages) {
            temp = mssg.toString();
            sb.append(temp);
            sb.append("\n");
        }
        return sb.toString();
    }
}
