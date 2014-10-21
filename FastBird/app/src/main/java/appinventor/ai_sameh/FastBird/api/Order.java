package appinventor.ai_sameh.FastBird.api;

/**
 * Created by suresh on 21/10/14.
 */
public class Order {
    private String FBDNumber, ProgressStatus, phone1, phone2, orderTo, PickupAddressTitle, DeliveryAddressTitle, CollectionAmount, ServiceType;
    private String MoneyDelivered, NetTotal, MoneyDeliveryType;

    public String getFBDNumber() {
        return FBDNumber;
    }

    public String getProgressStatus() {
        return ProgressStatus;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getOrderTo() {
        return orderTo;
    }

    public String getPickupAddressTitle() {
        return PickupAddressTitle;
    }

    public String getDeliveryAddressTitle() {
        return DeliveryAddressTitle;
    }

    public String getCollectionAmount() {
        return CollectionAmount;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public String getMoneyDelivered() {
        return MoneyDelivered;
    }

    public String getNetTotal() {
        return NetTotal;
    }

    public String getMoneyDeliveryType() {
        return MoneyDeliveryType;
    }
}