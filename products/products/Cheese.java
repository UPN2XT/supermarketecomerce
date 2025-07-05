package products.products;

import java.time.ZonedDateTime;

import products.Expires;
import products.Shippable;

public class Cheese extends Expires implements Shippable {

    private final double weight;

    public Cheese(int quantity, double price, ZonedDateTime expiryDate, double weight) {
        super("Cheese", quantity, price, expiryDate);
        if (weight <= 0)
            throw new NumberFormatException("Weight can not be less than or equal zero");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

}