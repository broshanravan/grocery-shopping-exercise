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

    private static PromotionsService promotionsService = new PromotionServiceImpl();
    private static List<BasketItem> basket = new LinkedList<BasketItem>();

    @BeforeAll
    static void prepareBasket() {
        GroceryItem Banana = new GroceryItem(1,"BA123","Banana", MeasurementUnit.weight,1.50);
        GroceryItem Egg = new GroceryItem(1,"EG123","Egg", MeasurementUnit.count,0.70);
        GroceryItem anotherEgg = new GroceryItem(1,"EG123","Egg", MeasurementUnit.count,0.70);
        GroceryItem Banana2 = new GroceryItem(1,"BA123","Banana", MeasurementUnit.weight,1.50);
        GroceryItem Serial = new GroceryItem(3,"SR123","SERIALS", MeasurementUnit.count,2.50);
        GroceryItem Grape = new GroceryItem(4,"GR123","Grape", MeasurementUnit.weight,1.00);
        GroceryItem Milk = new GroceryItem(5,"MK123","Milk", MeasurementUnit.count,0.70);

        basket.add(new BasketItem(Banana,1.00));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(Egg,0));
        basket.add(new BasketItem(anotherEgg,0));
        basket.add(new BasketItem(Grape,2.00));
        basket.add(new BasketItem(Milk,0));
        basket.add(new BasketItem(Banana2,2.00));
        basket.add(new BasketItem(Serial,0));

        double weightDiscountRate = 0.25;
        double discountableWeight = 3;
        double countDiscountRate = 0.5;
        double discountableCount = 2;
        List<String> discountableBarCodes = new ArrayList<>();
        discountableBarCodes.add("BA123");
        discountableBarCodes.add("EG123");

        promotionsService = new PromotionServiceImpl(discountableBarCodes,
                weightDiscountRate,
                discountableWeight,
                countDiscountRate,
                discountableCount);



    }

    @Test
    public void getTotalDiscountTest(){
        double totalDiscount = promotionsService.getTotalDiscount(basket);
        assert(totalDiscount == 1.82) ;
    }

    @Test
    public void getCountDiscountWeightTest(){
        double weightDiscount = promotionsService.getDiscountWeight(basket);
        assert(weightDiscount == 1.12);

    }

    @Test
    public void getCountDiscountCountTest(){
        double countDiscount = promotionsService.getDiscountCount(basket);
        assert(countDiscount == 0.7);

    }


}
