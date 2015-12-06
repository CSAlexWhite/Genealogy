package com.alexwhitecs.Genealogy.GEDCOM.Record.Structure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.*;
import com.alexwhitecs.Genealogy.GEDCOM.Symbols;

import java.util.Vector;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;
/**
 * Created by alexw on 12/4/2015.
 */
public class Individual extends Parser {

    PersonalNameStructure name;
    Vector<IndividualEventStructure> lifeEvents;
    Vector<IndividualAttributeStructure> attributes;
    Vector<ChildToFamilyLink> familiesAsChild;
    Vector<SpouseToFamilyLink> familiesAsSpouse;

    String xref_individual, sex;
    String lastAssignment;
    String tablename, idColumn;

    public Individual(String xref_individual) throws GEDCOM_Exception {

        lifeEvents = new Vector<>();
        familiesAsChild = new Vector<>();
        familiesAsSpouse = new Vector<>();

        tablename = "individual"; idColumn = "individual_id";

        System.out.println("\n" + getLineNumber() + ": importing INDIVIDUAL RECORD\n");

        this.xref_individual = xref_individual;
        System.out.println(tabs() + "xref_individual: " + xref_individual);

        accept(INDI);
        nextLevel();

        while(getCurrentLevel() != 0){

//            if(getCurrentToken() == RESN) readRestrictionNotice();
            if(getCurrentToken() == NAME) readNameStructure();
            if(getCurrentToken() == SEX) readSex();

            if(Events.contains(getCurrentToken())) readEvent();
            if(Attributes.contains(getCurrentToken())) readAttribute();

            // TODO INDIVIDUAL ATTRIBUTE STRUCTURE
            if(getCurrentToken() == FAMC) readChildToFamilyLink();
            if(getCurrentToken() == FAMS) readSpouseToFamilyLink();
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

        pushToDB();

        System.out.println("\n" + getLineNumber() + ": import successful");
    }

    private void pushToDB() {

        String sql = "INSERT INTO individual" +
                " (x_ref_id, given_name, surname, sex)" +
                " SELECT * FROM (SELECT " +
                "\"" + xref_individual + "\", " +
                "\"" + name.getGivenName() + "\", " +
                "\"" + name.getSurname() + "\", " +
                "\"" + sex + "\")" +
                " AS tmp" +
                " WHERE NOT EXISTS (" +
                " SELECT x_ref_id FROM individual WHERE x_ref_id = " +
                "\"" + xref_individual + "\"" +
                " ) LIMIT 1;";

        executeSQL_Statement(sql);
    }

    private void readNameStructure() throws GEDCOM_Exception {

        name = new PersonalNameStructure();
    }

    private void readSex() throws GEDCOM_Exception {

        accept(SEX);

        lastAssignment = sex = getCurrentSpelling();
        System.out.println(tabs() + "sex: " + sex);
        accept(STRING);
        nextLevel();
    }

    private void readEvent() throws GEDCOM_Exception {

        lifeEvents.add(new IndividualEventStructure());
    }

    private void readAttribute() throws GEDCOM_Exception {

    }

    private void readChildToFamilyLink() throws GEDCOM_Exception {

        familiesAsChild.add(new ChildToFamilyLink(this));
    }

    private void readSpouseToFamilyLink() throws GEDCOM_Exception {

        familiesAsSpouse.add(new SpouseToFamilyLink(this));
    }

    /* INIMPLEMENTED TAG FUNCTIONS */
    private void readRestrictionNotice()  throws GEDCOM_Exception {}
    private void readReferenceNumber() throws GEDCOM_Exception {}
    private void readSubmitter() throws GEDCOM_Exception {}
    private void readAssociationStructure() throws GEDCOM_Exception {}
    private void readAlias() throws GEDCOM_Exception {}
    private void readAncestryInterest() throws GEDCOM_Exception {}
    private void readDescendantInterest() throws GEDCOM_Exception {}
    private void readRecordFileNumber() throws GEDCOM_Exception {}
    private void readAncestralFileNumber() throws GEDCOM_Exception {}
    private void readUserReferenceNumber() throws GEDCOM_Exception {}
    private void readRecordIDNumber() throws GEDCOM_Exception {}
    private void readDateChange() throws GEDCOM_Exception {}
    private void readNote() throws GEDCOM_Exception {}
    private void readSource() throws GEDCOM_Exception {}

    private void continueLine() throws GEDCOM_Exception {

        accept(CONT);
        while(getCurrentToken() == STRING) {

            lastAssignment += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println("contd.: " + lastAssignment);

        nextLevel();
    }

    private void printEvents(){

        for(IndividualEventStructure event : lifeEvents){

            System.out.println(event);
        }
    }

    /**
     * Pushes all of this object's data to the
     */
    private static void setSubmitter() {

    }

    public String getID(){

        return xref_individual;
    }

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
                if (e.name().equals(input.getCode())) {
                    return true;
                }
            }

            return false;
        }
    }
}
