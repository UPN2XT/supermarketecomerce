package products;

public abstract class Product {
    private final String name;
    private double price;
    private int quantity;

    public Product(String name, int price, int quantity) {
        this.name = name;
        if (price < 0)
            throw new NumberFormatException("Price can not be less than zero"); // zero because free items
        if (quantity < 0)
            throw new NumberFormatException("Quantity can not be less than zero"); // zero means out of stock
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new NumberFormatException("Price can not be less than zero");
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new NumberFormatException("Quantity can not be less than zero");
        this.quantity = quantity;
    }
}
