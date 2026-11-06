package view;

import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentTable  extends Panel{
    private JTable table;
    private DefaultTableModel model;

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public StudentTable(){
        setLayout(new BorderLayout());
        String[] columns ={
                "ID",
                "Name",
                "Age",
                "Gender",
                "City"
        };

        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
    public void  addStudent(int id, String name, int age, String gender, String city){
        model.addRow(new Object[]{id,name,age,gender,city});
    }

    public void updateStudent(int row, String name, int age, String gender, String city){
        model.setValueAt(name,row,1);
        model.setValueAt(age,row,2);
        model.setValueAt(gender,row,3);
        model.setValueAt(city,row,4);
    }
    public void deleteStudent(int row){
        model.removeRow(row);
    }

    public DefaultTableModel getModel() {
        return model;
    }

}
