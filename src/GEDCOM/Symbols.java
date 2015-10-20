package GEDCOM;

/**
 *  Contains the list of symbols for the parser to "accept" when reading a
 *  GEDCOM document
 */
public enum Symbols {


    /* TOKENS */

    NONE (""),      //
    POINTER ("POINTER"),   // a reference to an instantiated genealobject
    USER_TAG("USER DEFINED TAG"),   // indicates a user-defined tag, special delimiter "_" before
    IDENT("IDENTIFIER"),      // indicates either a tag, or a non numeric value
    INTEGER("INTEGER"),    // indicates an integer

    /* NON-TERMINALS */

    gedcom_line ("<gedcom_file>"),
    alpha("<alpha>"),
    alphanum("<alphanum"),
    any_char("<any_char"),
    delim("<delim>"),
    digit("<digit>"),
    escape("<escapte>"),
    escape_text("<escape_text>"),
    level("<level>"),
    line_item("<line_item>"),
    line_value("<line_value>"),
    non_at("<non_at>"),
    null_c("<null_c>"),
    optional_line_value("<optional_line_value>"),
    otherchar("<otherchar>"),
    pointer("<pointer>"),
    pointer_char("<pointer_char>"),
    pointer_string("<pointer_string>"),
    tag("<tag>"),
    terminator("<terminator>"),
    xref_ID("<xref_ID");


    Symbols(String input){


    }
}
