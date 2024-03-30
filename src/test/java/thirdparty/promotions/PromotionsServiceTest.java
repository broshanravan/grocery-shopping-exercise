package thirdparty.promotions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import thirdparty.entities.BasketItem;
import thirdparty.entities.GroceryItem;
import thirdparty.entities.enums.MeasurementUnit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PromotionsServiceTest {

    private static List<BasketItem> basket = new LinkedList<BasketItem>();
    PromotionService promotionService = new PromotionServiceImpl();

    @BeforeAll
    static void prepareBasket() {
        GroceryItem Banana = new GroceryItem(1,"BN123","Banana", MeasurementUnit.weight,1.50);
        GroceryItem Egg = new GroceryItem(1,"EG123","Egg", MeasurementUnit.count,1.00);
        GroceryItem Serial = new GroceryItem(3,"SR123","SERIALS", MeasurementUnit.count,2.50);
        GroceryItem Grape = new GroceryItem(4,"GR123","Grape", MeasurementUnit.weight,1.00);
        GroceryItem Milk = new GroceryItem(5,"MK123","Milk", MeasurementUnit.count,0.70);

        basket.add(new BasketItem(Banana,4.00));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(Grape,2.00));
        basket.add(new BasketItem(Milk,0));
        basket.add(new BasketItem(Serial,0));


        List<String> discountableBarCodes = new ArrayList<>();
        discountableBarCodes.add("BA123");
        discountableBarCodes.add("EG123");

    }

    @Test
    public void getTotalDiscountTest(){

        double totalDiscount = promotionService.getTotalDiscount(basket);
        assert(totalDiscount == 2.38) ;
    }

    @Test
    public void getCountDiscountWeightTest(){
        double weightDiscount = promotionService.getDiscountWeight(basket);
        assert(weightDiscount == 0.38);

    }

    @Test
    public void getCountDiscountCountTest(){
        double countDiscount = promotionService.getDiscountCount(basket);
        assert(countDiscount == 2);

    }


}
