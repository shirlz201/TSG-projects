/*
4/4 added dependency injection
modified two methods, getorder and vieworders

 */
package com.ss.flooringmastery.service;

import com.ss.flooringmastery.dao.FlooringMasteryAuditDao;
import com.ss.flooringmastery.dao.OrderDao;
import com.ss.flooringmastery.dao.PersistenceException;
import com.ss.flooringmastery.dao.ProductDao;
import com.ss.flooringmastery.dao.TaxDao;
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
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private FlooringMasteryAuditDao auditDao;

    BigDecimal bd = new BigDecimal("100");
    OrderDao myOrderDao;
    ProductDao myProductDao;
    TaxDao myTaxDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryAuditDao auditDao, OrderDao myOrderDao, ProductDao myProductDao, TaxDao myTaxDao) {
        this.auditDao = auditDao;
        this.myOrderDao = myOrderDao;
        this.myProductDao = myProductDao;
        this.myTaxDao = myTaxDao;
    }

    @Override
    public Order addOrder(Order order) throws PersistenceException, InvalidDataException, InvalidAreaException {

        validateOrderData(order);
        auditDao.writeAuditEntry(LocalDate.now() + "Order created.");
        myOrderDao.addOrder(order);
        return order;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws PersistenceException {
        return myOrderDao.getOrder(orderDate, orderNumber);
    }

    @Override
    public void editOrder(LocalDate orderDate, int orderNumber, Order order) throws PersistenceException, OrderDateNotFoundException {
        if (myOrderDao.getOrder(orderDate, orderNumber) == null) {
            throw new OrderDateNotFoundException("Order date not found. Please re-enter a new date.");
        } else {
            myOrderDao.editOrder(orderDate, orderNumber, order);
        }
    }

    @Override
    public List<Order> getAllOrders(LocalDate orderDate) throws PersistenceException, OrderDateNotFoundException {

        return myOrderDao.getAllOrders(orderDate);
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws PersistenceException, OrderDateNotFoundException {
        Order removedOrder = myOrderDao.removeOrder(orderDate, orderNumber);
        if (removedOrder == null) {
            throw new OrderDateNotFoundException("Order is not in the system.");
        }
        auditDao.writeAuditEntry(LocalDate.now() + "Order have been removed.");
        return removedOrder;
    }

    @Override
    public Order calculate(Order order) throws PersistenceException, InvalidAreaException, InvalidDataException {

        validateOrderData(order);
        
        order.setTaxRate(myTaxDao.getTax(order.getState()).getTaxRate());

        order.setCostSquareFoot(myProductDao.getProduct(order.getProductType()).getCostSquareFoot());
        order.setLaborCostSquareFoot(myProductDao.getProduct(order.getProductType()).getLaborCostSquareFoot());

        order.setMaterialCost(order.getArea().multiply(myProductDao.getProduct(order.getProductType()).getCostSquareFoot()));
        order.setLaborCost(order.getArea().multiply(myProductDao.getProduct(order.getProductType()).getLaborCostSquareFoot()));
        order.setTax(order.getMaterialCost().add(order.getLaborCost()).multiply(myTaxDao.getTax(order.getState()).getTaxRate().divide(bd)).setScale(2, HALF_UP));
        order.setTotal(order.getMaterialCost().add(order.getLaborCost().add(order.getTax())));

        return order;
    }

    @Override
    public void saveOrder() throws PersistenceException {
        try {
            myOrderDao.saveOrder();

        } catch (PersistenceException e) {
            System.out.println("Error: Cannot save data at this time.");
        }
    }

    @Override
    public List<Tax> getStateTaxList() throws PersistenceException {
        return myTaxDao.getAllTaxes();

    }

    @Override
    public List<Product> getProductList() throws PersistenceException {
        return myProductDao.getAllProducts();
    }

    private void validateOrderData(Order order) throws PersistenceException, InvalidDataException, InvalidAreaException {

        if (order.getCustomerName() == null
                || order.getCustomerName().trim().length() == 0
                || order.getState() == null
                || order.getState().trim().length() == 0
                || order.getProductType() == null
                || order.getProductType().trim().length() == 0
                || order.getArea().equals(null)) {
            throw new InvalidDataException(
                    "ERROR: All fields [Customer name, State, Product and Area] are required.");
        }
        if (myProductDao.getProduct(order.getProductType()) == null) {
            throw new InvalidDataException(
                    "Error: The product entered is not in our system! Please re-enter data.");
        }
        if (myTaxDao.getTax(order.getState()) == null) {
            throw new InvalidDataException("Error: The state enterd in not in the system! Please re-enter data.");
        }
        if (order.getArea().intValue() < 100 || order.getArea().intValue() > 200) {
            throw new InvalidAreaException(
                    "ERROR: Please enter a valid Area (Min=100 and Max=200).");
        }

    }

}
