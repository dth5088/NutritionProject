/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dth5088
 */
public class FoodService {
    public static void findRecipes(String ingredient1, String ingredient2, Callback callback) {
        String APP_KEY = Constants.APP_KEY;
        String APP_ID = Constants.APP_ID;
        
        OkHttpClient client = new OkHttpClient.Builder().build();
        
        String ingredients = (ingredient1 + "," + ingredient2).replaceAll("\\s", "");
        
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETER, ingredients);
        urlBuilder.addQueryParameter(Constants.APP_QUERY_PARAMETER,APP_ID);
        urlBuilder.addQueryParameter(Constants.KEY_QUERY_PARAMETER, APP_KEY);
        String url = urlBuilder.build().toString();
        
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

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
    
    public ArrayList<Recipe> processResults(Response response) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        
        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject returnJSON = new JSONObject(jsonData);
                JSONArray recipesJSON = returnJSON.getJSONArray("hits");
                for(int i =  0; i < 10; i++) {
                    JSONObject recipeArrayJSON = recipesJSON.getJSONObject(i);
                    JSONObject recipeJSON = recipeArrayJSON.getJSONObject("recipe");
                    String name = recipeJSON.getString("label");
                    String imageUrl = recipeJSON.getString("image");
                    String sourceUrl = recipeJSON.getString("url");
                    String ingredients = recipeJSON.getJSONArray("ingredientLines").toString();

                    Recipe recipe = new Recipe (name, imageUrl, sourceUrl, ingredients);
                    recipes.add(recipe);
                }
                
            }
        } catch(IOException | JSONException e) {
            
        }
        return recipes;
    }
    
    public static String getNutritionFacts(String ingredient) throws IOException{
        String urlString, contentAsString, jsonResult;
        
        String BASE_URL, APP_ID, APP_KEY, INGREDIENT;
        HttpURLConnection conn = null;
        InputStream is = null;
        URL url;
        int response;
        
        try {
            
            String stringURL = Constants.BASE_URL + "?" + Constants.APP_QUERY_PARAMETER + "=" + Constants.APP_ID + "&" +
                    Constants.KEY_QUERY_PARAMETER + "="+Constants.APP_KEY +"&"+Constants.INGREDIENT_PARAMETER + "=" + URLEncoder.encode(ingredient, "UTF-8").replace("+","%20");
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
        }
        finally {
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
    
    public static String parseFood(String food) throws IOException{
        String urlString, contentAsString, jsonResult;
        
        HttpURLConnection conn = null;
        InputStream is = null;
        URL url;
        int response;
        
        try {
            
            
            String stringURL = buildParserUrl(food);
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
        }
        finally {
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
    private static String buildParserUrl(String food) throws UnsupportedEncodingException {
       String APP_KEY = String.join("=",Constants.KEY_QUERY_PARAMETER, Constants.APP_KEY);
       String APP_ID = String.join("=", Constants.APP_QUERY_PARAMETER, Constants.APP_ID);
       String INGREDIENT = String.join("=", Constants.INGREDIENT_PARAMETER, jsonEncode(food));
       String ret = Constants.PARSER_BASE_URL + "?" + INGREDIENT + "&" + APP_ID + "&" + APP_KEY;
       return ret;
    }
    private static String jsonEncode(String in) throws UnsupportedEncodingException {
        return URLEncoder.encode(in, "UTF-8").replace("+","%20");
    }
    
}
