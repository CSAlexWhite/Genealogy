package GEDCOM;


public class Importer {

    public SourceHandler input;

    public Importer(String inputFileName){

        try
        {
            input = new SourceHandler(inputFileName);

            Lexer.setIO(input);
            Lexer.nextToken();
            //System.out.println();

            for(int i=0; i<50; i++){
                Lexer.nextToken();
            }

//            Parser.program();
        }

        catch (SourceException error)
        {
            error.printStackTrace();
        }
    }
}
