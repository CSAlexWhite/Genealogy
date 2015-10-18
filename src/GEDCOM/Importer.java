package GEDCOM;


public class Importer {

    public SourceHandler input;

    public Importer(String inputFileName){

        try
        {
            input = new SourceHandler(inputFileName);

            Lexer.setIO(input);
            Lexer.nextToken();

//            Parser.program();
        }

        catch (SourceException error)
        {
            error.printStackTrace();
        }
    }
}
