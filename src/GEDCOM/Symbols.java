package GEDCOM;

/**
 *  Contains the list of symbols for the parser to "accept" when reading a
 *  GEDCOM document
 */
public enum Symbols {


    /* TYPES */
    NONE (""),
    POINTER ("POINTER"),   // a reference to an instantiated genealobject
    TAG ("TAG"),
    USER_TAG("USER DEFINED TAG"),   // indicates a user-defined tag, special delimiter "_" before
    LEVEL ("LEVEL"),
    STRING("STRING"),      // indicates either a tag, or a non numeric value
    NUMERIC("NUMERIC"),    // indicates an integer

    /* HEADER TAGS */
    HEAD("HEADER"),
    SOUR("SOURCE"),
    VERS("VERSION"),
    NAME("NAME"),
    CORP("CORORATION"),
    DATA("DATA"),
    DATE("DATE"),
    COPR("COPYRIGHT"),
    DEST("RECEIVING SYSTEM NAME"),
    TIME("TIME"),
    SUBM("SUBMITTER"),
    SUBN("SUBMISSION"),
    FILE("FILENAME"),
    GEDC("GEDCOM"),
    FORM("FORM"),
    CHAR("CHARACTER SET"),
    LANG("LANGUAGE"),
    PLAC("PLACE HIERARCHY"),
    NOTE("CONTENT DESCRIPTION"),

    /* HEADER TAGS */
    FAM("FAMILY RECORD"),
    RESN("RESTRICTION NOTICE"),
    HUSB("HUSBAND"),
    WIFE("WIFE"),
    CHIL("CHILD"),
    NCHI("NUMBER OF CHILDREN"),
    REFN("REFERENCE NUMBER"),
    RIN("RECORD ID NUMBER"),

    /* INDIVIDUAL TAGS */
    INDI("INDIVIDUAL"),
    SEX("SEX VALUE"),
    ALIAS("ALIAS"),
    ANCI("GENERATION OF ANCESTORS"),
    DESI("GENERATION OF DESCENDANTS"),
    RFN("RECORD FILE NUMBER"),
    AFN("ANCESTRAL FILE NUMBER");

    Symbols(String input){


    }
}
