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
    USER_TAG("USER_TAG"),   // indicates a user-defined tag, special delimiter "_" before
    LEVEL ("LEVEL"),
    STRING("STRING"),      // indicates either a tag, or a non numeric value
    NUMERIC("NUMERIC"),    // indicates an integer

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
    GEDC("GEDCOM"),
    FORM("FORM"),
    CHAR("CHAR"),
    LANG("LANG"),
    PLAC("PLAC"),
    NOTE("NOTE"),

    /* HEADER TAGS */
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

    /* SPECIAL TAGS */
    CONC("CONC"),
    TRLR("TRLR");

    private String code;

    private Symbols(String code) {
        this.code = code;
    }

    public String getCode() { return code; }

    public static Symbols is(String code) {
        for(Symbols s : values()) {
            if(s.code.equals(code)) return s;
        }
        return null;
    }
}
