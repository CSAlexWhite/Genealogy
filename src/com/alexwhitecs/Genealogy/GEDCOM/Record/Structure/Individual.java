package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.*;
import com.alexwhitecs.Genealogy.GEDCOM.SourceException;
import com.alexwhitecs.Genealogy.GEDCOM.Symbols;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;
/**
 * Created by alexw on 12/4/2015.
 */
public class Individual extends Parser {

    Personal_Name_Structure name;
    Vector<IndividualEventStructure> life_events

    String individual_id, personal_name, sex;
    String last_assignment;

    public enum Events{

        BIRT("BIRT"),
        CHR("CHR"),
        DEAT("DEAT"),
        BURI("BURI"),
        CREM("CREM"),
        ADOP("ADOP"),
        BAPM("BAPM"),
        BARM("BARM"),
        BASM("BASM"),
        BLES("BLES"),
        CHRA("CHRA"),
        CONF("CONF"),
        FCOM("FCOM"),
        ORDN("ORDN"),
        NATU("NATU"),
        IMMI("IMMI"),
        CENS("CENS"),
        PROB("PROB"),
        WILL("WILL"),
        GRAD("GRAD"),
        RETI("RETI"),
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

            System.out.println(input.getCode());

            for (Events e : values()) {
                if (e.code.equals(input.getCode())) {
                    return true;
                }
            }

            return false;
        }
    }

    public enum Attributes{

        CAST("CAST"),
        DSCR("DSCR"),
        EDUC("EDUC"),
        IDNO("IDNO"),
        NATI("NATI"),
        NCHI("NCHI"),
        NMR("NMR"),
        OCCU("OCCU"),
        PROP("PROP"),
        RELI("RELI"),
        RESI("RESI"),
        SSN("SSN"),
        TITL("TITL"),
        FACT("FACT");

        private String code;

        Attributes(String code) {
            this.code = code;
        }

        public String getCode() { return code; }

        /**
         * Tests for membership in this Events enum
         * @param input
         * @return
         */
        public static boolean contains(Symbols input) {

            for (Attributes e : values()) {
                if (e.name().equals(input)) {
                    return true;
                }
            }

            return false;
        }
    }

    public Individual() throws SourceException {

        System.out.println("\n" + getLineNumber() + ": importing INDIVIDUAL RECORD\n");

        int required = 0;      // there are 4 required elements in header

        individual_id = getCurrentSpelling();
        System.out.println("\tindividual_id\t\t" + individual_id);

        accept(POINTER);
        accept(INDI);
        nextLevel();

        while(getCurrentLevel() != 0){

//            if(getCurrentToken() == RESN) readRestrictionNotice();
            if(getCurrentToken() == NAME) readNameStructure();
            if(getCurrentToken() == SEX) readSex();

            if(Events.contains(getCurrentToken())) readEvent();

            // TODO INDIVIDUAL EVENT STRUCTURE
            // TODO INDIVIDUAL ATTRIBUTE STRUCTURE
            // TODO (NO) LDS INDIVIDUAL OCCURRENCE
//            if(getCurrentToken() == FAMC) readChildToFamilyLink();
//            if(getCurrentToken() == FAMS) readSpouseToFamilyLink();
//            if(getCurrentToken() == REFN) readReferenceNumber();
//            if(getCurrentToken() == SUBM) readSubmitter();
//            if(getCurrentToken() == ASSO) readAssociationStructure();
//            if(getCurrentToken() == ALIA) readAlias();
//            if(getCurrentToken() == ANCI) readAncestryInterest();
//            if(getCurrentToken() == DESI) readDescendantInterest();
//            if(getCurrentToken() == RFN) readRecordFileNumber();
//            if(getCurrentToken() == AFN) readAncestralFileNumber();
//            if(getCurrentToken() == REFN) readUserReferenceNumber();
//            if(getCurrentToken() == RIN) readRecordIDNumber();
//            if(getCurrentToken() == CHAN) readDateChange();
//            if(getCurrentToken() == NOTE) readNote();
//            if(getCurrentToken() == SOUR) readSource();
            // TODO MULTIMEDIA LINK


            if(getCurrentToken() == CONT) continueLine();
        }

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    private void readRestrictionNotice()  throws SourceException{

    }

    private void readNameStructure() throws SourceException {

        name = new Personal_Name_Structure(getCurrentLevel());
    }

    private void readSex() throws SourceException {

        accept(SEX);

        last_assignment = sex = getCurrentSpelling();
        System.out.println("\tsex\t\t\t\t\t" + sex);
        accept(STRING);

        nextLevel();
        System.out.println(getCurrentToken());
    }

    private void readEvent() throws SourceException{

        if(getCurrentToken() == BIRT) readBirth();
    }

    private void readBirth() throws SourceException {

        accept(BIRT);
        nextLevel();
        readEventDetail();
    }

    private void readEventDetail() throws SourceException{

        // TODO MAYBE MAKE THIS METHOD GLOBALLY AVAILABLE?

        while(getCurrentLevel() != 1){

//            if(getCurrentToken() == TYPE)
            if(getCurrentToken() == DATE){


            }

            if(getCurrentToken() == PLAC){

                while(getCurrentToken() == STRING) {

                    //submitter_address += (getCurrentSpelling() + " ");
                    accept(STRING);
                }
            }

//            if(getCurrentToken() == ADDR)
//            if(getCurrentToken() == AGNC)
//            if(getCurrentToken() == RELI)
//            if(getCurrentToken() == CAUS)
//            if(getCurrentToken() == RESN)
//            if(getCurrentToken() == NOTE)
//            if(getCurrentToken() == SOUR)
            // TODO MULTIMEDIA LINK
        }
    }

    private void readChildToFamilyLink() throws SourceException {

    }

    private void readSpouseToFamilyLink() throws SourceException {

    }

    private void readReferenceNumber() throws SourceException {

    }

    private void readSubmitter() throws SourceException {

    }

    private void readAssociationStructure() throws SourceException {

    }

    private void readAlias() throws SourceException {

    }

    private void readAncestryInterest() throws SourceException {

    }

    private void readDescendantInterest() throws SourceException {

    }

    private void readRecordFileNumber() throws SourceException {

    }

    private void readAncestralFileNumber() throws SourceException {

    }

    private void readUserReferenceNumber() throws SourceException {

    }

    private void readRecordIDNumber() throws SourceException {

    }

    private void readDateChange() throws SourceException {

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
