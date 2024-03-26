package thirdparty.promotions;

import thirdparty.entities.BasketItem;
import uk.gov.dwp.uc.pairtest.domain.GroceryRequest;

import java.math.BigDecimal;
import java.util.List;

public interface PromotionsService {

    public BigDecimal getAmountSaved(List<BasketItem> basket);

    public double getDiscountWeight(List<BasketItem> basket ,String barcode);

    public double getTotalDiscountCount(List<BasketItem> basket ,String barcode);

    public double getDiscountCount(List<BasketItem> basket);
}
