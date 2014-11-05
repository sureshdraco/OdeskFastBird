package appinventor.ai_sameh.FastBird.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.OrderTrackStatusRequest;
import appinventor.ai_sameh.FastBird.api.response.OrderTrackHistoryResponse;
import appinventor.ai_sameh.FastBird.view.ActivityProgressIndicator;
import appinventor.ai_sameh.FastBird.view.CommentActivity;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class OrderArrayAdapter extends ArrayAdapter<Order> {
	public static final int ORDER_INFO_DIALOG = 1;
	public static final int ORDER_TRACK_STATUS_DIALOG = 5;
	private boolean isFastBird;
	private List<Order> orderList = new ArrayList<Order>();
	private Context context;

	static class OrderViewHolder {
		TextView orderNumber, orderTo, phone1, phone2, orderStatus;
		Button infoButton, commentButton, trackButton;
		ImageView shareIcon;
	}

	public OrderArrayAdapter(Context context, int textViewResourceId, boolean isFastBird) {
		super(context, textViewResourceId);
		this.context = context;
		this.isFastBird = isFastBird;
	}

	@Override
	public void add(Order object) {
		orderList.add(object);
		super.add(object);
	}

	@Override
	public int getCount() {
		return this.orderList.size();
	}

	@Override
	public Order getItem(int index) {
		return this.orderList.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		OrderViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.orders_card_item, parent, false);
			viewHolder = new OrderViewHolder();
			viewHolder.orderNumber = (TextView) row.findViewById(R.id.orderNumber);
			viewHolder.orderStatus = (TextView) row.findViewById(R.id.orderStatus);
			viewHolder.orderTo = (TextView) row.findViewById(R.id.orderTo);
			viewHolder.phone1 = (TextView) row.findViewById(R.id.phone1);
			viewHolder.phone2 = (TextView) row.findViewById(R.id.phone2);
			viewHolder.infoButton = (Button) row.findViewById(R.id.btnInfo);
			viewHolder.commentButton = (Button) row.findViewById(R.id.btnComment);
			viewHolder.trackButton = (Button) row.findViewById(R.id.btnTrackStatus);
			viewHolder.shareIcon = (ImageView) row.findViewById(R.id.share);
			viewHolder.phone1.setMovementMethod(LinkMovementMethod.getInstance());
			viewHolder.phone2.setMovementMethod(LinkMovementMethod.getInstance());
			row.setTag(viewHolder);
		} else {
			viewHolder = (OrderViewHolder) row.getTag();
		}
		Order order = getItem(position);
		viewHolder.orderNumber.setText(getContext().getResources().getString(R.string.order_number, order.getFBDNumber()));
		viewHolder.orderStatus.setText(order.getProgressStatus());
		viewHolder.orderTo.setText(getContext().getResources().getString(R.string.order_to, order.getDeliveryAddressTitle()));

		String htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone1(), order.getDeliveryPhone1());
		viewHolder.phone1.setText(Html.fromHtml(htmlString));

		htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone2(), order.getDeliveryPhone2());
		viewHolder.phone2.setText(Html.fromHtml(htmlString));

		viewHolder.infoButton.setOnClickListener(new InfoClickListener(order));
		viewHolder.commentButton.setOnClickListener(new CommentClickListener(order.getFBDNumber()));
		viewHolder.trackButton.setVisibility(isFastBird ? View.VISIBLE : View.GONE);
		viewHolder.trackButton.setOnClickListener(new TrackButtonClickListener(order));
		viewHolder.shareIcon.setOnClickListener(new ShareButtonClickListener(order));
		return row;
	}

	class InfoClickListener implements View.OnClickListener {
		private final Order order;

		public InfoClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			PreferenceUtil.saveSelectedOrder(context, order);
			((Activity) context).showDialog(ORDER_INFO_DIALOG);
		}
	}

	class ShareButtonClickListener implements View.OnClickListener {
		private final Order order;

		public ShareButtonClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, getTextToShare(order));
			sendIntent.setType("text/plain");
			context.startActivity(sendIntent);
		}
	}

	private String getTextToShare(Order order) {
		StringBuilder stringBuilder = new StringBuilder(getContext().getResources().getString(R.string.order_number, order.getFBDNumber()));
		stringBuilder.append("\n");
		stringBuilder.append(getContext().getResources().getString(R.string.order_to, order.getDeliveryAddressTitle()));
		stringBuilder.append("\n");
		stringBuilder.append(context.getResources().getString(R.string.phone1, order.getDeliveryPhone1()));
		stringBuilder.append(order.getDeliveryPhone1());
		stringBuilder.append("\n");
		return "";
	}

	class CommentClickListener implements View.OnClickListener {
		private final String fbdNumber;

		public CommentClickListener(String fbdNumber) {
			this.fbdNumber = fbdNumber;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, CommentActivity.class);
			intent.putExtra("fbdNumber", fbdNumber);
			context.startActivity(intent);
			((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

		}
	}

	class TrackButtonClickListener implements View.OnClickListener {
		private final Order order;

		public TrackButtonClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			((Activity) context).showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
			String username = PreferenceUtil.getEmail(context);
			String password = PreferenceUtil.getPassword(context);
			ApiRequests.getOrderTrackStatus(context, new OrderTrackStatusRequest(username, password, order.getFBDNumber()), new Response.Listener<OrderTrackHistoryResponse>() {
				@Override
				public void onResponse(OrderTrackHistoryResponse orderTrackHistoryResponse) {
					((Activity) context).dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					PreferenceUtil.saveSelectedOrderTrackHistory(context, orderTrackHistoryResponse);
					((Activity) context).showDialog(ORDER_TRACK_STATUS_DIALOG);
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError volleyError) {
					((Activity) context).dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					Crouton.showText((Activity) context, "Failed to get track status!", Style.ALERT);
				}
			});
		}
	}
}