package thirdparty.promotions;

import Services.MaintenanceService;
import Services.MaintenanceServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thirdparty.entities.BasketItem;
import thirdparty.entities.PromotionalDiscount;
import thirdparty.entities.enums.MeasurementUnit;
import thirdparty.inventory.GroceryItemsInventory;
import thirdparty.inventory.GroceryItemsInventoryImpl;

import java.text.DecimalFormat;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    MaintenanceService maintenanceService = new MaintenanceServiceImpl();
    GroceryItemsInventory groceryItemsInventory = new GroceryItemsInventoryImpl();
    Map<String, PromotionalDiscount>  promotionalDiscounts = groceryItemsInventory.getPromotionalDiscounts();
    Set<String> discountedBarcodes;// = promotionalDiscounts.keySet();

    public PromotionalDiscount getBarcodePromotionalDiscountRate(String barcode){
        return promotionalDiscounts.get(barcode);
    }

    /**
     * Uses the promotional data to go through
     * the items in the basket and calculate the total
     * promotional discount for all items for which the
     * measurement unit s weight
     * @param basket
     * @return
     */
    public double getDiscountWeight(List<BasketItem> basket){
        discountedBarcodes = promotionalDiscounts.keySet();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double discount = 0;
        double weight = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket){
            String barCode =basketItem.getGroceryItem().getBarCode();
            double itemPricePerWeightUnit = basketItem.getGroceryItem().getPrice();
            MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit();
            if(discountedBarcodes.contains(barCode) && measurementUnit.equals(MeasurementUnit.weight)) {

                double  discountItemWeight = 0;
                double discountThreshold = getBarcodePromotionalDiscountRate(barCode).getDiscountThreshold();
                for (BasketItem discountedBasketItem: basket) {
                    if (discountedBasketItem.getGroceryItem().getBarCode().equalsIgnoreCase(barCode)
                            &&!countedBarCodes.contains(barCode)) {
                        discountItemWeight += basketItem.getQuantity() ;
                    }

                }

                if (discountItemWeight > discountThreshold) {
                    PromotionalDiscount promotionalDiscount = getBarcodePromotionalDiscountRate(barCode);
                    double discountableWeight = (discountItemWeight-discountThreshold);
                    discount += itemPricePerWeightUnit * discountableWeight * promotionalDiscount.getDiscountRate();
                    countedBarCodes.add(barCode);

                }
            }

        }
        discount = Double.parseDouble(decimalFormat.format(discount));
        return discount;
    }

    /**
     *  Uses the promotional data to go through
     *  the items in the basket and calculate the total
     *  promotional discount for all items for which the
     *   measurement unit s count
     * @param basket
     * @return
     */
    public double getDiscountCount(List<BasketItem> basket){
        discountedBarcodes = promotionalDiscounts.keySet();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double discount = 0;
        int count = 0;
        List<String> countedBarCodes = new LinkedList<String>();
        for (BasketItem basketItem: basket){
            String barCode = basketItem.getGroceryItem().getBarCode();
            double itemPrice = basketItem.getGroceryItem().getPrice();
            MeasurementUnit measurementUnit = basketItem.getGroceryItem().getMeasurementUnit();

            if(discountedBarcodes.contains(barCode) && measurementUnit.equals(MeasurementUnit.count)) {
              double discountThreshold = getBarcodePromotionalDiscountRate(barCode).getDiscountThreshold();
              int discountedItemCount = 0;

              for (BasketItem doscountedBasketItem: basket) {
                  //String discountedBarCode  = doscountedBasketItem.getGroceryItem().getBarCode();
                  if (doscountedBasketItem.getGroceryItem().getBarCode().equalsIgnoreCase(barCode)
                  &&!countedBarCodes.contains(barCode)) {
                      discountedItemCount += doscountedBasketItem.getQuantity();
                  }

              }
              if (discountedItemCount >= discountThreshold) {
                  PromotionalDiscount promotionalDiscount = promotionalDiscounts.get(barCode);
                  discountedItemCount -= (int)(discountedItemCount % discountThreshold);


                  discount += itemPrice * discountedItemCount * promotionalDiscount.getDiscountRate();;
                  countedBarCodes.add(barCode);

              }

          }

        }
        discount = Double.parseDouble(decimalFormat.format(discount));
        return discount;
    }

    /**
     * calculate the tonal amount of discount
     * in the basket by adding both above discounts
     * @param basket
     * @return
     */
    public double getTotalDiscount(List<BasketItem> basket){
       double totalDiscount = 0;

        double discountWeight = this.getDiscountWeight(basket);
        double discountCount = this.getDiscountCount(basket);

        totalDiscount = discountWeight + discountCount;
        return totalDiscount;
    }

    @Override
    public void setPromotionalDiscounts(Map<String, PromotionalDiscount> promotionalDiscountsIn){
        this.promotionalDiscounts = promotionalDiscountsIn ;
    }





}
