package thirdparty.inventory;

import thirdparty.entities.GroceryItem;
import thirdparty.entities.PromotionalDiscount;

import java.util.HashMap;
import java.util.Map;

public class GroceryInventoryInMemoryDBImpl implements GroceryItemsInventory{

    public boolean barCodeAlreadyExist(String barcode){
        //Todo implement the hibernate functionality to check if the barcode is already in use
        return true;
    }

    public GroceryItem getGroceryItem(String barCode){
        //Todo Implementing the Hibernate functionality to retrieving
        // the item from database
        return new GroceryItem();
    }


    public void saveGroceryItem(GroceryItem groceryItem){

        //Todo Implementing the Hibernate functionality to add
        // a new grocery item to the Database


    }

    public Map<String, PromotionalDiscount> getPromotionalDiscounts(){
        Map<String, PromotionalDiscount> discountMap= new HashMap<String, PromotionalDiscount>();
        //Todo Implementing the Hibernate functionality to
        // to find out if theere are any promotional discounts
        // associated with the items in the receipt


        return discountMap;
    }




}
