package appinventor.ai_sameh.FastBird.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.util.OrderDialogUtil;
import appinventor.ai_sameh.FastBird.util.TimestampUtil;
import appinventor.ai_sameh.FastBird.view.WithFastBirdOrdersFragment;

public class OrderArrayAdapter extends ArrayAdapter<Order> {
	public static final int ORDER_INFO_DIALOG = 1;
	public static final int ORDER_TRACK_STATUS_DIALOG = 5;
	private final boolean enableEditBtn;
	private WithFastBirdOrdersFragment withFastBirdOrdersFragment;
	private boolean isDrafts;
	private List<Order> orderList = new ArrayList<Order>();
	private Context context;
	private int orderType;
	private ShapeDrawable footerBackground;

	static class OrderViewHolder {
		TextView orderNumber, orderTo, phone1, phone2, orderStatus, remainingDays;
		Button commentButton, trackButton, editButton, deleteButton;
		ImageView shareIcon;
		View orderStatusContainer;
	}

	public OrderArrayAdapter(Context context, int textViewResourceId, int orderType, boolean isDrafts, boolean enableEditBtn) {
		super(context, textViewResourceId);
		this.context = context;
		this.isDrafts = isDrafts;
		this.enableEditBtn = enableEditBtn;
		this.orderType = orderType;
	}

	public OrderArrayAdapter(Context context, int textViewResourceId, int orderType, boolean isDrafts, boolean enableEditBtn, WithFastBirdOrdersFragment withFastBirdOrdersFragment) {
		super(context, textViewResourceId);
		this.withFastBirdOrdersFragment = withFastBirdOrdersFragment;
		this.context = context;
		this.isDrafts = isDrafts;
		this.orderType = orderType;
		this.enableEditBtn = enableEditBtn;
	}

	@Override
	public void add(Order object) {
		orderList.add(object);
		super.add(object);
	}

	@Override
	public void clear() {
		orderList.clear();
		super.clear();
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
			viewHolder.remainingDays = (TextView) row.findViewById(R.id.daysRemaining);
			viewHolder.orderStatus = (TextView) row.findViewById(R.id.orderStatus);
			viewHolder.orderTo = (TextView) row.findViewById(R.id.orderTo);
			viewHolder.phone1 = (TextView) row.findViewById(R.id.phone1);
			viewHolder.phone2 = (TextView) row.findViewById(R.id.phone2);
			viewHolder.commentButton = (Button) row.findViewById(R.id.btnComment);
			viewHolder.trackButton = (Button) row.findViewById(R.id.btnTrackStatus);
			viewHolder.editButton = (Button) row.findViewById(R.id.btnEdit);
			viewHolder.deleteButton = (Button) row.findViewById(R.id.btnDelete);
			viewHolder.orderStatusContainer = row.findViewById(R.id.orderStatusContainer);
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
		viewHolder.commentButton.setText(context.getString(R.string.comments_btn) + " (" + order.getTotalUnreadComments() + ")");
		try {
			switch (orderType) {
			case WithFastBirdOrdersFragment.ORDER_TYPE_HISTORY:
				viewHolder.remainingDays.setVisibility(View.GONE);
				break;
			case WithFastBirdOrdersFragment.ORDER_TYPE_PICKUPS:
				viewHolder.remainingDays.setVisibility(View.GONE);
				break;
			case WithFastBirdOrdersFragment.ORDER_TYPE_SHIPMENTS:
				viewHolder.remainingDays.setVisibility(View.VISIBLE);
				viewHolder.remainingDays.setText(TimestampUtil.getDaysBetween(new Date(), TimestampUtil.getIso8601Date(order.getProgressStatusDate())));
				break;
			}
		} catch (Exception e) {
			viewHolder.remainingDays.setText("");
		}

		// viewHolder.remainingDays.setText(order.getDaysRemaining());

		String htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone1(), order.getDeliveryPhone1());
		viewHolder.phone1.setText(Html.fromHtml(htmlString));
		htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone2(), order.getDeliveryPhone2());
		viewHolder.phone2.setText(Html.fromHtml(htmlString));
		row.setOnClickListener(new InfoClickListener(order));
		viewHolder.commentButton.setOnClickListener(new CommentClickListener(order.getFBDNumber()));
		viewHolder.trackButton.setVisibility(isDrafts ? View.GONE : View.VISIBLE);
		viewHolder.editButton.setVisibility(enableEditBtn ? View.VISIBLE : View.GONE);
		viewHolder.deleteButton.setVisibility(enableEditBtn ? View.VISIBLE : View.GONE);
		viewHolder.trackButton.setOnClickListener(new TrackButtonClickListener(order));
		viewHolder.editButton.setOnClickListener(new EditButtonClickListener(order));
		viewHolder.deleteButton.setOnClickListener(new DeleteButtonClickListener(order, withFastBirdOrdersFragment));
		viewHolder.shareIcon.setOnClickListener(new ShareButtonClickListener(order));
		if (!TextUtils.isEmpty(order.getProgressColorCode())) {
			footerBackground = new ShapeDrawable();
			float[] radii = new float[8];
			radii[0] = 4;
			radii[1] = 4;
			radii[2] = 4;
			radii[3] = 4;
			footerBackground.setShape(new RoundRectShape(radii, null, null));
			footerBackground.getPaint().setColor(Color.parseColor(order.getProgressColorCode()));
			viewHolder.orderStatusContainer.setBackground(footerBackground);
		}
		return row;
	}

	class InfoClickListener implements View.OnClickListener {
		private final Order order;

		public InfoClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			OrderDialogUtil.openOrderInfo(getContext(), order);
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
			OrderDialogUtil.openOrderComments(getContext(), fbdNumber);
		}
	}

	class TrackButtonClickListener implements View.OnClickListener {
		private final Order order;

		public TrackButtonClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			OrderDialogUtil.openOrderTrack(getContext(), order);
		}
	}

	class EditButtonClickListener implements View.OnClickListener {
		private final Order order;

		public EditButtonClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			OrderDialogUtil.openOrderUpdate(getContext(), order);
		}
	}

	class DeleteButtonClickListener implements View.OnClickListener {
		private final Order order;
		private WithFastBirdOrdersFragment withFastBirdOrdersFragment;

		public DeleteButtonClickListener(Order order, WithFastBirdOrdersFragment withFastBirdOrdersFragment) {
			this.order = order;
			this.withFastBirdOrdersFragment = withFastBirdOrdersFragment;
		}

		@Override
		public void onClick(View v) {
			OrderDialogUtil.openDeleteConfirm((Activity) context, order, withFastBirdOrdersFragment);
		}
	}
}