package view;

import javax.swing.*;
import java.awt.GridLayout;

public class StudentForm extends JPanel {

    private JTextField nameField;
    private JTextField ageField;

    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;

    private JComboBox<String> citycombox;

    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton clearButton;



    public JTextField getAgeField() { return ageField; }
    public void setAgeField(JTextField ageField) { this.ageField = ageField; }

    public JTextField getNameField() { return nameField; }
    public void setNameField(JTextField nameField) { this.nameField = nameField; }

    public JRadioButton getMaleRadio() { return maleRadio; }
    public void setMaleRadio(JRadioButton maleRadio) { this.maleRadio = maleRadio; }

    public JRadioButton getFemaleRadio() { return femaleRadio; }
    public void setFemaleRadio(JRadioButton femaleRadio) { this.femaleRadio = femaleRadio; }

    public JComboBox<String> getCitycombox() { return citycombox; }
    public void setCitycombox(JComboBox<String> citycombox) { this.citycombox = citycombox; }

    public JButton getAddButton() { return addButton; }
    public void setAddButton(JButton addButton) { this.addButton = addButton; }

    public JButton getDeleteButton() { return deleteButton; }
    public void setDeleteButton(JButton deleteButton) { this.deleteButton = deleteButton; }

    public JButton getUpdateButton() { return updateButton; }
    public void setUpdateButton(JButton updateButton) { this.updateButton = updateButton; }

    public JButton getClearButton() { return clearButton; }
    public void setClearButton(JButton clearButton) { this.clearButton = clearButton; }


    public String getGender() {
        if (maleRadio.isSelected()) return "Male";
        if (femaleRadio.isSelected()) return "Female";
        return "Other";
    }

    public String getCity() {
        return (String) citycombox.getSelectedItem();
    }

    public void clear() {
        nameField.setText("");
        ageField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        if (citycombox.getItemCount() > 0) {
            citycombox.setSelectedIndex(0);
        }
    }

    public void setFormData(String name, String age, String gender, String city) {
        nameField.setText(name);
        ageField.setText(age);
        if ("Male".equalsIgnoreCase(gender)) {
            maleRadio.setSelected(true);
        } else if ("Female".equalsIgnoreCase(gender)) {
            femaleRadio.setSelected(true);
        }
        citycombox.setSelectedItem(city);
    }



    public StudentForm() {

        setLayout(new GridLayout(6, 1, 5, 5));


        nameField = new JTextField(10);
        ageField  = new JTextField(10);

        maleRadio   = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        ButtonGroup group = new ButtonGroup();
        group.add(maleRadio);
        group.add(femaleRadio);

        citycombox = new JComboBox<>(new String[]{"Sousse", "Tunis", "Sfax"});

        addButton    = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton  = new JButton("Clear");


        JPanel nameRow = new JPanel(new GridLayout(1, 2, 10, 5));
        nameRow.add(new JLabel("Name"));
        nameRow.add(nameField);
        add(nameRow);


        JPanel ageRow = new JPanel(new GridLayout(1, 2, 10, 5));
        ageRow.add(new JLabel("Age"));
        ageRow.add(ageField);
        add(ageRow);

        JPanel genderRow = new JPanel(new GridLayout(1, 2, 10, 5));
        genderRow.add(new JLabel("Gender"));

        JPanel radiosPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        radiosPanel.add(maleRadio);
        radiosPanel.add(femaleRadio);
        genderRow.add(radiosPanel);
        add(genderRow);

        JPanel cityRow = new JPanel(new GridLayout(1, 2, 10, 5));
        cityRow.add(new JLabel("City"));
        cityRow.add(citycombox);
        add(cityRow);

        JPanel btnRow1 = new JPanel(new GridLayout(1, 2, 10, 5));
        btnRow1.add(addButton);
        btnRow1.add(updateButton);
        add(btnRow1);

        JPanel btnRow2 = new JPanel(new GridLayout(1, 2, 10, 5));
        btnRow2.add(deleteButton);
        btnRow2.add(clearButton);
        add(btnRow2);
    }
}