package thirdparty.promotions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import thirdparty.entities.BasketItem;
import thirdparty.entities.GroceryItem;
import thirdparty.entities.PromotionalDiscount;
import thirdparty.entities.enums.MeasurementUnit;
import thirdparty.inventory.GroceryItemsInventory;
import thirdparty.inventory.GroceryItemsInventoryImpl;

import java.util.*;

import static org.mockito.Mockito.when;

public class PromotionsServiceTest {

    private List<BasketItem> basket = new LinkedList<BasketItem>();
    PromotionService promotionService = new PromotionServiceImpl();

    GroceryItemsInventory groceryItemsInventory = Mockito.mock(GroceryItemsInventoryImpl.class);

    GroceryItem Banana = new GroceryItem(1,"BN123","Banana", MeasurementUnit.weight,1.50);
    GroceryItem Egg = new GroceryItem(1,"EG123","Egg", MeasurementUnit.count,1.00);
    GroceryItem Serial = new GroceryItem(3,"SR123","SERIALS", MeasurementUnit.count,2.50);
    GroceryItem Grape = new GroceryItem(4,"GR123","Grape", MeasurementUnit.weight,1.00);
    GroceryItem Milk = new GroceryItem(5,"MK123","Milk", MeasurementUnit.count,0.70);
    GroceryItem Orange = new GroceryItem(5,"OR123","Orange", MeasurementUnit.weight,0.70);
    GroceryItem Bread = new GroceryItem(5,"BR123","Bread", MeasurementUnit.count,0.80);


    @BeforeEach
    public void prepareBasket() {

        basket.add(new BasketItem(Banana,5.00));
        basket.add(new BasketItem(Egg,1));
        basket.add(new BasketItem(Egg,1));
        basket.add(new BasketItem(Egg,1));
        basket.add(new BasketItem(Egg,1));
        basket.add(new BasketItem(Grape,2.00));
        basket.add(new BasketItem(Milk,1));
        basket.add(new BasketItem(Serial,1));


        List<String> discountableBarCodes = new ArrayList<>();
        discountableBarCodes.add("BA123");
        discountableBarCodes.add("EG123");
        discountableBarCodes.add("BR123");
        discountableBarCodes.add("OR123");

        Map<String, PromotionalDiscount> promotionalDiscounts = new HashMap<String, PromotionalDiscount>();
        promotionalDiscounts.put("BN123", (new PromotionalDiscount("BN123", 0.25,2)));
        promotionalDiscounts.put("EG123", (new PromotionalDiscount("EG123", 0.50,2)));
        promotionalDiscounts.put("BR123", (new PromotionalDiscount("BR123", 0.50,2)));
        promotionalDiscounts.put("OR123", (new PromotionalDiscount("OR123", 0.40,2)));
        when (groceryItemsInventory.getPromotionalDiscounts()).thenReturn(promotionalDiscounts);

        promotionService.setGroceryItemsInventory(groceryItemsInventory);
        promotionService.setPromotionalDiscounts(promotionalDiscounts);



    }

    @Test
    public void getTotalDiscountTest(){

        double totalDiscount = promotionService.getTotalDiscount(basket);
        assert(totalDiscount == 3.12) ;
    }

    @Test
    public void getDiscountWeightTest(){
        double weightDiscount = promotionService.getDiscountWeight(basket);
        assert(weightDiscount == 1.12);

    }

    @Test
    public void getDiscountCountTest(){
        double countDiscount = promotionService.getDiscountCount(basket);
        assert(countDiscount == 2);

    }

    @Test
    public void getCountDiscountMultiDiscountedTest(){


        basket.add(new BasketItem(Bread,4));

        double countDiscount = promotionService.getDiscountCount(basket);

        assert(countDiscount == 3.6);

    }

    @Test
    public void getWeightDiscountMultiDiscountedTest(){

        basket.add(new BasketItem(Orange,4));

        double countDiscount = promotionService.getDiscountWeight(basket);

        assert(countDiscount == 1.69);

    }


}
