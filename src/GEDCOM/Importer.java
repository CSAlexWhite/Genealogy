package GEDCOM;
import static GEDCOM.Symbols.*;

public class Importer {

    public SourceHandler input;
    public Lexer currentLexer;

    public Importer(String inputFileName){

        try
        {
            input = new SourceHandler(inputFileName);

            currentLexer = new Lexer("input_log.txt");
            Lexer.setIO(input);

            while(Lexer.nextToken()){}

            System.out.println("DONE");

//            Parser.program();
        }

        catch (SourceException error)
        {
            error.printStackTrace();
        }
    }
}
