package thirdparty.inventory;

import thirdparty.entities.GroceryItem;
import thirdparty.entities.PromotionalDiscount;

import java.util.HashMap;
import java.util.Map;

public class GroceryInventoryInMemoryDBImpl implements GroceryItemsInventory{

    public boolean barCodeAlreadyExist(String barcode){
        return true;
    }

    public GroceryItem getGroceryItem(String barCode){
        return new GroceryItem();
    }


    public void saveGroceryItem(GroceryItem groceryItem){

    }

    public Map<String, PromotionalDiscount> getPromotionalDiscounts(){
        Map<String, PromotionalDiscount> discountMap= new HashMap<String, PromotionalDiscount>();

        return discountMap;
    }




}
