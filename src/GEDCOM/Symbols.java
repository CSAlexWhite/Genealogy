package GEDCOM;

/**
 *  Contains the list of symbols for the parser to "accept" when reading a
 *  GEDCOM document
 */
public enum Symbols {


    /* TOKENS */

    NONE (""),
    END ("END"),
    POINTER ("POINTER"),   // a reference to an instantiated genealobject
    TAG ("TAG"),
    USER_TAG("USER DEFINED TAG"),   // indicates a user-defined tag, special delimiter "_" before
    LEVEL ("LEVEL"),
    STRING("STRING"),      // indicates either a tag, or a non numeric value
    NUMERIC("NUMERIC");    // indicates an integer

    Symbols(String input){


    }
}
