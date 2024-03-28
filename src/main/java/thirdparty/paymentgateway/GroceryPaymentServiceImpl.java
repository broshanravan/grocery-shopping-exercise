package thirdparty.paymentgateway;

import lombok.Getter;
import lombok.Setter;

import java.math.RoundingMode;
import java.text.DecimalFormat;


@Getter
@Setter
public class GroceryPaymentServiceImpl implements GroceryPaymentService {
    private boolean paymentCompleted;
    private double change;
    private String paidAmount;
    private String paymentResultMessage;
    @Override
    public void makePayment(long accountId, double totalAmountToPay) {


        paymentCompleted = false;
        final DecimalFormat decfor = new DecimalFormat("0.00");
        decfor.setRoundingMode(RoundingMode.DOWN);
        double paid = Double.parseDouble(paidAmount);
        if(paid < totalAmountToPay){
            double remaining = totalAmountToPay - paid ;
            paymentResultMessage = String.valueOf(remaining);

        } else if (paid > totalAmountToPay) {
            change = paid - totalAmountToPay ;

            paymentResultMessage = "Change due £ " + decfor.format(change);;
            paymentCompleted =true;
        } else {
            paymentResultMessage = "";
            paymentCompleted =true;
        }


    }

    public PaymentResult getPaymentResult(){
        PaymentResult paymentResult = new PaymentResult(paymentCompleted,
                change,
                paidAmount,
                paymentResultMessage
        );
        return paymentResult;

    }

    public boolean isAmountValid(String amount) {
        if (amount == null) {
            return false;
        }


        try {
            double d = Double.parseDouble(amount);
            if(d < 0){
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
