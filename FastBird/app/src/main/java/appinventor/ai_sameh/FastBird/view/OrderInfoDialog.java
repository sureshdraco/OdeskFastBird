package appinventor.ai_sameh.FastBird.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import appinventor.ai_sameh.FastBird.R;

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
}
