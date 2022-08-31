import java.math.BigDecimal;
import java.math.RoundingMode;


public class Order {

    private final long id;
    private final Client client;
    private final Product product;
    private BigDecimal orderValue;

    public Order(long id, Client client, Product product) {
        this.id = id;
        this.client = client;
        this.product = product;
        this.orderValue = this.product.getPrice();
    }

    public Client getClient() {
        return client;
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    //setOrderValue sets order value of order, taking into account discount.
    public void setOrderValue(BigDecimal discount) {
        this.orderValue = (
                this.product.getPrice().
                        subtract(this.product.getPrice().multiply(discount)
                                .setScale(3, RoundingMode.CEILING))).setScale(2, RoundingMode.CEILING);
    }

    public BigDecimal getOrderValue() {
        return orderValue;
    }

    @Override
    public String toString() {
        return "Order id: " + this.id + ", Client id: " + this.client.getId() + ", Product Id: " + this.product.getId() +
                ", Order value: " + this.orderValue;
    }
}
