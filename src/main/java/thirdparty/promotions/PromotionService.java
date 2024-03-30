package thirdparty.promotions;

import thirdparty.entities.BasketItem;

import java.util.List;

public interface PromotionService {

    public double getDiscountWeight(List<BasketItem> basket);

    public double getTotalDiscount(List<BasketItem> basket);

    public double getDiscountCount(List<BasketItem> basket);
}
