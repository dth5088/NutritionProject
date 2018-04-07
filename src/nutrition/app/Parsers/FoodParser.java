package nutrition.app.Parsers;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dth5088
 */
public class FoodParser {
    FoodList searchResults;
    
    public FoodParser(String searchTerm, String usdaResponse) throws JSONException {
        JSONObject object = new JSONObject(usdaResponse);
        searchResults = new FoodList(searchTerm);
        JSONObject list = object.getJSONObject("list");
        
        JSONArray arr = (JSONArray)list.get("item");
        for(int i = 0; i < arr.length();i++) {
            JSONObject foodObject = arr.getJSONObject(i);
            USDAFood food = new USDAFood(foodObject);
            searchResults.addFood(food);
            
        }
    }
    
    
    public FoodParser(FoodList searchResults) {
        this.searchResults = searchResults;
    }
    
    public void setList(FoodList searchResults) {
        this.searchResults = searchResults;
    }
    
    public FoodList getSearchResults() {
        return searchResults;
    }
}
