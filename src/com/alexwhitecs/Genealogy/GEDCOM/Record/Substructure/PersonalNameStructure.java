package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class PersonalNameStructure extends Parser {

    String lastAssignment;
    String name;

    public PersonalNameStructure() throws GEDCOM_Exception {

        accept(NAME);

        lastAssignment = name = "";

        while(getCurrentToken() == STRING) {

            name += (getCurrentSpelling() + " ");
            accept(STRING);
        }

//        if(getCurrentToken() == TYPE) readNameType();
        // TODO add Personal Name Pieces etc.

        System.out.println(tabs() + "personal_name: " + name);

        nextLevel();
    }
}
