package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Family;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Associates a child with a family.
 */
public class ChildToFamilyLink extends Parser{

    String individualID, familyID;

    Individual child;
    Family family;

    public ChildToFamilyLink(Individual person) throws GEDCOM_Exception {

        child = person;
        family = null;

        individualID = person.getID();

        accept(FAMC);
        familyID = getCurrentSpelling();

        System.out.println(tabs() + "familyID as child: " + familyID);
        accept(POINTER);
        nextLevel();
    }

    @Override
    public String toString() {

        return "";
    }
}