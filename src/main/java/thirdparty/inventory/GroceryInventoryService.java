package thirdparty.inventory;

import thirdparty.entities.GroceryItem;

public interface GroceryInventoryService {


    public boolean doesBarCodeExist(String barcode);

    public GroceryItem getGroceryItem(String barCode);

     public void saveGroceryItem(GroceryItem groceryItem);

}
