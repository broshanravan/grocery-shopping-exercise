package thirdparty.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionalDiscount {
    String discountedBar;
    double discountRate;
    double discountThreshold;
}