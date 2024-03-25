package Services;

import thirdparty.entities.GroceryItem;
import thirdparty.inventory.GroceryInventoryService;
import thirdparty.inventory.GroceryInventoryServiceImpl;

public class StockMaintenanceServiceImpl implements StockMaintenanceService{

    GroceryInventoryService inventoryService = new GroceryInventoryServiceImpl();
    private boolean isItemDetailsValid = true;
    public boolean isBarcodeInUse(String barCode){
        return inventoryService.doesBarCodeExist(barCode);

    }

    public boolean isBarCodeValid(String barCode){

        Boolean barCodeValid = true;
        String barcodeLetters  = barCode.substring(0,2);
        String barcodeNumbers  = barCode.substring(2,barCode.length());
        int number = 0;
        while (number < 10){
            if(barcodeLetters.contains(String.valueOf(number))){
                barCodeValid = false;
                break;
            }
            number++;
        }
        if(barCode.length() != 5){
            barCodeValid = false;;
        }
        try{
            Double.parseDouble(barcodeNumbers);
        } catch (NumberFormatException nfe){
            barCodeValid = false;
        }

        return barCodeValid;
    }

    public void addItemToInventory(GroceryItem groceryItem){
        if(isItemDetailsValid) {

        }

    }
    public void saveItemToInventory(GroceryItem groceryItem){
        inventoryService.saveGroceryItem(groceryItem);

    }
}
