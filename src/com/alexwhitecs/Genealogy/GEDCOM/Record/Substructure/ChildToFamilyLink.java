package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Family;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;

import java.util.Vector;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.executeSQL_Statement;
import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Associates a child with a family.
 */
public class ChildToFamilyLink extends Parser{

    String individualID, familyID, pedigreeLinkageType, linkageStatus;

    Vector<NoteStructure> notes;

    Individual child;
    Family family;

    public ChildToFamilyLink(Individual person, String family_xref){

        child = person;
        familyID = family_xref;
        individualID = person.getID();

        pushToDB();
    }

    public ChildToFamilyLink(Individual person) throws GEDCOM_Exception {

        child = person;
        family = null;

        individualID = person.getID();

        accept(FAMC);
        familyID = getCurrentSpelling();

        pushToDB();

        System.out.println(tabs() + "familyID as child: " + familyID);
        accept(POINTER);
        nextLevel();
    }

    private void pushToDB() {

        String sql = "INSERT INTO child_family" +
                " (individual_id, family_id)" +
                " SELECT * FROM (SELECT " +
                "\"" + individualID + "\", " +
                "\"" + familyID + "\") " +
                " AS tmp" +
                " WHERE NOT EXISTS (" +
                " SELECT family_id FROM child_family " + " " +
                " WHERE family_id = " +
                "\"" + familyID + "\"" +
                " AND individual_id = \"" + individualID +
                "\" ) LIMIT 1;";

        executeSQL_Statement(sql);
    }

    @Override
    public String toString() {

        return "";
    }
}