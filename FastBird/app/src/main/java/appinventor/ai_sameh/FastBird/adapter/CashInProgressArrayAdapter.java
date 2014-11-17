package appinventor.ai_sameh.FastBird.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import appinventor.ai_sameh.FastBird.api.model.Order;

public class CashInProgressArrayAdapter extends ArrayAdapter<Order> {
    public static final int DIALOG_CASH_IN_PROGRESS = 4;
    private List<Order> orderArrayList = new ArrayList<Order>();
    private Context context;

    static class CashViewHolder {
        TextView name, phone, fbdNumber, collectionAmount, serviceType, netTotal;
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
            viewHolder.fbdNumber = (TextView) row.findViewById(R.id.fbdNumber);
            viewHolder.collectionAmount = (TextView) row.findViewById(R.id.collectionAmount);
            viewHolder.netTotal = (TextView) row.findViewById(R.id.netTotal);
            viewHolder.serviceType = (TextView) row.findViewById(R.id.serviceType);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CashViewHolder) row.getTag();
        }
        Order order = getItem(position);
        viewHolder.fbdNumber.setText(order.getFBDNumber());
        viewHolder.name.setText(order.getDeliveryAddressTitle());
        viewHolder.phone.setText(order.getDeliveryPhone1());
        viewHolder.collectionAmount.setText(order.getCollectionAmount());
        viewHolder.netTotal.setText(order.getNetTotal());
        viewHolder.serviceType.setText(order.getServiceType());
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