/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.controller;

import com.ss.flooringmastery.dao.PersistenceException;
import com.ss.flooringmastery.dto.Order;
import com.ss.flooringmastery.service.FlooringMasteryServiceLayer;
import com.ss.flooringmastery.service.InvalidAreaException;
import com.ss.flooringmastery.service.InvalidDataException;
import com.ss.flooringmastery.service.OrderDateNotFoundException;
import com.ss.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author shirl
 */
public class FlooringMasteryController {

    FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveWork();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }

            quit();

        } catch (PersistenceException e) {
            System.out.println("Error: Cannot load data");
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void displayOrders() throws PersistenceException {

        boolean hasErrors = false;

        do {
            LocalDate ld = view.getOrderDate();
            try {
                List<Order> orderList = service.getAllOrders(ld);
                view.displayDisplayAllBanner();
                view.displayOrdersByDate(orderList);
                hasErrors = false;
                //not printing exception message - double check*
            } catch (OrderDateNotFoundException e) {
                System.out.println("ERROR: Order date not found.");
                hasErrors = true;

            }

        } while (hasErrors);

    }

    private void addOrder() throws PersistenceException {

        view.displayCreateOrderBanner();
        view.listStates(service.getStateTaxList());
        view.listProducts(service.getProductList());

        boolean hasErrors = false;

        do {
            Order newOrder = view.getOrderInfo();
            try {
                Order order = service.calculate(newOrder);
                view.displayOrderSummary(newOrder);
                String answer = view.getUserSaveApproval();
                if (answer.equalsIgnoreCase("Y")) {

                    service.addOrder(order);
                    hasErrors = false;

                } else if (answer.equalsIgnoreCase("N")) {
                    view.displayCancelBanner();
                }

            } catch (InvalidDataException | InvalidAreaException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);

    }

    private void editOrder() throws PersistenceException {
        view.displayEditBanner();
        LocalDate orderDate = view.getOrderDate();
        int orderNumber = view.getOrderNumber();

        try {

            Order previousOrder = service.getOrder(orderDate, orderNumber);
            String answer = view.getUserUpdateApproval();
            if (answer.equalsIgnoreCase("Y")) {
                view.listStates(service.getStateTaxList());
                view.listProducts(service.getProductList());
                Order editedOrder = view.editOrder(previousOrder);
                service.calculate(editedOrder);
                service.editOrder(orderDate, orderNumber, editedOrder);
                view.displayUpdateSuccessBanner();
            }

        } catch (OrderDateNotFoundException | InvalidDataException | InvalidAreaException e) {
            e.getMessage();
        }

    }

    private void removeOrder() throws PersistenceException {

        view.displayRemoveOrderBanner();
        LocalDate ld = view.getOrderDate();
        int orderNumber = view.getOrderNumber();

        try {

            Order order = service.getOrder(ld, orderNumber);
            if (order != null) {
                view.displayOrderSummary(order);
                String answer = view.getUserRemoveApproval();
                if (answer.equalsIgnoreCase("Y")) {
                    service.removeOrder(ld, orderNumber);
                    view.displayRemoveSuccessBanner();
                }

            } else {
                view.displayCancelBanner();
            }

        } catch (InvalidDataException | OrderDateNotFoundException e) {
            view.displayErrorMessage(e.getMessage());
        }

    }

    private void saveWork() throws PersistenceException {
        view.displaySaveWorkBanner();
        service.saveOrder();
        view.displaySaveWorkSuccessBanner();

    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();

    }

    private void quit() {
        view.displayExitBanner();
    }

}
