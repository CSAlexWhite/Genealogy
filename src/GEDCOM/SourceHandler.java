package GEDCOM;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * its main method nextChar() retrieves the next character from the specified
 * source file, sets that as its "currentChar" and prints it to the console
 */
public class SourceHandler {

    private int currentChar;
    private LineNumberReader sourceFileReader;

    /********** CONSTRUCTOR(S) **********/

    public SourceHandler(String filename){

        openSourceFile(filename);
        printLineNumber();
        nextChar();
    }

    /********** MAIN METHODS  **********/

    /**
     * reads a single character of the input file and sets it to the currentChar
     * variable, to be read by another
     * class when necessary
     */
    public void nextChar() {

        try
        {
            switch(currentChar = sourceFileReader.read()) {

                case '\n':
                    currentChar = ' ';
                    System.out.println();
                    printLineNumber();
                    return;

                case '\r':
                    currentChar = ' ';
                    return;

                case -1:
                    currentChar = -1;
                    return;

                case '\t':
                    currentChar = ' ';
                    System.out.println('\t');
                    return;

                default:
                    System.out.print((char) currentChar);
                    return;
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void openSourceFile(String filename) {

        try
        {
            sourceFileReader = new LineNumberReader(new FileReader(filename));
        }

        catch (FileNotFoundException e)
        {
            //throw new SourceFileErrorException("Unable to open input file.")
            e.printStackTrace();
        }
    }

    /********** GETTERS  **********/

    public int getCurrentChar(){

        return currentChar;
    }

    /********** UTILITIES **********/

    private LineNumberReader getSourceFileReader(){

        return sourceFileReader;
    }

    private void printLineNumber() {

        System.out.printf("%-5s ", String.valueOf(sourceFileReader.getLineNumber()));
    }
}
