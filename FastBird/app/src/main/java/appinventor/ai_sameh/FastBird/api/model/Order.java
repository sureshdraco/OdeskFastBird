package appinventor.ai_sameh.FastBird.api.model;

import java.util.Date;

import appinventor.ai_sameh.FastBird.util.TimestampUtil;

/**
 * Created by suresh on 21/10/14.
 */
public class Order {
	private String FBDNumber, DeliveryPhone1, DeliveryPhone2, DeliveryContactName, PickupAddressTitle, DeliveryAddressTitle, CollectionAmount;
	private String ApprovalDate, DeliveryLocation, DeliveryNotes, DeliveryRoad, FastPayCode, FastPayStatus, MoneyDelivered, EPaymentDate,
			MoneyDeliveryType, NetTotal, OrderDate, ProgressStatus, ProgressStatusDate, ServiceType, Size, Height, Weight, Length, Width, ReferenceNo, DeliveryBlockNo,
			DeliveryBuildingNo,
			DeliveryFlatNo, ProgressColorCode, PaymentMehod;

    public String getPaymentMethod() {
        return PaymentMehod;
    }

    public String getFBDNumber() {
		return FBDNumber == null ? "" : FBDNumber;
	}

	public String getProgressStatus() {
		return ProgressStatus == null ? "" : ProgressStatus;
	}

	public String getDeliveryPhone1() {
		return DeliveryPhone1 == null ? "" : DeliveryPhone1;
	}

	public String getDeliveryPhone2() {
		return DeliveryPhone2 == null ? "" : DeliveryPhone2;
	}

	public String getDeliveryContactName() {
		return DeliveryContactName == null ? "" : DeliveryContactName;
	}

	public String getPickupAddressTitle() {
		return PickupAddressTitle == null ? "" : PickupAddressTitle;
	}

	public String getDeliveryAddressTitle() {
		return DeliveryAddressTitle == null ? "" : DeliveryAddressTitle;
	}

	public String getCollectionAmount() {
		return CollectionAmount == null ? "" : CollectionAmount;
	}

	public String getServiceType() {
		return ServiceType == null ? "" : ServiceType;
	}

	public String getMoneyDelivered() {
		return MoneyDelivered == null ? "" : MoneyDelivered;
	}

	public String getNetTotal() {
		return NetTotal == null ? "" : NetTotal;
	}

	public String getMoneyDeliveryType() {
		return MoneyDeliveryType == null ? "" : MoneyDeliveryType;
	}

	public String getApprovalDate() {
		return ApprovalDate == null ? "" : ApprovalDate;
	}

	public String getDeliveryLocation() {
		return DeliveryLocation == null ? "" : DeliveryLocation;
	}

	public String getDeliveryNotes() {
		return DeliveryNotes == null ? "" : DeliveryNotes;
	}

	public String getDeliveryRoad() {
		return DeliveryRoad == null ? "" : DeliveryRoad;
	}

	public String getFastPayCode() {
		return FastPayCode == null ? "" : FastPayCode;
	}

	public String getFastPayStatus() {
		return FastPayStatus == null ? "" : FastPayStatus;
	}

	public String getOrderDate() {
		return OrderDate == null ? "" : OrderDate;
	}

	public String getProgressStatusDate() {
		return ProgressStatusDate == null ? "" : ProgressStatusDate;
	}

	public String getSize() {
		return Size == null ? "" : Size;
	}

	public String getWeight() {
		return Weight == null ? "" : Weight;
	}

	public String getReferenceNo() {
		return ReferenceNo == null ? "" : ReferenceNo;
	}

	public String getDeliveryBlockNo() {
		return DeliveryBlockNo == null ? "" : DeliveryBlockNo;
	}

	public String getDeliveryBuildingNo() {
		return DeliveryBuildingNo == null ? "" : DeliveryBuildingNo;
	}

	public String getDeliveryFlatNo() {
		return DeliveryFlatNo == null ? "" : DeliveryFlatNo;
	}

	public String getEPaymentDate() {
		return EPaymentDate == null ? "" : EPaymentDate;
	}

	public String getLength() {
		return Length;
	}

	public String getWidth() {
		return Width;
	}

	public String getHeight() {
		return Height;
	}

	public String getProgressColorCode() {
		return ProgressColorCode;
	}

    public String getPaymentMehod() {
        return PaymentMehod;
    }
}