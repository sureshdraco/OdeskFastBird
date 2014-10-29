package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class CreateOrderRequest extends LoginRequest {
    String pickupaddress, contactname, phone1, phone2, flatNo, buildingno, blockno, road, location, note, packagetype, servicetype, weight, length, height, width, deliverytime, moneydeliverytype, collectionamount, paymentmethod;

    public CreateOrderRequest(String username, String password, String pickupaddress, String contactname, String phone1, String phone2, String flatNo, String buildingno, String blockno, String road, String location, String note, String packagetype, String servicetype, String weight, String length, String height, String width, String deliverytime, String moneydeliverytype, String collectionamount, String paymentmethod) {
        super(username, password);
        this.pickupaddress = pickupaddress;
        this.contactname = contactname;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.flatNo = flatNo;
        this.buildingno = buildingno;
        this.blockno = blockno;
        this.road = road;
        this.location = location;
        this.note = note;
        this.packagetype = packagetype;
        this.servicetype = servicetype;
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.width = width;
        this.deliverytime = deliverytime;
        this.moneydeliverytype = moneydeliverytype;
        this.collectionamount = collectionamount;
        this.paymentmethod = paymentmethod;
    }

    public String getServicetype() {
        return servicetype;
    }

    public String getMoneydeliverytype() {
        return moneydeliverytype;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public String getBlockno() {
        return blockno;
    }

    public String getPickupaddress() {
        return pickupaddress;
    }

    public String getContactname() {
        return contactname;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public String getBuildingno() {
        return buildingno;
    }

    public String getRoad() {
        return road;
    }

    public String getLocation() {
        return location;
    }

    public String getNote() {
        return note;
    }

    public String getPackagetype() {
        return packagetype;
    }

    public String getWeight() {
        return weight;
    }

    public String getLength() {
        return length;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getCollectionamount() {
        return collectionamount;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }
}