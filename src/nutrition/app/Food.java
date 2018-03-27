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
    UnitOfMeasurement unitOfMeasurement;
    
    public Food(String name, int portionSize, double calories, double carbs, double protein, double fat)
    {
        this.name = name;
        this.portionSize = portionSize;
        macro = new Macronutrients(calories, carbs, protein, fat);
    }
    
    public Food(String name, int portionSize, String uom, double calories, double carbs, double protein, double fat) {
        this.name = name;
        this.portionSize = portionSize;
        macro = new Macronutrients(calories, carbs, protein, fat);
        setUnitOfMeasurement(uom);
    }
    
    public Food(String name, int portionSize, String uom, Macronutrients macro) {
        this.name = name;
        this.portionSize = portionSize;
        this.macro = macro;
        setUnitOfMeasurement(uom);
    }
    
    private void setUnitOfMeasurement(String stringUnitOfMeasurement) {
        switch(stringUnitOfMeasurement)
        {
            case "oz":
                unitOfMeasurement = UnitOfMeasurement.OUNCE;
                break;
            case "gram":
                unitOfMeasurement = UnitOfMeasurement.GRAM;
                break;
            case "cup":
                this.unitOfMeasurement = UnitOfMeasurement.CUP;
                break;
            case "tsp":
                this.unitOfMeasurement = UnitOfMeasurement.TEASPOON;
                break;
            case "tbsp":
                this.unitOfMeasurement = UnitOfMeasurement.TABLESPOON;
                break;
        }
    }
    
    public String toString() {
        return String.format("%s %s",name, macro);
    }
}
