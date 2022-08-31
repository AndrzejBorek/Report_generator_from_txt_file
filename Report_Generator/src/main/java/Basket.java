import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Basket {

    private final ArrayList<Order> orders;

    public Basket(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public BigDecimal getTotalValueOfAllOrdersAfterDiscount() {
        BigDecimal sum = new BigDecimal("0.00");
        for (Order order : this.orders) {
            sum = sum.add(order.getOrderValue());
        }
        return sum;
    }

    public BigDecimal getTotalSumOfDiscountForClient(Client c) {
        BigDecimal afterDiscountValueToPay = this.getValuesOfOrderedProducts().get(c);
        BigDecimal beforeDiscountValueToPay = this.getValuesOfProductPrices().get(c);
        return beforeDiscountValueToPay.subtract(afterDiscountValueToPay).setScale(2, RoundingMode.DOWN);

    }

    public BigDecimal getTotalValueOfAllOrdersBeforeDiscount() {
        BigDecimal sum = new BigDecimal("0.00");
        for (Order order : this.orders) {
            sum = sum.add(order.getProduct().getPrice()).setScale(2, RoundingMode.CEILING);
        }
        return sum.setScale(2, RoundingMode.CEILING);
    }

    public Set<Client> getClients() {
        Set<Client> clients = new HashSet<>();
        for (Order o : orders) {
            clients.add(o.getClient());
        }
        return clients;
    }

    public HashSet<Product> getProducts() {
        HashSet<Product> products = new HashSet<>();
        for (Order o : orders) {
            products.add(o.getProduct());
        }
        return products;
    }

    public HashSet<Long> getIdOfClients() {
        HashSet<Long> clients = new HashSet<>();
        for (Order o : orders) {
            clients.add(o.getClient().getId());
        }
        return clients;
    }

    public HashMap<Client, BigDecimal> getValuesOfOrderedProducts() {
        HashMap<Client, BigDecimal> values = new HashMap<>();
        for (Order o : orders) {
            if (!values.containsKey(o.getClient())) {
                values.put(o.getClient(), o.getOrderValue());
            } else {
                values.put(o.getClient(), values.get(o.getClient()).add(o.getOrderValue()).setScale(2, RoundingMode.CEILING));
            }
        }
        return values;
    }

    public HashMap<Client, BigDecimal> getValuesOfProductPrices() {
        HashMap<Client, BigDecimal> values = new HashMap<>();
        for (Order o : orders) {
            if (!values.containsKey(o.getClient())) {
                values.put(o.getClient(), o.getProduct().getPrice());
            } else {
                values.put(o.getClient(), values.get(o.getClient()).add(o.getProduct().getPrice()).setScale(2, RoundingMode.CEILING));
            }
        }
        return values;
    }

    public BigDecimal getValueOfClientAccount(Client c) {
        var values = this.getValuesOfOrderedProducts();
        return values.get(c).setScale(2, RoundingMode.CEILING);
    }

    public HashMap<Client, HashMap<Long, BigDecimal>> getNumberOfProductsBought() {
        HashMap<Client, HashMap<Long, BigDecimal>> result = new HashMap<>();
        for (Client c : this.getClients()) {
            result.put(c, this.getNumberOfProductsBoughtByClient(c));
        }
        return result;
    }

    public HashMap<Long, BigDecimal> getNumberOfProductsBoughtByClient(Client c) {
        ArrayList<Long> products = new ArrayList<>();
        for (Product p : this.getProducts()) {
            products.add(p.getId());
        }
        HashMap<Long, BigDecimal> result = new HashMap<>();
        for (Long i : products) {
            result.put(i, new BigDecimal("0"));
        }
        for (Order o : orders) {
            if (c.equals(o.getClient())) {
                result.put(o.getProduct().getId(), result.get(o.getProduct().getId()).add(new BigDecimal("1")));
            }
        }
        return result;
    }

    public HashMap<Client, BigDecimal> getDiscountForAllClients() {
        HashMap<Client, BigDecimal> result = new HashMap<>();
        for (Client c : this.getClients()) {
            result.put(c, this.getTotalSumOfDiscountForClient(c).setScale(2, RoundingMode.CEILING));
        }
        return result;
    }

    public HashMap<Product, Integer> getQuantityOfProductsSold() {
        HashMap<Product, Integer> result = new HashMap<>();

        for (Order o : orders) {
            if (!result.containsKey(o.getProduct())) {
                result.put(o.getProduct(), 1);
            } else {
                result.put(o.getProduct(), result.get(o.getProduct()) + 1);
            }
        }
        return result;
    }
}
