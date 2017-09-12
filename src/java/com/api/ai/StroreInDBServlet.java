/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai;

import com.api.ai.helper.MySQLDBServiceHelper;
import com.api.ai.dto.IntentVO;
import com.api.ai.helper.MLPythonAPI;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gowtham.juluri
 */
public class StroreInDBServlet extends HttpServlet {

    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(StroreInDBServlet.class);

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.info("Executing GET method....");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        log.info("Executing POST method (StroreInDBServlet)....");
        String jsonIntentDetaiils = request.getParameter("name");
        log.info("jsonIntentDetaiils (StroreInDBServlet)...."  +jsonIntentDetaiils);
        if (jsonIntentDetaiils != null && jsonIntentDetaiils.length() > 0) {
            MySQLDBServiceHelper dbSave = new MySQLDBServiceHelper();
            Gson gson = new Gson();
            IntentVO iv = gson.fromJson(jsonIntentDetaiils, IntentVO.class);
            System.out.println("iv " + iv);
            dbSave.saveIntentData(iv);
            //Do we need to call this script again?
//            MLPythonAPI.callPyhonScriptToTrain();
        } else {
            System.out.println("intent data received is NULL " + jsonIntentDetaiils);
        }
    }
}
