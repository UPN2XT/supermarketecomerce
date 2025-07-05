package products;

import java.time.ZonedDateTime;

public abstract class Expires extends Product {
    private final ZonedDateTime expiryDate;

    public Expires(String name, int quantity, double price, ZonedDateTime expiryDate) {
        super(name, quantity, quantity);
        if (expiryDate == null)
            throw new NullPointerException("Expirey date can not be null");
        this.expiryDate = expiryDate;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }
}
