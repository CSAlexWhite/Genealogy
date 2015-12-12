package com.alexwhitecs.Genealogy;

import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static com.alexwhitecs.Genealogy.FileOperations.*;
import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.getColumnAsArray;

public class Controller {

    @FXML TextField givenName;
    @FXML TextField maidenName;
    @FXML TextField surname;
    @FXML TextField sex;
    @FXML TextField dateOfBirth;
    @FXML TextField placeOfBirth;
    @FXML TextField dateOfDeath;
    @FXML TextField placeOfDeath;

    @FXML Button insertIndividual;

    @FXML ComboBox<String>  chooseFamily1 = new ComboBox<>();
    @FXML ComboBox<String>  chooseFamily2 = new ComboBox<>();
    @FXML ComboBox<String>  columnName1 = new ComboBox<>();
    @FXML ComboBox<String>  columnName2 = new ComboBox<>();

    @FXML
    public void insertIndividual(){

        String[] details = {givenName.getText(),
                            maidenName.getText(),
                            surname.getText(),
                            sex.getText(),
                            dateOfBirth.getText(),
                            placeOfBirth.getText(),
                            dateOfDeath.getText(),
                            placeOfDeath.getText()};

        new Individual(details);
    }

    @FXML
    public void populateChooseFamily1(){

        ObservableList<String> options = FXCollections.observableArrayList(getColumnAsArray("xref_id", "family"));
        chooseFamily1.setItems(options);
    }

    @FXML
    public void populateChooseFamily2(){

        ObservableList<String> options = FXCollections.observableArrayList(getColumnAsArray("xref_id", "family"));
        chooseFamily2.setItems(options);
    }

    public void setupLists(){

        populateColumn1();
        populateColumn2();
    }

    @FXML
    public void populateColumn1(){

        ObservableList<String> options = FXCollections.observableArrayList("xref_id", "given Name", "surname");
        columnName1.setItems(options);
    }

    @FXML
    public void populateColumn2(){

        ObservableList<String> options = FXCollections.observableArrayList("xref_id", "husband", "wife", "number children >" );
        columnName2.setItems(options);
    }

    @FXML
    public void printAllGEDCOM(){

        printIndividuals();
    }
}