package thirdparty.inventory;

import thirdparty.entities.GroceryItem;
import thirdparty.entities.PromotionalDiscount;

import java.util.Map;

public interface GroceryItemsInventory {


    public boolean barCodeAlreadyExist(String barcode);

    public GroceryItem getGroceryItem(String barCode);

     public void saveGroceryItem(GroceryItem groceryItem);

    Map<String, PromotionalDiscount> getPromotionalDiscounts();

}
