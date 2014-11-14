package appinventor.ai_sameh.FastBird.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.MoneyDetail;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.view.CommentActivity;

public class CashDetailListArrayAdapter extends ArrayAdapter<MoneyDetail> {
	private List<MoneyDetail> moneyDetails = new ArrayList<MoneyDetail>();
	private Context context;

	static class OrderViewHolder {
		TextView fbdNumber, amount, collectionAmount, serviceFee;
	}

	public CashDetailListArrayAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
	}

	@Override
	public void add(MoneyDetail object) {
		moneyDetails.add(object);
		super.add(object);
	}

	@Override
	public int getCount() {
		return this.moneyDetails.size();
	}

	@Override
	public MoneyDetail getItem(int index) {
		return this.moneyDetails.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		OrderViewHolder viewHolder;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.cash_card_detail_item, parent, false);
			viewHolder = new OrderViewHolder();
			viewHolder.fbdNumber = (TextView) row.findViewById(R.id.fbdNumber);
			viewHolder.amount = (TextView) row.findViewById(R.id.amount);
			viewHolder.collectionAmount = (TextView) row.findViewById(R.id.collectionAmount);
			viewHolder.serviceFee = (TextView) row.findViewById(R.id.serviceFee);
			row.setTag(viewHolder);
		} else {
			viewHolder = (OrderViewHolder) row.getTag();
		}
		MoneyDetail moneyDetail = getItem(position);
		viewHolder.fbdNumber.setText(getContext().getResources().getString(R.string.fbd_number, moneyDetail.getFBDNumber()));
		viewHolder.amount.setText(getContext().getResources().getString(R.string.total_amount, moneyDetail.getAmount()));
		viewHolder.collectionAmount.setText(getContext().getResources().getString(R.string.collectionAmount, moneyDetail.getMoneyCollectionAmount()));
		viewHolder.serviceFee.setText(getContext().getResources().getString(R.string.serviceFee, moneyDetail.getNetTotal()));
		return row;
	}
}