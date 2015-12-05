package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;

/**
 * Reads in the details of a family event and stores the data.
 */
public class FamilyEventDetail extends Parser{

    String husband, wife;       // TODO POINTERS TO THESE PEOPLE?
    int husbandAge, wifeAge;

    EventDetail eventDetail;

    public FamilyEventDetail() throws GEDCOM_Exception {

        eventDetail = new EventDetail();
    }

    @Override
    public String toString() {

        return "";
    }
}