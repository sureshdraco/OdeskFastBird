package appinventor.ai_sameh.FastBird.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.Order;
import appinventor.ai_sameh.FastBird.model.Card;

public class OrderArrayAdapter extends ArrayAdapter<Order> {
    private static final String TAG = "CardArrayAdapter";
    private List<Order> orderList = new ArrayList<Order>();

    static class OrderViewHolder {
        TextView orderNumber, orderTo, phone1, phone2, orderStatus;
    }

    public OrderArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
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
            row.setTag(viewHolder);
        } else {
            viewHolder = (OrderViewHolder)row.getTag();
        }
        Order order = getItem(position);
        viewHolder.orderNumber.setText(getContext().getResources().getString(R.string.order_number, order.getFBDNumber()));
        viewHolder.orderStatus.setText(order.getProgressStatus());
        viewHolder.orderTo.setText(getContext().getResources().getString(R.string.order_to, order.getOrderTo()));
        viewHolder.phone1.setText(getContext().getResources().getString(R.string.phone1, order.getPhone1()));
        viewHolder.phone2.setText(getContext().getResources().getString(R.string.phone1, order.getPhone2()));
        return row;

    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}