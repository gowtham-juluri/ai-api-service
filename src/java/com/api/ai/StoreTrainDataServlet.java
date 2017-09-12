/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.ai;

import static com.api.ai.StroreInDBServlet.log;
import com.api.ai.dto.TrainingDataVO;
import com.api.ai.helper.MLPythonAPI;
import com.api.ai.helper.MySQLDBServiceHelper;
import com.api.ai.helper.TrainingDataExcelReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.persistence.internal.oxm.conversion.Base64;




/**
 *
 * @author gowtham.juluri
 */
public class StoreTrainDataServlet extends HttpServlet {

    
    private static String FILE_PATH = "C:\\artificial-intelligence-poc\\TrainingData.xlsx";
    private static String AUDIO_FILE_PATH = "C:\\artificial-intelligence-poc\\";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        log.info("Executing POST method (Trainig trainingContent)....");        
        boolean hasTD = false;
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) {

                    if (item.isFormField()) {

                        String fieldName = item.getFieldName();
                        
                        System.out.println("fieldName(isFormField)...." + fieldName);
                        
                        if(fieldName.equalsIgnoreCase("data"))
                        {
                            String encoded = item.getString().split(",")[1];;
                            System.out.println("fieldValue(isFormField)...." + encoded);
                            FileOutputStream outputStream = null;                            
                            try {
                                byte[] decoded = Base64.base64Decode(encoded.getBytes());                               

                                outputStream = new FileOutputStream(new File(AUDIO_FILE_PATH + "audio-file"+".wav"));
                                outputStream.write(decoded);
                                System.out.println("Done with audio file copy to server...!");
                                hasTD = true;
                                outputStream.close();
                            } catch (IOException ex) {
                                Logger.getLogger(StroreInDBServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                
                            }
                        }
                    } else {
                        String fieldName = item.getFieldName();
                        System.out.println("fieldName(NOT isFormField)...." + fieldName);
                        if (fieldName != null && fieldName.equalsIgnoreCase("trainingContent")) {
                            String fileName = FilenameUtils.getName(item.getName());
                            InputStream fileContent;
                            try {
                                fileContent = item.getInputStream();

                                OutputStream outputStream = new FileOutputStream(new File(FILE_PATH));
                                int read = 0;
                                byte[] bytes = new byte[1024];

                                while ((read = fileContent.read(bytes)) != -1) {
                                    outputStream.write(bytes, 0, read);
                                }
                                System.out.println("Done with file copy to server...!");
                                hasTD = true;
                                outputStream.close();
                            } catch (IOException ex) {
                                Logger.getLogger(StroreInDBServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else if (fieldName != null && fieldName.equalsIgnoreCase("name")) {
                            
                        }
                    }
                }
            } catch (FileUploadException e) {
                throw new ServletException("Cannot parse multipart request.", e);
            }
        }
        TrainingDataExcelReader excelParse = new TrainingDataExcelReader();
        MySQLDBServiceHelper dbSave = new MySQLDBServiceHelper();
        if (hasTD) {
            System.out.println("hasTD is true...!");
            ArrayList<TrainingDataVO> al = excelParse.getTrainingDataAsList(FILE_PATH);
            dbSave.saveTrainingData(al);
            System.out.println("calling script >>>>>>>>>>>>>>>>>>>>>>>");
            MLPythonAPI.callPyhonScriptToTrain();

        }
        else
        {
            return;
        }
    }
}
