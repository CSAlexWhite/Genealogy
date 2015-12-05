package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.FamilyEventStructure;
import com.alexwhitecs.Genealogy.GEDCOM.Symbols;

import java.util.Vector;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;
/**
 * Created by alexw on 12/4/2015.
 */
public class Family extends Parser {

    String familyID, husband, wife;
    String lastAssignment;

    Vector<String> children;

    Vector<FamilyEventStructure> familyEvents;

    public enum Events{

        ANUL("ANUL"),
        CENS("CENS"),
        DIV("DIV"),
        DIVF("DIVF"),
        ENGA("ENGA"),
        MARB("MARB"),
        MARC("MARC"),
        MARR("MARR"),
        MARL("MARL"),
        MARS("MARS"),
        RESI("RESI"),
        EVEN("EVEN");

        private String code;

        Events(String code) {
            this.code = code;
        }

        public String getCode() { return code; }

        /**
         * Tests for membership in this Events enum
         * @param input
         * @return
         */
        public static boolean contains(Symbols input) {

            for (Events e : values()) {
                if (e.code.equals(input.getCode())) {
                    return true;
                }
            }

            return false;
        }
    }

    public Family(String familyID) throws GEDCOM_Exception {

        children = new Vector<String>();
        familyEvents = new Vector<FamilyEventStructure>();

        System.out.println("\n" + getLineNumber() + ": importing FAMILY RECORD\n");

        int required = 0;      // there are 4 required elements in header

        this.familyID = familyID;
        System.out.println("\tfamilyID\t" + familyID);

        accept(FAM);
        nextLevel();

        while(getCurrentLevel() != 0){

//            if(getCurrentToken() == RESN) readRestrictionNotice();
            if(Events.contains(getCurrentToken())) readEvent();
            if(getCurrentToken() == HUSB) readHusband();
            if(getCurrentToken() == WIFE) readWife();
            if(getCurrentToken() == CHIL) readChild();
//            if(getCurrentToken() == NCHI) readRecordFileNumber();
            // TODO (NO) LDS SPOUSE SEALING
//            if(getCurrentToken() == REFN) readReferenceNumber();
//            if(getCurrentToken() == RIN) readRecordIDNumber();
//            if(getCurrentToken() == CHAN) readChangeDate();
//            if(getCurrentToken() == NOTE) readNote();
//            if(getCurrentToken() == SOUR) readSource();
            // TODO MULTIMEDIA LINK

            if(getCurrentToken() == CONT) continueLine();
        }

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    private void readEvent() throws GEDCOM_Exception {

        familyEvents.add(new FamilyEventStructure());
    }

    private void readRestrictionNotice() throws GEDCOM_Exception {

    }

    private void readHusband() throws GEDCOM_Exception {

        accept(HUSB);

        lastAssignment = husband = getCurrentSpelling();
        System.out.println(tabs() + "husband: " + husband);
        accept(POINTER);
        nextLevel();
    }

    private void readWife() throws GEDCOM_Exception {

        accept(WIFE);

        lastAssignment = wife = getCurrentSpelling();
        System.out.println(tabs() + "wife: " + wife);
        accept(POINTER);
        nextLevel();
    }

    private void readChild() throws GEDCOM_Exception {

        String child;

        accept(CHIL);

        lastAssignment = child = getCurrentSpelling();
        children.add(child);
        System.out.println(tabs() + "child: " + child);
        accept(POINTER);
        nextLevel();
    }

    private void readRecordFileNumber() throws GEDCOM_Exception {

    }

    private void readReferenceNumber() throws GEDCOM_Exception {

    }

    private void readRecordIDNumber() throws GEDCOM_Exception {

    }


    private void readChangeDate() throws GEDCOM_Exception {

    }

    private void readNote() throws GEDCOM_Exception {

    }

    private void readSource() throws GEDCOM_Exception {

    }


    private void continueLine() throws GEDCOM_Exception {

        accept(CONT);
        while(getCurrentToken() == STRING) {

            lastAssignment += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println("\tcontd.\t\t\t" + lastAssignment);

        nextLevel();
    }


    /**
     * Pushes all of this object's data to the
     */
    private static void setSubmitter() {

    }
}