package thirdparty.promotions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thirdparty.entities.BasketItem;
import thirdparty.entities.enums.MeasurementUnit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionServiceImpl implements PromotionsService{
    private List<String> discountableBarCodes = new LinkedList<String>();
    double weightDiscountRate;
    double discountableWeightPerGroup;
    double countDiscountRate;
    double discountableCountPerGroup;




    public double getDiscountWeight(List<BasketItem> basket){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double discount = 0;
        int weight = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket){

            String barCode =basketItem.getGroceryItem().getBarCode();
            double itemPrice = basketItem.getGroceryItem().getPrice();
            MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit();
            if(isCodeDiscounted(barCode) && measurementUnit.equals(MeasurementUnit.weight)) {
                for (BasketItem doscountedBasketItem: basket) {
                    double itemWeight = basketItem.getWeight();
                    String discountedBarCode  = doscountedBasketItem.getGroceryItem().getBarCode();
                    if (!countedBarCodes.contains(barCode)) {
                        weight += itemWeight ;
                        if (weight >= discountableWeightPerGroup) {
                            //double discountableGroupsNum = Math.floor(weight / discountableWeightPerGroup);
                            discount += weight * itemPrice * weightDiscountRate;
                            countedBarCodes.add(barCode);
                        }
                    }

                }
                countedBarCodes.add(barCode);
            }

        }
        discount = Double.parseDouble(decimalFormat.format(discount));
        return discount;
    }

    public double getDiscountCount(List<BasketItem> basket){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double discount = 0;
        int count = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket){

            String barCode =basketItem.getGroceryItem().getBarCode();
            double itemPrice = basketItem.getGroceryItem().getPrice();
            MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit();
          if(isCodeDiscounted(barCode) && measurementUnit.equals(MeasurementUnit.count)) {
              for (BasketItem doscountedBasketItem: basket) {
                   //String discountedBarCode  = doscountedBasketItem.getGroceryItem().getBarCode();
                  if (!countedBarCodes.contains(barCode)) {
                      count += 1;
                      if (count >= discountableCountPerGroup) {
                          double discountableGroupsNum = Math.floor(count / discountableCountPerGroup);
                          discount += itemPrice * discountableGroupsNum * countDiscountRate * discountableCountPerGroup;;
                          countedBarCodes.add(barCode);
                      }
                  }

              }
              countedBarCodes.add(barCode);
          }

        }
        discount = Double.parseDouble(decimalFormat.format(discount));
        return discount;
    }

    public double getTotalDiscount(List<BasketItem> basket){
       double totalDiscount = 0;

        double discountWeight = this.getDiscountWeight(basket);
        double discountCount = this.getDiscountCount(basket);

        totalDiscount = discountWeight + discountCount;
        return totalDiscount;
    }


    private boolean isCodeDiscounted(String code){
        boolean isDiscount = discountableBarCodes.contains(code);
        return isDiscount;
    }
}
