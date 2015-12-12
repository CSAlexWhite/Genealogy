package com.alexwhitecs.Genealogy;

import com.alexwhitecs.Genealogy.Database.MySQL_Connector;
import com.alexwhitecs.Genealogy.GEDCOM.Importer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Controller guiController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Genea");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();

        //guiController = new Controller();
        guiController = fxmlLoader.getController();
        //guiController.setupLists();
    }

    public static void main(String[] args) {

        String inFile = "white.gedcom";

        try{

            MySQL_Connector.connect();

            new Importer(inFile);

            launch(args);

            MySQL_Connector.close();
        }
        catch(Exception error){error.printStackTrace();}


        Platform.exit();
    }
}
