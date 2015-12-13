package com.alexwhitecs.Genealogy.UserInterface;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by alexw on 12/12/2015.
 */
public class PersonData {

    SimpleStringProperty givenName;
    SimpleStringProperty surname;
    SimpleStringProperty xref;

    public PersonData(String givenName, String surname, String xref){

        this.givenName = new SimpleStringProperty(givenName);
        this.surname = new SimpleStringProperty(surname);
        this.xref = new SimpleStringProperty(xref);

    }

    public String getGivenName(){

        return givenName.get();
    }

    public String getSurname(){

        return surname.get();
    }

    public String getXref(){

        return xref.get();
    }
}
