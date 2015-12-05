package com.alexwhitecs.Genealogy.GEDCOM;

import java.io.PrintWriter;

import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * handles the input file character by character, determining how tokens
 * are identified, applying labels and associating them with tags, and
 * storing values and spellings for retrieval as it goes
 */
public class Tokenizer {

    /********** STATIC VARIABLES  **********/

    private static PrintWriter output;
    private static SourceHandler input;

    private static Symbols previousToken = NONE;
    private static Symbols currentToken = NONE;

    private static boolean currentTokenNeedsToBeInspected;
    private static boolean newLine = true;

    private static String currentSpelling = "";
    private static String specialChars = ":;-~/*!@#$%^&*()\"{}_[]|\\?/<>,.";

    private static int linePosition = 0;
    private static int currentLevel = 0;
    private static double currentValue;

    private static Object tokenValue;

    /********** CONSTRUCTOR  **********/

    public Tokenizer(PrintWriter outwriter){

        output = outwriter;
    }

    /********** MAIN METHOD  **********/
    /**
     * Sets the value and type of the next token to be read from the input file
     * @return
     * @throws GEDCOM_Exception
     */
    public static boolean nextToken() throws GEDCOM_Exception {

        previousToken = currentToken;
        tokenValue = null;

        /* Keep track of the position of each token on the line */
        if(newLine) {
            output.println();
            linePosition = 0;
        } else linePosition++;

        /* Print the current line to the input logfile */
        output.print((input.getLineNumber() + 1) + "." + linePosition + "\t");

        // TODO print the current token somewhere?
        StringBuffer currentTokenString = new StringBuffer(10);
        while (input.getCurrentChar() == ' ') newLine = input.nextChar();

        /* Scan for Tags and strings only, identify as such */
        if (!(input.getCurrentChar() == '_')
                && !(input.getCurrentChar() == '@')
                && !Character.isDigit((char) input.getCurrentChar()))
        {
            do {
                currentTokenString.append((char) input.getCurrentChar());
                newLine = input.nextChar();

            } while (Character.isLetterOrDigit((char) input.getCurrentChar())
                    || specialChars.contains(
                        Character.toString((char) input.getCurrentChar())));

            // TODO more character cases

            currentSpelling = currentTokenString.toString();

            /* Mark tags if positioned properly */
            if(linePosition == 1){

                if(currentSpelling.charAt(0) == '@') {

                    System.out.println("Current Token is Pointer");
                    currentToken = POINTER;
                }
                else currentToken = symbolFor(currentSpelling);
            }
            //TODO what if the line position symbolFor 1 and it's a pointer?

            else if(linePosition == 2 && previousToken == POINTER) currentToken = symbolFor(currentSpelling);
            else currentToken = STRING;

            if(currentToken == null) System.out.println(currentSpelling);
            output.println(currentToken + ": " + currentSpelling);

            /* End of File at TRLR */
            if(currentToken == TRLR) return false;

            return true;
        }

        /* Handle other cases: numeric entries and user tags */
        else {

            // TODO Handle Dates and Times

            switch ((char)input.getCurrentChar()) {

                case '0':case '1':case '2':case '3':case '4':
                case '5':case '6':case '7':case '8':case '9':

                    boolean mixed = false;
                    byte dots = 0;
                    byte nonNumerics = 0;

                    if(linePosition == 0) currentToken = LEVEL;

                    // TODO throw error if level symbolFor not an integer
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

                    currentSpelling = currentTokenString.toString();

                    if(!mixed){

                        currentValue = Double.parseDouble(currentTokenString.toString());
                        tokenValue = currentValue;

                        if(currentToken == LEVEL) output.println(currentToken + ": " + (int) currentValue);
                        else output.println(currentToken + ": " + tokenValue);
                    }

                    else {

                        output.println(currentToken + ": " + currentSpelling);
                    }

                    if(currentToken == LEVEL) currentLevel = (int) currentValue;

                    // TODO Dates and Times case here?

                    return true;

                case '@':

                    currentToken = POINTER; // TODO Only if at beginning - else throw error

                    do {
                        currentTokenString.append((char) input.getCurrentChar());
                        newLine = input.nextChar();
                    } while(input.getCurrentChar() != '@');

                    // TODO what happens to what's inside?

                    currentTokenString.append('@');


                    currentSpelling = currentTokenString.toString();
                    newLine = input.nextChar();

                    output.println(currentToken + ": " + currentTokenString);
                    return true;

                case '_':

                    currentToken = USER_TAG; // TODO Only if at the beginning

                    do {
                        currentTokenString.append((char) input.getCurrentChar());
                        newLine = input.nextChar();
                        if(Character.isDigit(input.getCurrentChar()))
                            throw new GEDCOM_Exception("Unexpected numeral in user tag");
                        // TODO symbolFor this exception sufficient?
                    } while(Character.isLetterOrDigit((char) input.getCurrentChar()));

                    output.println(currentToken + ": " + currentTokenString);
                    return true;

                default:

                    throw new GEDCOM_Exception("Bad token at line " + input.getLineNumber() +
                            " on " + currentToken + ": " + currentTokenString + tokenValue);
            }
        }
    }

    public static void setIO(SourceHandler inputHandler) {

        input = inputHandler;
    }

    /********** GETTERS  **********/

    public static Symbols getCurrentToken(){
        currentTokenNeedsToBeInspected = false;
        return currentToken;
    }

    public static String getCurrentSpelling(){
        return currentSpelling;
    }

    public static double getCurrentValue(){

        return currentValue;
    }

    public static int getCurrentLevel(){

        return currentLevel;
    }

    public static int getLineNumber(){

        return input.getLineNumber();
    }

    public static String tabs(){

        String tabs = "";

        for(int i=0; i<currentLevel; i++){

            tabs += "\t";
        }

        return tabs;
    }
}
