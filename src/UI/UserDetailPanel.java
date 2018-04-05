/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nutrition.app.Macronutrients;
import nutrition.app.User;

/**
 *
 * @author dth5088
 */
public class UserDetailPanel extends JPanel{
    int style = Font.BOLD | Font.ITALIC;
    Font font = new Font ("Arial", style , 14);
    User user;
    JLabel welcomeLabel;
    JLabel heightLabel = new JLabel("Height:"), weightLabel = new JLabel("Weight:")
            ,bdayLabel = new JLabel("Date of Birth:"), ageLabel = new JLabel("Age:"),genderLabel = new JLabel("Gender:")
            ,activityLabel = new JLabel("Activity Level:"),goalLabel = new JLabel("Goal:")
            ,recCalsLabel = new JLabel("Daily Calories:"), recCarbsLabel = new JLabel("Daily Carbs(g):")
            ,recProteinLabel = new JLabel("Daily Protein(g):"), recFatLabel = new JLabel("Daily Fat(g):");
    JTextField heightField = new JTextField(), weightField = new JTextField()
            ,bdayField = new JTextField(), ageField = new JTextField()
            , genderField = new JTextField(),activityField = new JTextField()
            ,goalField = new JTextField()
            ,calsField = new JTextField(),carbsField = new JTextField()
            ,proteinField = new JTextField(),fatField = new JTextField();
    
    public UserDetailPanel(User user) {
        this.user = user;
        welcomeLabel = new JLabel("Welcome, " + user.getFullName());
        welcomeLabel.setFont(font);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        setupFields(user);
        setupLayout();
        setFont(font);
    }
    
    private void setupFields(User user) {
        Macronutrients macros = user.getMacronutrients();
        int labelAlignment = JLabel.RIGHT;
        
        heightLabel.setFont(font);
        heightLabel.setHorizontalAlignment(labelAlignment);
        heightField.setEditable(false);
        heightField.setFont(font);
        heightField.setText(user.getHeightString());
        heightField.setHorizontalAlignment(JTextField.CENTER);
        
        
        weightLabel.setFont(font);
        weightLabel.setHorizontalAlignment(labelAlignment);
        weightField.setEditable(false);
        weightField.setFont(font);
        weightField.setText(user.getWeightString());
        weightField.setHorizontalAlignment(JTextField.CENTER);
        
        bdayLabel.setFont(font);
        bdayLabel.setHorizontalAlignment(labelAlignment);
        bdayField.setEditable(false);
        bdayField.setFont(font);
        bdayField.setText(user.getbdayString());
        bdayField.setHorizontalAlignment(JTextField.CENTER);
        
        ageLabel.setFont(font);
        ageLabel.setHorizontalAlignment(labelAlignment);
        ageField.setEditable(false);
        ageField.setFont(font);
        ageField.setText(user.getAgeString());
        ageField.setHorizontalAlignment(JTextField.CENTER);
        
        genderLabel.setFont(font);
        genderLabel.setHorizontalAlignment(labelAlignment);
        genderField.setEditable(false);
        genderField.setFont(font);
        genderField.setText(user.getGenderString());
        genderField.setHorizontalAlignment(JTextField.CENTER);
        
        activityLabel.setFont(font);
        activityLabel.setHorizontalAlignment(labelAlignment);
        activityField.setEditable(false);
        activityField.setFont(font);
        activityField.setText(user.getActivityLevelString());
        activityField.setHorizontalAlignment(JTextField.CENTER);
        
        goalLabel.setFont(font);
        goalLabel.setHorizontalAlignment(labelAlignment);
        goalField.setEditable(false);
        goalField.setFont(font);
        goalField.setText(user.getFitnessGoalString());
        goalField.setHorizontalAlignment(JTextField.CENTER);
        
        recCalsLabel.setFont(font);
        recCalsLabel.setHorizontalAlignment(labelAlignment);
        calsField.setEditable(false);
        calsField.setFont(font);
        String cals = String.format("%.2f", macros.getCalories());
        calsField.setText(cals);
        calsField.setHorizontalAlignment(JTextField.CENTER);
        
        recCarbsLabel.setFont(font);
        recCarbsLabel.setHorizontalAlignment(labelAlignment);
        carbsField.setEditable(false);
        carbsField.setFont(font);
        String carbs = String.format("%.2f", macros.getCarbohydrates());
        carbsField.setText(carbs);
        carbsField.setHorizontalAlignment(JTextField.CENTER);
        
        recProteinLabel.setFont(font);
        recProteinLabel.setHorizontalAlignment(labelAlignment);
        proteinField.setEditable(false);
        proteinField.setFont(font);
        String protein = String.format("%.2f", macros.getProtein());
        proteinField.setText(protein);
        proteinField.setHorizontalAlignment(JTextField.CENTER);
        
        recFatLabel.setFont(font);
        recFatLabel.setHorizontalAlignment(labelAlignment);
        fatField.setEditable(false);
        fatField.setFont(font);
        String fat = String.format("%.2f", macros.getFat());
        fatField.setText(fat);
        fatField.setHorizontalAlignment(JTextField.CENTER);
            
    }
    
    private void setupLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // create row
            // add welcome label
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5,0,5,0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(welcomeLabel, gbc);
        // end row
        
        gbc.weighty = 0.0;
        gbc.weightx = 1.0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(5,5,5,5);
        
        //add row
        gbc.gridy = 1;
            // add bday label
        gbc.gridx = 2;
        add(bdayLabel, gbc);
            // add bday text field
        gbc.gridx = 3;
        add(bdayField, gbc);
            // add age label
        gbc.gridx = 4;
        add(ageLabel, gbc);
            // add age text field
        gbc.gridx = 5;
        add(ageField, gbc);
        // row end
        
        // row start
        gbc.gridy = 2;
            // add height label
        gbc.gridx = 2;
        add(heightLabel, gbc);
            // add height text field
        gbc.gridx = 3;
        add(heightField,gbc);
            // add weight label
        gbc.gridx = 4;
        add(weightLabel, gbc);
            // add weight textField
        gbc.gridx = 5;
        add(weightField, gbc);
        // end row
        
        // row start
        gbc.gridy = 3;
            // add gender label
        gbc.gridx = 1;
        add(genderLabel, gbc);
            // add gender field
        gbc.gridx = 2;
        add(genderField, gbc);
            // add activity level label
        gbc.gridx = 3;
        add(activityLabel, gbc);
            // add activity level field
        gbc.gridx = 4;
        add(activityField, gbc);
            // add goal label
        gbc.gridx = 5;
        add(goalLabel, gbc);
            // add goalField
        gbc.gridx = 6;
        add(goalField, gbc);
        // end row
        
        // row start
        gbc.gridy = 4;
            // add recommended calories label
        gbc.gridx = 0;
        add(recCalsLabel, gbc);
            // add recommended calories field
        gbc.gridx = 1;
        add(calsField, gbc);
            // add recommended carbs label
        gbc.gridx = 2;    
        add(recCarbsLabel, gbc);
            // add carbs field
        gbc.gridx = 3;
        add(carbsField, gbc);
            // add protein label
        gbc.gridx = 4;
        add(recProteinLabel, gbc);
            // add protein field
        gbc.gridx = 5;
        add(proteinField, gbc);
            // add fat label
        gbc.gridx = 6;
        add(recFatLabel, gbc);
            // add fat field
        gbc.gridx = 7;
        add(fatField, gbc);
        // row end
    }
    
    
}
