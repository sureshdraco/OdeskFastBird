package appinventor.ai_sameh.FastBird.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.OrderArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.request.OrderTrackStatusRequest;
import appinventor.ai_sameh.FastBird.api.response.LoginResponse;
import appinventor.ai_sameh.FastBird.api.response.OrderTrackHistoryResponse;
import appinventor.ai_sameh.FastBird.view.ActivityProgressIndicator;
import appinventor.ai_sameh.FastBird.view.CommentActivity;
import appinventor.ai_sameh.FastBird.view.UpdateOrderActivity;
import appinventor.ai_sameh.FastBird.view.WithFastBirdOrdersFragment;
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

    public static void openDeleteConfirm(final Activity activity, final Order order, final WithFastBirdOrdersFragment withFastBirdOrdersFragment) {
        LayoutInflater li = LayoutInflater.from(activity);
        View promptsView = li.inflate(R.layout.delete_order_confirm_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                activity.showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                                final String email = PreferenceUtil.getEmail(activity);
                                final String password = PreferenceUtil.getPassword(activity);
                                ApiRequests.deleteOrder(activity, new OrderTrackStatusRequest(email, password, order.getFBDNumber()), new Response.Listener<LoginResponse>() {
                                    @Override
                                    public void onResponse(LoginResponse response) {
                                        activity.dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                                        if (response.getData().getError() != null) {
                                            Crouton.showText(activity, response.getData().getError(), Style.ALERT);
                                            return;
                                        }
                                        Crouton.showText(activity, "Order Removed!", Style.INFO);
                                        dialog.dismiss();
                                        withFastBirdOrdersFragment.setupList(email, password);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        activity.dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                                        Crouton.showText(activity, activity.getString(R.string.no_internet), Style.ALERT);
                                    }
                                });
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
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
