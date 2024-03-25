package thirdparty.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thirdparty.entities.enums.MeasurementUnit;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "GROCERY_ITEM")
public class GroceryItem implements Serializable {

    @Id
    @GeneratedValue
    private long itemId;

    @Column(name = "BAR_CODE")
    private String barCode;

    @Column(name = "ITEM_NAME")
    private String name;

    @Column(name = "MEASUREMENT_UNIT")
    private MeasurementUnit measurementUnit;

    @Column(name = "PRICE")
    private double price;

    public GroceryItem( String barCode, String name,MeasurementUnit measurementUnit,double price) {
        this.barCode = barCode;
        this.name = name;
        this.measurementUnit =measurementUnit;
        this.price = price;
    }
}
