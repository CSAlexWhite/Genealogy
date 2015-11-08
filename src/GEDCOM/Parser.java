package GEDCOM;

import static GEDCOM.Lexer.*;
import static GEDCOM.Symbols.*;

/**
 * receives tokens from the lexical analyzer and parses the structure of the
 * document according to the GEDCOM 5.5.1 grammar
 */
public class Parser {

    public static void importData() throws SourceException{


    }

    private static void accept(Symbols expectedToken) throws SourceException{

        if(getCurrentToken() == expectedToken) nextToken();
        //TODO else throw an exception
    }
}
