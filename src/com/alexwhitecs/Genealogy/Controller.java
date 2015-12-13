package com.alexwhitecs.Genealogy;

import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Individual;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Structure.Family;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.ChildToFamilyLink;
import com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure.SpouseToFamilyLink;
import com.alexwhitecs.Genealogy.UserInterface.FamilyData;
import com.alexwhitecs.Genealogy.UserInterface.PersonData;
import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.alexwhitecs.Genealogy.Database.MySQL_Connector.*;
import static com.alexwhitecs.Genealogy.Database.Output.*;

public class Controller implements Initializable{

    @FXML TextField givenName;
    @FXML TextField middleName;
    @FXML TextField maidenName;
    @FXML TextField surname;
    @FXML ChoiceBox<String> sex;
    @FXML TextField dateOfBirth;
    @FXML TextField placeOfBirth;
    @FXML TextField dateOfDeath;
    @FXML TextField placeOfDeath;

    @FXML Button btnInsertIndividual;
    @FXML Button btnRefreshLists;
    @FXML Button btnMakeConnection;

    @FXML TableView<PersonData> individualsTable1;
    @FXML TableView<String> individualsTable2;
    @FXML TableView<FamilyData> familiesTable1;
    @FXML TableView<String> familiesTable2;

    @FXML TableView<PersonData> parentsTable;
    @FXML TableView<PersonData> siblingsTable;
    @FXML TableView<PersonData> currentIndividualTable;
    @FXML TableView<PersonData> childrenTable;
    @FXML TableView<PersonData> currentSpouse;

    @FXML TableColumn parentsGivenNameColumn;
    @FXML TableColumn parentsFamilyColumn;
    @FXML TableColumn siblingsGivenNameColumn;
    @FXML TableColumn siblingsFamilyColumn;
    @FXML TableColumn currentIndividualGivenNameColumn;
    @FXML TableColumn currentIndividualFamilyColumn;
    @FXML TableColumn childrenGivenNameColumn;
    @FXML TableColumn childrenFamilyColumn;
    @FXML TableColumn currentSpouseName;
    @FXML TableColumn currentSpouseFamily;

    @FXML TableColumn givenNameColumn1;
    @FXML TableColumn surnameColumn1;
    @FXML TableColumn idColumn1;

    @FXML TableColumn familyNameColumn;
    @FXML TableColumn headOfHouseholdColumn;

    @FXML ComboBox<String> chooseChildOf = new ComboBox<>();
    @FXML ComboBox<String> chooseSpouseOf = new ComboBox<>();
    @FXML ComboBox<String>  columnName1 = new ComboBox<>();
    @FXML ComboBox<String>  columnName2 = new ComboBox<>();

    @FXML ChoiceBox<String> childOrSpouse = new ChoiceBox<String>();

    @FXML CheckBox startFamily = new CheckBox();

    public void setup(){

        populateChooseFamily1();
        populateChooseFamily2();
        populateSex();
    }

    @FXML
    public void makeConnection(){

        String individual_id, family_id;

        PersonData tempPerson = individualsTable1.getSelectionModel().getSelectedItem();
        FamilyData tempFamily = familiesTable1.getSelectionModel().getSelectedItem();

        individual_id = tempPerson.getXref();
        family_id = tempFamily.getSurname();

        if(childOrSpouse.getValue() == "Child of"){

            new ChildToFamilyLink(individual_id, family_id);
        }

        if(childOrSpouse.getValue() == "Wife of"){

            new SpouseToFamilyLink(individual_id, family_id);
            updateTable("family", "wife = '" + individual_id + "'", "xref_id = '" + family_id + "'");
        }

        if(childOrSpouse.getValue() == "Husband of"){

            new SpouseToFamilyLink(individual_id, family_id);
            updateTable("family", "husband = '" + individual_id + "'", "xref_id = '" + family_id + "'");
        }

        if(childOrSpouse.getValue() == null) return;
    }

    @FXML
    public void intializeConnectionOptions(){

        ObservableList<String> options = FXCollections.observableArrayList("Child of", "Wife of", "Husband of");

        childOrSpouse.setItems(options);
    }

