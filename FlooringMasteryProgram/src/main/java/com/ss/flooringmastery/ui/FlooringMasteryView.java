/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.ui;

import com.ss.flooringmastery.dto.Order;
import com.ss.flooringmastery.dto.Product;
import com.ss.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author shirl
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save current work");
        io.print("6. Quit");
        io.print("  ");
        return io.readInt("Please select from the choices above.", 1, 6);
    }
//=============================================================================

    public void displayDisplayAllBanner() {
        io.print("===DISPLAYING ALL ORDERS===");
    }

    public void displayOrdersByDate(List<Order> orders) {
        for (Order currentOrder : orders) {

            io.print("Order Number: " + currentOrder.getOrderNumber());
            io.print("Customer Name: " + currentOrder.getCustomerName());
            io.print("State: " + currentOrder.getState());
            io.print("Tax Rate: " + currentOrder.getTaxRate());
            io.print("Product Type: " + currentOrder.getProductType());
            io.print("Area: " + currentOrder.getArea());
            io.print("Cost(Sq.ft): " + currentOrder.getCostSquareFoot());
            io.print("Labor Cost(Sq.ft): " + currentOrder.getLaborCostSquareFoot());
            io.print("Material Cost: " + currentOrder.getMaterialCost());
            io.print("Labor Cost: " + currentOrder.getLaborCost());
            io.print("Tax: " + currentOrder.getTax());
            io.print("Total: " + currentOrder.getTotal());
            io.print(" ");
        }
        io.readString("Please hit enter to continue");
    }

//----------------------------------------------------------------------------
    public void displayCreateOrderBanner() {
        io.print("====CREATE ORDER====");
    }

    /*
     Add Order methods for User We need information from the user to create
     * a new Order object.
     * @return newOrder object
     */
    public Order getOrderInfo() {

        io.print("Please fill out the following fields:");
        String customerName = io.readString("Customer name: ");
        String state = io.readString("State: ");
        String product = io.readString("Product: ");
        BigDecimal area = io.readBigDecimal("Area: ");

        Order newOrder = new Order();
        newOrder.setCustomerName(customerName);
        newOrder.setState(state);
        newOrder.setProductType(product);
        newOrder.setArea(area);

        return newOrder;
    }

    public void displayOrderSummary(Order order) {

        io.print("Order Number: " + order.getOrderNumber());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Tax Rate: " + order.getTaxRate());
        io.print("Product: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("=========================");
        io.print("Total Cost: $" + order.getMaterialCost());
        io.print("Total Labor: $" + order.getLaborCost());
        io.print("Total Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
        io.print(" ");
        io.print(" ");

    }

    public void displayCreateOrderSuccessBanner() {
        io.print("Order successfully saved. Please hit enter to continue.");
    }

// ----------------------------------------------------------------------------
    public void listStates(List<Tax> list) {
        io.print("List of states:");

        for (Tax states : list) {
            io.print(states.getState());
        }
    }

    public void listProducts(List<Product> list) {
        io.print("List of products available:");

        for (Product products : list) {
            io.print(products.getProductType());
        }
    }

//------------------------------------------------------------------------------
    public LocalDate getOrderDate() {
        return io.readLocalDate("Please enter a date: (Ex. MM-DD-YYYY)",
                LocalDate.of(2005, 1, 1), LocalDate.of(2050, 1, 31));
    }

    public void displayDateBanner(LocalDate orderDate) {
        System.out.printf("=== Orders on %s ===\n", orderDate);

    }

    public int getOrderNumber() {
        return io.readInt("Enter the order number you wish to view: ");
    }

//------------------------------------------------------------------------------
    public void displayEditBanner() {
        io.print("===EDIT ORDER===");
    }

    public Order editOrder(Order order) {
        
        io.print("Current Name: " + order.getCustomerName() + "\n");
        String name = io.readString("Plase enter name:");
        if (!name.isEmpty()) {
            order.setCustomerName(name);
        }

        io.print("Current State: " + order.getState() + "\n");
        String state = io.readString("Please enter state: \n ");
        if (!state.isEmpty()) {
            order.setState(state);
        }

        io.print("Current Order: " + order.getProductType());
        String productType = io.readString("Please enter new product:\n");
        if (!productType.isEmpty()) {
            order.setProductType(productType);

        }

        io.print("Current area: " + order.getArea() + "\n");
        String area = io.readString("Please enter the area: \n");
        if (!area.isEmpty()) {
            order.setArea(new BigDecimal(area));
        }

        return order;
    }

    public void displayUpdateSuccessBanner(){
        io.print("Order has been updated successfully.");
    }
    
    public String getUserUpdateApproval() {
        return io.readString("Would you like to edit this order? (Y/N)");
    }
//-----------------------------------------------------------------------------

    public void displayRemoveOrderBanner() {
        io.print("===REMOVE ORDER===");
    }

    public String getUserRemoveApproval() {
        return io.readString("Are you sure you want to remove this order? (Y/N)");
    }

    public void displayRemoveSuccessBanner() {
        io.print("Order successfully removed. Please hit enter to continue.");

    }

    public void displayErrorMessage(String errorMessage) {
        io.print("=== ERROR ===");
        io.print(errorMessage);
    }
//-----------------------------------------------------------------------------

    public void displaySaveWorkBanner() {
        io.print("===SAVE WORK====");

    }

    public void displaySaveWorkSuccessBanner() {
        io.print("Work has been saved.");
    }

    public String getUserSaveApproval() {
        return io.readString("Would you like to save your work? (Y/N)");
    }

    public void displayCancelBanner() {
        io.print("Don't want to save? No problem. Let's go back to the main menu! :)");
    }

    public void displayExitBanner() {
        io.print("GoodBye!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command.");
    }

}
