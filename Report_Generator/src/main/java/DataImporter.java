import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;


public class DataImporter {

    public String getContentOfFile(String path) {
        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public ArrayList<String> getListOfObjectsArgumentsFromContent(String path) {
        String content = this.getContentOfFile(path);
        String[] arr = content.split(" ");
        return new ArrayList<>(Arrays.asList(arr));
    }

    public ArrayList<Order> getListOfOrdersAfterDiscount() {
        try {
            String ORDERS_PATH = "Orders.txt";
            ArrayList<String> list = this.getListOfObjectsArgumentsFromContent(ORDERS_PATH);
            ArrayList<Order> result = new ArrayList<>();
            for (int i = 0; i < list.size() - 1; i += 5) {
                long order_id = Long.parseLong(list.get(i));
                long client_id = Long.parseLong(list.get(i + 1));
                long product_id = Long.parseLong(list.get(i + 2));
                BigDecimal product_price = new BigDecimal(list.get(i + 3));
                Client client = new Client(client_id);
                Product p = new Product(product_id, product_price);
                Order o = new Order(order_id, client, p);
                o.setOrderValue(new BigDecimal(list.get(i + 4)));
                result.add(o);
            }
            return result;
        } catch (NumberFormatException e) {
            throw new Error(e);
        }
    }
}