    @FXML
    public void populateIndividualsTable1() {

        ObservableList<PersonData> personData = FXCollections.observableArrayList();
        ObservableList<String[]> inputPersonData = getTableAsArray("given_name, surname, xref_id", "individual");

        for(String[] datum : inputPersonData){

            personData.add(new PersonData(datum[0], datum[1], datum[2]));
        }

        givenNameColumn1.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        surnameColumn1.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));
        idColumn1.setCellValueFactory(new PropertyValueFactory<PersonData, String>("xref"));

        individualsTable1.setItems(personData);

        ObservableList<FamilyData> familyData = FXCollections.observableArrayList();
        ObservableList<String[]> inputFamilyData = getTableAsArray("xref_id, husband", "family");

        for(String[] datum : inputFamilyData){

            familyData.add(new FamilyData(datum[0], datum[1]));
        }

        familyNameColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));
        headOfHouseholdColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("headOfHousehold"));

        familiesTable1.setItems(familyData);
    }

    @FXML
    public void setIndividual(){

        PersonData tempPerson = individualsTable1.getSelectionModel().getSelectedItem();
        String currentIndividual = tempPerson.getXref();
        populateFamilyTreeView(tempPerson);
    }

    @FXML
    public void resetIndividual(){

        PersonData tempPerson = currentIndividualTable.getSelectionModel().getSelectedItem();
        String currentIndividual = tempPerson.getXref();
        populateFamilyTreeView(tempPerson);
    }

    @FXML
    public void setChildren(){

        PersonData tempPerson = childrenTable.getSelectionModel().getSelectedItem();
        String currentIndividual = tempPerson.getXref();
        populateFamilyTreeView(tempPerson);
    }

    @FXML
    public void setSiblings(){

        PersonData tempPerson = siblingsTable.getSelectionModel().getSelectedItem();
        String currentIndividual = tempPerson.getXref();
        populateFamilyTreeView(tempPerson);
    }

    @FXML
    public void setParents(){

        PersonData tempPerson = parentsTable.getSelectionModel().getSelectedItem();
        String currentIndividual = tempPerson.getXref();
        populateFamilyTreeView(tempPerson);
    }

    @FXML
    public void setSpouse(){

        PersonData tempPerson = currentSpouse.getSelectionModel().getSelectedItem();
        String currentIndividual = tempPerson.getXref();
        populateFamilyTreeView(tempPerson);
    }

    @FXML
    public void populateFamilyTreeView(){

        String individual_xref = setCurrentIndividualTable();
        setParentsTable(individual_xref);
        setSiblingsTable(individual_xref);
        setChildrenTable(individual_xref);
        setCurrentSpouseTable(individual_xref);
    }


    private void populateFamilyTreeView(PersonData tempPerson){

        String individual_xref = setCurrentIndividualTable(tempPerson);
        setParentsTable(individual_xref);
        setSiblingsTable(individual_xref);
        setChildrenTable(individual_xref);
        setCurrentSpouseTable(individual_xref);
    }

    private String setCurrentIndividualTable(PersonData tempPerson){

        String individual = tempPerson.getXref();

        ObservableList<PersonData> individualData = FXCollections.observableArrayList();

        individualData.add(tempPerson);

        currentIndividualGivenNameColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        currentIndividualFamilyColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));

        currentIndividualTable.setItems(individualData);

        return individual;
    }

    private String setCurrentIndividualTable(){

        PersonData tempPerson = individualsTable1.getSelectionModel().getSelectedItem();
        String individual_xref = tempPerson.getXref();

        ObservableList<PersonData> individualData = FXCollections.observableArrayList();

        individualData.add(tempPerson);

        currentIndividualGivenNameColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        currentIndividualFamilyColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));

        currentIndividualTable.setItems(individualData);

        return individual_xref;
    }

    private void setSiblingsTable(String currentIndividual){

        ObservableList<PersonData> personData = FXCollections.observableArrayList();
        ObservableList<String[]> inputPersonData = getQueryAsArray( "SELECT given_name, surname, xref_id" +
                                                                    " FROM individual" +
                                                                    " WHERE xref_id IN " +
                                                                    " (SELECT individual_id" +
                                                                    " FROM child_family" +
                                                                    " WHERE family_id = " +
                                                                    " (SELECT family_id" +
                                                                    " FROM child_family" +
                                                                    " WHERE individual_id = '" + currentIndividual + "')" +
                                                                    " AND xref_id != '" + currentIndividual + "')");

        for(String[] datum : inputPersonData){

            personData.add(new PersonData(datum[0], datum[1], datum[2]));
        }

        siblingsGivenNameColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        siblingsFamilyColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));

        siblingsTable.setItems(personData);
    }

    private void setChildrenTable(String currentIndividual){

        ObservableList<PersonData> personData = FXCollections.observableArrayList();
        ObservableList<String[]> inputPersonData = getQueryAsArray( "SELECT given_name, surname, xref_id" +
                                                                    " FROM individual" +
                                                                    " WHERE xref_id IN " +
                                                                    " (SELECT individual_id" +
                                                                    " FROM child_family" +
                                                                    " WHERE family_id = " +
                                                                    " (SELECT family_id" +
                                                                    " FROM spouse_family" +
                                                                    " WHERE individual_id = '" + currentIndividual + "'))");

        for(String[] datum : inputPersonData){

            personData.add(new PersonData(datum[0], datum[1], datum[2]));
        }

        childrenGivenNameColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        childrenFamilyColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));

        childrenTable.setItems(personData);
    }

    private void setParentsTable(String currentIndividual){

        ObservableList<PersonData> personData = FXCollections.observableArrayList();
        ObservableList<String[]> inputPersonData = getQueryAsArray( "SELECT given_name, surname, xref_id " +
                                                                    " FROM individual " +
                                                                    " WHERE xref_id IN " +
                                                                    " (SELECT husband " +
                                                                    " FROM family " +
                                                                    " WHERE xref_id = " +
                                                                    " (SELECT family_id " +
                                                                    " FROM child_family " +
                                                                    " WHERE individual_id = '" + currentIndividual + "'))" +
                                                                    " OR xref_id IN " +
                                                                    " (SELECT wife " +
                                                                    " FROM family " +
                                                                    " WHERE xref_id =  " +
                                                                    " (SELECT family_id " +
                                                                    " FROM child_family " +
                                                                    " WHERE individual_id = '" + currentIndividual + "'))");

        for(String[] datum : inputPersonData){

            personData.add(new PersonData(datum[0], datum[1], datum[2]));
        }

        parentsGivenNameColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        parentsFamilyColumn.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));

        parentsTable.setItems(personData);
    }

    private void setCurrentSpouseTable(String currentIndividual){

        ObservableList<PersonData> personData = FXCollections.observableArrayList();
        ObservableList<String[]> inputPersonData = getQueryAsArray( " SELECT given_name, surname, xref_id" +
                                                                    " FROM individual" +
                                                                    " WHERE xref_id IN " +
                                                                    " (SELECT individual_id" +
                                                                    " FROM spouse_family" +
                                                                    " WHERE family_id = " +
                                                                    " (SELECT family_id" +
                                                                    " FROM spouse_family" +
                                                                    " WHERE individual_id = '" + currentIndividual + "')" +
                                                                    " AND xref_id != '" + currentIndividual + "')");

        for(String[] datum : inputPersonData){

            personData.add(new PersonData(datum[0], datum[1], datum[2]));
        }

        currentSpouseName.setCellValueFactory(new PropertyValueFactory<PersonData, String>("givenName"));
        currentSpouseFamily.setCellValueFactory(new PropertyValueFactory<PersonData, String>("surname"));

        currentSpouse.setItems(personData);
    }

    @FXML
    public void insertIndividual(){

        String[] details = {(givenName.getText()+ " " + middleName.getText()),
                            maidenName.getText(),
                            surname.getText(),
                            sex.getValue(),
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

        if(startFamily.isSelected()) {

            System.out.println("starting new family!");

            String newFamilyXREF = "@" + (givenName.getText() + "_" + surname.getText()).replace(" ", "").toUpperCase() + "@";

            new Family(newFamilyXREF, newPerson.getID(), sex.getValue());
            new SpouseToFamilyLink(newPerson, newFamilyXREF);

            if (sex.getValue() == "M")
                updateTable("family", "husband = '" + newPerson.getID() + "'", "xref_id = '" + newFamilyXREF + "'");
            else updateTable("family", "wife = '" + newPerson.getID() + "'", "xref_id = '" +newFamilyXREF + "'");
        }

        startFamily.setSelected(false);
        givenName.setText("");
        middleName.setText("");
        maidenName.setText("");
        surname.setText("");
        dateOfBirth.setText("");
        placeOfBirth.setText("");
        dateOfDeath.setText("");
        placeOfDeath.setText("");

        chooseChildOf.setValue(null);
        chooseSpouseOf.setValue(null);
        sex.setValue(null);
    }

    @FXML
    public void populateChooseFamily1(){

        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("");
        options.addAll(getColumnAsArray("xref_id", "family"));
        chooseChildOf.setItems(options);
    }

    @FXML
    public void populateChooseFamily2(){

        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("");
        options.addAll(getColumnAsArray("xref_id", "family"));
        chooseSpouseOf.setItems(options);
    }

    @FXML
    public void populateSex(){

        ObservableList<String> options = FXCollections.observableArrayList("M", "F");
        sex.setItems(options);
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
    public void printoutAllGEDCOM(){

        printGEDCOM();
    }

    @FXML
    public void printoutDescendancy(){

        printDescendants("@GroverWhite@");
    }

    @FXML
    public void printoutFamilyTree(){

        printFamilyTree("@AlexanderMalcolmWhite@");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("INITIALIZING");

        populateChooseFamily1();
        populateChooseFamily2();
        populateSex();
        intializeConnectionOptions();
    }
}