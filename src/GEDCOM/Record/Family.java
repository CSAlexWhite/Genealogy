package GEDCOM.Record;

import GEDCOM.Parser;
import GEDCOM.SourceException;

import static GEDCOM.Tokenizer.*;
import static GEDCOM.Symbols.*;
/**
 * Created by alexw on 12/4/2015.
 */
public class Family extends Parser {

    String family_id;
    String last_assignment;

    public Family() throws SourceException {

        System.out.println("\n" + getLineNumber() + ": importing FAMILY RECORD\n");

        int required = 0;      // there are 4 required elements in header

        family_id = getCurrentSpelling();
        System.out.println("\tfamily_id\t" + family_id);

        accept(POINTER);
        accept(FAM);
        nextLevel();

        while(getCurrentLevel() != 0){

            if(getCurrentToken() == RESN) readRestrictionNotice();
            // TODO FAMILY EVENT STRUCTURE
            if(getCurrentToken() == HUSB) readAddress();
            if(getCurrentToken() == WIFE) readWife();
            if(getCurrentToken() == CHIL) readChildren();
            if(getCurrentToken() == NCHI) readRecordFileNumber();
            // TODO (NO) LDS SPOUSE SEALING
            if(getCurrentToken() == REFN) readReferenceNumber();
            if(getCurrentToken() == RIN) readRecordIDNumber();
            if(getCurrentToken() == CHAN) readChangeDate();
            if(getCurrentToken() == NOTE) readNote();
            if(getCurrentToken() == SOUR) readSource();
            // TODO MULTIMEDIA LINK

            if(getCurrentToken() == CONT) continueLine();
        }

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    private void readRestrictionNotice() throws SourceException {

    }

    private void readAddress() throws SourceException {

    }

    private void readWife() throws SourceException {

    }

    private void readChildren() throws SourceException {

    }

    private void readRecordFileNumber() throws SourceException {

    }

    private void readReferenceNumber() throws SourceException {

    }

    private void readRecordIDNumber() throws SourceException {

    }


    private void readChangeDate() throws SourceException {

    }

    private void readNote() throws SourceException {

    }

    private void readSource() throws SourceException {

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
