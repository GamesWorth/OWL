package model;

public class EmailCount {

    private int count;
    private String emailAddr;
    private String emailName;

    public EmailCount(String emailAddr, String emailName) {
        this.emailAddr = emailAddr;
        this.emailName = emailName;
        this.count = 1;
    }

    public void increment(){
        ++this.count;
    }

    public int getCount() {
        return count;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public String getEmailName() {
        return emailName;
    }

    @Override
    public String toString(){
        return "Email : "+emailAddr+" Name : "+emailName+" Count : "+count+"";
    }
}
