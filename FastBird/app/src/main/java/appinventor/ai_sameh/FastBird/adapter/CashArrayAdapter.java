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

public class CashArrayAdapter extends ArrayAdapter<MRBTransactions> {
    public static final int DIALOG_CASH_IN_WAY = 2;
    public static final int DIALOG_CASH_HISTORY = 3;
    private final boolean cashInWay;
    private List<MRBTransactions> transactionsArrayList = new ArrayList<MRBTransactions>();
    private Context context;

    static class CashViewHolder {
        TextView date, id, totalAmount;
    }

    public CashArrayAdapter(Context context, int textViewResourceId, boolean cashInWay) {
        super(context, textViewResourceId);
        this.context = context;
        this.cashInWay = cashInWay;
    }

    @Override
    public void add(MRBTransactions transaction) {
        transactionsArrayList.add(transaction);
        super.add(transaction);
    }

    @Override
    public int getCount() {
        return this.transactionsArrayList.size();
    }

    @Override
    public MRBTransactions getItem(int index) {
        return this.transactionsArrayList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CashViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.cash_card_item, parent, false);
            viewHolder = new CashViewHolder();
            viewHolder.id = (TextView) row.findViewById(R.id.id);
            viewHolder.totalAmount = (TextView) row.findViewById(R.id.totalAmounts);
            viewHolder.date = (TextView) row.findViewById(R.id.date);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CashViewHolder) row.getTag();
        }
        MRBTransactions transaction = getItem(position);
        viewHolder.date.setText(getContext().getResources().getString(R.string.date, transaction.getDate()));
        viewHolder.id.setText(getContext().getResources().getString(R.string.id, transaction.getId()));
        viewHolder.totalAmount.setText(getContext().getResources().getString(R.string.total_amount, transaction.getTotalAmounts()));
        row.setOnClickListener(new InfoClickListener(transaction));
        return row;
    }


    class InfoClickListener implements View.OnClickListener {
        private final MRBTransactions transaction;

        public InfoClickListener(MRBTransactions transaction) {
            this.transaction = transaction;
        }

        @Override
        public void onClick(View v) {
            if(cashInWay) {
                PreferenceUtil.saveSelectedCashInWay(context, transaction);
                ((Activity) context).showDialog(DIALOG_CASH_IN_WAY);
            } else {
                PreferenceUtil.saveSelectedCashHistory(context, transaction);
                ((Activity) context).showDialog(DIALOG_CASH_HISTORY);
            }
        }
    }
}