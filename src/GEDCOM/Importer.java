package GEDCOM;

import java.io.FileNotFoundException;

public class Importer {

    public SourceHandler input;
    public Tokenizer tokenizer;
    public Parser parser;

    public Importer(String inputFileName){

        try{

            input = new SourceHandler(inputFileName);
            tokenizer = new Tokenizer("input_log.txt");
            tokenizer.setIO(input);
            parser = new Parser(tokenizer);

        }   catch(SourceException error) { error.printStackTrace();}
            catch(FileNotFoundException error) {error.printStackTrace();}

        System.out.println("DONE");
    }
}
