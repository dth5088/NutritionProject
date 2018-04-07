/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
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
    int style = Font.BOLD | Font.ITALIC;
    Font font = new Font ("Arial", style , 10);
    
    public NutrientResultPanel() {
        super.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(95,5,0,5);
        
        model = new  NutrientResultTableModel();
        table = new JTable(model) {
                @Override
                public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    if(column > 0 && column < 4)
                    {
                        ((JLabel)c).setHorizontalAlignment(SwingConstants.CENTER);
                    }
                    if(!isRowSelected(row)) {
                        String type = (String) getModel().getValueAt(row, 0);
                        c.setBackground( row % 2 == 0 ? null : Color.LIGHT_GRAY);
                        ((JComponent)c).setBorder(new LineBorder(Color.LIGHT_GRAY));
                    }
                    setRowHeight(row, 30);
                    return c;
                }
            };
        table.setFont(font);
        table.getTableHeader().setFont(font);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder(Constants.compound, "Display"));
        Dimension test = new Dimension(375,350);
        scrollPane.setMinimumSize(test);
        scrollPane.setMaximumSize(test);
        scrollPane.setPreferredSize(test);
        super.add(scrollPane, gbc);
    }
    
    public void addNutrientToDisplay(USDANutrient nutrient) {
        model.addNutrient(nutrient);
    }
    
    public void clearNutrients() {
        model.clearRows();
    }
    
    
    
}
