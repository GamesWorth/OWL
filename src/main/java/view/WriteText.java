package view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteText {

    private BufferedWriter writer;

    public WriteText(String fname){
        try {
            writer = new BufferedWriter(new FileWriter(fname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String line){
        try {
            writer.append(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
