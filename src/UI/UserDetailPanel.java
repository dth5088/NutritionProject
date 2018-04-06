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
import javax.swing.GroupLayout;
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
            ,recCalsLabel = new JLabel("Calories:"), recCarbsLabel = new JLabel("Carbs(g):")
            ,recProteinLabel = new JLabel("Protein(g):"), recFatLabel = new JLabel("Fat(g):");
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
        heightField.setHorizontalAlignment(JTextField.LEFT);
        
        
        weightLabel.setFont(font);
        weightLabel.setHorizontalAlignment(labelAlignment);
        weightField.setEditable(false);
        weightField.setFont(font);
        weightField.setText(user.getWeightString());
        weightField.setHorizontalAlignment(JTextField.LEFT);
        
        bdayLabel.setFont(font);
        bdayLabel.setHorizontalAlignment(labelAlignment);
        bdayField.setEditable(false);
        bdayField.setFont(font);
        bdayField.setText(user.getbdayString());
        bdayField.setHorizontalAlignment(JTextField.LEFT);
        
        ageLabel.setFont(font);
        ageLabel.setHorizontalAlignment(labelAlignment);
        ageField.setEditable(false);
        ageField.setFont(font);
        ageField.setText(user.getAgeString());
        ageField.setHorizontalAlignment(JTextField.LEFT);
        
        genderLabel.setFont(font);
        genderLabel.setHorizontalAlignment(labelAlignment);
        genderField.setEditable(false);
        genderField.setFont(font);
        genderField.setText(user.getGenderString());
        genderField.setHorizontalAlignment(JTextField.LEFT);
        
        activityLabel.setFont(font);
        activityLabel.setHorizontalAlignment(labelAlignment);
        activityField.setEditable(false);
        activityField.setFont(font);
        activityField.setText(user.getActivityLevelString());
        activityField.setHorizontalAlignment(JTextField.LEFT);
        
        goalLabel.setFont(font);
        goalLabel.setHorizontalAlignment(labelAlignment);
        goalField.setEditable(false);
        goalField.setFont(font);
        goalField.setText(user.getFitnessGoalString());
        goalField.setHorizontalAlignment(JTextField.LEFT);
        
        recCalsLabel.setFont(font);
        recCalsLabel.setHorizontalAlignment(labelAlignment);
        calsField.setEditable(false);
        calsField.setFont(font);
        String cals = String.format("%.2f", macros.getCalories());
        calsField.setText(cals);
        calsField.setHorizontalAlignment(JTextField.LEFT);
        
        recCarbsLabel.setFont(font);
        recCarbsLabel.setHorizontalAlignment(labelAlignment);
        carbsField.setEditable(false);
        carbsField.setFont(font);
        String carbs = String.format("%.2f", macros.getCarbohydrates());
        carbsField.setText(carbs);
        carbsField.setHorizontalAlignment(JTextField.LEFT);
        
        recProteinLabel.setFont(font);
        recProteinLabel.setHorizontalAlignment(labelAlignment);
        proteinField.setEditable(false);
        proteinField.setFont(font);
        String protein = String.format("%.2f", macros.getProtein());
        proteinField.setText(protein);
        proteinField.setHorizontalAlignment(JTextField.LEFT);
        
        recFatLabel.setFont(font);
        recFatLabel.setHorizontalAlignment(labelAlignment);
        fatField.setEditable(false);
        fatField.setFont(font);
        String fat = String.format("%.2f", macros.getFat());
        fatField.setText(fat);
        fatField.setHorizontalAlignment(JTextField.LEFT);
            
    }
    
    
    private void setupLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.0;
        // create row
            // add welcome label
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(welcomeLabel, gbc);
        // end row
        gbc.weightx = 1.0;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(heightLabel, gbc);
        gbc.gridx = 1;
        add(heightField, gbc);
        gbc.gridx = 2;
        add(weightLabel, gbc); 
        gbc.gridx = 3;
        add(weightField, gbc);
        
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(bdayLabel, gbc);
        gbc.gridx = 1;
        add(bdayField, gbc);
        gbc.gridx = 2;
        add(ageLabel, gbc);
        gbc.gridx = 3;
        add(ageField, gbc);
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(genderLabel, gbc);
        gbc.gridx = 1;
        add(genderField, gbc);
        gbc.gridx = 2;
        add(goalLabel, gbc);
        gbc.gridx = 3;
        add(goalField, gbc);
        
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(activityLabel, gbc);
        gbc.gridx = 1;
        add(activityField, gbc);
        gbc.gridx = 2;
        add(recCalsLabel, gbc);
        gbc.gridx = 3;
        add(calsField, gbc);
        
        gbc.gridy = 5;
        gbc.gridx = 0;
        add(recCarbsLabel, gbc);
        gbc.gridx = 1;
        add(carbsField, gbc);
        gbc.gridx = 2;
        add(recProteinLabel, gbc);
        gbc.gridx = 3;
        add(proteinField, gbc);
        
        gbc.gridy = 6;
        gbc.gridx = 0;
        add(recFatLabel, gbc);
        gbc.gridx = 1;
        add(fatField, gbc);
    }
    
    
}
