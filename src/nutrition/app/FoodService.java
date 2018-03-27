/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.io.IOException;
import java.util.ArrayList;
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
    public static void findReciples(String ingredient1, String ingredient2, Callback callback) {
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
}
