package com.alexwhitecs.Genealogy.GEDCOM;

/**
 * An exception thrown during the importation of a GEDCOM file.
 */
public class GEDCOM_Exception extends Exception{

    public GEDCOM_Exception(String message) {

        super(message);
    }
}
