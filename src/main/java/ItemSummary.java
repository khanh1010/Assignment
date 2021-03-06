
import helper.CsvHelper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ItemSummary {

    private static final String SALE_ITEM_DIR = "D:\\assignment\\input\\SalesItem.csv";
    private static final String SALE_LIST_DIR = "D:\\assignment\\input\\SalesList.csv";
    private static final String DISCOUNT_RATE_DIR = "D:\\assignment\\input\\Customer.csv";


    public static void main(String[] args) throws IOException, ParseException {

        List<Customer> customerList = new ArrayList<>();
        Map<String, Integer> itemSummary = new HashMap<>();
        List<SalesItem> items = new ArrayList<>();
        Map<String, Integer> sortedItemSummary = new HashMap<>();

        CSVParser rateParser = CsvHelper.getCSVParser(DISCOUNT_RATE_DIR);
        for (CSVRecord record : rateParser) {
            Customer discount = new Customer(record.get("会社名"), record.get("割引率"));
            customerList.add(discount);
        }

        //Read SaleItem

        CSVParser SaleItemParser = CsvHelper.getCSVParser(SALE_ITEM_DIR);
        for (CSVRecord record : SaleItemParser) {
            SalesItem salesItem = new SalesItem(record.get("商品コード"), record.get("商品名"), Integer.parseInt(record.get("単価")));
            items.add(salesItem);
        }

        //Read SaleList

        CSVParser SaleListParser = CsvHelper.getCSVParser(SALE_LIST_DIR);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
        for (CSVRecord record : SaleListParser) {
            Sales sales = new Sales(dateFormat.parse(record.get("販売日")), record.get("商品コード"), Integer.parseInt(record.get("数量")), record.get("販売先"));
            String itemCode = sales.getItemCode();
            String companyName = sales.getCustomer();
            Optional<SalesItem> matchedItem = items.stream().filter(a -> a.getItemCode().equalsIgnoreCase(itemCode)).findFirst();
            Optional<Customer> matchedCustomer = customerList.stream().filter(a -> a.getCompanyName().equalsIgnoreCase(companyName)).findFirst();
            if (matchedItem.isPresent()) {
                String item = matchedItem.get().getItemName();
                if (itemSummary.containsKey(item)) {
                    if (matchedCustomer.isPresent()) {
                        double rate = matchedCustomer.get().getDiscountRate();
                        double discountedPrice = (1 - rate) * (sales.getAmount() * matchedItem.get().getPrice());
                        int previousValue = (itemSummary.get(item));
                        int latestValue = (int) (previousValue + discountedPrice);
                        itemSummary.put(item, latestValue);

                    }
                } else {
                    if (matchedCustomer.isPresent()) {
                        double rate = matchedCustomer.get().getDiscountRate();
                        int discountedPrice = (int) ((1 - rate) * (sales.getAmount() * matchedItem.get().getPrice()));
                        itemSummary.put(item, discountedPrice);

                    }
                }

            }

        }
        sortedItemSummary = itemSummary.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        for (Map.Entry<String, Integer> entry :
                sortedItemSummary.entrySet()) {
            System.out.println(entry.getKey() + "の売上高合計は、" + entry.getValue() + "円です");
        }

    }

}
