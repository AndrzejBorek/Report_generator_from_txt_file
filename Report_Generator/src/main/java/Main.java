import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        DataImporter dataImporter = new DataImporter();
        ArrayList<Order> list_orders_after = dataImporter.getListOfOrdersAfterDiscount();
        Basket basket_after = new Basket(list_orders_after);
        DataExporter.generateReportProducts(basket_after.getQuantityOfProductsSold());
        DataExporter.generateReportClientsPayment(basket_after.getValuesOfOrderedProducts());
        DataExporter.generateReportDiscounts(basket_after.getDiscountForAllClients());
        DataExporter.generateReportClientsProducts(basket_after.getNumberOfProductsBought());

    }
}
