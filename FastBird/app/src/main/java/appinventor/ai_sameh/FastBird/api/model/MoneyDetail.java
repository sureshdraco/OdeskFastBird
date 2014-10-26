package appinventor.ai_sameh.FastBird.api.model;

/**
 * Created by suresh on 22/10/14.
 */
public class MoneyDetail {
    private String Amount, FBDNumber, MoneyCollectionAmount, NetTotal, OrderId;

    public String getAmount() {
        return Amount;
    }

    public String getFBDNumber() {
        return FBDNumber;
    }

    public String getMoneyCollectionAmount() {
        return MoneyCollectionAmount;
    }

    public String getNetTotal() {
        return NetTotal;
    }

    public String getOrderId() {
        return OrderId;
    }

    @Override
    public String toString() {
        return "MoneyDetail{" +
                "Amount='" + Amount + '\'' +
                ", FBDNumber='" + FBDNumber + '\'' +
                ", MoneyCollectionAmount='" + MoneyCollectionAmount + '\'' +
                ", NetTotal='" + NetTotal + '\'' +
                ", OrderId='" + OrderId + '\'' +
                '}';
    }
}
