package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class IndividualEventStructure extends Parser{

    String date, familyID;
    String eventType;
    IndividualEventDetail eventDetail;

    public IndividualEventStructure() throws GEDCOM_Exception {

        eventType = getCurrentToken().getCode();
        System.out.println(tabs() + eventType );
        accept(getCurrentToken());
        eventDetail = new IndividualEventDetail();
        if(getCurrentToken() == FAMC) setFamily();
    }

    private void setFamily() {


    }

    @Override
    public String toString() {

        return eventDetail.toString();
    }
}