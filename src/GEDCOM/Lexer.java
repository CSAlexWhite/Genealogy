package GEDCOM;

import static GEDCOM.Symbols.*;

/**
 * handles the input file character by character, determining what to do next
 * with everything
 */
public class Lexer {


    private static SourceHandler input;
    //private static OutputFileHandler output;
    //public static int stringTable[];

    private static Symbols currentToken = NONE;
    private boolean currentTokenNeedsToBeInspected;

    //private static int currentValue;
    //private static int currentSpelling;
    private static int endOfString = -1;
    //private static Object tokenValue;

    public static void nextToken() {


    }

    public static void setIO(SourceHandler inputHandler) {

        input = inputHandler;
    }


}
