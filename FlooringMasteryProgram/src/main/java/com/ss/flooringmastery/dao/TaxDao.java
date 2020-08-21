/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Tax;
import java.util.List;

/**
 *
 * @author shirl
 */
public interface TaxDao {
    /**
     * Returns a String array containing the taxes of all the states in the file
     * 
     * @return String array containing the taxes of all the states in the file
     * @throws PersistenceException 
     */
    List<Tax> getAllTaxes() throws PersistenceException;
    /**
     * Returns the Tax object associated with the given state. Returns null if
     * such state exists
     * 
     * @param state State of the tax retrieve
     * @return the Tax object associated with the given state, null if no such
     * tax exists
     * @throws PersistenceException 
     */
    Tax getTax(String state)throws PersistenceException;
    
}
