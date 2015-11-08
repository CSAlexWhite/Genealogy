package GEDCOM;

import java.io.*;

/**
 * its main method nextChar() retrieves the next character from the specified
 * source file, sets that as its "currentChar" and prints it to the console
 */
public class SourceHandler {

    private int currentChar;
    private LineNumberReader sourceFileReader;

    PrintWriter output;


    /********** CONSTRUCTOR(S) **********/

    public SourceHandler(String infilename){

        try{

            output = new PrintWriter("output.txt");

        } catch(FileNotFoundException error){ error.printStackTrace();}

        openSourceFile(infilename);
        nextChar();

        // TODO get output working
        output.println("SOMETHING SOMETHING!!!");
    }

    /********** MAIN METHODS  **********/

    /**
     * reads a single character of the input file and sets it to the currentChar
     * variable, to be read by another
     * class when necessary
     */
    public boolean nextChar() {

        try
        {

            switch(currentChar = sourceFileReader.read()) {

                case '\n':

                    currentChar = ' ';
                    return true;

                case '\r':

                    currentChar = ' ';
                    return true;

                case -1:

                    currentChar = -1;
                    return false;

                case '\t':

                    currentChar = ' ';
                    output.println('\t');
                    return false;

                default:

                    output.print((char) currentChar);
                    return false;
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
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

    public int getLineNumber(){

        return sourceFileReader.getLineNumber();
    }

    /********** UTILITIES **********/

    private LineNumberReader getSourceFileReader(){

        return sourceFileReader;
    }

    private void printLineNumber() {

        System.out.printf("%-5s ", String.valueOf(sourceFileReader.getLineNumber()));
    }

    public boolean newLine(int input) {

        if(input < sourceFileReader.getLineNumber()) return true;
        return false;
    }
}
