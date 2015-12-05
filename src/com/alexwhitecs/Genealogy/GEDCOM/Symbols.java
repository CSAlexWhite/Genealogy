package com.alexwhitecs.Genealogy.GEDCOM;

/**
 *  Contains the list of symbols for the parser to "accept" when reading a
 *  GEDCOM document
 */
public enum Symbols {


    /* DATA TYPES */
    NONE (""),
    POINTER ("POINTER"),   // a reference to an instantiated genealobject
    TAG ("TAG"),
    USER_TAG("USER_TAG"),   // indicates a user-defined tag, special delimiter "_" before
    LEVEL ("LEVEL"),
    STRING("STRING"),      // indicates either a tag, or a non numeric value
    NUMERIC("NUMERIC"),    // indicates an integer

    /* UTILITY TAGS */
    TYPE("TYPE"),

    /* HEADER TAGS */
    HEAD("HEAD"),
    SOUR("SOUR"),
    VERS("VERS"),
    NAME("NAME"),
    CORP("CORP"),
    DATA("DATA"),
    DATE("DATE"),
    COPR("COPR"),
    DEST("DEST"),
    TIME("TIME"),
    SUBM("SUBM"),
    SUBN("SUBN"),
    FILE("FILE"),
    GEDC("GEDC"),
    FORM("FORM"),
    CHAR("CHAR"),
    LANG("LANG"),
    PLAC("PLAC"),
    NOTE("NOTE"),

    /* FAMILY TAGS */
    FAM("FAM"),
    RESN("RESN"),
    HUSB("HUSB"),
    WIFE("WIFE"),
    CHIL("CHIL"),
    NCHI("NCHI"),
    REFN("REFN"),
    RIN("RIN"),

    /* INDIVIDUAL TAGS */
    INDI("INDI"),
    SEX("SEX"),
    ALIAS("ALIAS"),
    ANCI("ANCI"),
    DESI("DESI"),
    RFN("RFN"),
    AFN("AFN"),
    ASSO("ASSO"),
    ALIA("ALIA"),

    /* INDIVIDUAL ATTRIBUTE TAGS */
    CAST("CAST"),
    DSCR("DSCR"),
    EDUC("EDUC"),
    IDNO("IDNO"),
    NATI("NATI"),
    NMR("NMR"),
    OCCU("OCCU"),
    PROP("PROP"),
    RELI("RELI"),
    RESI("RESI"),
    SSN("SSN"),
    TITL("TITL"),
    FACT("FACT"),


    /* INDIVIDUAL EVENT TAGS */
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
    EVEN("EVEN"),
    AGNC("AGNC"),
    CAUS("CAUS"),

    /* FAMILY EVENT STRUCTURE */
    MARR("MARR"),


    /* CHILD TO FAMILY LINK */
    FAMC("FAMC"),

    /* SPOUSE TO FAMILY LINK */
    FAMS("FAMS"),

    /* ADDRESS TAGS */
    ADDR("ADDR"),
    ADR1("ADR1"),
    ADR2("ADR2"),
    ADR3("ADR3"),
    CITY("CITY"),
    STAE("STAE"),
    POST("POST"),
    CTRY("CTRY"),
    PHON("PHON"),
    EMAIL("EMAIL"),
    FAX("FAX"),
    WWW("WWW"),

    /* SPECIAL TAGS */
    CONC("CONC"),
    CONT("CONT"),
    CHAN("CHAN"),
    TRLR("TRLR");

    private String code;

    Symbols(String code) {
        this.code = code;
    }

    public String getCode() { return code; }

    /**
     * Returns the symbol that corresponds to the given string,
     * and if it's not there, throws an error
     * @param code
     * @return
     */
    public static Symbols symbolFor(String code) throws GEDCOM_Exception {
        for(Symbols s : values()) {
            if(s.code.equals(code)) return s;
        }
        throw new GEDCOM_Exception("Symbol " + code + " not found");
        //return null;
    }
}
