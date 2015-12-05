package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;

/**
 * Reads in a structure of events associated with a family and stores
 * their data appropriately
 */
public class FamilyEventStructure extends Parser{

    String date;
    String eventType;
    FamilyEventDetail eventDetail;

    public FamilyEventStructure() throws GEDCOM_Exception {

        eventType = getCurrentToken().getCode();
        System.out.println(tabs() + eventType);
        accept(getCurrentToken());
        eventDetail = new FamilyEventDetail();
    }

    @Override
    public String toString() {

        return "";
    }
}