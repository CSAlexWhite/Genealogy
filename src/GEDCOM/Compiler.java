package GEDCOM;


public class Compiler {

    public SourceHandler input;

    public Compiler(String inputFileName){

        try
        {
            input = new SourceHandler(inputFileName);

            Lexer.setIO(input);
            Lexer.nextToken();

            Parser.program();

            if(true) throw new SourceException("Fuck this");


        }

        catch (SourceException error)
        {

        }
    }
}
