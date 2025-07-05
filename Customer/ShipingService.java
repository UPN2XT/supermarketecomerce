package Customer;

import java.util.HashMap;
import java.util.Map;

import products.Product;
import products.Shippable;

public class ShipingService {
    Map<Customer, Map<Shippable, Integer>> Shipments;

    private String weightOutput(double weight) {
        if (weight >= 1000)
            return (weight / 1000) + "kg";
        return weight + "g";
    }

    public ShipingService() {
        Shipments = new HashMap<>();
    }

    public void ship(Map<Shippable, Integer> products, Customer customer) {
        if (!Shipments.containsKey(customer))
            Shipments.put(customer, new HashMap<>());
        double totalWeight = 0;
        String shipment = "** Shipment notice **";
        for (Shippable p : products.keySet()) {
            Map<Shippable, Integer> ps = Shipments.get(customer);
            if (ps.containsKey(p))
                ps.put(p, ps.get(p) + products.get(p));
            else
                ps.put(p, products.get(p));
            ((Product) p).setQuantity(((Product) p).getQuantity() - products.get(p)); // remove shipped item from inv
            double weight = ((Shippable) p).getWeight();
            totalWeight += weight;
            int quant = products.get(p);
            String name = p.getName();
            shipment += "\n" + quant + "x " + name + " " + weightOutput(weight);
        }
        System.out.println(shipment + "\nTotal package weight " + weightOutput(totalWeight) + "\n\n");
    }

    public void printShipments() {
        for (Customer c : Shipments.keySet()) {
            System.out.println("shipments to " + c.getName());
            Map<Shippable, Integer> ships = Shipments.get(c);
            for (Shippable s : ships.keySet()) {
                System.out.println(s.getName() + " " + "x" + ships.get(s));
            }
        }
    }
}
