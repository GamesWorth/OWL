import controller.InboxAnalysis;
import controller.OAuthService;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spark.Spark.*;

public class app {
    public static void main(String[] args) {
        System.out.println("Starting app...");
        org.apache.log4j.BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
        InboxAnalysis prog = new InboxAnalysis();
    }

}

