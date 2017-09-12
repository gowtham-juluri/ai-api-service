package com.api.ai.helper;

import com.api.ai.dto.EntityVO;
import com.api.ai.dto.IntentVO;
import com.api.ai.dto.ParameterVO;
import com.api.ai.dto.TrainingDataVO;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

// H2 Database Example
public class MySQLDBServiceHelper {

    //CREATE USER 'vadbuser'@'localhost' IDENTIFIED BY 'welcome1';
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/virtualagent";
    private static final String DB_USER = "vadbuser";
    private static final String DB_PASSWORD = "welcome1";

    public static void main(String[] args) throws Exception {

        MySQLDBServiceHelper h = new MySQLDBServiceHelper();
//        h.getIntentData("name of intent");
        String json = "{\"intent\":\"sdadxcxcv\",\"userSays\":\"sadasdsda\",\"textMessage\":\"sadasdasd\",\"action\":\"sadas\",\"params\":[{\"isRequired\":true,\"paramName\":\"sadasd\",\"entityName\":\"sadasd\",\"entityValue\":\"sadasd\",\"isList\":true}]}";
//
        Gson gson = new Gson();
        IntentVO iv = gson.fromJson(json, IntentVO.class);
        System.out.println("iv " + iv);
//        h.saveIntentData(iv);
//         Connection connection = h.getDBConnection();
    }

    /**
     *
     * @param intentName
     */
    public IntentVO getIntentData(String intentName) {

        String query = "SELECT INTENT_NAME,INTENT_ACTION, RESPONSE_MESSAGE,REQUIRED,"
                + "PARAM_NAME,ENTITY_NAME,ENTITY_VALUE IS_LIST "
                + "FROM INTENT_DATA INTENT, INTENT_ASS_ACTION_PARAMS PARAM "
                + "WHERE INTENT.INTENT_ID=PARAM.INTENT_ID AND INTENT.INTENT_NAME='" + intentName + "'";
        
        System.out.println("Query to fetch intent data >>>> " + query);
        Connection connection = getDBConnection();
        Statement stmt = null;
        IntentVO responseVO = new IntentVO();
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ParameterVO pvo = null;
            ArrayList<ParameterVO> a = new ArrayList<ParameterVO>();
            responseVO.setParams(a);
            while (rs.next()) {
                pvo = new ParameterVO();
                a.add(pvo);
                responseVO.setIntent(rs.getString("INTENT_NAME"));
                responseVO.setAction(rs.getString("INTENT_ACTION"));
                responseVO.setResponseMessage(rs.getString("RESPONSE_MESSAGE"));
                pvo.setIsRequired(rs.getString("REQUIRED"));
                pvo.setParamName(rs.getString("PARAM_NAME"));
                pvo.setEntityName(rs.getString("ENTITY_NAME"));
                pvo.setEntityValue(rs.getString("ENTITY_VALUE"));
                pvo.setIsList(rs.getString("IS_LIST"));
            }
            System.out.println("responseVO from H2 Database >>>> " + responseVO);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBServiceHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return responseVO;
    }

    /**
     *
     * @param ivo
     * @param usersaysList 
     */
    public void saveIntentData(IntentVO ivo, ArrayList<String> usersaysList) {
        
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(true);
            stmt = connection.createStatement();

            String i = "INSERT INTO INTENT_DATA(INTENT_NAME,INTENT_ACTION,RESPONSE_MESSAGE, INTENT_CREATE_DATE) VALUES("
                    + "'" + ivo.getIntent() + "',"
                    + "'" + ivo.getAction() + "',"
                    + "'" + ivo.getResponseMessage() + "',"
                    + "CURRENT_TIMESTAMP)";

            stmt.execute(i, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = stmt.getGeneratedKeys();
            Integer intentID = -1;
            if (rs.next()) {

                intentID = rs.getInt(1);
                System.out.println("intentID generated >>> " + intentID);
                
                String j = "INSERT INTO INTENT_ASS_ACTION_PARAMS(INTENT_ID,REQUIRED,PARAM_NAME,ENTITY_NAME,ENTITY_VALUE,ISLIST,PARAM_CREATE_DATE) "
                        + "VALUES(";
                String k = "";
                ArrayList<ParameterVO> apl = (ArrayList) ivo.getParams();
                
                for (ParameterVO apl1 : apl) {
                    k = "";
                    k = j + intentID.intValue() + ","
                            + "'" + apl1.getIsRequired() + "',"
                            + "'" + apl1.getParamName() + "',"
                            + "'" + apl1.getEntityName() + "',"
                            + "'" + apl1.getEntityValue() + "',"
                            + "'" + apl1.getIsList() + "',"
                            + "CURRENT_TIMESTAMP)";
                    System.out.println("Inserting Entity Param >>> " + k);
                    stmt.execute(k);
                }
                
                String m = "INSERT INTO USER_SAYS(INTENT_ID,USER_SAYS_TEXT) "
                        + "VALUES(";
                
                for (int l = 0; l < usersaysList.size(); l++) {
                    String u = usersaysList.get(l);
                    k = "";
                    k = m + intentID.intValue() + ","
                            + "'" + u + "')";
                    System.out.println("Inserting USER_SAYS >>> " + k);
                    stmt.execute(k);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MySQLDBServiceHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /**
     * 
     * @param al 
     */
    public void saveTrainingData(ArrayList<TrainingDataVO> al) {

        Connection connection = getDBConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection.setAutoCommit(true);
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            TrainingDataVO tDataVO;
            for (Iterator<TrainingDataVO> iterator = al.iterator(); iterator.hasNext();) {

                tDataVO = iterator.next();

                connection.setAutoCommit(true);
                stmt = connection.createStatement();
                String i = "INSERT INTO TRAINING_DATA(INTENT_NAME, USER_SAYS) VALUES("
                        + "'" + tDataVO.getIntentName() + "',"
                        + "'" + tDataVO.getUserSay() + "')";

                stmt.execute(i, Statement.RETURN_GENERATED_KEYS);

                rs = stmt.getGeneratedKeys();
                Integer intentID = -1;
                if (rs.next()) {

                    intentID = rs.getInt(1);
                    System.out.println("intentID generated (training data) >>> " + intentID.intValue());
                    String j = "INSERT INTO TRAINING_DATA_ENTITY_MAP(INTENT_ID,ENTITY_NAME,ENTITY_VALUE) VALUES(";
                    String k = "";
                    ArrayList<EntityVO> apl = (ArrayList) tDataVO.getEntityList();
                    if (apl != null) {
                        for (EntityVO apl1 : apl) {
                            k = "";
                            k = j + intentID.intValue() + ","
                                    + "'" + apl1.getEntityName() + "',"
                                    + "'" + apl1.getEntityValue() + "')";
                            System.out.println("k >>> " + k);
                            stmt.execute(k);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                try {
                    connection.close();
                    rs.close();
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLDBServiceHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    /**
     * 
     * @param al 
     */
    public void saveTrainingDataOLD(ArrayList<TrainingDataVO> al) {

        try {
            String INSERT_RECORDS = "INSERT INTO TRAINING_DATA(INTENT_NAME, USER_SAYS) VALUES(?,?)";
            Connection con = getDBConnection();
            PreparedStatement prepStmt = null;
            prepStmt = con.prepareStatement(INSERT_RECORDS);
            
            for (int i = 0; i < al.size(); i++) {
                TrainingDataVO td = al.get(i);
                prepStmt.setString(1,td.getIntentName());
                prepStmt.setString(2,td.getUserSay());
                prepStmt.addBatch();
            }
            prepStmt.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDBServiceHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }
}
