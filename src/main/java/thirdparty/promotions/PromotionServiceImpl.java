package thirdparty.promotions;

import thirdparty.entities.BasketItem;
import thirdparty.entities.enums.MeasurementUnit;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class PromotionServiceImpl implements PromotionsService{

    private static List<String> discountableBarCodes = new LinkedList<String>();
    double weightDiscountRate = 0.25;
    double discountableAmount = 2;
    double countDiscountRate = 0.5;


    static {
        discountableBarCodes.add("BN123");
        discountableBarCodes.add("EG123");
    }
    public double getDiscountWeight(List<BasketItem> basket ,String barcode){
        double discount = 0;
        int ItemWeight = 0;
        for (BasketItem basketItem: basket){
            if(discountableBarCodes.contains(basketItem.getGroceryItem().getBarCode())){
                if(basketItem.getGroceryItem().getMeasurementUnit().equals(MeasurementUnit.weight))
                ItemWeight += 1;
            }
            if(ItemWeight > discountableAmount){
                discount = basketItem.getGroceryItem().getPrice() * countDiscountRate;
            }

        }

        return discount;
    }

    public double getDiscountCount(List<BasketItem> basket){
        double discount = 0;
        int count = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket){
          if(discountableBarCodes.contains(basketItem.getGroceryItem().getBarCode())) {
              String discountedItemBarcode = basketItem.getGroceryItem().getBarCode();
              if (!countedBarCodes.contains(discountedItemBarcode)){
                  countedBarCodes.add(discountedItemBarcode);
                  count += 1;
              }
              if (!countedBarCodes.contains(discountedItemBarcode)) {
                  double itemPrice = basketItem.getGroceryItem().getPrice();
                  double discountable = Math.floor(count / discountableAmount);
                  discount += itemPrice * discountable * countDiscountRate;
                  countedBarCodes.add(discountedItemBarcode);
              }
              countedBarCodes.add(discountedItemBarcode);
          }

        }
        return discount;
    }

    public double getTotalDiscountCount(List<BasketItem> basket ,String barcode){
        double discount = 0;
        int count = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket) {
            barcode = basketItem.getGroceryItem().getBarCode();
            if (!countedBarCodes.contains(barcode) && discountableBarCodes.contains(barcode)) {
                if (basketItem.getGroceryItem().getMeasurementUnit().equals(MeasurementUnit.count)) {
                    discount += getDiscountWeight(basket, barcode);
                } else {
                    discount += getDiscountCount(basket);
                }
            }
        }

        return discount;
    }

    public BigDecimal getAmountSaved(List<BasketItem> basket){

        BigDecimal discountAmount = new  BigDecimal(0);

        return discountAmount;

    }
}
