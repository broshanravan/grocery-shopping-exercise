package thirdparty.promotions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thirdparty.entities.BasketItem;
import thirdparty.entities.enums.MeasurementUnit;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionServiceImpl implements PromotionsService{
    private List<String> discountableBarCodes = new LinkedList<String>();
    double weightDiscountRate;
    double discountableAmount;
    double countDiscountRate;

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

            String barCode =basketItem.getGroceryItem().getBarCode();
            double itemPrice = basketItem.getGroceryItem().getPrice();
            MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit();
          if(isCodeDiscounted(barCode) && measurementUnit.equals(MeasurementUnit.count)) {
              for (BasketItem doscountedBasketItem: basket) {
                   String discountedBarCode  = doscountedBasketItem.getGroceryItem().getBarCode();
                  if (!countedBarCodes.contains(barCode)) {
                      count += 1;
                      if (count >= discountableAmount) {
                          double discountableNum = Math.floor(count / discountableAmount);
                          discount += itemPrice * discountableNum * countDiscountRate;
                          countedBarCodes.add(barCode);
                      }
                  }

              }
              countedBarCodes.add(barCode);
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

    private boolean isCodeDiscounted(String code){
        boolean isDiscount = discountableBarCodes.contains(code);
        return isDiscount;
    }
}
