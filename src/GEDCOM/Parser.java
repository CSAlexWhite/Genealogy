package GEDCOM;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static GEDCOM.Tokenizer.*;
import static GEDCOM.Symbols.*;

/**
 * receives tokens from the lexical analyzer and parses the structure of the
 * document according to the GEDCOM 5.5.1 grammar
 */
public class Parser{

    PrintWriter output;
    Tokenizer tokenizer;
    String outfilename = "parse_tree.txt";

    static Header header;
    static Submitter submitter;

    public Parser(){}

    public Parser(Tokenizer t) throws SourceException, FileNotFoundException{

        System.out.println("Parser Running");
        output = new PrintWriter(outfilename);

        tokenizer = t;
        nextToken();

//        while(nextToken()){}

        readGEDCOM();


    }

    protected static void accept(Symbols expectedToken) throws SourceException{

        if(getCurrentToken() == expectedToken) {

            //System.out.println("Accepted " + getCurrentToken());
            nextToken();
        }
        else throw new SourceException("ERROR: Expected " + expectedToken +
                                        "\nand found " + getCurrentToken());
    }

    protected static void nextLevel() throws SourceException{

        //System.out.println("Level " + getCurrentLevel());

        if(getCurrentToken() != LEVEL)
            throw new SourceException("ERROR: Level Unmarked\n" +
                                        "found " + getCurrentToken() +
                                        ": " + getCurrentSpelling());

        else nextToken();
    }

    protected static void readGEDCOM() throws SourceException{

        header = new Header();
        submitter = new Submitter();

        // while... individual = new individual... etc.
    }
}
