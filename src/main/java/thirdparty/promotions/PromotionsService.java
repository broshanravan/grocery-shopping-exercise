package thirdparty.promotions;

import uk.gov.dwp.uc.pairtest.domain.GroceryRequest;

import java.math.BigDecimal;

public interface PromotionsService {

    BigDecimal getAmountSaved(GroceryRequest[] groceryRequests);
}
