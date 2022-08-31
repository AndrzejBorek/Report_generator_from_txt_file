import java.math.BigDecimal;
import java.math.RoundingMode;


public class Product {

    private final long id;
    private BigDecimal price;

    public Product(long id) {
        this.id = id;
    }

    public Product(long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.CEILING);
    }

    public String toString() {
        return "Product id: " + this.getId() + ", Product price: " + this.getPrice();
    }

    @Override
    public boolean equals(Object p) {
        if (p == this) {
            return true;
        }
        if (!(p instanceof final Product other)) {
            return false;
        }
        return id == (other.getId());
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }
}
