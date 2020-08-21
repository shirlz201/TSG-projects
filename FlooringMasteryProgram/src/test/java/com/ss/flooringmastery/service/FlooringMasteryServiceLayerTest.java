/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.service;

import com.ss.flooringmastery.dao.FlooringMasteryAuditDao;
import com.ss.flooringmastery.dao.FlooringMasteryAuditDaoStubImpl;
import com.ss.flooringmastery.dao.OrderDao;
import com.ss.flooringmastery.dao.OrderDaoFileStubImpl;
import com.ss.flooringmastery.dao.PersistenceException;
import com.ss.flooringmastery.dao.ProductDao;
import com.ss.flooringmastery.dao.ProductDaoFileImpl;
import com.ss.flooringmastery.dao.TaxDao;
import com.ss.flooringmastery.dao.TaxDaoFileImpl;
import com.ss.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author shirl
 */
public class FlooringMasteryServiceLayerTest {

    private FlooringMasteryServiceLayer service;

    public FlooringMasteryServiceLayerTest() {
        
        OrderDao orderDao = new OrderDaoFileStubImpl();
        ProductDao productDao = new ProductDaoFileImpl();
        TaxDao taxDao = new TaxDaoFileImpl();
        FlooringMasteryAuditDao auditDao = new FlooringMasteryAuditDaoStubImpl();
        service = new FlooringMasteryServiceLayerImpl(auditDao, orderDao, productDao, taxDao);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() throws PersistenceException, OrderDateNotFoundException, InvalidDataException {
        List<Order> orderList = service.getAllOrders(LocalDate.now());
        for (Order order : orderList) {
            service.removeOrder(LocalDate.now(), order.getOrderNumber());
        }
    }

    /**
     * Test of addOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testAddOrder() throws Exception {
        Order oneOrder = new Order();
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
        service.addOrder(oneOrder);
    }

    /**
     * Test of getOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testAddOrderwithInvalidData() throws Exception {
        Order oneOrder = new Order();
        oneOrder.setOrderNumber(1);
        oneOrder.setCustomerName("Amber");
        oneOrder.setState("NJ");
        oneOrder.setTaxRate(new BigDecimal("6.25"));
        oneOrder.setProductType("Wood");
        oneOrder.setArea(new BigDecimal("100"));
        oneOrder.setCostSquareFoot(new BigDecimal("5.15"));
        oneOrder.setLaborCostSquareFoot(new BigDecimal("4.75"));
        oneOrder.setMaterialCost(new BigDecimal("515.00"));
        oneOrder.setLaborCost(new BigDecimal("475.00"));
        oneOrder.setTax(new BigDecimal("61.88"));
        oneOrder.setTotal(new BigDecimal("1051.88"));
        assertThrows(Exception.class, () -> service.addOrder(oneOrder),
                () -> "InvalidData Exception expected");

    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        assertEquals(2, service.getAllOrders(LocalDate.now()).size());

    }

    /**
     * Test of editOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testEditOrder() throws Exception {
        Order newOrder = new Order();
        newOrder.setOrderNumber(1);
        newOrder.setCustomerName("Shirley");
        newOrder.setState("IN");
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("100.00"));

        service.addOrder(newOrder);

        Order updatedOrder = service.getOrder(LocalDate.now(), 1);
        updatedOrder.setCustomerName("Shirley Sosa");
        updatedOrder.setState("MI");
        updatedOrder.setProductType("Carpet");

        service.editOrder(LocalDate.now(), 1, updatedOrder);

        Order fromService = service.getOrder(LocalDate.now(), 1);
        assertEquals(1, fromService.getOrderNumber());
        assertEquals("MI", fromService.getState());

        assertEquals("Carpet", fromService.getProductType());
        assertEquals(new BigDecimal("100"), fromService.getArea());
        assertEquals(new BigDecimal("1051.88"), fromService.getTotal());
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        Order newOrder = new Order();
        newOrder.setOrderNumber(1);
        newOrder.setCustomerName("Kim");
        newOrder.setState("OH");
        newOrder.setProductType("Wood");
        newOrder.setArea(new BigDecimal("100.00"));

        assertThrows(Exception.class, () -> service.removeOrder(LocalDate.now(),3),
                () -> "OrderNotFound expected");
   
       
    }

    /**
     * Test of calculate method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testCalculate() throws Exception {
        Order oneOrder = new Order();
        oneOrder.setOrderNumber(1);
        oneOrder.setCustomerName("Kim");
        oneOrder.setState("OH");
        oneOrder.setProductType("Wood");
        oneOrder.setArea(new BigDecimal("100"));
        service.addOrder(oneOrder);
        service.calculate(oneOrder);
        BigDecimal actualTotal = oneOrder.getTotal();
        BigDecimal expectedTotal = new BigDecimal("1051.88");

        assertEquals(expectedTotal, actualTotal);
    }

    /**
     * Test of getStateTaxList method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetStateTaxList() throws Exception {
        assertEquals(4, service.getStateTaxList().size());
    }

    /**
     * Test of getProductList method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetProductList() throws Exception {
        assertEquals(4, service.getProductList().size());
    }

}
