/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author dth5088
 */
public class Constants {
    
    public static final String APP_KEY = "9545905b112d501f51a0b2169292158f";
    public static final String APP_ID = "003195be";
    
    public static final String BASE_URL = "https://api.edamam.com/api/nutrition-data";
    public static final String PARSER_BASE_URL = "https://api.edamam.com/api/food-database/parser";
    public static final String INGREDIENT_PARAMETER = "ingr";
    public static final String QUERY_PARAMETER = "q";
    public static final String KEY_QUERY_PARAMETER = "app_key";
    public static final String APP_QUERY_PARAMETER = "app_id";
    
    
    public static String USDA_API_KEY = "api_key";
    public static String USDA_API_KEYVALUE = "b9KNuZaS5aw9SP8nXcS94XoPwEeGPHrMkzGJUN7H";
    public static String USDA_SEARCH_FOOD_URL = "https://api.nal.usda.gov/ndb/search/";
    public static String USDA_REPORT_URL = "https://api.nal.usda.gov/ndb/reports/";
    
    public static Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    public static Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    public static Border compound = BorderFactory.createCompoundBorder(raisedbevel,loweredbevel);
    
    public static String USDA_FORMAT_KEY = "format";
    public static String USDA_SEARCH_TERM_KEY = "q";
    public static String USDA_DATA_SOURCE_KEY = "ds";
    public static String USDA_FOOD_GROUP_KEY = "fg";
    public static String USDA_SORT_OPTION_KEY = "sort";
    public static String USDA_SORT_FOODNAME = "n";
    public static String USDA_SORT_RELEVANCE = "r";
    public static String USDA_MAX_KEY = "max";
    public static String USDA_OFFSET_KEY = "offset";
    public static String USDA_FORMAT_VALUE = "json";
    public static String USDA_DBNO_KEY = "ndbno";
    public static String USDA_TYPE_KEY = "type";
    public static String USDA_LISTTYPE_KEY = "lt";              // can be d = derivation codes, f = food , n = all nutrients, ns = speciality nutrients, nr = standard release nutrients only,g = food group
    
    
    
    
}
