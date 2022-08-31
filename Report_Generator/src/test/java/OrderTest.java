import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void test_getOrderValue() {
        //given
        Client c = new Client(1);
        Product p = new Product(1, new BigDecimal("1.00"));
        Order o = new Order(1, c, p);
        //when
        var actual = o.getOrderValue();
        var expected = new BigDecimal("1.00");
        //then
        assertEquals(expected, actual);
    }

    @Test
    void test_setOrderValue() {
        //given
        Client c = new Client(1);
        Product p = new Product(1, new BigDecimal("10.43"));
        Order o = new Order(1, c, p);

        //when
        o.setOrderValue(new BigDecimal("0.50"));
        var expected = o.getOrderValue();
        var actual = new BigDecimal("5.22");

        //then
        assertEquals(actual, expected);
    }
}