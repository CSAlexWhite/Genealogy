package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.*;
import com.alexwhitecs.Genealogy.GEDCOM.SourceException;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/4/2015.
 */
public class Submitter extends Parser {

    AddressStructure address;

    String submitter_id, submitter_name, submitter_address;
    String last_assignment;

    public Submitter() throws SourceException {

        System.out.println("\n" + getLineNumber() + ": importing SUBMISSION RECORD\n");

        int required = 0;      // there are 4 required elements in header

        submitter_id = getCurrentSpelling();
        System.out.println("\tsubmitter_id\t" + submitter_id);

        accept(POINTER);
        accept(SUBM);
        nextLevel();

        while(getCurrentLevel() != 0){

            if(getCurrentToken() == NAME) readName();
            if(getCurrentToken() == ADDR) readAddress();
            // TODO MULTIMEDIA LINK
            if(getCurrentToken() == LANG) readLanguage();
            if(getCurrentToken() == RFN) readRecordFileNumber();
            if(getCurrentToken() == RIN) readRecordIDNumber();
            if(getCurrentToken() == NOTE) readNoteStructure();
            if(getCurrentToken() == CHAN) readChangeDate();
            if(getCurrentToken() == CONT) continueLine();
        }

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    private void readName() throws SourceException{

        accept(NAME);

        last_assignment = submitter_name = getCurrentSpelling();
        System.out.println("\tname\t\t\t" + submitter_name);
        accept(STRING);

        nextLevel();
    }

    private void readAddress() throws SourceException{

        address = new AddressStructure(getCurrentLevel());
    }

    private void readLanguage() throws SourceException {

    }

    private void readRecordFileNumber() throws SourceException {

    }

    private void readRecordIDNumber() throws SourceException {

    }

    private void readNoteStructure() throws SourceException {

    }

    private void readChangeDate() throws SourceException {

    }

    private void continueLine() throws SourceException{

        accept(CONT);
        while(getCurrentToken() == STRING) {

            last_assignment += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println("\tcontd.\t\t\t" + last_assignment);

        nextLevel();
    }


    /**
     * Pushes all of this object's data to the
     */
    private static void setSubmitter() {

    }
}
