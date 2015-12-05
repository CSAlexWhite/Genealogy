package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.SourceException;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/4/2015.
 */
public class Header extends Parser {

    String charset, file_id, gedcom_version, gedcom_format, submitter_id;
    String last_assignment;

    public Header() throws SourceException {

        System.out.println("\n" + getLineNumber() + ": importing HEADER\n");

        nextLevel();
        accept(HEAD);
        nextLevel();

        while(getCurrentLevel() != 0){

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
            if(getCurrentToken() == CONT) continueLine();
        }

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    /**
     * Sets the source, which symbolFor the "initial or original material from which
     * the information [in the file] was obtained."
     * @throws SourceException
     */
    private void readSource() throws SourceException {

        accept(SOUR);
        last_assignment = file_id = getCurrentSpelling();
        System.out.println("\tsource\t\t\t" + file_id);
        accept(STRING);

        nextLevel();
    }

    private void readDestination() throws SourceException {

        accept(DEST);
        nextLevel();
    }

    private void readHeaderDate() throws SourceException {

        accept(DATE);
        nextLevel();
    }

    private void readSubmitter() throws SourceException {

        accept(SUBM);
        accept(POINTER);

        last_assignment = submitter_id = getCurrentSpelling();
        System.out.println("\tsubmitter_id\t" + submitter_id);

        nextLevel();
    }

    private void readSubmission() throws SourceException {

        accept(SUBN);
        nextLevel();
    }

    private void readFilename() throws SourceException {

        accept(FILE);
        nextLevel();
    }

    private void readCopyright() throws SourceException {

        accept(COPR);
        nextLevel();
    }

    private void readGEDCOM_Info() throws SourceException {

        accept(GEDC);
        nextLevel();

        readGEDCOM_Version();
        readGEDCOM_Format();
    }

    private void readGEDCOM_Version() throws SourceException {

        accept(VERS);

        last_assignment = gedcom_version = getCurrentSpelling();
        System.out.println("\tgedcom_version\t" + gedcom_version);

        accept(NUMERIC);
        nextLevel();
    }

    private void readGEDCOM_Format() throws SourceException {

        accept(FORM);

        last_assignment = gedcom_format = getCurrentSpelling();
        System.out.println("\tgedcom_format\t" + gedcom_format);

        accept(STRING);
        nextLevel();
    }

    private void readCharset() throws SourceException {

        accept(CHAR);

        last_assignment = charset = getCurrentSpelling();
        System.out.println("\tcharset\t\t\t" + charset);

        accept(STRING);

        nextLevel();
    }

    private void readLanguage() throws SourceException {

        accept(LANG);
        nextLevel();
    }

    private void readPlace() throws SourceException {

        accept(PLAC);
        nextLevel();
    }

    private void readNote() throws SourceException {

        accept(NOTE);
        nextLevel();
    }

    private void continueLine() throws SourceException{

        accept(CONT);
        while(getCurrentToken() == STRING) {

            last_assignment += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        nextLevel();
    }

    private void setHeader() {

    }
}
