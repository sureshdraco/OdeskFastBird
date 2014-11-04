package appinventor.ai_sameh.FastBird.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;
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

    public static void setupOrderTrackStatusDetailUi(Context context, Dialog dialog, OrderTrackHistoryResponse orderTrackHistoryResponse) {
        Resources resources = context.getResources();
        ((TextView) dialog.findViewById(R.id.currentStatus)).setText(resources.getString(R.string.current_status, orderTrackHistoryResponse.getData().getCurrentStatus()));
        StringBuilder orderHistory = new StringBuilder();
        for (TrackStatus trackStatus : orderTrackHistoryResponse.getData().getHistory()) {
            orderHistory.append(trackStatus.toString());
            orderHistory.append("\n");
        }
        ((TextView) dialog.findViewById(R.id.detailContent)).setText(orderHistory);
    }
}