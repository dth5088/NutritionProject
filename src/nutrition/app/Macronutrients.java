/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.text.DecimalFormat;

/**
 *
 * @author dth5088
 */
public class Macronutrients {
    private double carbohydrate;        // 1 gram = 4 calories
    private double carbPercent = 0.5;
    private double protein;             // 1 gram = 4 calories
    private double proteinPercent = 0.25;
    private double fat;                 // 1 gram = 9 calories
    private double fatPercent = 0.25;
    private double calories;            // calories = carbs + proteins + fat
    
    
    public Macronutrients(double calories, double carbohydrate, double protein, double fat) {
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calories = calories;
    }
    
    public Macronutrients(double totalDailyEnergyExpenditure, double weight, Goal goal) {
        
        switch(goal) {
            case FAT_LOSS:
                calories = totalDailyEnergyExpenditure - (totalDailyEnergyExpenditure * 0.20);
                carbohydrate = (calories * carbPercent) / 4;
                protein = (calories * proteinPercent) / 4;
                fat = (calories * fatPercent) / 9;
                break;
            case MAINTAIN:
                calories = totalDailyEnergyExpenditure;
                carbohydrate = (calories * carbPercent) / 4;
                protein = (calories * proteinPercent) / 4;
                fat = (calories * fatPercent) / 9;
                break;
            case GAIN_MASS:
                calories = totalDailyEnergyExpenditure + (totalDailyEnergyExpenditure * 0.20);
                protein = weight * 0.825;
                fat = (calories * fatPercent) / 9;
                carbohydrate = (calories - (fat * 9) - (protein * 4))/4;
                break;
                
        }
    }
    
    public double getCarbohydrates() {
        return carbohydrate;
    }
    
    public double getCalories() {
        return calories;
    }
    
    public double getProtein() {
        return protein;
    }
    public double getFat() {
        return fat;
    }
    
    @Override
    public String toString() {
        String str = "\n*******************************************************\n";
        str += String.format("*%s %.2f\n", " Total Calories:",calories);
        str += String.format("*%s%.2f%s %6s%.2f%s %6s%.2f%s\n"," Carbohydrates:",carbohydrate,"g","Protein:",protein,"g","Fat:",fat,"g");
        str += "*******************************************************";
        return str;
    }
}
