package thirdparty.promotions;

import thirdparty.entities.BasketItem;
import uk.gov.dwp.uc.pairtest.domain.GroceryRequest;

import java.math.BigDecimal;
import java.util.List;

public interface PromotionsService {

    public double getDiscountWeight(List<BasketItem> basket);

    public double getTotalDiscount(List<BasketItem> basket);

    public double getDiscountCount(List<BasketItem> basket);
}
