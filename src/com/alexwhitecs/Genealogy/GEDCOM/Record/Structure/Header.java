package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/4/2015.
 */
public class Header extends Parser {

    String charset, fileID, gedcomVersion, gedcomFormat, submitterID;
    String lastAssignment;

    int currentLevel;

    public Header() throws GEDCOM_Exception {

        System.out.println("\n" + getLineNumber() + ": importing HEADER\n");

        currentLevel = getCurrentLevel();

        nextLevel();
        accept(HEAD);
        nextLevel();

        while(getCurrentLevel() > currentLevel){

            if(getCurrentToken() == SOUR) readSource();
//            if(getCurrentToken() == DEST) readDestination();
//            if(getCurrentToken() == DATE) readHeaderDate();
            if(getCurrentToken() == SUBM) readSubmitter();
//            if(getCurrentToken() == SUBN) readSubmission();
//            if(getCurrentToken() == FILE) readFilename();
//            if(getCurrentToken() == COPR) readCopyright();
            if(getCurrentToken() == GEDC) readGEDCOM_Info();
            if(getCurrentToken() == CHAR) readCharset();
//            if(getCurrentToken() == LANG) readLanguage();
//            if(getCurrentToken() == PLAC) readPlace();
//            if(getCurrentToken() == NOTE) readNote();
            System.out.println(getCurrentToken());
            if(getCurrentToken() == CONT) continueLine();
        }

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    /**
     * Sets the source, which symbolFor the "initial or original material from which
     * the information [in the file] was obtained."
     * @throws GEDCOM_Exception
     */
    private void readSource() throws GEDCOM_Exception {

        accept(SOUR);

//        lastAssignment = fileID = getCurrentSpelling();

        while(getCurrentToken() == STRING) {

            lastAssignment = fileID += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println(tabs() + "source: " + fileID);

        nextLevel();
    }

    private void readDestination() throws GEDCOM_Exception {

        accept(DEST);
        nextLevel();
    }

    private void readHeaderDate() throws GEDCOM_Exception {

        accept(DATE);
        nextLevel();
    }

    private void readSubmitter() throws GEDCOM_Exception {

        accept(SUBM);

        lastAssignment = submitterID = getCurrentSpelling();
        System.out.println(tabs() + "submitterID: " + submitterID);
        accept(POINTER);
        nextLevel();
    }

    private void readSubmission() throws GEDCOM_Exception {

        accept(SUBN);
        nextLevel();
    }

    private void readFilename() throws GEDCOM_Exception {

        accept(FILE);
        nextLevel();
    }

    private void readCopyright() throws GEDCOM_Exception {

        accept(COPR);
        nextLevel();
    }

    private void readGEDCOM_Info() throws GEDCOM_Exception {

        accept(GEDC);
        nextLevel();

        readGEDCOM_Version();
        readGEDCOM_Format();
    }

    private void readGEDCOM_Version() throws GEDCOM_Exception {

        accept(VERS);

        lastAssignment = gedcomVersion = getCurrentSpelling();
        System.out.println(tabs() + "gedcomVersion: " + gedcomVersion);

        accept(NUMERIC);
        nextLevel();
    }

    private void readGEDCOM_Format() throws GEDCOM_Exception {

        accept(FORM);

        lastAssignment = gedcomFormat = getCurrentSpelling();
        System.out.println(tabs() + "gedcomFormat: " + gedcomFormat);

        accept(STRING);
        nextLevel();
    }

    private void readCharset() throws GEDCOM_Exception {

        accept(CHAR);

        lastAssignment = charset = getCurrentSpelling();
        System.out.println(tabs() + "charset: " + charset);

        accept(STRING);

        nextLevel();
    }

    private void readLanguage() throws GEDCOM_Exception {

        accept(LANG);
        nextLevel();
    }

    private void readPlace() throws GEDCOM_Exception {

        accept(PLAC);
        nextLevel();
    }

    private void readNote() throws GEDCOM_Exception {

        accept(NOTE);
        nextLevel();
    }

    private void continueLine() throws GEDCOM_Exception {

        accept(CONT);
        while(getCurrentToken() == STRING) {

            lastAssignment += (getCurrentSpelling() + " ");
            accept(STRING);
        }


        System.out.println(tabs() + "contd.:" + lastAssignment);

        nextLevel();
    }

    private void setHeader() {

    }
}
