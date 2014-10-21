package appinventor.ai_sameh.FastBird.view;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Window;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.util.DialogCancelledListener;

public class CustomDialog extends Dialog {
	private DialogCancelledListener mDialogCancelledListener = null;

	public CustomDialog(Context context) {
		super(context);

		Window window = this.getWindow();
		window.setBackgroundDrawableResource(R.drawable.background_dialog_window);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_SEARCH) {
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void setCancelable(boolean flag) {
		// If dialog cannot be cancelled, DialogCancelledListener should not be
		// called
		// when back key is pressed
		if (flag == false) {
			mDialogCancelledListener = null;
		}
		super.setCancelable(flag);
	}

	public void setDialogCancelledListener(DialogCancelledListener dlgClosedListener) {
		super.setCancelable(true);
		mDialogCancelledListener = dlgClosedListener;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mDialogCancelledListener != null) mDialogCancelledListener.onDialogCancelled();
	}
}
