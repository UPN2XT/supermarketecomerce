package Customer;

import java.util.HashMap;
import java.util.Map;

import products.Product;

public class Cart {
    private final Map<Product, Integer> cart;

    public Cart() {
        cart = new HashMap<>();
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public void add(Product p, Integer quant) throws Exception {
        cart.put(p, (cart.containsKey(p) ? cart.get(p) : 0) + quant);
        if (p.getQuantity() < cart.get(p)) {
            cart.put(p, (cart.containsKey(p) ? cart.get(p) : 0) - quant);
            if (cart.get(p) == 0)
                cart.remove(p);
            throw new Exception("Not enough Invetory x" + quant + " of " + p.getName() + " was not added");
        }

    }
}
