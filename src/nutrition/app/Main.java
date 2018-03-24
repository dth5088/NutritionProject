/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.time.LocalDate;
import java.time.Month;

/**
 *
 * @author dth5088
 */
public class Main {
    public static void main(String[] args) {
        double lbs_to_kg = 0.453592;
        double kg_to_lbs = 2.20462;
        double inch_to_cm = 2.54;
        double cm_to_inch = 0.393701;
        
        
        double myWeightInLbs = 180;      // in lbs
        double myHeightInInches = 72;       // in inches
        
        double myWeightInKg = myWeightInLbs * lbs_to_kg;
        double myHeightInCm = myHeightInInches * inch_to_cm;
        
        
        LocalDate dob = LocalDate.of(1992, Month.JUNE, 20);
        Gender gender = Gender.MALE;
        ActivityLevel activityLevel = ActivityLevel.SEDENTARY;
        Goal fitnessGoal = Goal.FAT_LOSS;
        
        User user = new User("Dustin","Huntoon",myHeightInCm,myWeightInKg,dob,gender,activityLevel,fitnessGoal);
        
        Food chicken1 = new Food("Chicken",1,24,0,7,1);
        Food chicken2 = new Food("Chicken",2,47,0,13,3);
        user.addFood(chicken1);
        user.addFood(chicken2);
        
        System.out.println(user);
        System.out.println(user.getFood());
        
        
    }
}
