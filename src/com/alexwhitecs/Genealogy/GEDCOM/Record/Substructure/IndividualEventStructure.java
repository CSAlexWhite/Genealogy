package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.executeSQL_Statement;
import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.getResult;
import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class IndividualEventStructure extends Parser{

    String familyID = "";
    String eventType = "";
    Individual individual;
    IndividualEventDetail eventDetail;

    public IndividualEventStructure(Individual individual, String eventType, String date, String place) throws GEDCOM_Exception{

        this.eventType = eventType;
        this.eventDetail = new IndividualEventDetail(date, place);
        this.individual = individual;
        pushToDB();
    }

    public IndividualEventStructure(Individual individual) throws GEDCOM_Exception {

        this.individual = individual;
        eventType = getCurrentToken().getCode();
        System.out.println(tabs() + eventType );
        accept(getCurrentToken());
        eventDetail = new IndividualEventDetail();
        if(getCurrentToken() == FAMC) setFamily();

        pushToDB();
    }

    private void pushToDB() {

        // 1. INSERT A NEW PLACE, GET THE PLACE ID
        // 2. INSERT EVENT DETAILS
        // TODO ERASE FAMILY ID AND ADOPPED BY FROM PLACE...

        String placeName = eventDetail.getEventDetail().placeStructure.placeName;

        /* INSERT THE PLACE DETAILS */
        String sql = "INSERT INTO place" +
                " (place_name)" +
                " SELECT * FROM (SELECT " +
                "\"" + placeName + "\") " +
                " AS tmp" +
                " WHERE NOT EXISTS (" +
                " SELECT place_name FROM place WHERE place_name = " +
                "\"" + placeName + "\"" +
                " ) LIMIT 1;";

        executeSQL_Statement(sql);

        String place_id =
                getResult("place_id", "place", "place_name", placeName);

        String date = eventDetail.eventDetail.date;

        /* INSERT THE INDIVIDUAL EVENT DETAILS */
        sql = "INSERT INTO individual_event" +
                " (individual_xref, type, date, place_id)" +
                " SELECT * FROM (SELECT " +
                "\"" + individual.getID() + "\", " +
                "\"" + eventType + "\", " +
                "\"" + date + "\", " +
                "\"" + place_id + "\") " +
                " AS tmp" +
                " WHERE NOT EXISTS (" +
                " SELECT type FROM individual_event " +
                " WHERE individual_xref = " +               // NO SAME EVENT SAME DATE
                "\"" + individual.getID() + "\"" +
                " AND type = \"" + eventType +
                "\" AND date = \"" + date +
                "\" ) LIMIT 1;";

        // TODO need to change the check against individuals, else only one event per individual

        executeSQL_Statement(sql);
    }

    private void setFamily() {


    }

    @Override
    public String toString() {

        return eventDetail.toString();
    }
}