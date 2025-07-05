import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import Customer.Cart;
import Customer.Customer;
import Customer.ShipingService;
import products.Expires;
import products.Product;
import products.Shippable;
import products.products.Biscuits;
import products.products.Cheese;
import products.products.Mobile;
import products.products.MobileScratchCard;
import products.products.TV;

public class main {

    static Customer customer = new Customer("someone", 15000);
    static int shippingPrice = 30;
    static ShipingService shipingService = new ShipingService();

    public static void checkout(Customer customer, Cart cart) throws Exception {
        Map<Product, Integer> products = cart.getCart();
        Map<Shippable, Integer> shipments = new HashMap<>();
        if (cart.getCart().isEmpty())
            throw new Exception("Cart is empty");
        ZonedDateTime now = ZonedDateTime.now();
        String checkout = "** Checkout receipt **";
        double totalPrice = 0;
        boolean isShiped = false;
        for (Product p : products.keySet()) {
            if (p instanceof Expires && ((Expires) p).getExpiryDate().isBefore(now))
                throw new Exception("product " + p.getName() + "is expired");
            if (p.getQuantity() < products.get(p))
                throw new Exception("There is not enough Inventory of product " + p.getName()); // for hypothetical
                                                                                                // concurrency
            // purposes
            int quant = products.get(p);
            String name = p.getName();
            double price = p.getPrice();
            if (p instanceof Shippable)
                shipments.put((Shippable) p, quant);

            totalPrice += price;
            checkout += "\n" + +quant + "x " + name + " " + price;

        }

        if (!shipments.isEmpty())
            totalPrice += shippingPrice;

        if (totalPrice > customer.getBalance())
            throw new Exception("User has insufficent funds");

        if (!shipments.isEmpty())
            shipingService.ship(shipments, customer);

        System.out.println(checkout + "\n----------------------\n" + "Subtotal " + totalPrice + "\n"
                + (isShiped ? "Shipping" + shippingPrice + "\n" : "")
                + "Amount " + totalPrice);
        customer.setBalance(customer.getBalance() - totalPrice);
        System.out.println("Balance left " + customer.getBalance());

    }

    // normal test without shipping
    public static void test0() {
        System.out.println("test 0");
        Cart cart = new Cart();
        try {
            MobileScratchCard mobileScratchCard = new MobileScratchCard(5, 10);
            cart.add(mobileScratchCard, 1);
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // normal test with shipping
    public static void test1() {
        System.out.println("\ntest 1");
        Cart cart = new Cart();
        try {
            Biscuits biscuits = new Biscuits(10, 20, ZonedDateTime.now().plusDays(1), 350);
            Mobile mobile = new Mobile(3, 1000, 400);
            MobileScratchCard mobileScratchCard = new MobileScratchCard(5, 10);
            TV tv = new TV(5, 300, 2500);
            Cheese cheese = new Cheese(2, 20, ZonedDateTime.now().plusDays(1), 250);
            cart.add(mobileScratchCard, 1);
            cart.add(biscuits, 2);
            cart.add(mobile, 1);
            cart.add(tv, 1);
            cart.add(cheese, 1);
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // empty cart
    public static void test2() {
        System.out.println("\ntest 2");
        Cart cart = new Cart();
        try {
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // out stock / not enouch inventroy Adding to cart
    public static void test3() {
        System.out.println("\ntest 3");
        try {
            Cart cart = new Cart();
            MobileScratchCard mobileScratchCard = new MobileScratchCard(1, 10);
            cart.add(mobileScratchCard, 1);
            cart.add(mobileScratchCard, 1); // 2nd add should exceed inv
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // out stock / not enouch inventroy Checkout
    public static void test4() {
        System.out.println("\ntest 4");
        try {
            Cart cart = new Cart();
            MobileScratchCard mobileScratchCard = new MobileScratchCard(1, 10);
            cart.add(mobileScratchCard, 1);
            mobileScratchCard.setQuantity(0);
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // expired item
    public static void test5() {
        System.out.println("\ntest 5");
        try {
            Cart cart = new Cart();
            Biscuits biscuits = new Biscuits(10, 20, ZonedDateTime.now().minusDays(1), 350);
            cart.add(biscuits, 1);
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // insufficent funds
    public static void test6() {
        System.out.println("\ntest 6");
        Cart cart = new Cart();
        MobileScratchCard mobileScratchCard = new MobileScratchCard(1, 100000);
        try {
            cart.add(mobileScratchCard, 1);
            checkout(customer, cart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // adding a negative number to price also the same applies for weight > 0, and
    // quant >= 0
    public static void test7() {
        System.out.println("\ntest 7");
        try {
            new MobileScratchCard(-1, -1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        test0();
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        System.out.println("\nPrinting Shipments from Shiping Service");
        shipingService.printShipments();
    }
}