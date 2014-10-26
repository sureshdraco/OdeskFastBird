package appinventor.ai_sameh.FastBird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;

public class CashInProgressArrayAdapter extends ArrayAdapter<MRBTransactions> {
    private List<MRBTransactions> transactionsArrayList = new ArrayList<MRBTransactions>();
    private Context context;

    static class CashViewHolder {
        TextView fbdNumber, collectionAmount, serviceType, netTotal;
    }

    public CashInProgressArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
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
            row = inflater.inflate(R.layout.cash_in_progress_card_item, parent, false);
            viewHolder = new CashViewHolder();
            viewHolder.fbdNumber = (TextView) row.findViewById(R.id.fbdNumber);
            viewHolder.collectionAmount = (TextView) row.findViewById(R.id.collectionAmount);
            viewHolder.netTotal = (TextView) row.findViewById(R.id.netTotal);
            viewHolder.serviceType = (TextView) row.findViewById(R.id.serviceType);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CashViewHolder) row.getTag();
        }
        MRBTransactions transaction = getItem(position);
//        viewHolder.fbdNumber.setText(getContext().getResources().getString(R.string.fbd_number, transaction.()));
//        viewHolder.id.setText(getContext().getResources().getString(R.string.id, transaction.getId()));
//        viewHolder.totalAmount.setText(getContext().getResources().getString(R.string.total_amount, transaction.getTotalAmounts()));
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
//            if(cashInWay) {
//                PreferenceUtil.saveSelectedCashInWay(context, transaction);
//                ((Activity) context).showDialog(DIALOG_CASH_IN_WAY);
//            } else {
//                PreferenceUtil.saveSelectedCashHistory(context, transaction);
//                ((Activity) context).showDialog(DIALOG_CASH_HISTORY);
//            }
        }
    }
}