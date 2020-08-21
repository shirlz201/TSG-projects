/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery;

import com.ss.flooringmastery.controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author shirl
 */
public class App {

    public static void main(String[] args) {

//        UserIO myIo = new UserIOConsoleImpl();
//        FlooringMasteryView myView = new FlooringMasteryView(myIo);
//        OrderDao myOrderDao = new OrderDaoFileImpl();
//        ProductDao myProductDao = new ProductDaoFileImpl();
//        TaxDao myTaxDao = new TaxDaoFileImpl();
//        FlooringMasteryAuditDao myAuditDao = new FlooringMasteryAuditDaoFileImpl();
//        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerImpl(myAuditDao, myOrderDao, myProductDao, myTaxDao);
//        FlooringMasteryController controller = new FlooringMasteryController(myView, myService);
//
//        controller.run();

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller
                = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();

    }
}
