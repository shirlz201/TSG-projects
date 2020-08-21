/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Product;
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
public class ProductDaoFileImpl implements ProductDao {

    public static String PRODUCT_FILE = "Product.txt";
    public static final String DELIMITER = "::";

    public ProductDaoFileImpl(String fileName) {
        PRODUCT_FILE = fileName;
    }

    public ProductDaoFileImpl() {
    }

    private Map<String, Product> products = new HashMap<>();

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        loadFile();
        return new ArrayList(products.values());
    }

    @Override
    public Product getProduct(String productType) throws PersistenceException {
        loadFile();
        return products.get(productType);
    }

    private void loadFile() throws PersistenceException {

        Scanner scanner;

        try {
            //create a scanner for reading the product file
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load product data into memory", e);
        }
        // holds the most recent line to be read from the file
        String currentLine;
        // holds the most recent product unmarshalled
        Product currentProduct;

        /* This will go through the PRODUCT_FILE line by line into a Product
        object by calling the unmarshallProduct method */
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Product
            currentProduct = unmarshallProduct(currentLine);

            // use the product type as the map key for our product object
            products.put(currentProduct.getProductType(), currentProduct);
        }
        // close scanner
        scanner.close();
    }

    private Product unmarshallProduct(String productAsText) {
        //      productTokens Array:
        // _________________________________
        // |        |          |           | 
        // |Product |CostPer   |LaborCost  |
        // | Type   |SquareFt  |PerSquareFt|   
        // ---------------------------------
        //    [0]        [1]        [2]        
        String[] productTokens = productAsText.split(DELIMITER);
        //productAsText is expecting a line read in from our file!
        String productType = productTokens[0];

        Product productFromFile = new Product(productType);
        productFromFile.setCostSquareFoot(new BigDecimal(productTokens[1]));
        productFromFile.setLaborCostSquareFoot(new BigDecimal(productTokens[2]));

        return productFromFile;

    }

}
