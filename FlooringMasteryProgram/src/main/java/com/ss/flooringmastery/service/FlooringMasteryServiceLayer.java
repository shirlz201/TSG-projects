/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.service;

import com.ss.flooringmastery.dao.PersistenceException;
import com.ss.flooringmastery.dto.Order;
import com.ss.flooringmastery.dto.Product;
import com.ss.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author shirl
 */
public interface FlooringMasteryServiceLayer {

    Order addOrder(Order order) throws PersistenceException, InvalidDataException, InvalidAreaException;

    Order getOrder(LocalDate orderDate, int orderNumber) throws PersistenceException;

    List<Order> getAllOrders(LocalDate orderDate) throws PersistenceException, OrderDateNotFoundException;

    void editOrder(LocalDate orderDate,int orderNumber, Order order) throws PersistenceException, OrderDateNotFoundException;

    Order removeOrder(LocalDate orderDate, int orderNumber) throws PersistenceException, InvalidDataException, OrderDateNotFoundException;

    Order calculate(Order order) throws PersistenceException,InvalidDataException, InvalidAreaException;
    
    void saveOrder() throws PersistenceException;
    
    List<Tax> getStateTaxList() throws PersistenceException;
    
    List<Product> getProductList() throws PersistenceException;
}
