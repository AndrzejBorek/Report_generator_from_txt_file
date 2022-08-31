import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class DataExporter {

    private static final String CLIENTS_PRODUCTS_PATH = "reports/reportsClientProducts/";
    private static final String CLIENTS_PATH = "reports/reportsClients/";
    private static final String CLIENTS_DISCOUNTS_PATH = "reports/reportsClientsDiscounts/";
    private static final String PRODUCTS_PATH = "reports/reportsProducts/";

    public static void generateReportDiscounts(HashMap<Client, BigDecimal> clients) {

        String fileName = CLIENTS_DISCOUNTS_PATH + "ClientsDiscountsReport_" + LocalDate.now();
        try (FileWriter fw = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fw)) {
            for (Client c : clients.keySet()) {
                bw.write(c.toString());
                bw.write(", Total discount: " + clients.get(c));
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateReportClientsPayment(HashMap<Client, BigDecimal> clients) throws IOException {
        String fileName = CLIENTS_PATH + "ClientsReport_" + LocalDate.now();
        try (FileWriter fw = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fw)) {
            for (Client c : clients.keySet()) {
                bw.write(c.toString());
                bw.write(", amount to pay: " + clients.get(c));
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateReportClientsProducts(HashMap<Client, HashMap<Long, BigDecimal>> values) {
        String fileName = CLIENTS_PRODUCTS_PATH + "ClientsProductReport_" + LocalDate.now();
        try (FileWriter fw = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fw)) {
            for (Client c : values.keySet()) {
                bw.write(c.toString());
                bw.write(", Products bought: " + values.get(c));
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateReportProducts(HashMap<Product, Integer> products) {
        String fileName = PRODUCTS_PATH + "ProductsReport_" + LocalDate.now();
        try (FileWriter fw = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fw)) {
            for (Product p : products.keySet()) {
                bw.write(p.toString());
                bw.write(", Quantity of item sold: " + products.get(p));
                bw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


