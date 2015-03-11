package appinventor.ai_sameh.FastBird.api.response;

import appinventor.ai_sameh.FastBird.api.model.Order;

/**
 * Created by suresh on 12/10/14.
 */
public class GetOrderResponse {
	private d d;

	public d getData() {
		return d;
	}

	public class d {
		private String Error;
		private String FBDNumber, DeliveryPhone1, DeliveryPhone2, DeliveryContactName, PickupAddressTitle, DeliveryAddressTitle, CollectionAmount;
		private String ApprovalDate, DeliveryLocation, DeliveryNotes, DeliveryRoad, FastPayCode, FastPayStatus, MoneyDelivered, EPaymentDate,
				MoneyDeliveryType, NetTotal, OrderDate, ProgressStatus, ProgressStatusDate, ServiceType, Size, Height, Weight, Length, Width, ReferenceNo, DeliveryBlockNo,
				DeliveryBuildingNo,
				DeliveryFlatNo, ProgressColorCode, PaymentMehod;

		public Order getOrder() {
			return new Order(FBDNumber, DeliveryPhone1, DeliveryPhone2, DeliveryContactName, PickupAddressTitle, DeliveryAddressTitle,
					CollectionAmount, ApprovalDate, DeliveryLocation, DeliveryNotes, DeliveryRoad, FastPayCode, FastPayStatus, MoneyDelivered, EPaymentDate,
					MoneyDeliveryType, NetTotal, OrderDate, ProgressStatus, ProgressStatusDate, ServiceType, Size, Height, Weight, Length, Width, ReferenceNo, DeliveryBlockNo,
					DeliveryBuildingNo,
					DeliveryFlatNo, ProgressColorCode, PaymentMehod);
		}

		public String getError() {
			return Error;
		}
	}
}
