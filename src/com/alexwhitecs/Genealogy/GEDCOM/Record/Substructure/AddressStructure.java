package com.alexwhitecs.Genealogy.GEDCOM.Record.Substructure;

import com.alexwhitecs.Genealogy.GEDCOM.Parser;
import com.alexwhitecs.Genealogy.GEDCOM.GEDCOM_Exception;

import static com.alexwhitecs.Genealogy.GEDCOM.Tokenizer.*;
import static com.alexwhitecs.Genealogy.GEDCOM.Symbols.*;

/**
 * Created by alexw on 12/5/2015.
 */
public class AddressStructure extends Parser{

    String lastAssignment;
    String addressLine, addressLine1, addressLine2, addressLine3,
            city, state, postCode, country, phone, email, fax, webPage;

    public AddressStructure() throws GEDCOM_Exception {

        init();

        accept(ADDR);

        lastAssignment = addressLine = "";

        while(getCurrentToken() == STRING
                || getCurrentToken() == NUMERIC) {

            System.out.println(getCurrentSpelling());

            addressLine += (getCurrentSpelling() + " ");
            accept(getCurrentToken());
        }

        System.out.println(tabs() + "address: " + addressLine);

        nextLevel();
    }

    @Override
    public String toString() {

        // TODO MAKE THIS WORK BETTER
        return addressLine + "\n" + addressLine1 + "\n" + addressLine2 + "\n" +
                addressLine3 + "\n" + city + "\n" + state + " " + postCode + "\n" +
                country + "\n" + phone + "\n" + email + "\n" + fax + "\n" + webPage;
    }

    private void init(){

        addressLine = "";
        addressLine1 = "";
        addressLine2 = "";
        addressLine3 = "";
        city = "";
        state = "";
        postCode = "";
        country = "";
        phone = "";
        email = "";
        fax = "";
        webPage = "";
    }
}
