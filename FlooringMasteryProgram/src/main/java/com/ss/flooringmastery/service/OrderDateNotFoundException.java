/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.service;

/**
 *
 * @author shirl
 */
public class OrderDateNotFoundException extends Exception {

    public OrderDateNotFoundException(String message) {
        super(message);
    }

    public OrderDateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
