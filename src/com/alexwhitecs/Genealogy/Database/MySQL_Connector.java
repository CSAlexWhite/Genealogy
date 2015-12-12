package com.alexwhitecs.Genealogy.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by alexw on 12/5/2015.
 */
public class MySQL_Connector {

    static Connection connection = null;
    static Statement statement = null;

    static final String URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASSWORD = "root";

    /**
     * Initiates the connection to the MySQL database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void connect() throws SQLException, ClassNotFoundException{

        Class.forName("com.mysql.jdbc.Driver");
        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            setupDB();
        }
        catch(SQLException error){

            System.out.println("Could not connect to Database :(");
            error.printStackTrace();
        }

        setupDB();
        createTables();

        getTableAsArray("individual");
        getTableAsArray("family");
        getTableAsArray("individual_event");
        getTableAsArray("place");
    }

    /**
     * performs the input SQL statement on the database, returns the primary key
     * of the last inserted row
     * @param inputStatement
     * @return
     */
    public static void executeSQL_Statement(String inputStatement){

        String sql = inputStatement;
        try{
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();

        } catch(SQLException error){error.printStackTrace();}
    }

    public static int getLastID(String tablename, String idname){

        int lastid = 0;

        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT MAX(" + idname + ") FROM " + tablename);

            while(rs.next()) lastid = rs.getInt(1);

            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return lastid;
    }

    public static String getResult(String select, String from, String where, String equals){

        String result = "";
        String query =  "SELECT " + select +
                        " FROM " + from +
                        " WHERE " + where +
                        " LIKE '" + equals +"'";
        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){

                result = rs.getString(select);
            }

            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return result;
    }

    public static ObservableList<String> getColumnAsArray(String select, String from, String where, String equals){

        ObservableList<String> results = FXCollections.observableArrayList();

        String query =  "SELECT " + select +
                " FROM " + from +
                " WHERE " + where +
                " LIKE '" + equals +"'";
        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) results.add(rs.getString(select));

            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return results;
    }

    public static ObservableList<String> getColumnAsArray(String select, String from, String groupBy){

        ObservableList<String> results = FXCollections.observableArrayList();

        String query =  "SELECT " + select +
                " FROM " + from +
                " GROUP BY " + groupBy;
        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) results.add(rs.getString(select));

            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return results;
    }

    public static ObservableList<String> getColumnAsArray(String select, String from){

        ObservableList<String> results = FXCollections.observableArrayList();

        String query =  "SELECT " + select +
                " FROM " + from;
        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()) results.add(rs.getString(select));

            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return results;
    }

    public static ArrayList<String[]> getTableAsArray(String select, String from, String where, String equals){

        ArrayList<String[]> rowValues = new ArrayList<String[]>();

        try {
            String query =  "SELECT " + select +
                    " FROM " + from +
                    " WHERE " + where +
                    " LIKE '" + equals +"'";

            statement = connection.createStatement();
            ResultSet individualData = statement.executeQuery(query);

            int cols = individualData.getMetaData().getColumnCount();
            while(individualData.next()){

                String[] row = new String[cols];

                for(int i=1; i<=cols; i++){

                   row[i-1] = individualData.getString(i);
                }

                rowValues.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for(String[] element : rowValues){
//
//            for(String elt : element) System.out.print(elt + "\t");
//            System.out.println();
//        }

        return rowValues;
    }

    public static ArrayList<String[]> getTableAsArray(String tablename){

        ArrayList<String[]> rowValues = new ArrayList<String[]>();

        try {
            String sql = "SELECT * FROM " + tablename;

            statement = connection.createStatement();
            ResultSet individualData = statement.executeQuery(sql);

            int cols = individualData.getMetaData().getColumnCount();
            while(individualData.next()){

                String[] row = new String[cols];

                for(int i=1; i<=cols; i++){

                    row[i-1] = individualData.getString(i);
                }

                rowValues.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for(String[] element : rowValues){
//
//            for(String elt : element) System.out.print(elt + "\t");
//            System.out.println();
//        }

        return rowValues;
    }

    public static ArrayList<String[]> getQueryAsArray(String query){

        ArrayList<String[]> rowValues = new ArrayList<String[]>();

        try {

            statement = connection.createStatement();
            ResultSet individualData = statement.executeQuery(query);

            int cols = individualData.getMetaData().getColumnCount();
            while(individualData.next()){

                String[] row = new String[cols];

                for(int i=1; i<=cols; i++){

                    row[i-1] = individualData.getString(i);
                }

                rowValues.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        for(String[] element : rowValues){
//
//            for(String elt : element) System.out.print(elt + "\t");
//            System.out.println();
//        }

        return rowValues;
    }

    private static void setupDB()throws SQLException{

            executeSQL_Statement("CREATE SCHEMA IF NOT EXISTS GENEALOGY");
            executeSQL_Statement("USE GENEALOGY");
    }

    private static boolean createTables() throws SQLException{

        String tableFile = "create_tables.sql", line = "", sql = "";
        boolean started;

        try {

            BufferedReader tableReader = new BufferedReader(new FileReader(tableFile));

            while ((line = tableReader.readLine()) != null) {

                if(line.trim().isEmpty()){

                    executeSQL_Statement(sql);
                    sql = "";
                }

                line = line.replaceAll("(\\n)", " ");
                sql += line;
            }

            executeSQL_Statement(sql);
        }
        catch(FileNotFoundException error){ error.printStackTrace(); return false;}
        catch(IOException error){ error.printStackTrace(); return false;}

        return true;
    }

    public static void close() throws SQLException{

        connection.close();
    }
}
