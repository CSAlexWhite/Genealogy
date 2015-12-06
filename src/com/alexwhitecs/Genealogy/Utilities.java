package com.alexwhitecs.Genealogy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by alexw on 12/6/2015.
 */
public class Utilities {

    File logfile;
    FileWriter logwriter;

    public Utilities(){

        try{

            logfile = new File("logfile.text");
            logfile.createNewFile();

            logwriter = new FileWriter(logfile);

        }
        catch(IOException error){error.printStackTrace();}
    }

    private static void log(){

    }

    public void close(){

        try{

            logwriter.flush();
            logwriter.close();
        }
        catch(IOException error){error.printStackTrace();}
    }
}
