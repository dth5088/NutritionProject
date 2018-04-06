/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 *
 * @author dth5088
 */
public class User {
    private String firstName;
    private String lastName;
    private double height = 0;
    private double weight = 0;
    private LocalDate dob;
    private int age = 0;
    private ArrayList<Food> preferredFoods = new ArrayList<>();
    private Goal fitnessGoal;
    private ActivityLevel activityLevel;
    private Gender gender;
    private Macronutrients macros;
    private double REE = 0;
    private double TDEE = 0;
    

    /**
     * Constructs a new User.
     * 
     * @param firstName
     * @param lastName
     * @param height
     * @param weight
     * @param dob
     * @param gender
     * @param activityLevel
     * @param fitnessGoal 
     */
    public User(String firstName, String lastName, double height, double weight, LocalDate dob, Gender gender, ActivityLevel activityLevel, Goal fitnessGoal) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.fitnessGoal = fitnessGoal;
        setAge();
        calculateMacros();
    }
    
    /**
     * Set Age as Years from Date of Birth to Now
     */
    private void setAge() {
        LocalDate now = LocalDate.now();
        age = Period.between(dob, now).getYears();
    }
    
    /**
     * Set or change goal
     * 
     * @param goal 
     */
    public void setGoal(Goal goal) {
        fitnessGoal = goal;
    }
    
    /**
     * set or change activityLevel.
     * 
     * @param level 
     */
    public void setActivityLevel(ActivityLevel level) {
        activityLevel = level;
    }
    
    /**
     * Add Food to preferredFoods arrayList.
     * 
     * @param food 
     */
    public void addFood(Food food) {
        preferredFoods.add(food);
    }
    
    public void parsePreferredFoods() {
        FoodListParser parser = new FoodListParser(this);
    }
    
    public String getFood() {
        String foods = "";
        for(Food food : preferredFoods) {
            foods += "\n" + food + "\n";
        }
        return foods;
    }
    
    public String getReeAndTdee() {
        return "REE: " + REE + " TDEE: " + TDEE;
    }
    /**
     * Sets macros to the result of calling the proper method (male/female)
     * to calculate macros based on all user input.
     */
    private void calculateMacros() {
        if(activityLevel != null && fitnessGoal != null && gender != null)
        {
            switch(gender)
            {
                case MALE:
                    macros = forMale();
                    break;
                case FEMALE:
                    macros = forFemale();
                    break;
            }
        }
    }
     
    /**
     * Returns the Macronutrient constructed for a Male user with the specified
     * activityLevel, gender, and goal.
     * 
     * @return suggestedMacros
     */
    private Macronutrients forMale() {
        double restingEnergyExpenditure  = ((10 * weight) + (6.25 * height) - (5 * age) + 5);
        REE = restingEnergyExpenditure;
        double totalDailyEnergyExpenditure = 0;
        
        switch(activityLevel)
        {
            case SEDENTARY:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.2);
                break;
                
            case LIGHT:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.375);
                break;
                
            case MODERATE:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.55);
                break;
                
            case HEAVY:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.725);
                break;
        }
        TDEE = totalDailyEnergyExpenditure;
        return new Macronutrients(totalDailyEnergyExpenditure, weight, fitnessGoal);
    }
    
    /**
     * Returns the Macronutrient constructed for a Female user with the specified
     * activityLevel, gender, and goal.
     * 
     * @return suggestedMacros
     */
    private Macronutrients forFemale() {
        double restingEnergyExpenditure  = (10 * weight + 6.25 * height - 5 * age - 161);
        REE = restingEnergyExpenditure;
        double totalDailyEnergyExpenditure = 0;
        
        switch(activityLevel)
        {
            case SEDENTARY:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.2);
                break;
                
            case LIGHT:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.375);
                break;
                
            case MODERATE:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.55);
                break;
                
            case HEAVY:
                totalDailyEnergyExpenditure = (restingEnergyExpenditure * 1.725);
                break;
        }
        TDEE = totalDailyEnergyExpenditure;
        return new Macronutrients(totalDailyEnergyExpenditure, weight, fitnessGoal);
    }
    
    /**
     * If macros can be calculated, calculate and return it, if it's already been calculated, return it, if not return null;
     * 
     * @return macros || null
     */
    public Macronutrients getMacronutrients() {
        if(macros == null && activityLevel != null && fitnessGoal != null && gender != null)
        {
            calculateMacros();
        }
        if(macros != null)
            return macros;
        return null;
    }
    
    public String getGenderString() {
         switch(gender)
        {
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
        }
         return "";
    }
    
    public String getFitnessGoalString() {
         switch(fitnessGoal)
        {
            case FAT_LOSS:
                return "Fat Loss";
            case MAINTAIN:
                return "Maintain";
            case GAIN_MASS:
                return "Gain Mass";
            default:
                return "Fat Loss";
        }
    }
    
    public String getActivityLevelString() {
        switch(activityLevel) {
            case SEDENTARY:
                return "Sedentary";
            case LIGHT:
                return "Light";
            case MODERATE:
                return "Moderate";
            case HEAVY:
                return "Heavy";
        }
        return "";
    }
    @Override
    public String toString() {
        String str = String.format("%s %6s %6s","Name:", firstName, lastName);
        switch(gender)
        {
            case MALE:
                str += String.format("%10s %s\n", "Gender:", "Male");
                break;
            case FEMALE:
                str += String.format("%10s%s\n", "Gender:", "Female");
                break;
        }
        str += String.format("%s%d%s%10s%.2f%s%10s%.2f%s\n", "Age:",age, "(y)","Height:", height,"cm" ,"Weight:", weight,"kg");
        switch(fitnessGoal)
        {
            case FAT_LOSS:
                str += String.format("\t%s%s", "Goal:", "Lose Fat");
                break;
            case MAINTAIN:
                str += String.format("\t%s%s", "Goal:","Maintain");
                break;
            case GAIN_MASS:
                str += String.format("\t%s%s","Goal:","Gain Mass");
                break;
        }
        if(macros != null)
        {
            str += "\n" + macros.toString();
        }
        return str;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getHeightString() {
        return height + "";
    }
    
    public String getWeightString() {
        return String.format("%.2f",weight);
    }
    
    public String getbdayString() {
        return dob.toString();
    }
    
    public String getAgeString() {
        return age + "";
    }

    //firstName,lastName,height,weight,dateofbirth,age,goal,activity,gender
    public String getOutputString() {
        
        return firstName +","+lastName+","+height+","+weight+","+dob+","+getFitnessGoalString()+","+getActivityLevelString()+","+getGenderString();
    }
    
    
}
