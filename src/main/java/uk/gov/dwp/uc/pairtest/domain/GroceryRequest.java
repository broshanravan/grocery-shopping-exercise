package uk.gov.dwp.uc.pairtest.domain;

/**
 * Should be an Immutable Object
 */
public class GroceryRequest {

    private int noOfItems;
    private Grocery grocery;

    public GroceryRequest(Grocery grocery, int noOfItems) {
        this.grocery = grocery;
        this.noOfItems = noOfItems;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public Grocery getGrocery() {
        return grocery;
    }

    public enum Grocery {
        MILK_CARTON_1LTR("MK001"),
        BREAD_LOAF("BL001"),
        EGGS_6PK("EG001");

        private final String code;
        Grocery(String code){
            this.code = code;
        }

        public String getCode(){
            return code;
        }
    }

}
