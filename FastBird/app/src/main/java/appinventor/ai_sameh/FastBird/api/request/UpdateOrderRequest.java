package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class UpdateOrderRequest extends CreateOrderRequest {
	private String fbdnumber;

	public UpdateOrderRequest(String username, String password, String pickupaddress, String contactname, String phone1, String phone2, String flatNo, String buildingno,
			String blockno, String road, String location, String note, String packagetype, String weight, String length, String height, String width,
			String deliverytime, String moneydeliverytype, String collectionamount, String paymentmethod, String fbdnumber, String pickupAddressLocationId) {
		super(username, password, pickupaddress, contactname, phone1, phone2, flatNo, buildingno, blockno, road, location, note, packagetype, weight, length, height,
				width, deliverytime, moneydeliverytype, collectionamount, paymentmethod, pickupAddressLocationId);
		this.fbdnumber = fbdnumber;
	}
}
