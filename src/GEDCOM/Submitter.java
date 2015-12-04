package GEDCOM;

import static GEDCOM.Tokenizer.*;
import static GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/4/2015.
 */
public class Submitter extends Parser{

    static String submitter_id;

    public Submitter() throws SourceException{

        System.out.println("\n ** STARTED IMPORTING SUBMISSION RECORD at line " + getLineNumber() + "**\n");

        int required = 0;      // there are 4 required elements in header

        accept(POINTER);
        submitter_id = getCurrentSpelling();
        accept(SUBM);
        nextLevel();

        while(getCurrentLevel() != 0){

            if(getCurrentToken() == SOUR){ required++; readSource(); }
        }

        System.out.println("\n ** FINISHED IMPORTING SUBMISSION RECORD **\n");
    }

    /**
     * Sets the source, which is the "initial or original material from which
     * the information [in the file] was obtained."
     * @throws SourceException
     */
    private static void readSource() throws SourceException {

        accept(SOUR);
        System.out.println("Setting Source as " + getCurrentSpelling());
        //file_id = getCurrentSpelling();
        accept(STRING);

        if(getCurrentToken() == VERS) setSourceVersion();
        if(getCurrentToken() == NAME) setProductName();
        if(getCurrentToken() == CORP) setBusinessName();
        if(getCurrentToken() == DATA) setSourceName();

        nextLevel();
    }

    private static void setSourceVersion() throws SourceException {

        accept(VERS);
        nextLevel();
    }

    private static void setProductName() throws SourceException {

        accept(NAME);
        nextLevel();
    }

    private static void setBusinessName() throws SourceException {

        accept(CORP);
        nextLevel();
    }

    private static void setSourceName() throws SourceException {

        accept(DATA);
        nextLevel();
    }


    private static void readDestination() throws SourceException {

        accept(DEST);
        nextLevel();
    }

    private static void setHeaderDate() throws SourceException {

        System.out.println("Setting Header Date");

        accept(LEVEL);
        accept(DATE);

        // read in a date

        if(getCurrentToken() == TIME) setTime();
        nextLevel();
    }

    private static void setTime()  throws SourceException{

        accept(TIME);
        nextLevel();
    }

    private static void readSubmitter() throws SourceException {

        accept(SUBM);

        accept(POINTER);
        nextLevel();
    }

    private static void readSubmission() throws SourceException {

        accept(SUBN);
        nextLevel();
    }

    private static void readFilename() throws SourceException {

        accept(FILE);
        nextLevel();
    }

    private static void readCopyright() throws SourceException {

        accept(COPR);
        nextLevel();
    }

//    private static void readGEDCOM_Info() throws SourceException {
//
//        accept(GEDC);
//        nextLevel();
//
//        readGEDCOM_Version();
//        readGEDCOM_Format();
//    }
//
//    private static void readGEDCOM_Version() throws SourceException {
//
//        accept(VERS);
//        System.out.println("Setting GEDCOM Version as " + getCurrentSpelling());
//        gedcom_version = getCurrentSpelling();
//        accept(NUMERIC);
//        nextLevel();
//    }
//
//    private static void readGEDCOM_Format() throws SourceException {
//
//        accept(FORM);
//        System.out.println("Setting GEDCOM Format as " + getCurrentSpelling());
//        format = getCurrentSpelling();
//        accept(STRING);
//        nextLevel();
//    }
//
//    private static void readCharset() throws SourceException {
//
//        accept(CHAR);
//        System.out.println("Setting Chartype as " + getCurrentSpelling());
//        charset = getCurrentSpelling();
//        accept(STRING);
//
//        if(getCurrentToken() == VERS) setCharsetVersion();
//        nextLevel();
//    }

    private static void setCharsetVersion()  throws SourceException{

        accept(VERS);
        nextLevel();
    }

    private static void readLanguage() throws SourceException {

        accept(LANG);
        nextLevel();
    }

    private static void readPlace() throws SourceException {

        setPlaceFormat();
        nextLevel();
    }

    private static void setPlaceFormat()  throws SourceException{

        accept(PLAC);
        nextLevel();
    }

    private static void readNote() throws SourceException {

        accept(NOTE);
        nextLevel();
    }

    private static void setHeader() {

    }
}
