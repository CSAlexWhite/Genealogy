package com.alexwhitecs.Genealogy;

import javafx.collections.ObservableList;

import java.util.ArrayList;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.*;

/**
 * Created by alexw on 12/12/2015.
 */
public class FileOperations {

    public static void printIndividuals(){

        // TODO get specific with columns here
        ArrayList<String[]> people = getTableAsArray("individual");

        for(String[] person : people){

            System.out.println("0 " + person[1] + " INDI");
            System.out.println("1 " + "NAME " + person[2] + " /" + person[3] + "/");
            System.out.println("1 " + "SEX " + person[4]);

            ArrayList<String[]> events = getTableAsArray(   "type, date, place_id",
                                                            "individual_event",
                                                            "individual_xref",
                                                            person[1]);

            for(String[] event : events){

                System.out.println("1 " + event[0] + " " + event[1]);

                ArrayList<String[]> places = getTableAsArray(   "place_name",
                                                                "place",
                                                                "place_id",
                                                                event[2]);

                for(String[] place : places){

                    System.out.println("2 PLACE " + place[0]);
                }
            }
        }
    }
}
