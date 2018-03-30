/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import nutrition.app.Constants;
import nutrition.app.Parsers.USDANutrient;

/**
 *
 * @author dth5088
 */
public class NutrientResultPanel extends JPanel{
    JScrollPane scrollPane;
    NutrientResultTableModel model;
    JTable table;
    
    public NutrientResultPanel() {
        super.setLayout(new GridBagLayout());
        super.setMinimumSize(new Dimension(400,450));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30,5,-45,5);
        
        model = new  NutrientResultTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder(Constants.compound, "Display"));
        super.add(scrollPane, gbc);
    }
    
    public void addNutrientToDisplay(USDANutrient nutrient) {
        model.addNutrient(nutrient);
    }
    
    public void clearNutrients() {
        model.clearRows();
    }
    
    
    
}
