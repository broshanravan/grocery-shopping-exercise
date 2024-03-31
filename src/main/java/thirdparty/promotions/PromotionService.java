package thirdparty.promotions;

import lombok.Getter;
import lombok.Setter;
import thirdparty.entities.BasketItem;
import thirdparty.entities.PromotionalDiscount;
import thirdparty.inventory.GroceryItemsInventory;

import java.util.List;
import java.util.Map;


public interface PromotionService {

    public void setGroceryItemsInventory(GroceryItemsInventory groceryItemsInventory);
    public void setPromotionalDiscounts(Map<String, PromotionalDiscount> promotionalDiscountsIn);

    public double getDiscountWeight(List<BasketItem> basket);

    public double getTotalDiscount(List<BasketItem> basket);

    public double getDiscountCount(List<BasketItem> basket);
}
