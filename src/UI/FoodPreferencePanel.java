/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import nutrition.app.Food;
import nutrition.app.User;

/**
 *
 * @author dth5088
 */
public class FoodPreferencePanel extends JPanel{
    
    
    public FoodPreferencePanel(User user) {
        try {
            ArrayList<Food> foodList = user.getPreferredFoods();
            int numRows = foodList.size() + 1;
            JPanel panel = new JPanel(new GridLayout(numRows, 1));
            panel.add(getLabelPanel());
            for(Food food : foodList) {
                panel.add(addFoodToPanel(food));
            }
            JScrollPane scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            add(scrollPane);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private JPanel getLabelPanel() {
        JPanel panel = new JPanel(new GridLayout(1,7));
        JLabel nameLabel = new JLabel("NAME");
        JLabel unitLabel = new JLabel("UNIT");
        JLabel pSize = new JLabel("PORTION SIZE");
        JLabel cals = new JLabel("CALORIES");
        JLabel carbs = new JLabel("CARBS (g)");
        JLabel protein = new JLabel("PROTEIN (g)");
        JLabel fat = new JLabel("FAT (g)");
        panel.add(nameLabel);
        panel.add(unitLabel);
        panel.add(pSize);
        panel.add(cals);
        panel.add(carbs);
        panel.add(protein);
        panel.add(fat);
        return panel;
    }
    private JPanel addFoodToPanel(Food food) {
        JPanel panel = new JPanel(new GridLayout(1,8));
        JLabel nameLabel = new JLabel(), unitLabel = new JLabel()
                , portionSizeLabel = new JLabel(), caloriesLabel = new JLabel()
                , carbsLabel = new JLabel(), proteinLabel = new JLabel()
                , fatLabel = new JLabel();
        for(HashMap.Entry<String,Object> entry : food.getMapped().entrySet())
        {
            switch(entry.getKey())
            {
                case "name":
                    nameLabel.setText(entry.getValue().toString());
                    break;
                case "unit":
                    unitLabel.setText(entry.getValue().toString());
                    break;
                case "portionSize":
                    portionSizeLabel.setText(entry.getValue().toString());
                    break;
                case "calories":
                    caloriesLabel.setText(entry.getValue().toString());
                    break;
                case "carbs":
                    carbsLabel.setText(entry.getValue().toString());
                    break;
                case "protein":
                    proteinLabel.setText(entry.getValue().toString());
                    break;
                case "fat":
                    fatLabel.setText(entry.getValue().toString());
                    break;
            }
        }
        panel.add(nameLabel);
        panel.add(unitLabel);
        panel.add(portionSizeLabel);
        panel.add(caloriesLabel);
        panel.add(carbsLabel);
        panel.add(proteinLabel);
        panel.add(fatLabel);
        return panel;
        
    }
}
