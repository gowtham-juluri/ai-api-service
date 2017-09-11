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
public class ParameterVO {

    
    private String paramName;
    private String entityName;
    
    private String entityValue;
    private String isRequired;
    private String isList;

    @Override
    public String toString() {
        return "ParameterVO{" + "paramName=" + paramName + ", entityName=" + entityName + ", entityValue=" + entityValue + ", isRequired=" + isRequired + ", isList=" + isList + '}';
    }
    
    

    
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public void setEntityValue(String entityValue) {
        this.entityValue = entityValue;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public void setIsList(String isList) {
        this.isList = isList;
    }
    
    public String getParamName() {
        return paramName;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityValue() {
        return entityValue;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public String getIsList() {
        return isList;
    }
    
}
