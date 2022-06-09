

public class SalesItem {

    private String itemCode;
    private String itemName;
    private int price;

    //コンストラクタ
    public SalesItem(String itemCode, String itemName, int price) {
        super();
        setItemCode(itemCode);
        setItemName(itemName);
        setPrice(price);
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
