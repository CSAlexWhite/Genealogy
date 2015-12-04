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

    public Parser(Tokenizer t) throws SourceException, FileNotFoundException{

        System.out.println("Parser Running");

        tokenizer = t;
        nextToken();

//        while(nextToken())

        readGEDCOM();

        output = new PrintWriter(outfilename);
    }

    private static void accept(Symbols expectedToken) throws SourceException{

        if(getCurrentToken() == expectedToken) {

            System.out.println("Accepted " + getCurrentToken());
            nextToken();
        }
        else throw new SourceException("ERROR: Expected " + expectedToken +
                                        "\nand found " + getCurrentToken());
    }

    private static void nextLevel() throws SourceException{

        if(getCurrentToken() != LEVEL)
            throw new SourceException("ERROR: Level Unmarked\n" +
                                        "found " + getCurrentToken());

        else nextToken();
    }

    private static void readGEDCOM() throws SourceException{

        readHeader();

        setRecords();
    }

    /* BEGIN HEADER METHODS */

    private static void readHeader() throws SourceException {

        nextLevel();
        accept(HEAD);
        nextLevel();
        accept(CHAR);
        setCharType();
        nextLevel();
        accept(SOUR);
        setSource();
        if(getCurrentToken() == DEST) setDestination();
        if(getCurrentToken() == DATE) setHeaderDate();
        accept(SUBM);
        setSubmitter();
        if(getCurrentToken() == SUBN) setSubmission();
        if(getCurrentToken() == FILE) setFilename();
        if(getCurrentToken() == COPR) setCopyright();
        accept(GEDC);
        setGEDCOM_Info();
        accept(CHAR);
        setCharset();
        if(getCurrentToken() == LANG) setLanguage();
        if(getCurrentToken() == PLAC) setPlace();
        if(getCurrentToken() == NOTE) setNote();
        if(getCurrentToken() == SUBM) setSubmitterInfo();
    }

    private static void setCharType() throws SourceException{

        System.out.println("Setting Chartype as " + getCurrentSpelling());
        accept(STRING);
    }

    /* BEGIN READ SOURCE METHODS */

    private static void setSource() throws SourceException {

        System.out.println("Setting Source");

        accept(LEVEL);

        if(getCurrentToken() == VERS) setSourceVersion();
        if(getCurrentToken() == NAME) setProductName();
        if(getCurrentToken() == CORP) setBusinessName();
        if(getCurrentToken() == DATA) setSourceName();
    }

        private static void setSourceVersion() throws SourceException {

            accept(LEVEL);
            accept(VERS);
        }

        private static void setProductName() throws SourceException {

            accept(LEVEL);
            accept(NAME);
        }

        private static void setBusinessName() throws SourceException {

            accept(LEVEL);
            accept(CORP);
        }

        private static void setSourceName() throws SourceException {

            accept(LEVEL);
            accept(DATA);
        }

    /* END READ SOURCE METHDOS */


    private static void setDestination() throws SourceException {

        accept(LEVEL);
        accept(DEST);
    }

    private static void setHeaderDate() throws SourceException {

        System.out.println("Setting Header Date");

        accept(LEVEL);
        accept(DATE);

        // read in a date

        if(getCurrentToken() == TIME) setTime();
    }

        private static void setTime()  throws SourceException{

            accept(LEVEL);
        }

    private static void setSubmitter() throws SourceException {

        accept(LEVEL);
        accept(SUBM);
    }

    private static void setSubmission() throws SourceException {

        accept(LEVEL);
        accept(SUBN);
    }

    private static void setFilename() throws SourceException {

        accept(LEVEL);
        accept(FILE);
    }

    private static void setCopyright() throws SourceException {

        accept(LEVEL);
        accept(COPR);
    }

    private static void setGEDCOM_Info() throws SourceException {

        accept(LEVEL);
        setGEDCOMVersion();
        setGEDCOMFormat();
    }

        private static void setGEDCOMVersion() throws SourceException {

            accept(LEVEL);
            accept(VERS);
        }

        private static void setGEDCOMFormat() throws SourceException {

            accept(LEVEL);
            accept(FORM);
        }

    private static void setCharset() throws SourceException {

        accept(LEVEL);
        if(getCurrentToken() == VERS) setCharsetVersion();
    }

        private static void setCharsetVersion()  throws SourceException{

            accept(LEVEL);
            accept(VERS);
        }

    private static void setLanguage() throws SourceException {

        accept(LEVEL);
        accept(LANG);
    }

    private static void setPlace() throws SourceException {

        accept(LEVEL);
        setPlaceFormat();
    }

        private static void setPlaceFormat()  throws SourceException{

            accept(LEVEL);
            accept(PLAC);
        }

    private static void setNote() throws SourceException {

        accept(LEVEL);
        accept(NOTE);
    }

    private static void setSubmitterInfo()  throws SourceException{

        accept(LEVEL);
    }

    private static void setRecords() throws SourceException {

        accept(LEVEL);

    }

    /* END HEADER METHODS */
}
