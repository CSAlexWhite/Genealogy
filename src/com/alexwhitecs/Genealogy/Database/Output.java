package com.alexwhitecs.Genealogy.Database;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.*;

/**
 * Created by alexw on 12/12/2015.
 */
public class Output {

    static PrintWriter writer;

    static int treeLevel = 0;

    private static void printLevel(){

        if(treeLevel == 1) {
            writer.print("\t");
            return;
        }

        for(int i=1; i<treeLevel; i++) writer.print("\t");
            writer.print("|-->");
    }

    private static String dashes(){

        String dashes = "";

        for(int i=1; i<Math.ceil(Math.sqrt(Math.pow(10, 4-treeLevel))); i++) dashes += "-";

        return dashes;
    }

    private static String tabs(){

        String tabs = "";

        for(int i=1; i<treeLevel; i++) tabs += "\t";
        tabs += "\t";

        return tabs;
    }

    public static void flush(){

        writer.flush();
    }

    public static void printFamilyTree(File outputfile, String individualXREF){

        try {
            writer = new PrintWriter(outputfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("***************************");
        System.out.println("FAMILY TREE OF " +
                getResult("given_name", "individual", "xref_id", individualXREF).trim() + " " +
                getResult("surname", "individual", "xref_id", individualXREF).trim());

        printTree(individualXREF);
    }

    static int possibleLevels = 5;
    static Vector<StringBuilder> layers = new Vector<StringBuilder>();
    public static void printTree(String individualXREF){

        for(int i=0; i<10; i++) layers.add(new StringBuilder());

        treeLevel = 0;
        System.out.println( getResult("given_name", "individual", "xref_id", individualXREF).trim() + " " +
                getResult("surname", "individual", "xref_id", individualXREF).trim());

        getParents(individualXREF);

        for(StringBuilder layer : layers){

            if(layer.equals("")) continue;
            System.out.println(layer.toString());
        }

    }

    public static void getParents(String individualXREF) {

        treeLevel++;

        Vector<String> parentsof = new Vector<String>();

        if (individualXREF.trim() == "") return;

        String familyXREF = getResult("family_id", "child_family", "individual_id", individualXREF);

        if (familyXREF.trim() == "") return;

        String husband = getResult("husband", "family", "xref_id", familyXREF);
        String wife = getResult("wife", "family", "xref_id", familyXREF);

        if (!(husband.trim() == "")) {
            layers.elementAt(treeLevel).append(dashes() + getResult("given_name", "individual", "xref_id", husband).trim() + " " +
                    getResult("surname", "individual", "xref_id", husband).trim() + dashes());

        }

        if (!(wife.trim() == "")) {
            layers.elementAt(treeLevel).append((dashes() + getResult("given_name", "individual", "xref_id", wife).trim() + " " +
                    getResult("surname", "individual", "xref_id", wife).trim())+ dashes());

        }

        getParents(husband);treeLevel--;
        getParents(wife);treeLevel--;
    }

    public static void printDescendants(File outputfile, String individualXREF){

        try {
            writer = new PrintWriter(outputfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        treeLevel = 0;
        writer.println("***************************");
        writer.println("DESCENDANTS OF " +
                getResult("given_name", "individual", "xref_id", individualXREF).trim() + " " +
                getResult("surname", "individual", "xref_id", individualXREF).trim());

        printDescendancy(individualXREF);
    }

    public static void printDescendancy(String individualXREF){

        treeLevel++;
        printLevel();
        if(individualXREF == "") return;

        String[] spouseInfo = getSpouse(individualXREF);
        String spouse = spouseInfo[0];

        String spouse1 = (getResult("given_name", "individual", "xref_id", individualXREF).trim() + " " +
                          getResult("surname", "individual", "xref_id", individualXREF).trim()).trim();

        String spouse1Birth = getResult("SELECT date" +
                                        " FROM individual_event" +
                                        " WHERE individual_event.individual_xref = '" + individualXREF +
                                        "' AND individual_event.`type` LIKE \"BIRT\" ");

        String spouse1Death = getResult(" SELECT date" +
                                        " FROM individual_event" +
                                        " WHERE individual_event.individual_xref = '" + individualXREF +
                                        "' AND individual_event.`type` LIKE \"DEAT\" ");

        spouse1 += "\n" + tabs() + "b. " + spouse1Birth;
        if(!spouse1Death.isEmpty()) spouse1 += " d. " + spouse1Death;

        String spouse2 = (getResult("given_name", "individual", "xref_id", spouse).trim() + " " +
                         getResult("surname", "individual", "xref_id", spouse).trim()).trim();

        ObservableList<String> children = getChildren(individualXREF, spouseInfo[1]);

        if(!spouse2.isEmpty()) {
            writer.print(spouse1 + "\n" + tabs() + "m. " + spouse2);
            writer.println(", " + getResult("date", "family_event", "family_xref",
                    getResult("xref_id", "family", spouseInfo[1], individualXREF)));
        }

        else writer.println(spouse1);

        for(String child : children) printDescendancy(child);
        treeLevel--;
    }

    public static String[] getSpouse(String individualXREF){

        String partnerType;

        // TODO MULTIPLE SPOUSES
        String spouse = getResult("wife", "family", "husband", individualXREF);
        partnerType = "husband";

        if(spouse == "") {

            spouse = getResult("husband", "family", "wife", individualXREF);
            partnerType = "wife";
        }

        String[] result = {spouse, partnerType};

        return result;
    }

    public static ArrayList<String[]> getSpouses(String individualXREF){

        ArrayList<String[]> information, results;

        String partnerType = "husband";
        information = getTableAsArray("wife", "family", partnerType, individualXREF);

        if(information.isEmpty()){

            partnerType = "wife";
            information = getTableAsArray("husband", "family", partnerType, individualXREF);
        }

        results = new ArrayList<>();
        for(String[] info : information){

            String[] result = {info[0], partnerType};
            results.add(result);
        }

        return results;
    }

    public static ObservableList<String> getChildren(String individualXREF, String spouseType){

        String familyXREF = getResult("xref_id", "family", spouseType, individualXREF);

        ObservableList<String> children_xref = getColumnAsArray("individual_id", "child_family", "family_id", familyXREF);
        Vector<String> children = new Vector<String>();

        return children_xref;
    }

    public static void printGEDCOM(){

        printHeader();
        printIndividuals();
        printFamilies();

        writer.println("0 TRLR");

        writer.flush();
        writer.close();

        writer.println("Data written to main.gedcom");
    }

    public static void saveAsGEDCOM(File outputfile){

        try {
            writer = new PrintWriter(outputfile);
        } catch (FileNotFoundException e) {e.printStackTrace();}

        printGEDCOM();
    }

    public static void saveGEDCOM(){

        try {
            writer = new PrintWriter("main.gedcom");
        } catch (FileNotFoundException e) {e.printStackTrace();}

        printGEDCOM();
    }

    public static void printHeader(){

        writer.println("0 HEAD");
        writer.println("1 CHAR ASCII");
        writer.println("1 SOUR GENEA: FAMILY TREE SOFTWARE");
        writer.println("1 GEDC");
        writer.println("2 VERS 5.5.1");
        writer.println("2 FORM Lineage-Linked");
        writer.println("1 SUBM @ALEXWHITE@");
        writer.println("0 @SUBMITTER@ SUBM");
        writer.println("1 NAME Alex White");
        writer.println("1 ADDR alexwhite123@gmail.com");
    }

    public static void printIndividuals(){

        // TODO get specific with columns here
        ArrayList<String[]> people = getTableAsArray("individual");

        for(String[] person : people){

            String surname = ("/" + person[3] + "/").replace(" ", "");

            writer.println("0 " + person[1] + " INDI");
            writer.println("1 " + "NAME " + person[2] + " " + surname);
            writer.println("1 " + "SEX " + person[4]);

            ArrayList<String[]> events = getTableAsArray(   "type, date, place_id",
                                                            "individual_event",
                                                            "individual_xref",
                                                            person[1]);

            for(String[] event : events){

                writer.println("1 " + event[0]);

                String place = getResult(   "place_name",
                                            "place",
                                            "place_id",
                                            event[2]);

                writer.println("2 PLAC " + place.trim());

                writer.println("2 DATE " + event[1].trim());
            }

            ArrayList<String[]> famCs = getTableAsArray(    "family_id",
                                                            "child_family",
                                                            "individual_id",
                                                            person[1]);

            for(String[] famC : famCs) writer.println("1 FAMC " + famC[0]);


            ArrayList<String[]> famSs = getTableAsArray(    "family_id",
                                                            "spouse_family",
                                                            "individual_id",
                                                            person[1]);

            for(String[] famS : famSs) writer.println("1 FAMS " + famS[0]);
        }
    }

    public static void printFamilies(){

        writer.println("");

        ArrayList<String[]> families = getTableAsArray("family");

        for(String[] family : families) {

            writer.println("0 " + family[1] + " FAM");

            ArrayList<String[]> events = getTableAsArray("type, date, place_id",
                    "family_event",
                    "family_xref",
                    family[1]);

            for (String[] event : events) {

                writer.println("1 " + event[0]);

                String place = getResult("place_name",
                        "place",
                        "place_id",
                        event[2]);

                writer.println("2 PLAC " + place.trim());

                writer.println("2 DATE " + event[1].trim());
            }

            writer.println("1 HUSB " + family[2]);
            writer.println("1 WIFE " + family[3]);

            ArrayList<String[]> children = getTableAsArray(     "individual_id",
                                                                "child_family",
                                                                "family_id",
                                                                family[1]);

            for(String[] child : children){

                writer.println("1 CHIL " + child[0]);
            }
        }
    }
}
