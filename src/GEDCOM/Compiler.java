package GEDCOM;

/**
 * Created by Alex on 10/11/2015.
 */
public class Compiler {

    public SourceHandler input;

    public Compiler(String inputFileName){

        try
        {
            input = new SourceHandler(inputFileName);
        }

        catch (SourceFileErrorException error)
        {

        }
    }
}
