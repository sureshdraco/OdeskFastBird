package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class CreateOrderRequest extends LoginRequest {
    String pickupAddress, contactName, phone1, phone2, flatNo, buildingNo, blockNo, road, location, note, packageType, serviceType, weight, length, height, width, deliveryTime, moneyDeliveryType, collectionAmount, paymentMethod;

    public CreateOrderRequest(String username, String password, String pickupAddress, String contactName, String phone1, String phone2, String flatNo, String buildingNo, String blockNo, String road, String location, String note, String packageType, String serviceType, String weight, String length, String height, String width, String deliveryTime, String moneyDeliveryType, String collectionAmount, String paymentMethod) {
        super(username, password);
        this.pickupAddress = pickupAddress;
        this.contactName = contactName;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.flatNo = flatNo;
        this.buildingNo = buildingNo;
        this.blockNo = blockNo;
        this.road = road;
        this.location = location;
        this.note = note;
        this.packageType = packageType;
        this.serviceType = serviceType;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.width = width;
        this.deliveryTime = deliveryTime;
        this.moneyDeliveryType = moneyDeliveryType;
        this.collectionAmount = collectionAmount;
        this.paymentMethod = paymentMethod;
    }
}
