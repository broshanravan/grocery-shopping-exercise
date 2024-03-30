package Services;

import thirdparty.entities.GroceryItem;
import thirdparty.entities.PromotionalDiscount;

import java.util.List;


public interface MaintenanceService {


    public boolean isBarFormatCodeValid(String barCode);

    public void saveItemToInventory(GroceryItem groceryItem);







    }
