import helper.CsvHelper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesItem {

	private String itemCode;
	private String itemName;
	private int price;

	//コンストラクタ
	public SalesItem() throws IOException {
		super();

	}
	public List<SalesItem> readItem() throws IOException {

		List<SalesItem> items = new ArrayList<>();
		String InputFilesDirPath = "D:\\assignment\\input\\SalesItem.csv";

		CSVParser parser = CsvHelper.getCSVParser(InputFilesDirPath);
		for (CSVRecord record : parser) {
			SalesItem item = new SalesItem();

			String itemCode = record.get("商品コード");
			item.setItemCode(itemCode);

			String itemName = record.get("商品名");
			item.setItemName(itemName);

			int price = Integer.parseInt(record.get("単価"));
			item.setPrice(price);
			items.add(item);

		}
		return items;

	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
