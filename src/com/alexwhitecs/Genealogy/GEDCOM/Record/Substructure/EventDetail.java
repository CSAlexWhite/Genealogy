package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Symbols;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

import java.util.Vector;

/**
 * Created by alexw on 12/5/2015.
 */
public class EventDetail extends Parser{

    String type, date = "", agency, religion, cause, restrictionNotice;
    String lastAssignment;

    int startLevel;

    PlaceStructure placeStructure;
    AddressStructure address;
    Vector<NoteStructure> notes;
    Vector<SourceCitation> sources;
    // TODO MULTIMEDIA LINK

    public EventDetail(String date, String placeName){

        this.date = date;
        this.placeStructure = new PlaceStructure(placeName);
    }

    public EventDetail() throws GEDCOM_Exception {

        startLevel = getCurrentLevel();

        nextLevel();
        while(getCurrentLevel() != 1){

            if(getCurrentToken() == TYPE) readType();
            if(getCurrentToken() == DATE) readDate();
//            if(getCurrentToken() == AGNC) readAgency();
//            if(getCurrentToken() == RELI) readReligion();
//            if(getCurrentToken() == CAUS) readCause();
//            if(getCurrentToken() == RESN) readRestrictionNotice();

            if(OtherDetails.contains(getCurrentToken())) readDetail();
        }
    }

    private void readType() throws GEDCOM_Exception {

        accept(TYPE);
        lastAssignment = type = getCurrentSpelling();
        System.out.println(tabs() + "type: " + type);
        accept(STRING);
        nextLevel();
    }

    private void readDate() throws GEDCOM_Exception {

        accept(DATE);

        lastAssignment = date = "";

        System.out.print(tabs());

        while(getCurrentToken() == STRING
                || getCurrentToken() == NUMERIC){

            date += (getCurrentSpelling() + " ");

            if(getCurrentToken() == STRING) accept(STRING);
            else accept(NUMERIC);
        }

        System.out.println("date: " + date);
        nextLevel();
    }


    private void readAgency() throws GEDCOM_Exception {

    }

    private void readReligion() throws GEDCOM_Exception {

    }

    private void readCause() throws GEDCOM_Exception {

    }

    private void readRestrictionNotice() throws GEDCOM_Exception {

    }

    private void readDetail() throws GEDCOM_Exception {

        if(getCurrentToken() == PLAC) placeStructure = new PlaceStructure();
        if(getCurrentToken() == ADDR) address = new AddressStructure();
        if(getCurrentToken() == NOTE) notes.add(new NoteStructure());
        if(getCurrentToken() == SOUR) sources.add(new SourceCitation());
    }

    @Override
    public String toString() {

        return type + " " + date;
    }

    public enum OtherDetails{

        PLAC("PLAC"),
        ADDR("ADDR"),
        NOTE("NOTE"),
        SOUR("SOUR");

        private String code;

        OtherDetails(String code) {
            this.code = code;
        }

        public String getCode() { return code; }

        /**
         * Tests for membership in this Events enum
         * @param input
         * @return
         */
        public static boolean contains(Symbols input) {

            for (OtherDetails e : values()) {
                if (e.code.equals(input.getCode())) {
                    return true;
                }
            }

            return false;
        }
    }
}