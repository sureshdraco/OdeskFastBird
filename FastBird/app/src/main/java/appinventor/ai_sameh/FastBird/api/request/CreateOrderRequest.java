package appinventor.ai_sameh.FastBird.api.request;

import android.text.TextUtils;

/**
 * Created by suresh on 12/10/14.
 */
public class CreateOrderRequest extends LoginRequest {
    String pickupaddress, contactname, phone1, phone2, flatno, buildingno, blockno, road, location, note, packagetype, servicetype, weight, length, height, width, deliverytime, moneydeliverytype, collectionamount, paymentmethod;
    String pickupAddressLocationId;

    public CreateOrderRequest(String username, String password, String pickupaddress, String contactname, String phone1, String phone2, String flatno, String buildingno, String blockno, String road, String location, String note, String packagetype, String servicetype, String weight, String length, String height, String width, String deliverytime, String moneydeliverytype, String collectionamount, String paymentmethod, String pickupAddressLocationId) {
        super(username, password);
        this.pickupAddressLocationId = pickupAddressLocationId;
        this.pickupaddress = pickupaddress;
        this.contactname = contactname;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.flatno = flatno;
        this.buildingno = buildingno;
        this.blockno = blockno;
        this.road = road;
        this.location = location;
        this.note = note;
        this.packagetype = packagetype;
        this.servicetype = servicetype;
        this.weight = TextUtils.isEmpty(weight) ? "0" : weight;
        this.length = TextUtils.isEmpty(length) ? "0" : length;
        this.height = TextUtils.isEmpty(height) ? "0" : height;
        this.width = TextUtils.isEmpty(width) ? "0" : width;
        this.deliverytime = deliverytime;
        this.moneydeliverytype = moneydeliverytype;
        this.collectionamount = TextUtils.isEmpty(collectionamount) ? "0" : collectionamount;
        this.paymentmethod = paymentmethod;
    }

    public String getPickupAddressLocationId() {
        return pickupAddressLocationId;
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

    public String getFlatno() {
        return flatno;
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
        if (TextUtils.isEmpty(weight))
            return "0";
        return weight;
    }

    public String getLength() {
        if (TextUtils.isEmpty(length))
            return "0";
        return length;
    }

    public String getHeight() {
        if (TextUtils.isEmpty(height))
            return "0";
        return height;
    }

    public String getWidth() {
        if (TextUtils.isEmpty(width))
            return "0";
        return width;
    }

    public String getCollectionamount() {
        return collectionamount;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public void setCollectionamount(String collectionamount) {
        this.collectionamount = collectionamount;
    }
}