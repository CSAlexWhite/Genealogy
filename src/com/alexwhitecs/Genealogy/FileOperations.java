package com.alexwhitecs.Genealogy;

import javafx.collections.ObservableList;

import java.util.ArrayList;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.*;

/**
 * Created by alexw on 12/12/2015.
 */
public class FileOperations {

    public static void printGEDCOM(){

        printHeader();
        printIndividuals();
        printFamilies();

        System.out.println("0 TRLR");
    }

    public static void printHeader(){

        System.out.println("0 HEAD");
        System.out.println("1 CHAR ASCII");
        System.out.println("1 SOUR GENEA: FAMILY TREE SOFTWARE");
        System.out.println("1 GEDC");
        System.out.println("2 VERS 5.5.1");
        System.out.println("2 FORM Lineage-Linked");
        System.out.println("1 SUBM @ALEXWHITE@");
        System.out.println("0 @SUBMITTER@ SUBM");
        System.out.println("1 NAME Alex White");
        System.out.println("1 ADDR alexwhite123@gmail.com");
    }

    public static void printIndividuals(){

        // TODO get specific with columns here
        ArrayList<String[]> people = getTableAsArray("individual");

        for(String[] person : people){

            String surname = ("/" + person[3] + "/").replace(" ", "");

            System.out.println("0 " + person[1] + " INDI");
            System.out.println("1 " + "NAME " + person[2] + " " + surname);
            System.out.println("1 " + "SEX " + person[4]);

            ArrayList<String[]> events = getTableAsArray(   "type, date, place_id",
                                                            "individual_event",
                                                            "individual_xref",
                                                            person[1]);

            for(String[] event : events){

                System.out.println("1 " + event[0]);

                String place = getResult(   "place_name",
                                            "place",
                                            "place_id",
                                            event[2]);

                System.out.println("2 PLAC " + place.trim());

                System.out.println("2 DATE " + event[1].trim());
            }

            ArrayList<String[]> famCs = getTableAsArray(    "family_id",
                                                            "child_family",
                                                            "individual_id",
                                                            person[1]);

            for(String[] famC : famCs) System.out.println("1 FAMC " + famC[0]);


            ArrayList<String[]> famSs = getTableAsArray(    "family_id",
                                                            "spouse_family",
                                                            "individual_id",
                                                            person[1]);

            for(String[] famS : famSs) System.out.println("1 FAMS " + famS[0]);
        }
    }

    public static void printFamilies(){

        System.out.println("");

        ArrayList<String[]> families = getTableAsArray("family");

        for(String[] family : families) {

            System.out.println("0 " + family[1] + " FAM");

            ArrayList<String[]> events = getTableAsArray("type, date, place_id",
                    "family_event",
                    "family_xref",
                    family[1]);

            for (String[] event : events) {

                System.out.println("1 " + event[0]);

                String place = getResult("place_name",
                        "place",
                        "place_id",
                        event[2]);

                System.out.println("2 PLAC " + place.trim());

                System.out.println("2 DATE " + event[1].trim());
            }

            System.out.println("1 HUSB " + family[2]);
            System.out.println("1 WIFE " + family[3]);

            ArrayList<String[]> children = getTableAsArray(     "individual_id",
                                                                "child_family",
                                                                "family_id",
                                                                family[1]);

            for(String[] child : children){

                System.out.println("1 CHIL " + child[0]);
            }
        }
    }
}
