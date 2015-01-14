package appinventor.ai_sameh.FastBird.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.util.DecimalUtil;
import appinventor.ai_sameh.FastBird.util.TimestampUtil;

public class CashInProgressArrayAdapter extends ArrayAdapter<Order> {
	public static final int DIALOG_CASH_IN_PROGRESS = 4;
	private List<Order> orderArrayList = new ArrayList<Order>();
	private Context context;

	static class CashViewHolder {
		TextView name, phone, fbdNumber, collectionAmount, serviceType, netTotal, totalBd, payment, collectionBd;
		TextView remainingDays;
	}

	public CashInProgressArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
	}

	@Override
	public void add(Order order) {
		orderArrayList.add(order);
		super.add(order);
	}

	@Override
	public void clear() {
		orderArrayList.clear();
		super.clear();
	}

	@Override
	public int getCount() {
		return this.orderArrayList.size();
	}

	@Override
	public Order getItem(int index) {
		return this.orderArrayList.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		CashViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.cash_in_progress_card_item, parent, false);
			viewHolder = new CashViewHolder();
			viewHolder.name = (TextView) row.findViewById(R.id.name);
			viewHolder.phone = (TextView) row.findViewById(R.id.phone);
			viewHolder.remainingDays = (TextView) row.findViewById(R.id.daysRemaining);
			viewHolder.phone.setMovementMethod(LinkMovementMethod.getInstance());
			viewHolder.fbdNumber = (TextView) row.findViewById(R.id.fbdnumber);
			viewHolder.collectionAmount = (TextView) row.findViewById(R.id.collectionAmount);
			viewHolder.netTotal = (TextView) row.findViewById(R.id.netTotal);
			viewHolder.serviceType = (TextView) row.findViewById(R.id.serviceType);
			viewHolder.totalBd = (TextView) row.findViewById(R.id.total);
			viewHolder.payment = (TextView) row.findViewById(R.id.payment);
			viewHolder.collectionBd = (TextView) row.findViewById(R.id.collectionBd);
			row.setTag(viewHolder);
		} else {
			viewHolder = (CashViewHolder) row.getTag();
		}
		Order order = getItem(position);
		viewHolder.fbdNumber.setText(order.getFBDNumber());
		viewHolder.name.setText(order.getDeliveryAddressTitle());
		String htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone1(), order.getDeliveryPhone1());
		viewHolder.phone.setText(Html.fromHtml(htmlString));
		viewHolder.collectionAmount.setText(DecimalUtil.formatDecimal(order.getCollectionAmount()));
		viewHolder.netTotal.setText(DecimalUtil.formatDecimal(order.getNetTotal()));
		viewHolder.serviceType.setText(order.getServiceType());
		try {
			viewHolder.remainingDays.setText(TimestampUtil.getDaysBetween(new Date(), TimestampUtil.getIso8601Date(order.getProgressStatusDate())));
		} catch (ParseException e) {
			viewHolder.remainingDays.setText("");
		}
		if (!TextUtils.isEmpty(order.getPaymentMehod()) && order.getPaymentMehod().equals("0")) {
			viewHolder.collectionBd.setText(context.getResources().getString(R.string.total_amount_bd));
			viewHolder.payment.setText("Credit");
			viewHolder.totalBd.setText(context.getResources().getString(R.string.total_bd, DecimalUtil.formatDecimal(order.getCollectionAmount())));
		} else {
			viewHolder.collectionBd.setText(context.getResources().getString(R.string.collection_amount_bd));
			viewHolder.payment.setText("Cash On Delivery");
			try {
				Float totalBd = Float.parseFloat(order.getCollectionAmount()) - Float.parseFloat(order.getNetTotal());
				viewHolder.totalBd.setText(context.getResources().getString(R.string.total_bd, DecimalUtil.formatDecimal(String.valueOf(totalBd))));
			} catch (Exception ex) {
				viewHolder.totalBd.setText("");
			}
		}
		row.setOnClickListener(new InfoClickListener(order));
		return row;
	}

	class InfoClickListener implements View.OnClickListener {
		private final Order order;

		public InfoClickListener(Order order) {
			this.order = order;
		}

		@Override
		public void onClick(View v) {
			PreferenceUtil.saveSelectedCashInProgress(context, order);
			((Activity) context).showDialog(DIALOG_CASH_IN_PROGRESS);
		}
	}
}