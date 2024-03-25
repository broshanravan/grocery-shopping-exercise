package thirdparty.paymentgateway;

public interface GroceryPaymentService {

    public void makePayment(long accountId, double totalAmountToPay);
    public void setPaidAmount(String paidAmount);

    public PaymentResult getPaymentResult();

    public boolean isAmountValid(String amount);
}
