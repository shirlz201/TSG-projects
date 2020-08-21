/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author shirl
 */
public interface ProductDao {

    /**
     * Returns a String array containing the product types of all products that
     * we have to offer in the given file.
     * 
     * @return String array containing the product types of all products in the
     * given file.
     * @throws PersistenceException
     */
    List<Product> getAllProducts() throws PersistenceException;
    
    /**
     * Returns the Product object associated with the given product type. Null
     * if no such product exists
     * 
     * @param productType Type of the product to retrieve
     * @return the Product object associated with the given product type, null
     * if no such product exists.
     * @throws PersistenceException 
     */
    Product getProduct(String productType) throws PersistenceException;

}
