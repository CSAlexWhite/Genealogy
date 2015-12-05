package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.*;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/4/2015.
 */
public class Submitter extends Parser {

    AddressStructure submitterAddress;

    String submitterID, submitterName;
    String lastAssignment;

    public Submitter() throws GEDCOM_Exception {

        System.out.println("\n" + getLineNumber() + ": importing SUBMISSION RECORD\n");

        int required = 0;      // there are 4 required elements in header

        submitterID = getCurrentSpelling();
        System.out.println("submitterID: " + submitterID);

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

    private void readName() throws GEDCOM_Exception {

        accept(NAME);

        lastAssignment = submitterName = getCurrentSpelling();
        System.out.println(tabs() + "name: " + submitterName);
        accept(STRING);

        nextLevel();
    }

    private void readAddress() throws GEDCOM_Exception {

        submitterAddress = new AddressStructure();
    }

    private void readLanguage() throws GEDCOM_Exception {

    }

    private void readRecordFileNumber() throws GEDCOM_Exception {

    }

    private void readRecordIDNumber() throws GEDCOM_Exception {

    }

    private void readNoteStructure() throws GEDCOM_Exception {

    }

    private void readChangeDate() throws GEDCOM_Exception {

    }

    private void continueLine() throws GEDCOM_Exception {

        accept(CONT);

        System.out.print(tabs());

        while(getCurrentToken() == STRING) {

            lastAssignment += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println("contd.:" + lastAssignment);

        nextLevel();
    }


    /**
     * Pushes all of this object's data to the
     */
    private static void setSubmitter() {

    }
}
