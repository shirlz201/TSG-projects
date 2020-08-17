/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.dtos;


import java.util.Objects;

/**
 *
 * @author shirley
 */
public class Tags {
    
private int id;
private String tagName;
   
public Tags(){
} 

public Tags(String tagName){
    this.tagName = tagName;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.tagName);
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
        final Tags other = (Tags) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.tagName, other.tagName)) {
            return false;
        }
        return true;
    }

   



    
    
}
