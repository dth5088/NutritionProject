/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app.Parsers;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author dth5088
 */
public class FoodList {
    String foodName;
    ArrayList<USDAFood> list;
    public FoodList(String foodName) {
        this.foodName = foodName;
        list = new ArrayList<>();
    }
    
    public FoodList(FoodList foodList) {
        this.foodName = foodList.foodName;
        this.list = foodList.list;
    }
    public void addFood(USDAFood food) {
        list.add(food);
    }
    
    public USDAFood getFoodFromListByName(String name) {
      
        for(USDAFood food : list) {
            if(food.getFoodName().equalsIgnoreCase(name))
                return food;
        }
        return null;
    }
    
    public ArrayList<USDAFood> getFoodList() {
        return list;
    }
   
    public String toString() {
        String str = String.format("%s%s\n", "Food:",foodName);
        for(USDAFood food : list) {
            str += String.format("%s\n",food.toString());
        }
        return str;
    }
}
