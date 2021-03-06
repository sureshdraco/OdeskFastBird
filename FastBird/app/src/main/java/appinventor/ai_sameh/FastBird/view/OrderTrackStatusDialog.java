package appinventor.ai_sameh.FastBird.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.TrackStatusAdapter;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.model.TrackStatus;
import appinventor.ai_sameh.FastBird.api.response.OrderTrackHistoryResponse;

public class OrderTrackStatusDialog {

    public static CustomDialog showOrderTrackStatusDetail(final Context mContext) {

        final CustomDialog dialog = new CustomDialog(mContext);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_track_status_history);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        dialog.findViewById(R.id.closeBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static void setupOrderTrackStatusDetailUi(final Context context, Dialog dialog, final OrderTrackHistoryResponse orderTrackHistoryResponse) {
        final Resources resources = context.getResources();
        ((TextView) dialog.findViewById(R.id.currentStatus)).setText(resources.getString(R.string.current_status, orderTrackHistoryResponse.getData().getCurrentStatus()));
        ListView trackList = (ListView) dialog.findViewById(R.id.trackListView);
        TrackStatusAdapter trackAdapter = new TrackStatusAdapter(context, orderTrackHistoryResponse.getData().getHistory());
        StringBuilder orderHistory = new StringBuilder();
        for (TrackStatus trackStatus : orderTrackHistoryResponse.getData().getHistory()) {
            orderHistory.append(trackStatus.toString());
        }
        trackList.setAdapter(trackAdapter);

        final String shareText = resources.getString(R.string.current_status, orderTrackHistoryResponse.getData().getCurrentStatus()) + orderHistory;
        //((TextView) dialog.findViewById(R.id.detailContent)).setText(Html.fromHtml(orderHistory.toString()));
        (dialog.findViewById(R.id.shareButton)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
    }
}