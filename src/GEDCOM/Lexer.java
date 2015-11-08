package GEDCOM;

import static GEDCOM.Symbols.*;

/**
 * handles the input file character by character, determining what to do next
 * with everything
 */
public class Lexer {


    private static SourceHandler input;

    private static Symbols currentToken = NONE;
    private static boolean currentTokenNeedsToBeInspected;
    private static boolean isAllCaps = true;
    private static boolean newLine = true;

    private static String currentSpelling = "";
    private static int endOfString = -1;
    private static int linePosition = 0;
    private static Object tokenValue;

    /**
     * the name, age, birthdate, etc. for the current entry
     */
    private static double currentValue;

    public static boolean nextToken() throws SourceException {

//        if(currentSpelling.equals("allowed")){
//
//            System.out.println("What the fuck");
//            return false;
//        }

        if(newLine) {

            System.out.println();
            linePosition = 0;
        }

        else linePosition++;

        System.out.print(input.getLineNumber() + "." + linePosition + "\t");

        // TODO print the current token somewhere?

        if (currentTokenNeedsToBeInspected)
            throw new SourceException("Error in parser: token not read");
        else currentTokenNeedsToBeInspected = false;

        /* to store the current string */
        StringBuffer currentTokenString = new StringBuffer(10);
        while (input.getCurrentChar() == ' ') newLine = input.nextChar();

        tokenValue = null;

        if (!(input.getCurrentChar() == '_')
                && !(input.getCurrentChar() == '@')
                && !Character.isDigit((char) input.getCurrentChar())){
            do {
                currentTokenString.append((char) input.getCurrentChar());
                newLine = input.nextChar();

            } while (Character.isAlphabetic((char) input.getCurrentChar())); // TODO more character cases

            currentSpelling = currentTokenString.toString();
            
            if(linePosition == 1) currentToken = TAG;
            else currentToken = STRING;

            System.out.println(currentToken + ": " + currentSpelling);

            if(currentSpelling.equals("TRLR")) return false;
            return true;
        }

        else {

            // TODO Handle Dates and Times

            switch ((char)input.getCurrentChar()) {

                case '0':case '1':case '2':case '3':case '4':
                case '5':case '6':case '7':case '8':case '9':

                    boolean mixed = false;
                    byte dots = 0;
                    byte nonNumerics = 0;

                    if(linePosition == 0) currentToken = LEVEL;
                    // TODO throw error if level is not an integer
                    else currentToken = NUMERIC;

                    do{
                        if (!Character.isDigit((char) input.getCurrentChar()))
                            nonNumerics++;

                        if(nonNumerics >0 && dots != 1) mixed = true;

                        currentTokenString.append((char) input.getCurrentChar());
                        newLine = input.nextChar();

                    } while ((input.getCurrentChar() >= '0' && input.getCurrentChar() <= '9')
                            || input.getCurrentChar() == '.'
                            || input.getCurrentChar() == ':'
                            || input.getCurrentChar() == '-'
                            || input.getCurrentChar() == '/');

                    if(!mixed){

                        currentValue = Double.parseDouble(currentTokenString.toString());
                        tokenValue = currentValue;
                        System.out.println(currentToken + ": " + tokenValue);
                    }

                    else {

                        currentSpelling = currentTokenString.toString();
                        System.out.println(currentToken + ": " + currentSpelling);
                    }

                    // TODO Datees and Times case here?

                    return true;

                case '@':

                    currentToken = POINTER; // TODO Only if at beginning - else throw error

                    do {
                        currentTokenString.append((char) input.getCurrentChar());
                        newLine = input.nextChar();
                    } while(input.getCurrentChar() != '@');
                    
                    currentTokenString.append('@');
                    newLine = input.nextChar();

                    System.out.println(currentToken + ": " + currentTokenString);
                    return true;

                case '_':

                    currentToken = USER_TAG; // TODO Only if at the beginning

                    do {
                        currentTokenString.append((char) input.getCurrentChar());
                        newLine = input.nextChar();
                        if(Character.isDigit(input.getCurrentChar()))
                            throw new SourceException("Unexpected numeral in user tag");
                        // TODO is this exception sufficient?
                    } while(Character.isLetterOrDigit((char) input.getCurrentChar()));

                    System.out.println(currentToken + ": " + currentTokenString);
                    return true;

                default:

                    throw new SourceException("Bad token at line " + input.getLineNumber() +
                            " on " + currentToken + ": " + currentTokenString + tokenValue);
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

    public static double getCurrentValue(){

        return currentValue;
    }
}
