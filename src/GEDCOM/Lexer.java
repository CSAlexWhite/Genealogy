package GEDCOM;

import javax.xml.transform.Source;

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
    private static boolean currentTokenNeedsToBeInspected;

    private static String currentSpelling;
    private static int endOfString = -1;
    private static Object tokenValue;

    /**
     * the name, age, birthdate, etc. for the current entry
     */
    private static int currentValue;

    public static void nextToken() throws SourceException {

        // TODO print the current token somewhere?

        if (currentTokenNeedsToBeInspected)
            throw new SourceException("Error in parser: token not read");
        else currentTokenNeedsToBeInspected = false;

        /* to store the current string */
        StringBuffer currentTokenString = new StringBuffer(10);

        /* skip whitespace */
        while (input.getCurrentChar() == ' ') input.nextChar();

        tokenValue = null;

        if (Character.isLetter((char) input.getCurrentChar())) {

            do {
                currentTokenString.append((char) input.getCurrentChar());
                input.nextChar();
            } while (Character.isLetterOrDigit((char) input.getCurrentChar()));

            currentSpelling = currentTokenString.toString();

            currentToken = IDENT;

            return;
        }

        else {

            // TODO Handle Dates and Times

            switch ((char)input.getCurrentChar()) {

                case '0':case '1':case '2':case '3':case '4':
                case '5':case '6':case '7':case '8':case '9':

                    currentToken = INTEGER;
                    do{
                        currentTokenString.append((char) input.getCurrentChar());
                        input.nextChar();
                    } while (input.getCurrentChar() >= '0' && input.getCurrentChar() <= '9');

                    currentValue = Integer.parseInt(currentTokenString.toString());
                    tokenValue = currentValue;

                    // TODO Datees and Times case here?

                case '@':

                    currentToken = POINTER;

                    do {
                        currentTokenString.append((char) input.getCurrentChar());
                        input.nextChar();
                    } while(input.getCurrentChar() != '@');
                    
                    currentTokenString.append('@');

                    return;

                case '_':

                    currentToken = USER_TAG;

                    do {
                        currentTokenString.append((char) input.getCurrentChar());
                        input.nextChar();
                        if(Character.isDigit(input.getCurrentChar()))
                            throw new SourceException("Unexpected numberal in user tag");
                        // TODO is this exception sufficient?
                    } while(Character.isLetterOrDigit((char) input.getCurrentChar()));

                default:

                    throw new SourceException("Bad token at line " + input.getLineNumber());
            }
        }
    }


    public static void setIO(SourceHandler inputHandler) {

        input = inputHandler;
    }

    public static Symbols getCurrentToken(){
        currentTokenNeedsToBeInspected = false;
        return currentToken;
    }

    public static int getCurrentValue(){

        return currentValue;
    }


}
