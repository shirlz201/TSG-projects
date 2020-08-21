/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ss.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author shirl
 */
public class Product {

    private String productType;
    private BigDecimal costSquareFoot;
    private BigDecimal laborCostSquareFoot;

    public Product(String productType) {
        this.productType = productType;
    }


    public Product(String productType, BigDecimal costSquareFoot, BigDecimal laborCostSquareFoot) {
        this.productType = productType;
        this.costSquareFoot = costSquareFoot;
        this.laborCostSquareFoot = laborCostSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostSquareFoot() {
        return costSquareFoot;
    }

    public void setCostSquareFoot(BigDecimal costSquareFoot) {
        this.costSquareFoot = costSquareFoot;
    }

    public BigDecimal getLaborCostSquareFoot() {
        return laborCostSquareFoot;
    }

    public void setLaborCostSquareFoot(BigDecimal laborCostSquareFoot) {
        this.laborCostSquareFoot = laborCostSquareFoot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.productType);
        hash = 37 * hash + Objects.hashCode(this.costSquareFoot);
        hash = 37 * hash + Objects.hashCode(this.laborCostSquareFoot);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.costSquareFoot, other.costSquareFoot)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSquareFoot, other.laborCostSquareFoot)) {
            return false;
        }
        return true;
    }

    
    
    
}
