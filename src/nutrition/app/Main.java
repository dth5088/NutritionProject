/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nutrition.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author dth5088
 */
public class Main {
    static double lbs_to_kg = 0.453592;
    static double kg_to_lbs = 2.20462;
    static double inch_to_cm = 2.54;
    static double cm_to_inch = 0.393701;
    
    
    public static void main(String[] args) {
        
         try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
         MainFrame mainFrame = new MainFrame();
    }
    
    private static User makeDustin() {
        double myWeightInLbs = 180;
        double myHeightInInches = 72;
        double myWeightInKg = myWeightInLbs * lbs_to_kg;
        double myHeightInCm = myHeightInInches * inch_to_cm;
        LocalDate dob = LocalDate.of(1992, Month.JUNE, 20);
        Gender gender = Gender.MALE;
        ActivityLevel activityLevel = ActivityLevel.SEDENTARY;
        Goal fitnessGoal = Goal.FAT_LOSS;
        
        return new User("Dustin","Huntoon",myHeightInCm,myWeightInKg,dob,gender,activityLevel,fitnessGoal);
    }
    
    private static User makeStefanie() {
        double weight = 155 * lbs_to_kg;
        double height = 63 * inch_to_cm;
        LocalDate dob = LocalDate.of(1991,Month.SEPTEMBER, 9);
        Gender gender = Gender.FEMALE;
        ActivityLevel activityLevel = ActivityLevel.SEDENTARY;
        Goal fitnessGoal = Goal.FAT_LOSS;
        return new User("Stefanie","Huntoon",height,weight,dob,gender,activityLevel,fitnessGoal);
    }
    
    /**
     * Method to create Ryan.
     * 
     * @param firstName             -> YOUR FIRST NAME
     * @param lastName              -> YOUR LAST NAME
     * @param yourHeightInInches    -> YOUR HEIGHT IN INCHES
     * @param yourWeightInLbs       -> YOUR WEIGHT IN POUNDS
     * @param dateOfBirth           -> STRING REPRESENTATION OF YOUR BIRTHDAY (MONTH/DAY/YEAR) EX. "6/20/1992"
     * @param activityLevel         -> CAN BE activityLevel.SEDENTARY, activityLevel.LIGHT, activityLevel.MODERATE, OR activityLevel.HEAVY
     * @param fitnessGoal           -> CAN BE Goal.FAT_LOSS, Goal.MAINTAIN, OR Goal.GAIN_MASS
     * @return 
     */
    private static User makeRyan(String firstName, String lastName, double yourHeightInInches, double yourWeightInLbs, String dateOfBirth, ActivityLevel activityLevel, Goal fitnessGoal)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");    // Create format for String date(Month/Day/Year) -> LocalDate conversion   
        double heightInCm = yourHeightInInches * inch_to_cm;                        //convert your height in inches to centimeters
        double weightInKg = yourWeightInLbs * lbs_to_kg;                            // convert your weight in lbs to kg
        LocalDate dob = LocalDate.parse(dateOfBirth, formatter);                    // convert String date to LocalDate date using MM/dd/yyyy format
        Gender gender = Gender.MALE;
        
        return new User(firstName,lastName, heightInCm, weightInKg, dob, gender, activityLevel, fitnessGoal);
    }
    
    private static class MainFrame extends JFrame {
        
        
        public MainFrame() {
            createAndShowGUI();
            
        }

        private void createAndShowGUI() {
            JFrame frame = new JFrame("Nutrition Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            init(frame.getContentPane());
            frame.setPreferredSize(new Dimension(1200, 800));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        
        private void init(Container contentPane) {
            //contentPane.setLayout(new BorderLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            JTextArea textArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(800,600));
            
            gbc.gridx = 1;
            gbc.gridy = 1;
            
            contentPane.add(scrollPane, BorderLayout.CENTER);
            String test = "1 large apple";
            String test2 = "red apple";
            try {
                //String result = FoodService.getNutritionFacts(test);
                String result2 = FoodService.parseFood(test2);
                
                
                textArea.setText(result2);
            } catch(IOException e) {
            }
        }
        

    }
}
