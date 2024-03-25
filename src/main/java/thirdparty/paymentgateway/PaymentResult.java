package thirdparty.paymentgateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResult {

    private boolean paymentCompleted;
    private double change;
    private String paidAmount;;
    private String paymentResultMessage;

}
