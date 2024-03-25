package uk.gov.dwp.uc.pairtest;

import uk.gov.dwp.uc.pairtest.domain.GroceryPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public interface GroceryService {

    void purchaseGroceries(GroceryPurchaseRequest groceryPurchaseRequest) throws InvalidPurchaseException;

}
