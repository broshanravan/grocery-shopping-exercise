package thirdparty.promotions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thirdparty.entities.BasketItem;
import thirdparty.entities.GroceryItem;
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
        double weight = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket){

            String barCode =basketItem.getGroceryItem().getBarCode();
            double itemPrice = basketItem.getGroceryItem().getPrice();
            MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit();
            if(isCodeDiscounted(barCode) && measurementUnit.equals(MeasurementUnit.weight)) {
                double  discountItemWeight = 0;
                for (BasketItem discountedBasketItem: basket) {
                    //String discountedBarCode  = doscountedBasketItem.getGroceryItem().getBarCode();

                    if (discountedBasketItem.getGroceryItem().getBarCode().equalsIgnoreCase(barCode)
                            &&!countedBarCodes.contains(barCode)) {
                        discountItemWeight += basketItem.getWeight() ;
                    }

                }
                if (discountItemWeight >= discountableCountPerGroup) {
                    double discountableGroupsNum = Math.floor(discountItemWeight / discountableCountPerGroup);
                    discount += itemPrice * discountableGroupsNum * countDiscountRate ;;
                    countedBarCodes.add(barCode);

                }
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
              int discountItemCount = 0;
              for (BasketItem doscountedBasketItem: basket) {
                  //String discountedBarCode  = doscountedBasketItem.getGroceryItem().getBarCode();
                  if (doscountedBasketItem.getGroceryItem().getBarCode().equalsIgnoreCase(barCode)
                  &&!countedBarCodes.contains(barCode)) {
                      discountItemCount ++;
                  }

              }
              if (discountItemCount >= discountableCountPerGroup) {
                  double discountableGroupsNum = Math.floor(discountItemCount / discountableCountPerGroup);
                  discount += itemPrice * discountableGroupsNum * countDiscountRate * discountableCountPerGroup;;
                  countedBarCodes.add(barCode);

              }


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
