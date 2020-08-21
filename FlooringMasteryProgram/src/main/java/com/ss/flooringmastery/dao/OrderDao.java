/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author shirl
 */
public interface OrderDao {

    /**
     * Adds the given order to the system and associates it with the given order
     * date and order number. It will return an Order Object. Otherwise, it will
     * return null.
     *
     * @param orderDate date that each order is to be associated
     * @param order order to be added to the file
     * @return the Order object associated with the given order date and order
     * number if it exists, null otherwise
     * @throws PersistenceException
     */
    Order addOrder(Order order) throws PersistenceException;

    /**
     * Returns a String array containing the order dates and order number in the
     * file.
     * @param orderDate Date of the order to retrieve 
     * @return String array containing the order dates and numbers in the file
     * @throws PersistenceException
     */
    List<Order> getAllOrders(LocalDate orderDate) throws PersistenceException;

    /**
     * Returns the order object associated with the given order date and number
     * Returns null if no such order exists.
     *
     * @param orderDate Date of the order to retrieve
     * @param orderNumber Number of the order to retrieve
     * @return the Order object associated with the two given parameters, null
     * if no such order exists.
     */
    Order getOrder(LocalDate orderDate, int orderNumber) throws PersistenceException;

    /**
     * Returns nothing since there is an update to the order associated with the
     * given parameters.
     *
     * @param orderDate Date of the order to retrieve
     * @param orderNumber Number of the order to retrieve
     */
    void editOrder(LocalDate orderDate, int orderNumber, Order order) throws PersistenceException;

    /**
     * Removes from the file the order associated with the given order date and
     * order number. Returns the order object that is being removed or null if
     * there is no order associated with the given parameters.
     *
     * @param orderDate date of the order to be removed
     * @param orderNumber number of the order to be removed
     * @return Order object that was removed or null if no order was associated
     * with the given parameters.
     */
    Order removeOrder(LocalDate orderDate, int orderNumber) throws PersistenceException;
    /**
     * Returns nothing and will write(save) to the order file.
     * @throws PersistenceException 
     */
    void saveOrder() throws PersistenceException;

}
