package com.alexwhitecs.Genealogy;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.ChildToFamilyLink;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.SpouseToFamilyLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static com.alexwhitecs.Genealogy.Database.Output.*;
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

    @FXML ComboBox<String> chooseChildOf = new ComboBox<>();
    @FXML ComboBox<String> chooseSpouseOf = new ComboBox<>();
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

        Individual newPerson = new Individual(details);

        if(!(chooseChildOf.getValue() == null) && !chooseChildOf.getValue().isEmpty()) {

            System.out.println("choose Child of is: " + chooseChildOf.getValue());
            new ChildToFamilyLink(newPerson, chooseChildOf.getValue());
        }

        if(!(chooseSpouseOf.getValue() == null) && !chooseSpouseOf.getValue().isEmpty()){

            System.out.println("choose Spouse of is: " + chooseSpouseOf.getValue());
            new SpouseToFamilyLink(newPerson, chooseSpouseOf.getValue());
        }
    }

    @FXML
    public void populateChooseFamily1(){

        ObservableList<String> options = FXCollections.observableArrayList(getColumnAsArray("xref_id", "family"));
        chooseChildOf.setItems(options);
    }

    @FXML
    public void populateChooseFamily2(){

        ObservableList<String> options = FXCollections.observableArrayList(getColumnAsArray("xref_id", "family"));
        chooseSpouseOf.setItems(options);
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

        printGEDCOM();
    }
}