package com.alexwhitecs.Genealogy.Database;

import java.io.*;
import java.sql.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class MySQL_Connector {

    static Connection connection = null;
    static Statement statement = null;

    static final String URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASSWORD = "root";

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

//        executeSQL_Statement("INSERT INTO individual " +
//                "(x_ref_id, given_name, surname, sex) " +
//                "VALUES (" +
//                "\"" + "@someone@" + "\", " +
//                "\"" + "bobby" + "\", " +
//                "\"" + "tables" + "\", " +
//                "\"" + "M" + "\");");
    }

    /**
     * performs the input SQL statement on the database, returns the primary key
     * of the last inserted row
     * @param inputStatement
     * @return
     */
    public static int executeSQL_Statement(String inputStatement){

        int lastID = 0;

        String sql = inputStatement;
        try{
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();

        } catch(SQLException error){error.printStackTrace();}

        return lastID;
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
        }
        catch(FileNotFoundException error){ error.printStackTrace(); return false;}
        catch(IOException error){ error.printStackTrace(); return false;}

        return true;
    }

    public static void close() throws SQLException{

        connection.close();
    }
}
