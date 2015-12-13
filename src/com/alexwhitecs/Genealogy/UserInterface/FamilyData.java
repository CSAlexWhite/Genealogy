package com.alexwhitecs.Genealogy.UserInterface;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by alexw on 12/12/2015.
 */
public class FamilyData {

    SimpleStringProperty surname;
    SimpleStringProperty headOfHousehold;

    public FamilyData(String surname, String headOfHousehold){

        this.surname = new SimpleStringProperty(surname);
        this.headOfHousehold = new SimpleStringProperty(headOfHousehold);
    }

    public String getSurname(){

        return surname.get();
    }

    public String getHeadOfHousehold(){

        return headOfHousehold.get();
    }
}
