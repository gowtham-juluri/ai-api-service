/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai.dto;

/**
 *
 * @author gowtham.juluri
 */
public class EntityVO {

    private String entityName;
    private String entityValue;
    
    

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setEntityValue(String entityValue) {
        this.entityValue = entityValue;
    }
    public String getEntityName() {
        return entityName;
    }

    public String getEntityValue() {
        return entityValue;
    }
    
    
    @Override
    public String toString() {
        return "EntityVO{" + "entityName=" + entityName + ", entityValue=" + entityValue + '}';
    }
}
