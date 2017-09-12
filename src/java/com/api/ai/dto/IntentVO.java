/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai.dto;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author gowtham.juluri
 */
public class IntentVO {
    
    private String intent;
    private String userSays;
    private String action;
    private String event;
    private String textMessage;
    ArrayList<ParameterVO> params;

    @Override
    public String toString() {
        return "IntentVO{" + "intent=" + intent + ", userSays=" + userSays + ", action=" + action + ", event=" + event + ", textMessage=" + textMessage + ", params=" + params + '}';
    }

    
    
    

    public String getIntent() {
        return intent;
    }

    public String getUserSays() {
        return userSays;
    }

    public String getAction() {
        return action;
    }

    public String getEvent() {
        return event;
    }

    public String getResponseMessage() {
        return textMessage;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setUserSays(String userSays) {
        this.userSays = userSays;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setResponseMessage(String responseMessage) {
        this.textMessage = responseMessage;
    }

    public void setParams(ArrayList<ParameterVO> params) {
        this.params = params;
    }

    public List<ParameterVO> getParams() {
        return params;
    }
    
    
}
