package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Family;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.executeSQL_Statement;
import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.getResult;
import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;

/**
 * Reads in a structure of events associated with a family and stores
 * their data appropriately
 */
public class FamilyEventStructure extends Parser{

    String eventType = "";
    Family family;
    FamilyEventDetail eventDetail;

    String husbandAt;
    String wifeAt;
    String familyID;

    public FamilyEventStructure(String familyID, String individualID1,
                                String individualID2, String eventType,
                                String date, String place){

        this.eventType = eventType;
        this.familyID = familyID;
        this.husbandAt = individualID1;
        this.wifeAt = individualID2;
        this.eventDetail = new FamilyEventDetail(date, place);

        pushToDB();
    }

    public FamilyEventStructure(Family family) throws GEDCOM_Exception {

        this.family = family;
        eventType = getCurrentToken().getCode();
        System.out.println(tabs() + eventType);
        accept(getCurrentToken());
        eventDetail = new FamilyEventDetail();

        husbandAt = family.getHusband();
        wifeAt = family.getWife();
        familyID = family.getID();

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

        String date = eventDetail.eventDetail.date;

        String place_id =
                getResult("place_id", "place", "place_name", placeName);

        /* INSERT THE INDIVIDUAL EVENT DETAILS */
        sql = "INSERT INTO family_event" +
                " (family_xref, type, date, place_id, event_husband, event_wife)" +
                " SELECT * FROM (SELECT " +
                "\"" + familyID + "\", " +
                "\"" + eventType + "\", " +
                "\"" + date.replace(" , ", ", ") + "\", " +
                "\"" + place_id + "\", " +
                "\"" + husbandAt + "\", " +
                "\"" + wifeAt + "\") " +
                " AS tmp" +
                " WHERE NOT EXISTS (" +
                " SELECT family_xref FROM family_event " + " " +
                "WHERE family_xref = " +
                "\"" + familyID + "\"" +
                " AND type = \"" + eventType +
                "\" OR date = \"" + date +
                "\" ) LIMIT 1;";

        System.out.println(sql);
        executeSQL_Statement(sql);
    }

    @Override
    public String toString() {

        return "";
    }
}