package com.alexwhitecs.Genealogy;

import com.alexwhitecs.Genealogy.Database.MySQL_Connector;
import com.alexwhitecs.Genealogy.GEDCOM.Importer;
import com.alexwhitecs.Genealogy.GEDCOM.SourceHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

//        try{

            String inFile = "white.gedcom";



        try{

            MySQL_Connector.connect();

            new Importer(inFile);

            MySQL_Connector.close();
        }
        catch(Exception error){error.printStackTrace();}



//            input = new SourceHandler(inFile);
//            Tokenizer.setIO(input);
//            Tokenizer.nextToken();
//
//            //launch(args);
//
//            for(int i=0; i<100; i++){
//                input.nextChar();
//            }
//        }

//        catch(GEDCOM_Exception error){
//
//            error.printStackTrace();
//        }
        Platform.exit();
    }
}
