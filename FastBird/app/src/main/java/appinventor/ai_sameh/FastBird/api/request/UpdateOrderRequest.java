package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class UpdateOrderRequest extends CreateOrderRequest {
	private String fbdnumber;

	public UpdateOrderRequest(String username, String password, String pickupaddress, String contactname, String phone1, String phone2, String flatNo, String buildingno,
			String blockno, String road, String location, String note, String packagetype, String servicetype, String weight, String length, String height, String width,
			String deliverytime, String moneydeliverytype, String collectionamount, String paymentmethod, String fbdnumber) {
		super(username, password, pickupaddress, contactname, phone1, phone2, flatNo, buildingno, blockno, road, location, note, packagetype, servicetype, weight, length, height,
				width, deliverytime, moneydeliverytype, collectionamount, paymentmethod);
		this.fbdnumber = fbdnumber;
	}
}
