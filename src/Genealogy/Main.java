package Genealogy;

import GEDCOM.Importer;
import GEDCOM.SourceHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static SourceHandler input;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {

//        try{

            String inFile = "test.txt";

            new Importer(inFile);

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

//        catch(SourceException error){
//
//            error.printStackTrace();
//        }
        Platform.exit();
    }
}
