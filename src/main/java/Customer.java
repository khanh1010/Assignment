public class Customer {

    public String companyName;
    public Double discountRate;

    //コンストラクタ
    public Customer(String companyName, String discountRate) {
        super();

        setCompanyName(companyName);
        setDiscountRate(discountRate);

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = Double.valueOf(discountRate);
    }
}
