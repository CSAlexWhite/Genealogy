package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

/**
 * Created by alexw on 12/5/2015.
 */
public class IndividualEventDetail extends Parser{

    int age;

    EventDetail eventDetail;

    public IndividualEventDetail() throws GEDCOM_Exception {

        eventDetail = new EventDetail();
    }

    @Override
    public String toString() {

        return "";
    }
}