/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author shirl
 */
public class OrderDaoFileImpl implements OrderDao {

    LocalDate dt = LocalDate.now();

    public String fileName;

    public static final String DELIMITER = "::";

    public OrderDaoFileImpl() {

    }

    /**
     * We need to look up orders by orderDate which the following map will use
     * it a key and the value will hold another map to look it even more by its
     * order number and order pertaining to it.
     */
    private Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        int orderNumber = orders.size() + 1;
        order.setOrderNumber(orderNumber);
        
        if(orders.get(dt)== null) {
            orders.put(dt, new HashMap<>());
        }
        
        Order newOrder =orders.get(dt).put(orderNumber, order);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders(LocalDate orderDate) throws PersistenceException {
        this.dt = orderDate;
        loadFile();
        return new ArrayList<Order>(orders.get(orderDate).values());
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws PersistenceException {
        this.dt = orderDate;
        loadFile();
        return orders.get(orderDate).get(orderNumber);
    }

    @Override
    public void editOrder(LocalDate orderDate, int orderNumber, Order order) throws PersistenceException {
        orders.get(orderDate).put(orderNumber, order);
    
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws PersistenceException {
        Order removedOrder = orders.get(orderDate).remove(orderNumber);
        return removedOrder;        
    }

    @Override
    public void saveOrder() throws PersistenceException {
        writeFile(this.dt);
    }

    /**
     * This method will take in an order object. Over all it will organize the
     * order information from memory obj to a line of text. We are writing to
     * the file.
     *
     * Object ======> line of text
     *
     * @param anOrder
     * @return
     */
    private String marshallOrder(Order anOrder) {
        /* This method will help turn our Order object into a line of text.
        It needs to display: 1::Shirley::Wood::200..etc.
        Each property will be extracted and concatenate with the DELIMITER
        to space it out. OrderNumber has to be first and the rest will follow.        
         */
        String orderAsText = anOrder.getOrderNumber() + DELIMITER;
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostSquareFoot() + DELIMITER;
        orderAsText += anOrder.getLaborCostSquareFoot() + DELIMITER;
        orderAsText += anOrder.getMaterialCost() + DELIMITER;
        orderAsText += anOrder.getLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotal();

        //Right above, we turned an Order to text - below is returned.        
        return orderAsText;
    }

    private void writeFile(LocalDate orderDate) throws PersistenceException {
        this.dt = orderDate;
        String formatted = dt.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        fileName = "Orders_" + formatted + ".txt";

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));

        } catch (IOException e) {
            throw new PersistenceException("Could not save order", e);
        }
        // double check below!
        String ordersAsText;

        /* Previously had a List<Order> orderList = this.getAllOrders(dt); 
        but since I used a map. the following line above was removed. I placed 
        a reference to my map called Orders in my for loop. By replacing it, it 
        helped write to the file! 4/19/20 */

        for (Order currentOrder : orders.get(dt).values()) {
            //turn an Order into a String 
            ordersAsText = marshallOrder(currentOrder);
            //writes the order obj into the file
            out.println(ordersAsText);
            //force PW to write line into the file
            out.flush();
        }
        //clean up
        out.close();
    }

    /**
     * Line of text ======> Object
     *
     * @param ordersAsText
     * @return
     */
    private Order unmarshallOrder(String ordersAsText) {

        String[] orderTokens = ordersAsText.split(DELIMITER);
        //orderAsText is expecting a line read in from our file!

        Order orderFromFile = new Order();

        orderFromFile.setOrderNumber(Integer.parseInt(orderTokens[0]));
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);
        orderFromFile.setTaxRate(new BigDecimal(orderTokens[3]));
        orderFromFile.setProductType(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]));
        orderFromFile.setCostSquareFoot(new BigDecimal(orderTokens[6]));
        orderFromFile.setLaborCostSquareFoot(new BigDecimal(orderTokens[7]));
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]));
        orderFromFile.setTax(new BigDecimal(orderTokens[10]));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]));

        return orderFromFile;

    }

    private void loadFile() throws PersistenceException {

        String formatted = dt.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        fileName = "Orders_" + formatted + ".txt";

        Scanner scanner;

        try {
            // created scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load data into memory", e);
        }
        // currentLne holds the most recent line read from the file
        String currentLine;
        // currentOrder holds the most recent order unmarshalled
        Order currentOrder;
        /* The while loop will go through the ORDER file line by line, decoding
        each line into an Order object by calling the unmarshallOrder method
         */
        orders.put(dt, new HashMap<Integer, Order>());

        while (scanner.hasNextLine()) {
            //gets the next line in the file
            currentLine = scanner.nextLine();
            //unmarshall the line into an Order
            currentOrder = unmarshallOrder(currentLine);

            //use the order number as the map key for our order object
            orders.get(dt).put(currentOrder.getOrderNumber(), currentOrder);
        }

        //close scanner
        scanner.close();

    }

}
