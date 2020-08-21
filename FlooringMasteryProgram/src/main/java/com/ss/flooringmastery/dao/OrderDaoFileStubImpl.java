/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shirl
 */
public class OrderDaoFileStubImpl implements OrderDao {

    LocalDate ld;

    private Order oneOrder;
    private Order secondOrder;
    private List<Order> orderList = new ArrayList<>();

    public OrderDaoFileStubImpl() {
        oneOrder = new Order();
        oneOrder.setOrderNumber(1);
        oneOrder.setCustomerName("Shirley");
        oneOrder.setState("OH");
        oneOrder.setTaxRate(new BigDecimal("6.25"));
        oneOrder.setProductType("Wood");
        oneOrder.setArea(new BigDecimal("100"));
        oneOrder.setCostSquareFoot(new BigDecimal("5.15"));
        oneOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        oneOrder.setMaterialCost(new BigDecimal("515.00"));
        oneOrder.setLaborCost(new BigDecimal("475.00"));
        oneOrder.setTax(new BigDecimal("61.88"));
        oneOrder.setTotal(new BigDecimal("1051.88"));

        orderList.add(oneOrder);

        secondOrder = new Order();
        secondOrder.setOrderNumber(2);
        secondOrder.setCustomerName("John");
        secondOrder.setState("OH");
        secondOrder.setTaxRate(new BigDecimal("6.25"));
        secondOrder.setProductType("Wood");
        secondOrder.setArea(new BigDecimal("100"));
        secondOrder.setCostSquareFoot(new BigDecimal("5.15"));
        secondOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        secondOrder.setMaterialCost(new BigDecimal("515.00"));
        secondOrder.setLaborCost(new BigDecimal("475.00"));
        secondOrder.setTax(new BigDecimal("61.88"));
        secondOrder.setTotal(new BigDecimal("1051.88"));

        orderList.add(secondOrder);

    }

    @Override
    public Order addOrder(Order order) throws PersistenceException {
        if (order.getOrderNumber() == oneOrder.getOrderNumber()) {
            return oneOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders(LocalDate orderDate) throws PersistenceException {
        return orderList;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws PersistenceException {
        if (orderNumber == oneOrder.getOrderNumber()) {
            return oneOrder;
        } else {
            return null;
        }

    }

    @Override
    public void editOrder(LocalDate orderDate, int orderNumber, Order order) throws PersistenceException {
        if (orderNumber == oneOrder.getOrderNumber()) {
        } else {
            
        }
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws PersistenceException {
        if (orderNumber == oneOrder.getOrderNumber()) {
            return oneOrder;
        } else {
            return null;
        }
    }

    @Override
    public void saveOrder() throws PersistenceException {

    }

}
