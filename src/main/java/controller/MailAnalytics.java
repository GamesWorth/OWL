package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MailAnalytics {

    private Inbox inbox;
    private List<Analytics> analytics;

    public MailAnalytics(Inbox inbox){
        this.inbox = inbox;
        analytics = new ArrayList<>();
    }

    public void runFullAnalysis() {
        for (InboxFolder folder : inbox.getFolders()) {
            analytics.add(runAnalysis(folder));
        }
    }

    private Analytics runAnalysis(InboxFolder folder){
        Analytics analysis = new Analytics(folder.getContext());
        int totalM = genTotalEmails(folder.getAllMessages());
        List<EmailCount> recipFreqs = genRecips(folder.getAllMessages());
        EmailCount mostRecipFreq = mostFreqRecip(recipFreqs);
        int avgWordCount = genAvgBodyWordCount(folder.getAllMessages());
        double readRate = genReadRate(folder.getAllMessages());
        analysis.setAvgBodyWordCount(avgWordCount);
        analysis.setEmailCounts(recipFreqs);
        analysis.setReadRate(readRate);
        analysis.setTopRecip(mostRecipFreq);
        analysis.setTotalEmails(totalM);
        return analysis;
    }

    private List<EmailCount> genRecips(List<Message> mssgs){
        List<EmailCount> eCount = new ArrayList<>();
        for(Message mssg : mssgs){
            //geneate email count
            boolean match = false;
            for(EmailCount emailCount : eCount){
                if(emailCount.getEmailName().equals(mssg.getSender_email())){
                    //email is in counter
                    match = true;
                    emailCount.increment();
                    break;
                }
            }
            if(!match){
                //1 email not in counter
                eCount.add(new EmailCount(mssg.getSender_email(),mssg.getSender_name()));
            }
        }
        return eCount;
    }

    private EmailCount mostFreqRecip(List<EmailCount> fullList){
        EmailCount current = null;
        for(EmailCount email : fullList){
            //current null
            if(current == null){
                current = email;
            }else if(current.getCount()<email.getCount()){
                current = email;
            }
        }
        return current;
    }

    private int genAvgBodyWordCount(List<Message> mssgs){
        int sum=0;
        for(Message mssg : mssgs){
            sum = sum+countWords(mssg.getBody());
        }
        return sum/mssgs.size();
    }

    private int genTotalEmails(List<Message> mssgs){
        return mssgs.size();
    }

    private double genReadRate(List<Message> mssgs){
        double count=0;
        for(Message mssg : mssgs){
            if (mssg.isRead()) ++count;
        }
        double total = mssgs.size();
        return count/total;
    }

    private static int countWords(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return 0;
        }
        StringTokenizer tokens = new StringTokenizer(sentence);
        return tokens.countTokens();
    }

    public List<String>dispalyResults(){
        List<String> res = new ArrayList<>();
        for (Analytics x : analytics){
            String str = "Inbox: "+x.getTitle()+" Top Recipient: "+x.getTopRecip()+" Average Body Word Count: "+x.getAvgBodyWordCount()+"Total Emails: "+x.getTotalEmails()+"Read Rate: "+x.getReadRate();
            res.add(str);
        }
        return res;
    }
    public String dispalyResults(String search){
        for (Analytics x : analytics){
            if(x.getTitle().equals(search)){
                return "Inbox: "+x.getTitle()+" Top Recipient: "+x.getTopRecip()+" Average Body Word Count: "+x.getAvgBodyWordCount()+"Total Emails: "+x.getTotalEmails()+"Read Rate: "+x.getReadRate();
            }
        }
        return null;
    }

    public List<String> emailData(){
        List<String> res = new ArrayList<>();
        res.add("All Email Recipients");
        for (Analytics x : analytics){
            for(EmailCount e : x.getEmailCounts()){
                res.add("Address: "+e.getEmailAddr()+" Name: "+e.getEmailName()+" Count: "+e.getCount());
            }
        }
        return res;
    }
}
