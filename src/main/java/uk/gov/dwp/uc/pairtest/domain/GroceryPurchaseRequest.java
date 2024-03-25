package uk.gov.dwp.uc.pairtest.domain;

/**
 * Please make this an Immutable Object
 */
public class GroceryPurchaseRequest {

    private long accountId;
    private GroceryRequest[] groceryRequests;

    public GroceryPurchaseRequest(long accountId, GroceryRequest[] groceryRequests) {
        this.accountId = accountId;
        this.groceryRequests = groceryRequests;
    }

    public long getAccountId() {
        return accountId;
    }

    public GroceryRequest[] getGroceryRequests() {
        return groceryRequests;
    }
}
