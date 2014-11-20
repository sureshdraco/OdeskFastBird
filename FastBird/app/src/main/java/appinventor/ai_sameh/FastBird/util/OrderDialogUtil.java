package appinventor.ai_sameh.FastBird.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.OrderArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.OrderTrackStatusRequest;
import appinventor.ai_sameh.FastBird.api.response.OrderTrackHistoryResponse;
import appinventor.ai_sameh.FastBird.view.ActivityProgressIndicator;
import appinventor.ai_sameh.FastBird.view.CommentActivity;
import appinventor.ai_sameh.FastBird.view.UpdateOrderActivity;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by suresh on 20/11/14.
 */
public class OrderDialogUtil {
    public static void openOrderUpdate(Context context, Order order) {
        PreferenceUtil.saveSelectedOrder(context, order);
        Intent intent = new Intent(context, UpdateOrderActivity.class);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    public static void openOrderInfo(Context context, Order order) {
        PreferenceUtil.saveSelectedOrder(context, order);
        ((Activity) context).showDialog(OrderArrayAdapter.ORDER_INFO_DIALOG);
    }

    public static void openOrderComments(Context context, String fbdNumber) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra("fbdNumber", fbdNumber);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

    }

    public static void openOrderTrack(final Context context, Order order) {
        ((Activity) context).showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        String username = PreferenceUtil.getEmail(context);
        String password = PreferenceUtil.getPassword(context);
        ApiRequests.getOrderTrackStatus(context, new OrderTrackStatusRequest(username, password, order.getFBDNumber()), new Response.Listener<OrderTrackHistoryResponse>() {
            @Override
            public void onResponse(OrderTrackHistoryResponse orderTrackHistoryResponse) {
                ((Activity) context).dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                PreferenceUtil.saveSelectedOrderTrackHistory(context, orderTrackHistoryResponse);
                ((Activity) context).showDialog(OrderArrayAdapter.ORDER_TRACK_STATUS_DIALOG);
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
