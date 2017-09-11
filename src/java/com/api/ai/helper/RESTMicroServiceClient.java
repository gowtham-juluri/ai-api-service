package com.api.ai.helper;

import com.api.ai.dto.IntentVO;
import com.api.ai.dto.ParameterVO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class RESTMicroServiceClient {

    public static void main(String[] args) {

        try {
            IntentVO i = new IntentVO();
            i.setIntent("Travel");
            ArrayList<ParameterVO> pl = new ArrayList<ParameterVO>();
            
            ParameterVO p = new ParameterVO();            
            p.setEntityName("DATE");
            p.setEntityValue("tomorrow");
            pl.add(p);
            
            p = new ParameterVO();
            p.setEntityName("GPE");
            p.setEntityValue("Mumbai, Delhi");
            pl.add(p);
            
            i.setParams(pl);
            executeRestServcie(i);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RESTMicroServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param intent
     * @return
     */
    public static String executeRestServcie(IntentVO ivo) throws UnsupportedEncodingException {

        String u = "http://localhost:8080/api-ai-service/webresources/rest-micro/action";
        
        if(ivo.getIntent() != null && ivo.getIntent().length() >0)
        {
            u = "http://localhost:8080/api-ai-service/webresources/rest-micro/action?intent="+ivo.getIntent();
            
            ArrayList<ParameterVO> pl = (ArrayList)ivo.getParams();
            System.out.println("pl >>>>>>>>>>>>>>> " + pl);
            String qS = "";
            String ePair = "";
            for (int i = 0; i < pl.size(); i++) {
                ParameterVO g = pl.get(i);
                if(g.getEntityName() != null)
                {
                    ePair = "&";
                    ePair = ePair + g.getEntityName();                    
                    if(g.getEntityValue()!= null)
                    {
                        ePair = ePair + "=" + g.getEntityValue();
                    }
                }
                qS = qS + ePair;
            }
            qS = URLEncoder.encode(qS,"UTF-8");
            if(qS.length() >0)
            {
                u = u + "&" + qS;
            }
        }
        System.out.println("U >>>>>>>>>>>>>>> " + u);
        String output = "REST Service Error!!!!";
	  try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(u);
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
//            System.out.println(response.getEntity().getContent());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            System.out.println("Output from Server .... \n");
//            output = br.readLine();
            while ((output = br.readLine()) != null) {
                System.out.println("HERE....." + output);
                break;
            }
            httpClient.getConnectionManager().shutdown();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
          System.out.println("before sending...." + output);
        return output;
    }
}
