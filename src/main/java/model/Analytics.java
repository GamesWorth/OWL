package model;

import java.util.List;

public class Analytics {
    //private String topSentRecip;

    private String title;
    private EmailCount topRecip;
    private int avgBodyWordCount;
    private int totalEmails;
    private double readRate;
    private List<EmailCount> emailCounts;

    public Analytics(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public EmailCount getTopRecip() {
        return topRecip;
    }

    public void setTopRecip(EmailCount topRecip) {
        this.topRecip = topRecip;
    }

    public int getAvgBodyWordCount() {
        return avgBodyWordCount;
    }

    public void setAvgBodyWordCount(int avgBodyWordCount) {
        this.avgBodyWordCount = avgBodyWordCount;
    }

    public int getTotalEmails() {
        return totalEmails;
    }

    public void setTotalEmails(int totalEmails) {
        this.totalEmails = totalEmails;
    }

    public double getReadRate() {
        return readRate;
    }

    public void setReadRate(double readRate) {
        this.readRate = readRate;
    }

    public List<EmailCount> getEmailCounts() {
        return emailCounts;
    }

    public void setEmailCounts(List<EmailCount> emailCounts) {
        this.emailCounts = emailCounts;
    }
}
