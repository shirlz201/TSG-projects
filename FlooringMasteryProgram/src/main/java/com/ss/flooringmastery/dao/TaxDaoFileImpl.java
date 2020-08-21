/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class TaxDaoFileImpl implements TaxDao {

    public static String STATETAX_FILE = "Taxes.txt";
    public static final String DELIMITER = "::";

    public TaxDaoFileImpl(String fileName) {
        STATETAX_FILE = fileName;
    }

    public TaxDaoFileImpl() {
    }

    private Map<String, Tax> taxes = new HashMap<>();

    @Override
    public List<Tax> getAllTaxes() throws PersistenceException {
        loadFile();
        return new ArrayList(taxes.values());
    }

    @Override
    public Tax getTax(String state) throws PersistenceException {
        loadFile();
        return taxes.get(state);
    }

    private void loadFile() throws PersistenceException {
        Scanner scanner;

        try {
            //create a scanner for reading the product file
            scanner = new Scanner(new BufferedReader(new FileReader(STATETAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load product data into memory", e);
        }
        // holds the most recent line to be read from the file
        String currentLine;
        // holds the most recent product unmarshalled
        Tax currentTax;

        /* This will go through the PRODUCT_FILE line by line into a Product
        object by calling the unmarshallProduct method */
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Product
            currentTax = unmarshallTax(currentLine);

            // use the product type as the map key for our product object
            taxes.put(currentTax.getState(), currentTax);
        }
        // close scanner
        scanner.close();
    }

    private Tax unmarshallTax(String taxesAsText) {

        // taxTokens Array
        // ____________
        // |     |    | 
        // |State|Tax |
        // |Name |Rate|  
        // ------------
        //  [0]  [1]   
        String[] taxTokens = taxesAsText.split(DELIMITER);
        // use the stateName as the index of the array
        String stateName = taxTokens[0];

        //creating a new Tax obj & using its constructor
        Tax taxFromFile = new Tax(stateName);

        // Index 1 - taxRate
        taxFromFile.setTaxRate(new BigDecimal(taxTokens[1]));

        // a Tax object has been created - we have to return it
        return taxFromFile;

    }

}
