package appinventor.ai_sameh.FastBird.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.util.TimestampUtil;

public class OrderInfoDialog {

    public static CustomDialog showOrderDetail(final Context mContext) {

        final CustomDialog dialog = new CustomDialog(mContext);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_detail);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        dialog.findViewById(R.id.closeBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static void setupOrderDetailUi(Context context, Dialog dialog, Order order) {
        Resources resources = context.getResources();
        ((TextView) dialog.findViewById(R.id.collectionAmount)).setText(resources.getString(R.string.collection_amount, order.getCollectionAmount()));
        ((TextView) dialog.findViewById(R.id.DeliveryAddressTitle)).setText(resources.getString(R.string.deliverAddressTitle, order.getDeliveryAddressTitle()));
        String htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone1(), order.getDeliveryPhone1());

        ((TextView) dialog.findViewById(R.id.DeliveryPhone1)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) dialog.findViewById(R.id.DeliveryPhone1)).setText(Html.fromHtml(htmlString));
        htmlString = String.format("<a href='tel:%s'>%s</a>", order.getDeliveryPhone2(), order.getDeliveryPhone2());

        ((TextView) dialog.findViewById(R.id.DeliveryPhone2)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) dialog.findViewById(R.id.DeliveryPhone2)).setText(Html.fromHtml(htmlString));
        ((TextView) dialog.findViewById(R.id.fastPayCode)).setText(resources.getString(R.string.fast_pay_code, order.getFastPayCode()));
        if (!TextUtils.isEmpty(order.getProgressColorCode())) {
            dialog.findViewById(R.id.orderStatusContainer).setBackgroundColor(Color.parseColor(order.getProgressColorCode()));
        }
        ((TextView) dialog.findViewById(R.id.paymentResult)).setText(resources.getString(R.string.paymentResult, order.getFastPayStatus()));
        ((TextView) dialog.findViewById(R.id.FBDNumber)).setText(resources.getString(R.string.fbd_number, order.getFBDNumber()));
        ((TextView) dialog.findViewById(R.id.MoneyDelivered)).setText(resources.getString(R.string.moneyDelivered, order.getMoneyDelivered()));
        ((TextView) dialog.findViewById(R.id.MoneyDeliveryType)).setText(resources.getString(R.string.moneyDeliveredType, order.getMoneyDeliveryType()));
        ((TextView) dialog.findViewById(R.id.NetTotal)).setText(resources.getString(R.string.withdraw_money_net_total, order.getNetTotal()));
        ((TextView) dialog.findViewById(R.id.PickupAddressTitle)).setText(resources.getString(R.string.pickupAddressTitle, order.getPickupAddressTitle()));
        ((TextView) dialog.findViewById(R.id.ProgressStatus)).setText(resources.getString(R.string.progressStatus, order.getProgressStatus()));
        ((TextView) dialog.findViewById(R.id.ServiceType)).setText(resources.getString(R.string.service_type, order.getServiceType()));
        ((TextView) dialog.findViewById(R.id.approvalDate)).setText(resources.getString(R.string.approval_date, TimestampUtil.getFastBirdDateString(order.getApprovalDate())));
        ((TextView) dialog.findViewById(R.id.DeliveryLocation)).setText(resources.getString(R.string.deliveryLocation, order.getDeliveryLocation()));
        ((TextView) dialog.findViewById(R.id.Weight)).setText(resources.getString(R.string.weight, order.getWeight()));
        ((TextView) dialog.findViewById(R.id.ProgressStatusDate)).setText(resources.getString(R.string.progressStatusDate, TimestampUtil.getFastBirdDateString(order.getProgressStatusDate())));
        ((TextView) dialog.findViewById(R.id.OrderDate)).setText(resources.getString(R.string.orderDate, TimestampUtil.getFastBirdDateString(order.getOrderDate())));
        ((TextView) dialog.findViewById(R.id.DeliveryBlockNo)).setText(resources.getString(R.string.deliverBlockNo, order.getDeliveryBlockNo()));
        ((TextView) dialog.findViewById(R.id.DeliveryBuildingNo)).setText(resources.getString(R.string.deliverBuildingNo, order.getDeliveryBuildingNo()));
        ((TextView) dialog.findViewById(R.id.DeliveryNotes)).setText(resources.getString(R.string.deliveryNotes, order.getDeliveryNotes()));
        ((TextView) dialog.findViewById(R.id.DeliveryFlatNo)).setText(resources.getString(R.string.deliveryFlatNo, order.getDeliveryFlatNo()));
        ((TextView) dialog.findViewById(R.id.DeliveryRoad)).setText(resources.getString(R.string.deliveryRoad, order.getDeliveryRoad()));
        ((TextView) dialog.findViewById(R.id.EPaymentDate)).setText(resources.getString(R.string.paymentDate, TimestampUtil.getFastBirdDateString(order.getEPaymentDate())));
        ((TextView) dialog.findViewById(R.id.Size)).setText(resources.getString(R.string.size, order.getSize()));
    }
}