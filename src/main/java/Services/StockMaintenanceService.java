package Services;

import thirdparty.entities.GroceryItem;

public interface StockMaintenanceService {
    public boolean isBarcodeInUse(String barCode);

    public boolean isBarCodeValid(String barCode);

    public void addItemToInventory(GroceryItem groceryItem);

    public void saveItemToInventory(GroceryItem groceryItem);

}
