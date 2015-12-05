package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.SourceException;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class AddressStructure extends Parser{

    String last_assignment;
    String address;

    public AddressStructure(int currentLevel) throws SourceException{

        accept(ADDR);

        last_assignment = address = "";

        while(getCurrentToken() == STRING) {

            address += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println("\taddress\t\t\t" + address);

        nextLevel();
    }
}
