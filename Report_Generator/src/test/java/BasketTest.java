import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    private final BigDecimal firstProductPrice = new BigDecimal("0.50");
    private final BigDecimal secondProductPrice = new BigDecimal("9.23");
    private final Product firstProduct = new Product(1, firstProductPrice);
    private final Product secondProduct = new Product(2, secondProductPrice);
    private final Client firstClient = new Client(1);
    private final Client secondClient = new Client(2);

    private final BigDecimal discountFiftyPercent = new BigDecimal("0.50");

    private final BigDecimal discountZeroPercent = new BigDecimal("0.00");

    @Test
    void test_getTotalValueOfAllOrdersBeforeDiscount() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order firstOrder = new Order(1, firstClient, firstProduct);
        Order secondOrder = new Order(2, firstClient, secondProduct);
        Collections.addAll(orders, firstOrder, secondOrder);
        Basket b = new Basket(orders);

        //when
        var actualValueOfAllOrders = b.getTotalValueOfAllOrdersBeforeDiscount();
        var expectedValueOfAllOrders = firstProductPrice
                .add(secondProductPrice);

        //then
        assertEquals(expectedValueOfAllOrders, actualValueOfAllOrders);
    }

    @Test
    void test_getTotalValueOfAllOrdersAfterDiscount() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order firstOrder = new Order(1, firstClient, firstProduct);
        Order secondOrder = new Order(1, firstClient, secondProduct);
        Collections.addAll(orders, firstOrder, secondOrder);
        orders.forEach(order -> order.setOrderValue(discountFiftyPercent));
        Basket b = new Basket(orders);

        //when
        BigDecimal expectedValue = firstProductPrice.multiply(discountFiftyPercent).setScale(2, RoundingMode.CEILING)
                .add(secondProductPrice.multiply(discountFiftyPercent).setScale(2, RoundingMode.CEILING));
        BigDecimal actualValue = b.getTotalValueOfAllOrdersAfterDiscount();

        //then
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void test_getIdOfClients() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(new Order(1, firstClient, firstProduct));
        Basket b = new Basket(orders);

        //when
        var actualClients = b.getIdOfClients();
        var expectedClients = new HashSet<>();
        expectedClients.add(1L);

        //then
        assertAll(
                () -> assertEquals(expectedClients.size(), actualClients.size()),
                () -> assertEquals(expectedClients, actualClients)
        );

    }

    @Test
    public void test_getNumberOfItemsBoughtByClient() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order firstOrder = new Order(1, firstClient, firstProduct);
        Order secondOrder = new Order(2, firstClient, firstProduct);
        Collections.addAll(orders, firstOrder, secondOrder);
        Basket b = new Basket(orders);

        //when
        var expectedNumberOfItems = new HashMap<>();
        expectedNumberOfItems.put(1L, new BigDecimal("2"));
        var actualNumberOfItems = b.getNumberOfProductsBoughtByClient(firstClient);

        //then
        assertEquals(expectedNumberOfItems, actualNumberOfItems);
    }

    @Test
    public void test_getTotalSumOfDiscountForClient() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order o = new Order(1, firstClient, firstProduct);
        o.setOrderValue(discountFiftyPercent);
        orders.add(o);
        Basket b = new Basket(orders);

        //when
        BigDecimal actualSumOfDiscount = b.getTotalSumOfDiscountForClient(firstClient);
        BigDecimal expectedSumOfDiscount = firstProductPrice
                .multiply(discountFiftyPercent)
                .setScale(2, RoundingMode.DOWN);

        //then
        assertEquals(expectedSumOfDiscount, actualSumOfDiscount);
    }


    @Test
    public void test_getValueOfClientAccountWithClient() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order o = new Order(1, firstClient, firstProduct);
        o.setOrderValue(discountFiftyPercent);
        orders.add(o);
        Basket b = new Basket(orders);

        //when
        BigDecimal actualValue = b.getValueOfClientAccount(firstClient);
        BigDecimal expectedValue = firstProductPrice
                .multiply(discountFiftyPercent)
                .setScale(2, RoundingMode.CEILING);
        //then
        assertEquals(expectedValue, actualValue);
    }


    @Test
    public void test_areTotalDiscountsAndExpensesEqual() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order firstOrder = new Order(1, firstClient, firstProduct);
        Order secondOrder = new Order(2, secondClient, firstProduct);
        Collections.addAll(orders, firstOrder, secondOrder);
        for (Order order : orders) {
            order.setOrderValue(discountFiftyPercent);
        }
        Basket b = new Basket(orders);

        //when
        HashMap<Client, BigDecimal> discountsOfAllClients = b.getDiscountForAllClients();

        BigDecimal sumOfDiscountsOfAllClients = discountsOfAllClients.
                values().stream().reduce(new BigDecimal("0.00"), BigDecimal::add);

        Set<Client> clients = b.getClients();

        BigDecimal sumOfExpensesOfAllClients = clients.stream()
                .map(client -> b.getValuesOfOrderedProducts().get(client))
                .reduce(new BigDecimal("0.00"), BigDecimal::add);

        BigDecimal sumOfDiscountsAndExpenses = sumOfDiscountsOfAllClients.add(sumOfExpensesOfAllClients);

        BigDecimal sumOfProductPrices = b.getTotalValueOfAllOrdersBeforeDiscount();
        //then

        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN), sumOfDiscountsAndExpenses.subtract(sumOfProductPrices));

    }

    @Test
    public void test_getValuesOfOrderedProducts() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order firstOrder = new Order(1, firstClient, firstProduct);
        Order secondOrder = new Order(2, secondClient, secondProduct);
        Collections.addAll(orders, firstOrder, secondOrder);
        for (Order order : orders) {
            order.setOrderValue(discountZeroPercent);
        }
        Basket b = new Basket(orders);

        //when
        BigDecimal expectedValues = firstProductPrice.add(secondProductPrice);
        HashMap<Client, BigDecimal> valuesOfOrderedProducts = b.getValuesOfOrderedProducts();
        var actualValues = valuesOfOrderedProducts
                .values().stream().reduce(new BigDecimal("0.00"), BigDecimal::add);

        //then
        assertEquals(expectedValues, actualValues);
    }

    @Test
    public void test_getQuantityOfProductsSold() {
        //given
        ArrayList<Order> orders = new ArrayList<>();
        Order first_order = new Order(1, firstClient, firstProduct);
        Order second_order = new Order(2, firstClient, secondProduct);
        Collections.addAll(orders, first_order, second_order);
        Basket b = new Basket(orders);

        //when
        HashMap<Product, Integer> actualProducts = b.getQuantityOfProductsSold();
        HashMap<Product, Integer> expectedProducts = new HashMap<>();
        expectedProducts.put(firstProduct, 1);
        expectedProducts.put(secondProduct, 1);

        //then
        assertEquals(expectedProducts, actualProducts);
    }

}