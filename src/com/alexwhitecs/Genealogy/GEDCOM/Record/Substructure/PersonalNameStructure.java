package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;
import com.alexwhitecs.Genealogy.GEDCOM.Parser;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class PersonalNameStructure extends Parser {

    String givenName = "-givenName-", surname = "-surname-", maidenName;
    // TODO add Maiden Name?

    public PersonalNameStructure(String givenName, String surname){

        this.givenName = givenName;
        this.surname = surname;
    }

    public PersonalNameStructure() throws GEDCOM_Exception {

        accept(NAME);

        givenName = surname = "";

        while(getCurrentToken() == STRING) {

            if(getCurrentSpelling().charAt(0) == '/') {

                surname += (getCurrentSpelling().replace("/","") + " ");
            }
            else givenName += (getCurrentSpelling() + " ");
            accept(STRING);
        }

//        if(getCurrentToken() == TYPE) readNameType();
        // TODO add Personal Name Pieces etc.

        System.out.println(tabs() + "givenName: " + givenName);
        System.out.println(tabs() + "surname: " + surname);

        nextLevel();
    }

    public String getGivenName(){

        return givenName;
    }

    public String getSurname(){

        return surname;
    }
}
