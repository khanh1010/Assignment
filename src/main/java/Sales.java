import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.opencsv.CSVWriter;
import helper.CsvHelper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class Sales {

    //各フィールドは、private属性で内部保持したうえで、setter/getter を外部からのインターフェイスで用意しています。
    //（そうすることで、ゆくゆくクラス内部の処理が必要になったときにクラス内で完結して処理を実装できます。）

    private String saleDate;
    private String itemCode;
    private int amount;
    private String customer;

    Map<String, Integer> result = new LinkedHashMap<>();


    //コンストラクタ
    public Sales() throws IOException, ParseException {
        super();


        String InputFilesDirPath = "D:\\assignment\\input\\SalesList.csv";
        String OutFilesDirPath = "D:\\assignment\\input\\OutPut.csv";
        CSVParser parser = CsvHelper.getCSVParser(InputFilesDirPath);

        for (CSVRecord record : parser) {

            String saleDate = record.get("販売日");

            setSaleDate(saleDate);

            String itemCode = record.get("商品コード");
            setItemCode(itemCode);

            int Amount = Integer.parseInt(record.get("数量"));
            setAmount(Amount);

            String Customer = record.get("販売先");
            setCustomer(Customer);


            if (!result.containsKey(getSaleDate())) {

                if (getDaysBetweenDates(getLastKey(result), getSaleDate()).size() >= 1) {

                    for (String date : getDaysBetweenDates(getLastKey(result), getSaleDate())) {
                        result.put(date, 0);
                    }
                }
                SalesItem item = new SalesItem();
                List<SalesItem> i = item.readItem();
                Optional<SalesItem> matchedItem = i.stream().filter(a -> a.getItemCode().equalsIgnoreCase(getItemCode())).findFirst();

                if (matchedItem.isPresent()) {
                    int lastPrice = getAmount() * matchedItem.get().getPrice();
                    result.put(getSaleDate(), lastPrice);
                }

            } else {

                SalesItem item = new SalesItem();
                List<SalesItem> i = item.readItem();
                Optional<SalesItem> matchedItem = i.stream().filter(a -> a.getItemCode().equalsIgnoreCase(getItemCode())).findFirst();
                if (matchedItem.isPresent()) {

                    int lastPrice = (getAmount() * matchedItem.get().getPrice()) + (result.get(getSaleDate()));
                    result.put(getSaleDate(), lastPrice);
                }

            }

        }

        //Write Output
        writeToOutput(result, OutFilesDirPath);

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

    private static List<String> getDaysBetweenDates(String startdate, String enddate) throws ParseException {

        List<String> dates = new ArrayList<>();

        if (!(startdate == null)) {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
            Date sDate = sdf.parse(startdate);
            Date eDate = sdf.parse(enddate);


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

    private String getSaleDate() {
        return saleDate;
    }

    private void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getItemCode() {

        return itemCode;
    }

    private void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    private int getAmount() {
        return amount;
    }

    private void setAmount(int amount) {
        this.amount = amount;
    }

    private String getCustomer(String record) {
        return customer;
    }

    private void setCustomer(String customer) {
        this.customer = customer;
    }

}
