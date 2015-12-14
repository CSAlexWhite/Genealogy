package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;

/**
 * Reads in the details of a family event and stores the data.
 */
public class FamilyEventDetail extends Parser{

    String husband = "", wife = "";       // TODO POINTERS TO THESE PEOPLE?
    int husbandAge = 0, wifeAge = 0;

    EventDetail eventDetail;

    public FamilyEventDetail(String date, String place){

        this.eventDetail = new EventDetail(date, place);
    }

    public FamilyEventDetail() throws GEDCOM_Exception {

        eventDetail = new EventDetail();
    }

    @Override
    public String toString() {

        return "";
    }

    public EventDetail getEventDetail(){

        return eventDetail;
    }
}