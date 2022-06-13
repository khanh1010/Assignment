import org.apache.commons.csv.CSVRecord;

import java.util.Objects;

public class Customer {

    public String companyName;
    public String discountRate;

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

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }
}
