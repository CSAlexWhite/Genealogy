package com.alexwhitecs.Genealogy.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Connects to MySQL database via JDBC
 * Contains methods to read/write/update given different parameters
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

    /**
     * Retrieves the value of the last ID in the given table and column
     * @param tablename
     * @param idname
     * @return
     */
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

    /**
     * Given a tableName, a SET statement, and a WHERE statement, updates the table
     * @param tablename
     * @param setStatement
     * @param whereStatement
     */
    public static void updateTable(String tablename, String setStatement, String whereStatement){

        String sql = "UPDATE " + tablename +
                     " SET " + setStatement +
                     " WHERE " + whereStatement;

        executeSQL_Statement(sql);
    }

    /**
     * Given a SELECT FROM WHERE EQUALS statment, retrieves a singular result, the first
     * @param select
     * @param from
     * @param where
     * @param equals
     * @return
     */
    public static String getResult(String select, String from, String where, String equals){

        String result = "";
        String query =  "SELECT " + select + " FROM " + from + " WHERE " + where + " LIKE '" + equals +"'";
        return getResult(query);
    }

    /**
     * Given a query, retireves a singular result
     * @param query
     * @return
     */
    public static String getResult(String query){

        String result = "";
        try{

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) result = rs.getString(1);
            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return result;
    }

    /**
     * Given a query, retrieves a result set as a two-dimensional array
     * @param query
     * @return
     */
    public static ArrayList<String[]> retrieveQuery(String query){

        ArrayList<String[]> rowValues = new ArrayList<String[]>();

        try {

            statement = connection.createStatement();
            ResultSet individualData = statement.executeQuery(query);
            int cols = individualData.getMetaData().getColumnCount();
            while(individualData.next()){
                String[] row = new String[cols];
                for(int i=1; i<=cols; i++) row[i-1] = individualData.getString(i);
                rowValues.add(row);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        return rowValues;
    }

    /**
     * Given a SELECT FROM WHERE EQUALS statement, retrieves a result as a two-dimensional array
     * @param select
     * @param from
     * @param where
     * @param equals
     * @return
     */
    public static ArrayList<String[]> getTableAsArray(String select, String from, String where, String equals){

        String query =  "SELECT " + select + " FROM " + from + " WHERE " + where + " LIKE '" + equals +"'";
        return retrieveQuery(query);
    }

    /**
     * Given a table name, retrieves its 2D array representation
     * @param tablename
     * @return
     */
    public static ArrayList<String[]> getTableAsArray(String tablename){

        String query = "SELECT * FROM " + tablename;
        return retrieveQuery(query);
    }

    /**
     * Given a SELECT FROM WHERE EQUALS statement, returns a column as an Observable List
     * @param select
     * @param from
     * @param where
     * @param equals
     * @return
     */
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

    /**
     * Given a SELECT FROM statement, returns a column as an Observable List
     * @param select
     * @param from
     * @return
     */
    public static ObservableList<String> getColumnAsArray(String select, String from){

        ObservableList<String> results = FXCollections.observableArrayList();
        String query =  "SELECT " + select + " FROM " + from;

        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) results.add(rs.getString(select));
            rs.close(); statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return results;
    }


    /**
     * Given a SELECT FROM statement, retrieves a result as an Observable List of arrays
     * @param select
     * @param from
     * @return
     */
    public static ObservableList<String[]> getTableAsArray(String select, String from){

            String query =  "SELECT " + select + " FROM " + from;
            return getQueryAsArray(query);
    }

    /**
     * Given a query, retrieves the result set as an Observable List of arrays
     * @param query
     * @return
     */
    public static ObservableList<String[]> getQueryAsArray(String query){

        ObservableList<String[]> rowValues =FXCollections.observableArrayList();

        try {

            statement = connection.createStatement();
            ResultSet individualData = statement.executeQuery(query);
            int cols = individualData.getMetaData().getColumnCount();
            while(individualData.next()){
                String[] row = new String[cols];
                for(int i=1; i<=cols; i++) row[i-1] = individualData.getString(i);
                rowValues.add(row);
            }

        } catch (SQLException e) {e.printStackTrace();}

        return rowValues;
    }

    /**
     * Sets up the Database
     * @throws SQLException
     */
    private static void setupDB()throws SQLException{

        executeSQL_Statement("CREATE SCHEMA IF NOT EXISTS GENEALOGY");
        executeSQL_Statement("USE GENEALOGY");
    }

    /**
     * Builds the necessary tables from a SQL file
     * @return
     * @throws SQLException
     */
    private static boolean createTables() throws SQLException{

        String tableFile = "create_tables.sql", line = "", sql = "";

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
