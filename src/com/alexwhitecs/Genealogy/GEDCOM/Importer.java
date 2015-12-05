package com.alexwhitecs.Genealogy.GEDCOM;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Importer {

    public PrintWriter output;
    public SourceHandler input;
    public Tokenizer tokenizer;
    public Parser parser;

    /**
     * Organizes the importation of the GEDCOM document, including running
     * the parser and producing log files.
     * @param inputFileName
     */
    public Importer(String inputFileName){

        try{

            input = new SourceHandler(inputFileName);
            output = new PrintWriter("input_log.txt");
            tokenizer = new Tokenizer(output);
            tokenizer.setIO(input);
            parser = new Parser(tokenizer);
            output.flush();

        }   catch(GEDCOM_Exception error) { error.printStackTrace();}
            catch(FileNotFoundException error) {error.printStackTrace();}

        System.out.println("\n***\tGEDCOM IMPORT FINISHED\t***");
    }
}
