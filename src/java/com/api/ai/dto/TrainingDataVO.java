/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai.dto;

import java.util.ArrayList;

/**
 *
 * @author gowtham.juluri
 */
public class TrainingDataVO {
    
    private String intentName;
    private String userSay;
    
    ArrayList<EntityVO> entityList;

    public String getIntentName() {
        return intentName;
    }

    public String getUserSay() {
        return userSay;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public void setUserSay(String userSay) {
        this.userSay = userSay;
    }
    

    public ArrayList<EntityVO> getEntityList() {
        return entityList;
    }

    public void setEntityList(ArrayList<EntityVO> entityList) {
        this.entityList = entityList;
    }
}
