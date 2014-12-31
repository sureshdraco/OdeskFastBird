package appinventor.ai_sameh.FastBird.model;

/**
 * Created by suresh.kumar on 2014-11-20.
 */
public class OpenOrder {
	public static final java.lang.String NOTIFICATION_EXTRA_PAGE_COMMENTS = "comments";
	public static final java.lang.String NOTIFICATION_EXTRA_PAGE_TRACK = "track";
	public static final java.lang.String NOTIFICATION_EXTRA_PAGE_INFO = "info";
	public static final java.lang.String NOTIFICATION_EXTRA_ORDER_SHIPMENTS = "approved";
	public static final java.lang.String NOTIFICATION_EXTRA_ORDER_PICKUP = "pending";
	public static final java.lang.String NOTIFICATION_EXTRA_ORDER_HISTORY = "closed";
	private String order, page, orderTab;

	public OpenOrder(String order, String page, String orderTab) {
		this.order = order;
		this.page = page;
		this.orderTab = orderTab;
	}

	public String getOrderTab() {
		return orderTab;
	}

	public String getOrder() {
		return order;
	}

	public String getPage() {
		return page;
	}

	@Override
	public String toString() {
		return "OpenOrder{" +
				"order='" + order + '\'' +
				", page='" + page + '\'' +
				", orderTab='" + orderTab + '\'' +
				'}';
	}
}
