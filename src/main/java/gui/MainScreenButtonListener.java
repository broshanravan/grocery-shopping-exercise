package gui;

import thirdparty.entities.BasketItem;
import thirdparty.entities.GroceryItem;
import thirdparty.entities.enums.MeasurementUnit;
import thirdparty.paymentgateway.GroceryPaymentService;
import thirdparty.paymentgateway.GroceryPaymentServiceImpl;
import thirdparty.paymentgateway.PaymentResult;
import thirdparty.promotions.PromotionServiceImpl;
import thirdparty.promotions.PromotionService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;


public class MainScreenButtonListener implements ActionListener {




    protected List<BasketItem> basket = new LinkedList<BasketItem>();

    MainScreen mainScreen;
    GroceryPaymentService groceryPaymentService = new GroceryPaymentServiceImpl();
    PromotionService promotionService = new PromotionServiceImpl();
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    public MainScreenButtonListener(MainScreen mainScreen){
        this.mainScreen = mainScreen;
    }

    public void actionPerformed(ActionEvent e) {

                if (e.getActionCommand().equals("Add Item")){
                    if(mainScreen.groceryItem != null) {
                        addItemToBasket(mainScreen.groceryItem);
                        applyPromotionalDiscount();

                       // Double promotionalDiscount = promotionsService.getTotalDiscount(mainScreen.basket);
                       // mainScreen.promotionFld.setText(decfor.format(promotionalDiscount));
                    }

                } else if(e.getActionCommand().equals("Make Payment")){
                    makePayment();
                    isPaymentCompleted();
                }else if(e.getActionCommand().equals("Admin")){
                    LoginDialogBox  loginDialogBox= new LoginDialogBox();
                    loginDialogBox.setModal(true);
                    loginDialogBox.setVisible(true);

                }
                else if(e.getActionCommand().equals("Clear")){
                    mainScreen.resetScreen();
                }else if(e.getActionCommand().equals("Exit")){
                    System.exit(0);
                }

    }

    private void applyPromotionalDiscount(){
        double promotionalDiscount = promotionService.getTotalDiscount(basket);
        mainScreen.promotionFld.setText(String.valueOf(promotionalDiscount));


    }

    private void addItemToBasket(GroceryItem groceryItem){
        mainScreen.invalidWeightLbl.setVisible(false);
        if(groceryItem.getMeasurementUnit().equals(MeasurementUnit.weight)){
            String weight = mainScreen.weightFld.getText();
            if(weight != null && !"".equalsIgnoreCase(weight.trim()) && groceryPaymentService.isAmountValid(weight)) {
                double weightNum =Double.parseDouble(weight);
                double price = weightNum * groceryItem.getPrice();
                basket.add(new BasketItem(groceryItem,weightNum ));
                addItemToReceipt(new BasketItem(groceryItem, weightNum ));
                calculateTotal();
                mainScreen.clearItemDetails();
            } else {
                mainScreen.invalidWeightLbl.setVisible(true);
            }
        } else {
            basket.add(new BasketItem(groceryItem, 1));
            addItemToReceipt(new BasketItem(groceryItem, 1));
            mainScreen.clearItemDetails();
            calculateTotal();
        }

    }

    private boolean isPaymentCompleted(){

        if(mainScreen.paymentCompleted) {
            JOptionPane.showMessageDialog(mainScreen, mainScreen.paymentResultMessage );
            mainScreen.resetScreen();
            return true;
        } else{
            JOptionPane.showMessageDialog(mainScreen,  mainScreen.paymentResultMessage );
            return  false;

        }
    }

    int y = 0;
    String itemLabelContents ="";
    private void addItemToReceipt(BasketItem basketItem){
        decfor.setRoundingMode(RoundingMode.DOWN);
        String name = basketItem.getGroceryItem().getName();
        MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit()  ;

        double weight = basketItem.getQuantity();
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
        mainScreen.receipt.setText(itemLabelContents);
        itemLbl.setBounds(0, y, 380,20);

        y += 20;

    }

    private void calculateTotal(){

        double promotionalDiscount = promotionService.getTotalDiscount(basket);
        mainScreen.promotionFld.setText(String.valueOf(promotionalDiscount));
        double total = 0;

        for(BasketItem basketItem :basket ){
            if(basketItem.getGroceryItem().getMeasurementUnit().equals(MeasurementUnit.weight)){
                total += basketItem.getGroceryItem().getPrice() * basketItem.getQuantity();
            } else {
                total += basketItem.getGroceryItem().getPrice();
            }

        }

        double priceIncludingPromotions = total - promotionalDiscount;
        double vat = (priceIncludingPromotions) * 0.17;
        double grandTotal = priceIncludingPromotions  + vat;

        mainScreen.totalFld.setText(String.valueOf(decfor.format(total)));
        mainScreen.promotionFld.setText(decfor.format(promotionalDiscount));
        mainScreen.afterdiscountFld.setText(String.valueOf(decfor.format(priceIncludingPromotions)));
        mainScreen.vatFld.setText(String.valueOf(decfor.format(vat)));
        mainScreen.grandTotalFld.setText(String.valueOf(decfor.format(grandTotal)));



    }

    private void makePayment() {
        //groceryPaymentService.setPaidAmount(paymentFld.getText());

        double total = Double.parseDouble(mainScreen.grandTotalFld.getText());
        String paidStr =mainScreen.paymentFld.getText();
        if(groceryPaymentService.isAmountValid(paidStr) && total != 0) {
            double paymentAmount = Double.parseDouble(paidStr);
            double grandTotal = Double.parseDouble(mainScreen.grandTotalFld.getText());
            groceryPaymentService.setPaidAmount(paidStr);
            if(paymentAmount >= grandTotal) {
                groceryPaymentService.makePayment(123, total);

                PaymentResult paymentResult = groceryPaymentService.getPaymentResult();
                double change = paymentResult.getChange();
                mainScreen.paymentResultMessage = "Your Change is " + Double.parseDouble(decfor.format(change)) + ", Thank you!";
                mainScreen.paymentResultlable.setText(paymentResult.getPaymentResultMessage());
                mainScreen.paymentCompleted = true;
            }else {
                double remaining = grandTotal - paymentAmount;
                mainScreen.paymentResultMessage ="amount left to pay £" +  Double.parseDouble(decfor.format(remaining));
                mainScreen.paymentCompleted = false;

            }
        } else {
            mainScreen.paymentErrorLbl.setVisible(true);
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
