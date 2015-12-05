package GEDCOM;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Importer {

    public PrintWriter output;
    public SourceHandler input;
    public Tokenizer tokenizer;
    public Parser parser;

    public Importer(String inputFileName){

        try{

            input = new SourceHandler(inputFileName);
            output = new PrintWriter("input_log.txt");
            tokenizer = new Tokenizer(output);
            tokenizer.setIO(input);
            parser = new Parser(tokenizer);
            output.flush();

        }   catch(SourceException error) { error.printStackTrace();}
            catch(FileNotFoundException error) {error.printStackTrace();}

        System.out.println("\n***\tGEDCOM IMPORT FINISHED\t***");
    }
}
