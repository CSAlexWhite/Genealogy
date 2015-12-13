package com.alexwhitecs.Genealogy.UserInterface;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by alexw on 12/12/2015.
 */
public class Person {

    SimpleStringProperty givenName;
    SimpleStringProperty surname;

    public Person(String givenName, String surname){

        this.givenName = new SimpleStringProperty(givenName);
        this.surname = new SimpleStringProperty(surname);
    }

    public String getGivenName(){

        return givenName.get();
    }

    public String getSurname(){

        return surname.get();
    }
}
