package gui;

import Services.MaintenanceService;
import Services.MaintenanceServiceImpl;
import thirdparty.entities.GroceryItem;
import thirdparty.entities.enums.MeasurementUnit;
import thirdparty.inventory.GroceryItemsInventory;
import thirdparty.inventory.GroceryItemsInventoryImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminScreen extends JDialog{

    MaintenanceService maintenanceService = new MaintenanceServiceImpl();
    GroceryItemsInventory groceryItemsInventory = new GroceryItemsInventoryImpl();

    private int hight = 200;
    private int width = 465;
    JLabel barCodeLbl  = new JLabel("barCode");
    JTextField barCodeFld = new JTextField();
    JLabel nameLbl  = new JLabel("Item Name");
    JTextField nameFld = new JTextField();
    JLabel priceLbl  = new JLabel("Price");
    JTextField priceFld = new JTextField();

    JLabel measurementLbl  = new JLabel("Measurement unit");
    JComboBox measurementCombo = new JComboBox();

    JLabel barCodeErrorLbl  = new JLabel("Invalid Barcode");
    JLabel nameErrorLbl  = new JLabel("Please enter a name ");
    JLabel priceErrorLbl  = new JLabel("Please enter a valid price");
    JLabel measurementErrorLbl  = new JLabel("Select a valid measurement Unit");


    JButton addBtn = new JButton("Add Item");
    JButton exitBtn = new JButton("Completed");


    public AdminScreen(){
        setupScreen();
    }


    public void setupScreen(){
        setTitle("Inventory maintenance");
        this.setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 255, 242));


        this.setLayout(null);
        this.setSize(width, hight);


        getContentPane().add(barCodeLbl);
        getContentPane().add(barCodeFld);
        getContentPane().add(barCodeErrorLbl);
        barCodeLbl.setBounds(10,10, 80,20);
        barCodeFld.setBounds(130,10, 120,20);
        barCodeErrorLbl.setBounds(260,10, 150,20);
        barCodeErrorLbl.setForeground(Color.RED);
        barCodeErrorLbl.setVisible(false);

        getContentPane().add(nameLbl);
        getContentPane().add(nameFld);
        getContentPane().add(nameErrorLbl);
        nameLbl.setBounds(10,35, 80,20);
        nameFld.setBounds(130,35, 120,20);
        nameErrorLbl.setBounds(260,35, 120,20);;
        nameErrorLbl.setForeground(Color.RED);
        nameErrorLbl.setVisible(false);

        getContentPane().add(priceLbl);
        getContentPane().add(priceFld);
        getContentPane().add(priceErrorLbl);
        priceLbl.setBounds(10,60, 80,20);
        priceFld.setBounds(130,60, 120,20);
        priceErrorLbl.setBounds(260,60, 150,20);
        priceErrorLbl.setForeground(Color.RED);
        priceErrorLbl.setVisible(false);

        getContentPane().add(measurementLbl);
        getContentPane().add(measurementCombo);
        measurementCombo.addItem("Please select");
        measurementCombo.addItem(MeasurementUnit.count);
        measurementCombo.addItem(MeasurementUnit.weight);

        getContentPane().add(measurementErrorLbl);
        measurementLbl.setBounds(10,85, 120,20);
        measurementCombo.setBounds(130,85, 120,20);
        measurementErrorLbl.setBounds(260,85, 200,20);
        measurementErrorLbl.setForeground(Color.RED);
        measurementErrorLbl.setVisible(false);

        getContentPane().add(addBtn);
        getContentPane().add(exitBtn);
        addBtn.setBounds(30,130, 120,20);
        exitBtn.setBounds(270,130, 120,20);
        addBtn.addActionListener(buttonListener);
        exitBtn.addActionListener(buttonListener);

    }

    private  void clearErrors(){
        nameErrorLbl.setVisible(false);
        barCodeErrorLbl.setVisible(false);
        priceErrorLbl.setVisible(false);
        measurementErrorLbl.setVisible(false);
    }

    ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            clearErrors();

            if (e.getActionCommand().equals("Add Item")) {
                boolean validItemData = true ;

               String barCode = barCodeFld.getText();
               String name = nameFld.getText();
               String price = priceFld.getText();
               String measurementUnit = measurementCombo.getSelectedItem().toString();
               double priceNum = 0;

                if (barCode == null ||  "".equalsIgnoreCase(barCode.trim()) || !maintenanceService.isBarFormatCodeValid(barCode)) {
                    barCodeErrorLbl.setText("Invalid Barcode, Please try again");
                    barCodeErrorLbl.setVisible(true);
                    validItemData = false;
                }if (maintenanceService.containsSpecialCharacters(barCode)) {
                    barCodeErrorLbl.setText("No special characters allowed");
                    barCodeErrorLbl.setVisible(true);
                    validItemData = false;
                }else if (groceryItemsInventory.barCodeAlreadyExist(barCode)){
                    barCodeErrorLbl.setText("Bar Code is already in use");
                    barCodeErrorLbl.setVisible(true);
                    validItemData = false;

                }
               if(name == null ||  "".equalsIgnoreCase(name.trim())) {
                    nameErrorLbl.setVisible(true);
                   validItemData = false;
               }

               if(price == null ||  "".equalsIgnoreCase(price.trim())) {
                   priceErrorLbl.setVisible(true);
               } else {
                   try{
                       priceNum = Double.parseDouble(price);
                   }catch (NumberFormatException nfe){
                       priceErrorLbl.setVisible(true);
                       validItemData = false;
                   }
               }

               if (measurementUnit.equals("Please select")){
                   measurementErrorLbl.setVisible(true);
                   validItemData = false;
                }
                if (validItemData)  {
                    GroceryItem groceryItem = new GroceryItem(barCode, name, MeasurementUnit.valueOf(measurementUnit), priceNum);
                    maintenanceService.saveItemToInventory(groceryItem);
                    clearFields(groceryItem.getName());
                }

            } else if (e.getActionCommand().equals("Completed")) {
                completed();

            }

        }
    };


    private void completed(){
        this.setVisible(false);
    }
    private void clearFields(String itemName){
        JOptionPane.showMessageDialog(this,"Item " + itemName + " has been added to inventory");
        barCodeFld.setText("");
        nameFld.setText("");
        priceFld.setText("");
        measurementCombo.setSelectedItem("Please Select");

    }


}
