/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai;

import com.api.ai.helper.MySQLDBServiceHelper;
import com.api.ai.dto.IntentVO;
import com.api.ai.dto.ParameterVO;
import com.api.ai.helper.MLPythonAPI;
import com.api.ai.helper.RESTMicroServiceClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gowtham.juluri
 */
public class QueryAgentServlet extends HttpServlet {

    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(QueryAgentServlet.class);

    
    public static void main(String[] args)
    {
        try {
            QueryAgentServlet q = new QueryAgentServlet();
            q.testMethod("how is the climate in bangalore");
        } catch (ServletException ex) {
            Logger.getLogger(QueryAgentServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QueryAgentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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

        System.out.println("HERE in GET");
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
            throws ServletException, IOException {

        String msg = "{\"response\": \"Dear User, we are sorry to say that we are unable to understand your question.\"}";
        System.out.println("Executing POST method....");
        String userQuestion = (String) request.getParameter("name");
        if (userQuestion != null && userQuestion.length() > 0) {
            
            System.out.println("userQuestion >>> " + userQuestion);

            String dataFileMLSent = MLPythonAPI.callPyhonScriptToPredict(userQuestion);
            System.out.println("dataFileMLSent >>> " + dataFileMLSent);
            if (dataFileMLSent != null && dataFileMLSent.length() > 0) {

                File file = new File(dataFileMLSent);
                if (!file.exists() || !file.isFile()) {
                    System.out.println("File doesn\'t exist. Default message would be sent back to user.");

                } else {
                    //File exists
                    if (file.length() > 0) {
                        
                        String contents = new String(Files.readAllBytes(Paths.get(dataFileMLSent)));
                        //File exists and file length>0
                        BufferedReader b = new BufferedReader(new FileReader(file));

                        String readLine = "";

                        System.out.println("Reading file using Buffered Reader >>>>>>> contents >>>>>>> " + contents);
                        ArrayList<ParameterVO> pval = new ArrayList<ParameterVO>();
                        String intentPredicted="";
                        while ((readLine = b.readLine()) != null) {

                            System.out.println(readLine);
                            
                            if(readLine != null && readLine.indexOf("INTENT")>=0)
                            {
                                intentPredicted = getIntentName(readLine);
                            }
                            else
                            {
                                String entN = readLine.substring(0,readLine.indexOf("="));
                                String entV = readLine.substring(readLine.indexOf("=")+1);
                                System.out.println("QSA Entity Name >>>> " + entN);
                                System.out.println("QSA Entity Value >>>> " + entV);
                                ParameterVO pvo = new ParameterVO();
                                pvo.setEntityName(entN);
                                pvo.setEntityValue(entV);
                                pval.add(pvo);
                                
                            }
                        }
                        System.out.println("QSA intentPredicted >>>>>> " + intentPredicted);
                        System.out.println("QSA pval >>>>>> " + pval);
                        if (intentPredicted != null && intentPredicted.length() > 0) {
//                                MySQLDBServiceHelper h = new MySQLDBServiceHelper();
//                                IntentVO iData = h.getIntentData(intentPredicted);
                            IntentVO iData = new IntentVO();
                            iData.setIntent(intentPredicted);
                            iData.setParams(pval);
                            System.out.println("input params >>> " + iData.getParams());
                            String res = RESTMicroServiceClient.executeRestServcie(iData);
                            System.out.println("res >>> " + res);
                            if(res != null)
                            {
                                msg = "{\"response\": \"" + res + "\"}";
                            }
                        } else {
                            //intent data format is not as per the expcted format in the file
                        }
                    } else {
                        //sending default msg
                    }
                }
            } else {
//                sending default msg
            }
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(msg);
        out.flush();
    }

    protected void testMethod(String userQuestion)
            throws ServletException, IOException {

        String msg = "{\"response\": \"Dear User, we are sorry to say that we are unable to understand your question.\"}";
        System.out.println("Executing testMethod method....");
        if (userQuestion != null && userQuestion.length() > 0) {
            
            System.out.println(userQuestion);

            String dataFileMLSent = MLPythonAPI.callPyhonScriptToPredict(userQuestion);
            
            if (dataFileMLSent != null && dataFileMLSent.length() > 0) {

                File file = new File(dataFileMLSent);
                if (!file.exists() || !file.isFile()) {
                    System.out.println("File doesn\'t exist. Default message would be sent back to user.");

                } else {
                    //File exists
                    if (file.length() > 0) {
                        
                        String contents = new String(Files.readAllBytes(Paths.get(dataFileMLSent)));
                        //File exists and file length>0
                        BufferedReader b = new BufferedReader(new FileReader(file));

                        String readLine = "";

                        System.out.println("Reading file using Buffered Reader >>>>>>> contents >>>>>>> " + contents);
                        ArrayList<ParameterVO> pval = new ArrayList<ParameterVO>();
                        String intentPredicted="";
                        while ((readLine = b.readLine()) != null) {

                            System.out.println(readLine);
                            
                            if(readLine != null && readLine.indexOf("INTENT")>=0)
                            {
                                intentPredicted = getIntentName(readLine);
                            }
                            else
                            {
                                String entN = readLine.substring(0,readLine.indexOf("="));
                                String entV = readLine.substring(readLine.indexOf("=")+1);
                                System.out.println("QSA Entity Name >>>> " + entN);
                                System.out.println("QSA Entity Value >>>> " + entV);
                                ParameterVO pvo = new ParameterVO();
                                pvo.setEntityName(entN);
                                pvo.setEntityValue(entV);
                                pval.add(pvo);
                            }
                        }
                        System.out.println("QSA intentPredicted >>>>>> " + intentPredicted);
                        System.out.println("QSA pval >>>>>> " + pval);
                        if (intentPredicted != null && intentPredicted.length() > 0) {
//                                MySQLDBServiceHelper h = new MySQLDBServiceHelper();
//                                IntentVO iData = h.getIntentData(intentPredicted);
                            IntentVO iData = new IntentVO();
                            iData.setIntent(intentPredicted);
                            iData.setParams(pval);
                            System.out.println("input params >>> " + iData.getParams());
                            String res = RESTMicroServiceClient.executeRestServcie(iData);
                            System.out.println("res >>> " + res);
                            if(res != null)
                            {
                                msg = "{\"response\": \"" + res + "\"}";
                            }
                        } else {
                            //intent data format is not as per the expcted format in the file
                        }
                    } else {
                        //sending default msg
                    }
                }
            } else {
//                sending default msg
            }
        }
    }
    
    /**
     *
     * @param machineLResponse
     * @return
     */
    private String getIntentName(String machineLResponse) {

        String intent = "";
        if (machineLResponse != null && machineLResponse.length()>0) {
            intent = machineLResponse.substring(machineLResponse.lastIndexOf(" ")+1);
//            intent = intent.replaceAll("'", "");
        } else {
//            intent = machineLResponse.substring(0, machineLResponse.indexOf(","));
//            intent = intent.replaceAll("'", "");
        }
        return intent;
    }
}
