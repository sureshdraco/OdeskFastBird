using FastBirdAPIObjects;
public class CreateOrderResult
{
    public OrderCreationData d {get;set;}
}

public class ListServiceTypesResult
{
    public ServiceTypeData d { get; set; }
}

public class GetServiceTypePriceResult
{
    public ServiceTypePriceData d { get; set; }
}

public class ListPackageTypesResult
{
    public PackageTypeData d { get; set; }
}

public class ListLocationsResult
{
    public LocationData d { get; set; }
}

public class GetOrderStatusResult
{
    public OrderStatusData d { get; set; }
}


public class GetOrderApprovalStatusResult
{
    public OrderApprovalStatusData d { get; set; }
}


public class ListDeliveryTimesResult
{
    public DeliveryTimeData d { get; set; }
}

public class ListMoneyDeliveryTypesResult
{
    public MoneyDeliveryTypeData d { get; set; }
}

public class ListPickupAddressesResult
{
    public AddressListData d { get; set; }
}
public class GetClientCreditResult
{
    public CreditData d { get; set; }
}

public class GetLocationByBlockNoResult
{
    public LocationData d { get; set; }
}

public class ListOrdersResult
{
    public OrderListData d { get; set; }
}