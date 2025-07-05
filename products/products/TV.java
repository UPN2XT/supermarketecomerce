package products.products;

import products.Product;
import products.Shippable;

public class TV extends Product implements Shippable {

    private final double weight;

    public TV(int quantity, int price, double weight) {
        super("TV", price, quantity);
        if (weight <= 0)
            throw new NumberFormatException("Weight can not be less than or equal zero");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

}
