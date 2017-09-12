/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author gowtham.juluri
 */
public class MLPythonAPI {

    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MLPythonAPI.class);
    
    private static String DIR ="C:\\artificial-intelligence-poc\\";
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args)
    {
       callPyhonScriptToPredict("book a ticket");
    }
    /**
     * 
     * @return 
     */
    private static String getRandomFileID() {
        
        Random r = new Random();
        int numbers = 10000000 + (int)(r.nextFloat() * 89990000);
        String folderID =  String.valueOf(numbers);
        
        return folderID;
    }
    
    /**
     * 
     * @param userSays
     * @return
     * @throws IOException
     * @throws InterruptedException 
     */
    public static String callPyhonScriptToPredict(
            String userSays){
        
        log.info("started method callPyhonScriptToPredict now!");
        String fileID = getRandomFileID();
        String filePath = DIR + "VAResponse - " + fileID + ".txt";
        File f = new File(filePath);
        try {
            f.createNewFile();
            ProcessBuilder pb = new ProcessBuilder().inheritIO().
                    command("python", "C:\\artificial-intelligence-poc\\script\\RASA-NLU\\AI_rasa\\predict.py", userSays, filePath);
            Process p = pb.start();
            p.waitFor();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String s = "";
            System.out.println(s);

            while ((s = stdInput.readLine()) != null) {
                System.out.println("MLPythonAPI >>> (inside while)");
                System.out.println(s);
            }
            System.out.println("Done now!(file path created for this request) " + filePath);
           
        } catch (IOException ex) {
            Logger.getLogger(MLPythonAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MLPythonAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
         return filePath;
//         return "C:\\artificial-intelligence-poc\\VAResponse - 11635606.txt";
    }
    /**
     * 
     */
    public static void callPyhonScriptToTrain() {

        System.out.println("callPyhonScriptToTrain START ");
        try {
            log.info("callPyhonScriptToTrain started now!");
            
            ProcessBuilder pb = new ProcessBuilder().inheritIO().
                    command("python", "C:\\artificial-intelligence-poc\\script\\RASA-NLU\\AI_rasa\\train_agent.py");
            Process p = pb.start();
            p.waitFor();
            System.out.println("callPyhonScriptToTrain END. Completed");
        } catch (IOException ex) {
            Logger.getLogger(MLPythonAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MLPythonAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
