/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 *
 * @author dth5088
 */
public class FoodService {

    private static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
            String buffer = "";
            BufferedReader reader = null;
            
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line = null;
            while((line = reader.readLine()) != null) {
                buffer += line + "\n";
            }
        return buffer;
    }

    public static String fetchUSDA_FoodList(HashMap<String,String> options) throws IOException {
        
        HttpURLConnection conn = null;
        InputStream is = null;
        URL url;
        int response;
        String contentAsString;
        
        try {
            String stringURL = "";
            String tempURL = Constants.USDA_SEARCH_FOOD_URL + "?"; 
            String op1 = "",op2= "",op3= "",op4= "",op5= "",op6 = "";
           for(HashMap.Entry<String,String> entry : options.entrySet())
           {
               String temp = String.join("=", entry.getKey(), entry.getValue());
               switch(entry.getKey()) {
                   case "format":
                       op1 = temp;
                       tempURL += op1;
                       break;
                   case "q":
                       op2 = temp;
                       break;
                   case "sort":
                       op3 = temp;
                       break;
                   case "max":
                        op4 = temp;
                        break;
                   case "offset":
                       op5 = temp;
                       break;
                   case "api_key":
                       op6 = temp;
                       break;
               }
           }
            stringURL += String.join("&",tempURL,op2,op3,op4,op5,op6);
            System.out.println(stringURL);
            url = new URL(stringURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            
            response = conn.getResponseCode();
            
            if(response != HttpURLConnection.HTTP_OK) {
                return "Server returned: " + response + " aborting read.";
            }
            is = conn.getInputStream();
            
            contentAsString = readIt(is);
            return contentAsString;    
        } finally {
            if(is != null)
                try {
                    is.close();
                } catch(IOException ignore) {}
            if(conn != null)
                try {
                    conn.disconnect();
                } catch(IllegalStateException ignore) {}
        }
    }
    
    public static String getNutrientsFromNDBno(HashMap<String,String> options) throws IOException{ 
        HttpURLConnection conn = null;
        InputStream is = null;
        URL url;
        int response;
        String contentAsString;
        
        try {
            String stringURL = "";
            String tempURL = Constants.USDA_REPORT_URL + "?"; 
            String op1 = "",op2= "",op3= "",op4= "";
            for(HashMap.Entry<String,String> entry : options.entrySet())
           {
               String temp = String.join("=", entry.getKey(), entry.getValue());
               switch(entry.getKey()) {
                   case "ndbno":
                       op1 = temp;
                       tempURL += op1;
                       break;
                   case "type":
                       op2 = temp;
                       break;
                   case "format":
                       op3 = temp;
                       break;
                   case "api_key":
                        op4 = temp;
                        break;
               }
           }
            stringURL += String.join("&",tempURL,op2,op3,op4);
            url = new URL(stringURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            
            response = conn.getResponseCode();
            
            if(response != HttpURLConnection.HTTP_OK) {
                return "Server returned: " + response + " aborting read.";
            }
            is = conn.getInputStream();
            
            contentAsString = readIt(is);
            return contentAsString;    
        } finally {
            if(is != null)
                try {
                    is.close();
                } catch(IOException ignore) {}
            if(conn != null)
                try {
                    conn.disconnect();
                } catch(IllegalStateException ignore) {}
        }
    }
    public static String getFoodListMatching(String food) throws IOException{
        HttpURLConnection conn = null;
        InputStream is = null;
        URL url;
        int response;
        String contentAsString;
        try {
            String stringURL  = Constants.USDA_SEARCH_FOOD_URL + "?format=json&q="+food+"&sort=n&max=25&offset=0&api_key="+Constants.USDA_API_KEY; 
            url = new URL(stringURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            
            response = conn.getResponseCode();
            
            if(response != HttpURLConnection.HTTP_OK) {
                return "Server returned: " + response + " aborting read.";
            }
            is = conn.getInputStream();
            
            contentAsString = readIt(is);
            return contentAsString;    
        } finally {
            if(is != null)
                try {
                    is.close();
                } catch(IOException ignore) {}
            if(conn != null)
                try {
                    conn.disconnect();
                } catch(IllegalStateException ignore) {}
        }
    }
    
    private static String jsonEncode(String in) throws UnsupportedEncodingException {
        return URLEncoder.encode(in, "UTF-8");
    }
    
}
