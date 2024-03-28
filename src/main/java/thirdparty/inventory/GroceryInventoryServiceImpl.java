package thirdparty.inventory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import thirdparty.entities.Credentials;
import thirdparty.entities.GroceryItem;
import thirdparty.entities.enums.MeasurementUnit;
import thirdparty.inventory.exception.InvalidGroceryException;

import java.util.*;
import java.util.logging.Logger;

public class GroceryInventoryServiceImpl implements GroceryInventoryService{

    Logger logger = Logger.getLogger(this.getClass().getName());


    public Credentials getCredentials(){
        //ToDo get the following values from a DB
        return new Credentials("admin", "Password 123");
    }

    private static Map itemsMap = new HashMap<String,GroceryItem>();
    private static Set barCodeList = itemsMap.keySet();

    /**
     * Checks if the barCode is already
     * in use before adding a new item
     * with the specified bar code to
     * the database
     * @param barcode
     * @return
     */
    public boolean doesBarCodeExist(String barcode){
         return barCodeList.contains(barcode);
    }

    /**
     * As the database is in memory
     * each time the application restarts
     * all data is wiped
     * Hence it will be populated with
     * new initial date
     */
    static{
        populateTableWithInitialData();
    }

    /**
     * Retrieving an Item using its barCode
     * Populates database
     * Currently He Db is not ready
     * hence an hashMap is bean used
     * @param barCode
     * @return
     */
    public GroceryItem getGroceryItem(String barCode) {
        //Todo Using H2 database with Spring JPA to retrieve the item data

        if(itemsMap.get(barCode) == null) {
            throw new InvalidGroceryException("Item with this barCode does not exist");
        }
       return (GroceryItem)itemsMap.get(barCode);
    }

    /**
     * Adding a new grocery Item to the DB
     * Currently the H2 DB is not ready and
     * hence a hashMap is bean used instead
     * @param groceryItem
     */
    public void saveGroceryItem(GroceryItem groceryItem) {
        itemsMap.put(groceryItem.getBarCode().toUpperCase(), groceryItem);
    }

    /**
     * Populates database
     * Currently He Db is not ready
     * hence an hashMap is bean used
     * when application is initialises
     */
    private static void populateTableWithInitialData() {
        //Todo Using a H2 database instead of Hashmap
        GroceryItem milk = new GroceryItem("MK123", "Milk", MeasurementUnit.count, 1.00);
        itemsMap.put("MK123",milk);

        GroceryItem banana = new GroceryItem("BN123", "Banana", MeasurementUnit.weight, 1.50);
        itemsMap.put("BN123",banana);

        GroceryItem egg = new GroceryItem("EG123", "Egg", MeasurementUnit.count, 2.40);
        itemsMap.put("EG123",egg);

        GroceryItem serial = new GroceryItem("SR123", "Serial", MeasurementUnit.count, 3.50);
        itemsMap.put("SR123",serial);

        GroceryItem grape = new GroceryItem("GR123", "grape", MeasurementUnit.weight, 2.30);
        itemsMap.put("GR123",grape);

    }

}
