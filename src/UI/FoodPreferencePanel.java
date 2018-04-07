/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import nutrition.app.Food;
import nutrition.app.Parsers.USDANutrient;
import nutrition.app.User;

/**
 *
 * @author dth5088
 */
public class FoodPreferencePanel extends JPanel{
    JPanel panel;
    JScrollPane scrollPane;
    JTable table;
    String[] columnNames = {"NAME","UNIT","MANUFACTURER","PORTION SIZE","CALORIES","CARBS (g)","PROTEIN (g)","FAT (g)"};
    PreferenceTableModel model = new PreferenceTableModel();
    int style = Font.BOLD | Font.ITALIC;
    Font font = new Font ("Arial", style , 10);
    
    public FoodPreferencePanel(User user) {
        scrollPane = new JScrollPane();
        
        super.setLayout(new GridLayout(1,1));
        try {
            ArrayList<Food> foodList = user.getPreferredFoods();
            for(Food food : foodList)
            {
                model.addRow(food);
            }
            table = new JTable(model) {
                @Override
                public Component prepareRenderer(
                TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    if(column < 7)
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
            table.getTableHeader().setFont(font);
            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            table.setFont(font);
            table.getColumnModel().getColumn(0).setPreferredWidth(110);
            table.getColumnModel().getColumn(1).setPreferredWidth(60);
            table.getColumnModel().getColumn(2).setPreferredWidth(20);
            table.getColumnModel().getColumn(3).setPreferredWidth(20);
            table.getColumnModel().getColumn(4).setPreferredWidth(20);
            table.getColumnModel().getColumn(5).setPreferredWidth(30);
            table.getColumnModel().getColumn(6).setPreferredWidth(40);
            table.getColumnModel().getColumn(7).setPreferredWidth(30);
            scrollPane = new JScrollPane(table);
            super.add(scrollPane, BorderLayout.CENTER);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addFood(Food food) {
        model.addRow(food);
    }
    
    static class PreferenceTableModel extends AbstractTableModel {
        private static final long serialVersionUID = 1L;
        private List<PreferenceRow> rows;
        private HashMap<Food, PreferenceRow> mapLookup;
        String[] columnNames = {"NAME","MANUFACTURER","UNIT","PSize","CALS","CARBS (g)","PROTEIN (g)","FAT (g)"};
        DecimalFormat df = new DecimalFormat("#.00");
        
        public PreferenceTableModel() {
            rows = new ArrayList<>();
            mapLookup = new HashMap<>();
        }
        
        public void addRow(Food food) {
            PreferenceRow row = new PreferenceRow(food);
            if(!mapLookup.containsKey(food))
            {
                mapLookup.put(food, row);
                rows.add(row);
                fireTableRowsInserted(rows.size()-1, rows.size()-1);
            }
        }
        
        public Food getRow(int index) {
            PreferenceRow row = rows.get(index);
            return row.getFood();
        }
        @Override
        public int getRowCount() {
           return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            PreferenceRow row = rows.get(rowIndex);
            Food food = row.getFood();
            HashMap<String,Object> mapped = food.getMapped();
            Object value = null;
            String unformatted = "";
            switch(columnIndex) {
                case 0:
                    value = mapped.get("name").toString();
                    break;
                case 1:
                    value = mapped.get("manufacturer").toString();
                    break;
                case 2:
                    value = mapped.get("unit").toString();
                    break;
                case 3:
                    value = mapped.get("portionSize").toString();
                    break;
                case 4:
                    unformatted = mapped.get("calories").toString();
                    value = String.format("%.2f",Double.parseDouble(unformatted));
                    break;
                case 5:
                    unformatted = mapped.get("carbs").toString();
                    value = String.format("%.2f",Double.parseDouble(unformatted));
                    break;
                case 6:
                    unformatted = mapped.get("protein").toString();
                    value = String.format("%.2f",Double.parseDouble(unformatted));
                    break;
                case 7:
                    unformatted = mapped.get("fat").toString();
                    value = String.format("%.2f",Double.parseDouble(unformatted));
                    break;

            }
            return value;
        }
    }
    
    static class PreferenceRow {
        Food food;
        
        public PreferenceRow(Food food) {
            this.food = food;
        }
        
        public Food getFood() {
            return food;
        }
        
    }
}
