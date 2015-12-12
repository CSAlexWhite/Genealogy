package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

import java.util.Vector;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Reads in the data associated with a Place and stores them appropriately.
 */
public class PlaceStructure extends Parser{

    String placeName = "", placeHierarchy, phoneticVariation,
            phoneticType, romanizedVariation, romanizedType;
    String lastAssignment;

    String latitutde = "", longitude = "";

    Vector<NoteStructure> notes;

    public PlaceStructure(String placeName){

        this.placeName = placeName;
    }

    public PlaceStructure() throws GEDCOM_Exception {

        accept(PLAC);
        lastAssignment = placeName = "";

        while(getCurrentToken() == STRING){

            placeName += (getCurrentSpelling() + " ");
            accept(STRING);
        }

        System.out.println(tabs() + "placeName: " + placeName);
        nextLevel();
    }

    @Override
    public String toString() {

        return "";
    }
}