package com.alexwhitecs.Genealogy.GEDCOM;

import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * receives tokens from the lexical analyzer and parses the structure of the
 * document according to the GEDCOM 5.5.1 grammar
 */
public class Parser{

    static Header header;
    static Submitter submitter;
    static Vector<Individual> individuals;
    static Vector<Family> families;

    public Parser(){}

    public Parser(Tokenizer t) throws GEDCOM_Exception, FileNotFoundException{

        System.out.println("Parser Running");

        readGEDCOM();
    }

    /**
     * Designed to ensure that the next token in the input file is the expected
     * one, and if not it throws an exception
     * @param expectedToken
     * @throws GEDCOM_Exception
     */
    protected static void accept(Symbols expectedToken) throws GEDCOM_Exception {

        if(getCurrentToken() == expectedToken) nextToken();

        else throw new GEDCOM_Exception("ERROR: Expected " + expectedToken +
                                        "\nand found " + getCurrentToken());
    }

    /**
     * Attempts to read a level identifier, if it encounters something else,
     * throws an exception
     * @throws GEDCOM_Exception
     */
    protected static void nextLevel() throws GEDCOM_Exception {

        if(getCurrentToken() != LEVEL)
            throw new GEDCOM_Exception("ERROR: Level Unmarked\n" +
                                        "found " + getCurrentToken() +
                                        ": " + getCurrentSpelling());

        else nextToken();
    }

    /**
     * The main method which reads in a GEDCOM file in the proper sequence,
     * generating objects for every structure and substructure of the
     * document with associated variables and database connectors.
     * @throws GEDCOM_Exception
     */
    protected static void readGEDCOM() throws GEDCOM_Exception {

        nextToken();

        String currentPointer;

        header = new Header();
        submitter = new Submitter();
        individuals = new Vector<Individual>();
        families = new Vector<Family>();

        while(getCurrentToken() != TRLR){

            currentPointer = getCurrentSpelling();
            accept(POINTER);

            while(getCurrentToken() == INDI)
                individuals.add(new Individual(currentPointer));

            while(getCurrentToken() == FAM)
                families.add(new Family(currentPointer));

        }
    }
}
