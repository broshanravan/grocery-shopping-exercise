package gui;

import thirdparty.entities.BasketItem;
import thirdparty.entities.GroceryItem;
import thirdparty.entities.enums.MeasurementUnit;
import thirdparty.inventory.GroceryInventoryService;
import thirdparty.inventory.GroceryInventoryServiceImpl;
import thirdparty.paymentgateway.GroceryPaymentService;
import thirdparty.paymentgateway.GroceryPaymentServiceImpl;
import thirdparty.paymentgateway.PaymentResult;
import thirdparty.promotions.PromotionServiceImpl;
import thirdparty.promotions.PromotionsService;

import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainScreen extends JFrame {

    GroceryInventoryService groceryInventoryService = new GroceryInventoryServiceImpl();
    GroceryPaymentService groceryPaymentService = new GroceryPaymentServiceImpl();


    double weightDiscountRate = 0.25;
    double discountableweight = 3;
    double countDiscountRate = 0.5;
    double discountableCount = 2;
    List<String> discountableBarCodes = List.of("BN123","EG123");

    PromotionsService promotionsService = new PromotionServiceImpl(discountableBarCodes,
                                                 weightDiscountRate,
                                                 discountableweight,
                                                 countDiscountRate,
                                                 discountableCount);

    private JPanel backGroundPnl;

    double promotionalDiscount = 0 ;



    JLabel itemDetailsLbl = new JLabel("Item name                                Qty/weight                               Price");
    JLabel codeLbl  = new JLabel("Barcode");
    JTextField codeFld = new JTextField();
    JLabel paymentLbl  = new JLabel("paid");
    JTextField  paymentFld = new JTextField();
    JLabel promotionLbl = new JLabel("Promotion Discount");
    JTextField promotionFld = new JTextField();

    JLabel paymentResultlable = new JLabel();
    JLabel invalidCodeLbl  = new JLabel("Invalid barcode, please try again");

    JLabel invalidWeightLbl  = new JLabel("Please enter a valid weight");
    JLabel weightLbl  = new JLabel("weight");
    JLabel nameLbl  = new JLabel("Item Name");
    JTextField nameFld = new JTextField();
    JLabel priceLbl  = new JLabel("price £");
    JTextField priceFld = new JTextField();


    JTextField weightFld = new JTextField();

    private List<BasketItem> basket = new LinkedList<BasketItem>();

    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    JLabel totalLbl  = new JLabel("Total price £");
    JTextField totalFld = new JTextField();
    JLabel VATLbl  = new JLabel("VAT £");
    JTextField vatFld = new JTextField();
    JLabel grandTotalLbl  = new JLabel("Grand Total £");
    JTextField grandTotalFld = new JTextField();
    JButton addBtn = new JButton("Add Item");

    JButton clearBtn = new JButton("Payment");

    JButton payBtn = new JButton("Clear");
    JButton exitBtn = new JButton("Exit");
    JButton adminBtn = new JButton("Admin");

    JLabel paymentErrorLbl = new JLabel("Payment Should be numeric");

    TextArea receipt = new TextArea(100,420);

    JScrollPane scrollPane = new JScrollPane(receipt);

    private int hight = 500;
    private int width = 450;

    public MainScreen(){
        setupScreen();
        setVisible(true);
    }

    GroceryItem groceryItem = null;


    private void setupScreen() {

        setTitle("Checkout Screen");;

        receipt.setSize(new Dimension(100,420));
        setResizable(false);
        getContentPane().setBackground(new Color(255, 255, 242));
        this.setLayout(null);
        this.setSize(width, hight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        this.setResizable(false);
        addBtn.addActionListener(buttonListener);
        payBtn.addActionListener(buttonListener);
        adminBtn.addActionListener(buttonListener);
        clearBtn.addActionListener(buttonListener);
        exitBtn.addActionListener(buttonListener);

        getContentPane().add(codeLbl);
        getContentPane().add(nameLbl);
        getContentPane().add(priceLbl);
        getContentPane().add(invalidCodeLbl);
        getContentPane().add(invalidWeightLbl);

        getContentPane().add(weightLbl);
        getContentPane().add(weightFld);
        getContentPane().add(totalLbl);
        getContentPane().add(VATLbl);
        getContentPane().add(grandTotalLbl);
        getContentPane().add(addBtn);
        getContentPane().add(payBtn);
        getContentPane().add(clearBtn);
        getContentPane().add(exitBtn);


        getContentPane().add(adminBtn);

        getContentPane().add(codeFld);
        getContentPane().add(nameFld);
        getContentPane().add(priceFld);

        getContentPane().add(totalFld);
        getContentPane().add(vatFld);
        getContentPane().add(grandTotalFld);
        getContentPane().add(itemDetailsLbl);

        getContentPane().add(paymentLbl);
        getContentPane().add(paymentFld);
        getContentPane().add(promotionLbl);
        getContentPane().add(paymentErrorLbl);

        getContentPane().add(promotionFld);


        itemDetailsLbl.setBackground(Color.white);
        paymentFld.getDocument().addDocumentListener(new DocumentListener() {

            String paymentAmount = paymentFld.getText();
            public void validatePayment(){
                if(groceryPaymentService.isAmountValid(paymentAmount)){
                    paymentErrorLbl.setVisible(false);
                } else {
                    paymentErrorLbl.setVisible(true);
                }
            }


            public void changedUpdate(DocumentEvent e) {
                calculate();
            }

            public void removeUpdate(DocumentEvent e) {
                calculate();
            }

            public void insertUpdate(DocumentEvent e) {
                calculate();
            }
            public void calculate() {

            }

        });


        weightFld.getDocument().addDocumentListener(new DocumentListener() {

            String weight = weightFld.getText();
            public void inalidWeight(){
                if(groceryPaymentService.isAmountValid(weight)){
                    invalidWeightLbl.setVisible(false);
                } else {
                    invalidWeightLbl.setVisible(true);
                }
            }

            public void changedUpdate(DocumentEvent e) {
                calculateSingleItemPriceByWeight();
            }

            public void removeUpdate(DocumentEvent e) {
                calculateSingleItemPriceByWeight();
            }

            public void insertUpdate(DocumentEvent e) {
                calculateSingleItemPriceByWeight();
            }
            public void calculateSingleItemPriceByWeight() {
                decfor.setRoundingMode(RoundingMode.DOWN);
                String weight = weightFld.getText();
                if (groceryPaymentService.isAmountValid(weight)) {
                    double price = Double.parseDouble(weight) * groceryItem.getPrice();
                    String priceStr = decfor.format(price);
                    priceFld.setText(priceStr);
                } else {
                    priceFld.setText("");
                }

            }

        });


        codeFld.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                findItem();
            }
            public void removeUpdate(DocumentEvent e) {
                findItem();
            }
            public void insertUpdate(DocumentEvent e) {
                findItem();
            }

            public void findItem() {
                String barcode = codeFld.getText().toUpperCase();
                invalidCodeLbl.setVisible(false);
                invalidWeightLbl.setVisible(false);
                if(barcode.length() > 4) {
                    if(groceryInventoryService.doesBarCodeExist(barcode)) {
                        groceryItem = groceryInventoryService.getGroceryItem(barcode);
                        nameFld.setText(groceryItem.getName());
                        priceFld.setText(String.valueOf(decfor.format(groceryItem.getPrice())));
                        if(groceryItem.getMeasurementUnit().equals(MeasurementUnit.weight)) {
                            weightFld.setEditable(true);
                        } else {
                            weightFld.setEditable(false);
                        }
                    } else invalidCodeLbl.setVisible(true);

                } else {
                    invalidCodeLbl.setVisible(false);
                    invalidWeightLbl.setVisible(false);

                }
            }



        });

        codeLbl.setBounds(10,10, 80,20);
        codeFld.setBounds(100,10, 80,20);
        invalidCodeLbl.setBounds(200,10, 200,20);
        invalidWeightLbl.setBounds(200,60, 200,20);
        invalidCodeLbl.setForeground(java.awt.Color.red);
        invalidWeightLbl.setForeground(java.awt.Color.red);
        invalidCodeLbl.setVisible(false);
        invalidWeightLbl.setVisible(false);

        nameLbl.setBounds(10,35, 80,20);
        nameFld.setBounds(100,35, 150,20);



        weightLbl.setBounds(10,60, 80,20);
        weightFld.setBounds(100,60, 80,20);
        weightFld.setEditable(false);
        priceFld.setEditable(false);
        nameFld.setEditable(false);
        paymentResultlable.setVisible(true);
        nameFld.setBackground(Color.white);
        priceFld.setBackground(Color.white);

        priceLbl.setBounds(10,85, 100,20);
        priceFld.setBounds(100,85, 80,20);

        addBtn.setBounds(300,35, 100,20);


        totalLbl.setBounds(10,245, 100,20);
        totalFld.setBounds(130,245, 80,20);

        promotionLbl.setBounds(10,280, 150,20);
        promotionFld.setBounds(130,280, 80,20);


        VATLbl.setBounds(10,315, 100,20);
        vatFld.setBounds(130,315, 80,20);

        grandTotalLbl.setBounds(10,350, 100,20);
        grandTotalFld.setBounds(130,350, 80,20);

        paymentLbl.setBounds(10,385, 100,20);
        paymentFld.setBounds(130,385, 80,20);
        paymentErrorLbl.setBounds(150,375, 100,20);
        paymentErrorLbl.setVisible(false);



        clearBtn .setBounds(10,430, 100,20);
        payBtn.setBounds(115,430, 100,20);

        exitBtn.setBounds(220,430, 100,20);
        adminBtn.setBounds(325,430, 100,20);

        totalFld.setEnabled(false);
        vatFld.setEnabled(false);
        grandTotalFld.setEnabled(false);
        totalFld.setBackground(Color.white);
        totalFld.setBackground(Color.white);
        totalFld.setBackground(Color.white);


        receipt.setEnabled(false);


        scrollPane.setBackground(new Color(155, 150, 142));
        itemDetailsLbl.setBounds(10,118,380,20);
        scrollPane.setBounds(10,135, 380,100);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        codeLbl.setPreferredSize(new Dimension(150, 20));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });


    }



    private void addItemToBasket(GroceryItem groceryItem){
        invalidWeightLbl.setVisible(true);
        if(groceryItem.getMeasurementUnit().equals(MeasurementUnit.weight)){
            String weight = weightFld.getText();
            if(weight != null && !"".equalsIgnoreCase(weight.trim()) && groceryPaymentService.isAmountValid(weight)) {
                double weightNum =Double.parseDouble(weight);
                double price = weightNum * groceryItem.getPrice();
                basket.add(new BasketItem(groceryItem,weightNum ));
                addItemToReceipt(new BasketItem(groceryItem, weightNum ));
                calculateTotal();
                clearItemDetails();
            } else {
                invalidWeightLbl.setVisible(true);
            }
        } else {
            basket.add(new BasketItem(groceryItem, 0));
            addItemToReceipt(new BasketItem(groceryItem, 0));
            clearItemDetails();
            calculateTotal();
        }
        promotionalDiscount = promotionsService.getTotalDiscount(basket);
        promotionFld.setText(String.valueOf(promotionalDiscount));
    }

   ActionListener buttonListener = new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           if (e.getActionCommand().equals("Add Item")){
               if(groceryItem != null) {
                   addItemToBasket(groceryItem);
               }

           } else if(e.getActionCommand().equals("Payment")){
               makePayment();
               isPaymentCompleted();
           }else if(e.getActionCommand().equals("Admin")){
               LoginDialogBox  loginDialogBox= new LoginDialogBox();
               loginDialogBox.setModal(true);
               loginDialogBox.setVisible(true);

           }
           else if(e.getActionCommand().equals("Clear")){
               resetScreen();
           }else if(e.getActionCommand().equals("Exit")){
              System.exit(0);
           }

       }
   };

    private boolean isPaymentCompleted(){

        if(paymentCompleted) {
            JOptionPane.showMessageDialog(this, paymentResultMessage );
            resetScreen();
            return true;
        } else{
            JOptionPane.showMessageDialog(this,  paymentResultMessage );
            return  false;

        }
    }

    int y = 0;
    String itemLabelContents ="";
    private void addItemToReceipt(BasketItem basketItem){
        decfor.setRoundingMode(RoundingMode.DOWN);
        String name = basketItem.getGroceryItem().getName();
        MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit()  ;

        double weight = basketItem.getWeight();
        double price = basketItem.getGroceryItem().getPrice();
        //price = Double.parseDouble(decfor.format(price));
        String itemName = basketItem.getGroceryItem().getName();

        if(measurementUnit.equals(MeasurementUnit.count)) {

            itemLabelContents += itemName + getTabs(itemName.length(), true) + weight + getTabs(2, false) +
                    "£" + String.valueOf(decfor.format(price)) + "\n";
        } else {
            price = price * weight;
            itemLabelContents += itemName + getTabs(itemName.length(), true) + weight + getTabs(2, false) +
                    "£" + String.valueOf(decfor.format(price)) + "\n";

        }
        JLabel itemLbl = new JLabel(itemLabelContents);
        receipt.setText(itemLabelContents);
        itemLbl.setBounds(0, y, 380,20);
        promotionFld.setText(String.valueOf(promotionalDiscount));
        y += 20;

    }

    private void calculateTotal(){
        double total = 0;

        for(BasketItem basketItem :basket ){
            if(basketItem.getGroceryItem().getMeasurementUnit().equals(MeasurementUnit.weight)){
                total += basketItem.getGroceryItem().getPrice() * basketItem.getWeight();
            } else {
                total += basketItem.getGroceryItem().getPrice();
            }

        }
        total = total ;
        double priceIncludingPromotions = total - promotionalDiscount;
        double vat = (priceIncludingPromotions) * 0.17;
        double grandTotal = priceIncludingPromotions  + vat;
        totalFld.setText(String.valueOf(decfor.format(total)));
        promotionFld.setText(String.valueOf(promotionalDiscount));
        vatFld.setText(String.valueOf(decfor.format(vat)));
        grandTotalFld.setText(String.valueOf(decfor.format(grandTotal)));
    }


    public void resetScreen(){
        codeFld.setText("");
        nameFld.setText("");
        weightFld.setText("");
        weightFld.setEditable(false);
        totalFld.setText("");
        vatFld.setText("");
        priceFld.setText("");
        promotionFld.setText("");
        paymentFld.setText("");
        grandTotalFld.setText("");
        basket.removeAll(basket);
        paymentResultlable.setVisible(false);
        paymentErrorLbl.setVisible(false);
        receipt.setText("");
        basket.removeAll(basket);
        itemLabelContents="";
        y=0;
        priceFld.setText("");

    }

    private void clearItemDetails(){
        codeFld.setText("");
        nameFld.setText("");
        weightFld.setText("");
        weightFld.setEditable(false);
        priceFld.setText("");
        paymentErrorLbl.setVisible(false);

    }


    String paymentResultMessage = "";
    boolean paymentCompleted =false;
    private void makePayment() {
        //groceryPaymentService.setPaidAmount(paymentFld.getText());

        double total = Double.parseDouble(grandTotalFld.getText());
        String paidStr =paymentFld.getText();
        if(groceryPaymentService.isAmountValid(paidStr)) {
            double paymentAmount = Double.parseDouble(paidStr);
            double grandTotal = Double.parseDouble(grandTotalFld.getText());
            groceryPaymentService.setPaidAmount(paidStr);
            if(paymentAmount >= grandTotal) {
                groceryPaymentService.makePayment(123, total);

                PaymentResult paymentResult = groceryPaymentService.getPaymentResult();
                double change = paymentResult.getChange();
                paymentResultMessage = "Your Change is " + Double.parseDouble(decfor.format(change)) + ", thank you!";
                paymentResultlable.setText(paymentResult.getPaymentResultMessage());
                paymentCompleted = true;
            }else {
                double remaining = grandTotal - paymentAmount;
                paymentResultMessage ="amount left to pay £" +  Double.parseDouble(decfor.format(remaining));
                paymentCompleted = false;

            }
        } else {
            paymentErrorLbl.setVisible(true);
        }
    }

    private String getTabs(int wordLength, boolean isFirst){
        String tabs = "";
        int spaces = 0;
        int  requiredNumberOfSpaces = 32 - (wordLength - 6);
        if (wordLength< 0) {
            requiredNumberOfSpaces = 38 - wordLength;
        }
        if(!isFirst){
            requiredNumberOfSpaces = 30;
        }

        while(spaces != requiredNumberOfSpaces){
            tabs += " ";
            spaces ++;
        }

        return tabs;
    }


}
