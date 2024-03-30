package Services;

import thirdparty.entities.GroceryItem;
import thirdparty.entities.PromotionalDiscount;
import thirdparty.inventory.GroceryItemsInventory;
import thirdparty.inventory.GroceryItemsInventoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MaintenanceServiceImpl implements MaintenanceService {



    GroceryItemsInventory inventoryService = new GroceryItemsInventoryImpl();



    public boolean isBarFormatCodeValid(String barCode){

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

    public void saveItemToInventory(GroceryItem groceryItem){
        inventoryService.saveGroceryItem(groceryItem);


    }
}
