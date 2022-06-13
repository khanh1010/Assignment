import java.util.Date;

public class Sales {

    //各フィールドは、private属性で内部保持したうえで、setter/getter を外部からのインターフェイスで用意しています。
    //（そうすることで、ゆくゆくクラス内部の処理が必要になったときにクラス内で完結して処理を実装できます。）

    private Date saleDate;
    private String itemCode;
    private int amount;
    private String customer;


    //コンストラクタ
    public Sales(Date saleDate, String itemCode, int amount, String customer) {
        super();
        setSaleDate(saleDate);
        setItemCode(itemCode);
        setAmount(amount);
        setCustomer(customer);
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


}
