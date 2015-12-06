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
        dbExists();
        createTables();

        //createTables();
    }

    public static void instruct(String inputStatement){

        String sql = inputStatement;
        try{
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch(SQLException error){}
    }

    private static void setupDB()throws SQLException{

            instruct("CREATE SCHEMA IF NOT EXISTS GENEALOGY");
            instruct("USE GENEALOGY");
    }

    private static boolean dbExists() throws SQLException{

        ResultSet resultSet = connection.getMetaData().getCatalogs();

        while (resultSet.next()) {

//            if(resultSet.getString(1)=="genealogy") {
//
//                resultSet.close();
//                return true;
//            }
            System.out.println(resultSet.getString(1));
        }
        resultSet.close();

        return false;
    }

    private static boolean createTables() throws SQLException{

        String tableFile = "create_tables.sql", line = "", sql = "";

        try {

            BufferedReader tableReader = new BufferedReader(new FileReader(tableFile));

            while ((line = tableReader.readLine()) != null) {

                line = line.replaceAll("(\\n)", " ");
                sql += line;

                if(line.trim().isEmpty()){

                    instruct(sql);
                    sql = "";
                }
            }
        }
        catch(FileNotFoundException error){ error.printStackTrace(); return false;}
        catch(IOException error){ error.printStackTrace(); return false;}

        System.out.println(sql);

        instruct(sql);

        return true;
    }

    public static void close() throws SQLException{

        connection.close();
    }
}
