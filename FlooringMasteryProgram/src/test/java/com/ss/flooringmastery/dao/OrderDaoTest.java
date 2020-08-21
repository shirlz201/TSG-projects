/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dao;

import com.ss.flooringmastery.dto.Order;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author shirl
 */
public class OrderDaoTest {

    private OrderDao dao;

    public OrderDaoTest() throws PersistenceException {
        dao = new OrderDaoFileImpl();
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws PersistenceException {
        LocalDate testDate = LocalDate.parse("2020-04-19");
        List<Order> orderList = dao.getAllOrders(testDate);

        for (Order orders : orderList) {
            dao.removeOrder(testDate, orders.getOrderNumber());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class OrderDao.
     */
    @Test
    public void testAddOrder() throws Exception {

        Order newOrder = new Order();
        newOrder.setOrderNumber(1);
        newOrder.setCustomerName("John");
        newOrder.setState("MI");
        newOrder.setTaxRate(new BigDecimal("6.25"));
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setCostSquareFoot(new BigDecimal("5.15"));
        newOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        newOrder.setMaterialCost(new BigDecimal("515.00"));
        newOrder.setLaborCost(new BigDecimal("475"));
        newOrder.setTax(new BigDecimal("61.88"));
        newOrder.setTotal(new BigDecimal("1051.88"));

        LocalDate ld = LocalDate.parse("2020-04-19");

        Order myDao = dao.getOrder(ld, 1);

        assertEquals(myDao, newOrder);
    }

    /**
     * Test of getAllOrders method, of class OrderDao.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        //ARRANGE
        Order newOrder = new Order();

        newOrder.setOrderNumber(1);
        newOrder.setCustomerName("Kim");
        newOrder.setState("OH");
        newOrder.setTaxRate(new BigDecimal("6.25"));
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setCostSquareFoot(new BigDecimal("5.15"));
        newOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        newOrder.setMaterialCost(new BigDecimal("515.00"));
        newOrder.setLaborCost(new BigDecimal("475"));
        newOrder.setTax(new BigDecimal("61.88"));
        newOrder.setTotal(new BigDecimal("1051.88"));

        Order newOrder2 = new Order();
        newOrder2.setOrderNumber(2);
        newOrder2.setCustomerName("John");
        newOrder2.setState("OH");
        newOrder2.setTaxRate(new BigDecimal("6.25"));
        newOrder2.setProductType("Carpet");
        newOrder2.setArea(new BigDecimal("10.00"));
        newOrder2.setCostSquareFoot(new BigDecimal("2.25"));
        newOrder2.setLaborCostSquareFoot(new BigDecimal("2.10"));
        newOrder2.setMaterialCost(new BigDecimal("22.50"));
        newOrder2.setLaborCost(new BigDecimal("21"));
        newOrder2.setTax(new BigDecimal("2.72"));
        newOrder2.setTotal(new BigDecimal("46.22"));

        LocalDate testDate = LocalDate.parse("2020-04-19");
        //ACT
        dao.addOrder(newOrder);
        dao.addOrder(newOrder2);
        dao.saveOrder();

        //ASSERT
        assertEquals(2, dao.getAllOrders(testDate).size());
    }

    /**
     * Test of getOrder method, of class OrderDao.
     */
    @Test
    public void testGetOrder() throws Exception {
    }

    /**
     * Test of editOrder method, of class OrderDao.
     */
    @Test
    public void testEditOrder() throws Exception {
        //ARRANGE
        Order newOrder = new Order();
        
        newOrder.setOrderNumber(1);
        newOrder.setCustomerName("AmberRose");
        newOrder.setState("PA");
        newOrder.setTaxRate(new BigDecimal("6.75"));
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setCostSquareFoot(new BigDecimal("5.15"));
        newOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        newOrder.setMaterialCost(new BigDecimal("515.00"));
        newOrder.setLaborCost(new BigDecimal("475"));
        newOrder.setTax(new BigDecimal("66.83"));
        newOrder.setTotal(new BigDecimal("1056.83"));

        LocalDate testDate = LocalDate.parse("2019-04-19");

        dao.addOrder(newOrder);
        dao.saveOrder();

        Order updatedOrder = new Order();
        updatedOrder.setOrderNumber(1);
        updatedOrder.setCustomerName("Kimberly");
        updatedOrder.setState("PA");
        updatedOrder.setTaxRate(new BigDecimal("6.75"));
        updatedOrder.setProductType("Wood");
        updatedOrder.setArea(new BigDecimal("100.00"));
        updatedOrder.setCostSquareFoot(new BigDecimal("5.15"));
        updatedOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        updatedOrder.setMaterialCost(new BigDecimal("515.00"));
        updatedOrder.setLaborCost(new BigDecimal("475"));
        updatedOrder.setTax(new BigDecimal("66.83"));
        updatedOrder.setTotal(new BigDecimal("1056.83"));

        //ACT
        dao.editOrder(testDate, 1, updatedOrder);
        dao.saveOrder();
        Order orderDao = dao.getOrder(testDate, 1);

        //ASSERT
        assertEquals(updatedOrder, orderDao);
        assertNotEquals("Amber", orderDao.getCustomerName());
    }

    /**
     * Test of removeOrder method, of class OrderDao.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        //ARRANGE
        Order newOrder = new Order();
        newOrder.setOrderNumber(1);
        newOrder.setCustomerName("Shirley");
        newOrder.setState("PA");
        newOrder.setTaxRate(new BigDecimal("6.75"));
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setCostSquareFoot(new BigDecimal("5.15"));
        newOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        newOrder.setMaterialCost(new BigDecimal("515.00"));
        newOrder.setLaborCost(new BigDecimal("475"));
        newOrder.setTax(new BigDecimal("66.83"));
        newOrder.setTotal(new BigDecimal("1056.83"));

        LocalDate testDate = LocalDate.parse("2020-04-19");

        dao.addOrder(newOrder);

        Order newOrder2 = new Order();
        newOrder2.setOrderNumber(2);
        newOrder2.setCustomerName("John");
        newOrder2.setState("OH");
        newOrder2.setTaxRate(new BigDecimal("6.25"));
        newOrder2.setProductType("Carpet");
        newOrder2.setArea(new BigDecimal("10.00"));
        newOrder2.setCostSquareFoot(new BigDecimal("2.25"));
        newOrder2.setLaborCostSquareFoot(new BigDecimal("2.10"));
        newOrder2.setMaterialCost(new BigDecimal("22.50"));
        newOrder2.setLaborCost(new BigDecimal("21"));
        newOrder2.setTax(new BigDecimal("2.72"));
        newOrder2.setTotal(new BigDecimal("46.22"));

        //ACT
        dao.addOrder(newOrder2);
        dao.saveOrder();

        dao.removeOrder(testDate, 1);
        dao.saveOrder();

        //ASSERT
        assertEquals(1, dao.getAllOrders(testDate).size());
        assertNull(dao.getOrder(testDate, 1));
        assertNotNull(dao.getOrder(testDate, 2));
    }

}
