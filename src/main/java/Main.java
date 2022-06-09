
import com.opencsv.CSVWriter;
import helper.CsvHelper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    private static final String SALE_ITEM_DIR = "D:\\assignment\\input\\SalesItem.csv";
    private static final String SALE_LIST_DIR = "D:\\assignment\\input\\SalesList.csv";
    private static final String OUTPUT_FILE_DIR = "D:\\assignment\\output\\OutPutFile.csv";

    public static Map<String, Integer> result = new LinkedHashMap<>();
    public static List<SalesItem> items = new ArrayList<>();


    public static void main(String[] args) throws IOException, ParseException {

        //Read SaleItem

        CSVParser SaleItemParser = CsvHelper.getCSVParser(SALE_ITEM_DIR);
        for (CSVRecord record : SaleItemParser) {

            SalesItem salesItem = new SalesItem(record.get("商品コード"), record.get("商品名"), Integer.parseInt(record.get("単価")));

            items.add(salesItem);

        }

        //Read Sale

        CSVParser SaleListParser = CsvHelper.getCSVParser(SALE_LIST_DIR);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);

        for (CSVRecord record : SaleListParser) {

            Sales sales = new Sales(dateFormat.parse(record.get("販売日")), record.get("商品コード"), Integer.parseInt(record.get("数量")), record.get("販売先"));

            Date saleDate = sales.getSaleDate();
            String itemCode = sales.getItemCode();

            if (!result.containsKey(dateFormat.format(saleDate))) {

                if (getDaysBetweenDates(getLastKey(result), dateFormat.format(saleDate)).size() >= 1) {

                    for (String date : getDaysBetweenDates(getLastKey(result), dateFormat.format(saleDate))) {
                        result.put(date, 0);
                    }
                }

                Optional<SalesItem> matchedItem = items.stream().filter(a -> a.getItemCode().equalsIgnoreCase(itemCode)).findFirst();

                if (matchedItem.isPresent()) {
                    int lastPrice = sales.getAmount() * matchedItem.get().getPrice();
                    result.put(dateFormat.format(saleDate), lastPrice);
                }

            } else {

                Optional<SalesItem> matchedItem = items.stream().filter(a -> a.getItemCode().equalsIgnoreCase(itemCode)).findFirst();
                if (matchedItem.isPresent()) {

                    int lastPrice = (sales.getAmount() * matchedItem.get().getPrice()) + (result.get(dateFormat.format(saleDate)));
                    result.put(dateFormat.format(saleDate), lastPrice);
                }

            }

        }
        //Write Output
        writeToOutput(result, OUTPUT_FILE_DIR);

    }

    private static void writeToOutput(Map<String, Integer> entries, String path) {
        File file = new File(path);

        BufferedWriter bf = null;

        try {

            bf = new BufferedWriter(new FileWriter(file));
            CSVWriter writer = new CSVWriter(bf);
            String[] header = {"販売日", "売上総額"};
            writer.writeNext(header);
            for (Map.Entry<String, Integer> entry :
                    entries.entrySet()) {

                // put key and value separated by a comma
                bf.write(entry.getKey() + ","
                        + entry.getValue());

                // new line
                bf.newLine();
            }

            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getLastKey(Map<String, Integer> result) {

        int count = 1;

        String date = null;
        for (Map.Entry<String, Integer> it :
                result.entrySet()) {

            if (count == result.size()) {
                date = it.getKey();
            }
            count++;
        }

        return date;
    }

    private static List<String> getDaysBetweenDates(String startDate, String endDate) throws ParseException {


        List<String> dates = new ArrayList<>();

        if (!(startDate == null)) {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
            Date sDate = sdf.parse(startDate);
            Date eDate = sdf.parse(endDate);


            calendar.setTime(sDate);
            calendar.add(Calendar.DATE, 1);


            while (calendar.getTime().before(eDate)) {
                Date result = calendar.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
                String date = dateFormat.format(result);
                dates.add(date);
                calendar.add(Calendar.DATE, 1);
            }


        }
        return dates;
    }


}


