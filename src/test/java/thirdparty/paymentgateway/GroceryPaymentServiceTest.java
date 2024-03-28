package thirdparty.paymentgateway;

import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;

public class GroceryPaymentServiceTest {

    GroceryPaymentService groceryPaymentService = new GroceryPaymentServiceImpl();
    final DecimalFormat decfor = new DecimalFormat("0.00");

    @Test
    public void makePaymentOverPayingTest(){
        groceryPaymentService.setPaidAmount("15");
        groceryPaymentService.makePayment(1234,12.3);
        PaymentResult result = groceryPaymentService.getPaymentResult();
        String changeDue =  decfor.format(result.getChange());
       assert (changeDue.equals("2.70"));

    }


}
