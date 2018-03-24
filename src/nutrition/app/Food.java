/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.util.HashMap;

/**
 *
 * @author dth5088
 */
public class Food {
    String name;
    int portionSize;
    Macronutrients macro;
    
    public Food(String name, int portion, double calories, double carbs, double protein, double fat)
    {
        this.name = name;
        portionSize = portion;
        macro = new Macronutrients(calories, carbs, protein, fat);
    }
    
    
    public String toString() {
        return String.format("%s %s",name, macro);
    }
}
