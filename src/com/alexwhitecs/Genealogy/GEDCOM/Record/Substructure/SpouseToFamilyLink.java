package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Family;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;

import java.util.Vector;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Associates a spouse, either a mother or father, with a family.
 */
public class SpouseToFamilyLink extends Parser{

    String individualID, familyID;

    Individual spouse;
    Family family;

    Vector<NoteStructure> notes;

    public SpouseToFamilyLink(Individual person) throws GEDCOM_Exception {

        spouse = person;
        family = null; // TODO ADD REAL LINK SOMEHOW!

        individualID = person.getID();

        accept(FAMS);
        familyID = getCurrentSpelling();

        System.out.println(tabs() + "familyID as spouse: " + familyID);
        accept(POINTER);
        nextLevel();
    }

    @Override
    public String toString() {

        return "";
    }
}