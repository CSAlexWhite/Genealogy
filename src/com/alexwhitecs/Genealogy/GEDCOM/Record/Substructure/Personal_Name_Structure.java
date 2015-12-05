package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.SourceException;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class Personal_Name_Structure extends Parser {

    String last_assignment;
    String name;

    public Personal_Name_Structure(int currentLevel) throws SourceException{

        accept(NAME);

        last_assignment = name = "";

        while(getCurrentToken() == STRING) {

            name += (getCurrentSpelling() + " ");
            accept(STRING);
        }

//        if(getCurrentToken() == TYPE) readNameType();
        // TODO add Personal Name Pieces etc.

        System.out.println("\tpersonal_name\t\t" + name);

        nextLevel();
    }
}
