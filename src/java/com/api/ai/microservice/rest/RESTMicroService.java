/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai.microservice.rest;

import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.core.MultivaluedMap;

/**
 * REST Web Service
 *
 * @author gowtham.juluri
 */
@Path("rest-micro")
public class RESTMicroService {

    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RESTMicroService.class);
    @Context
    private UriInfo info;

    /**
     * Creates a new instance of RESTService
     */
    public RESTMicroService() {
    }

    @GET
    public String getJson() {

        log.info("uriinfo" + info.getPath());

        String from = info.getQueryParameters().getFirst("from");
        String to = info.getQueryParameters().getFirst("to");
        String id = info.getQueryParameters().getFirst("empid");

        return "Dear User,\n we have extracted uriInfo= " + info.getPath();
    }

    @PUT
    public void putJson(String content) {
    }

    @GET
    @Path("/action")
    @Consumes("application/json")
    public String getActionResponse(@Context UriInfo info) {
        
        log.info("getActionResponse uriinfo >>> getActionResponse" + info.getPath());
        String msg = "Dear User, sometimes I may not understand your questions. "
                + "I am a Virtual Agent. Kindly rephrase your question. Thank You.";
        String e = " Entity (key, value) mapping(s) identified is/are: ";
        if(info.getQueryParameters() != null)
        {
            MultivaluedMap<String, String> m = info.getQueryParameters();
            
            for (Map.Entry<String, List<String>> entrySet : m.entrySet()) {
                String key = entrySet.getKey();
                List<String> value = entrySet.getValue();
                log.info("key >> " + key);
                log.info("value >> " + value);
                if(key != null && key.length()>0)
                {
                    if(key.equalsIgnoreCase("intent"))
                    {
                        msg = "Dear User, we have identified intent as '"+ value.get(0)+"' from your question. ";
                    }
                    else
                    {
                        e = e + " (" + key + "," + value.get(0) + ")";
                    }
                }
            }
            System.out.println("here");
            msg = msg + e;
            log.info("new msg >>> " + msg);
        }
        log.info("cool...." + msg);
        return msg;
    }
}
