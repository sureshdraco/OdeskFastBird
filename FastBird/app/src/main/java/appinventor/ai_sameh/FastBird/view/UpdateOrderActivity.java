package appinventor.ai_sameh.FastBird.view;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import appinventor.ai_sameh.FastBird.R;

public class UpdateOrderActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_order);
		if (savedInstanceState == null) {
			Bundle b = new Bundle();
			b.putBoolean(CreateOrderFragment.UPDATE_ORDER, true);
			CreateOrderFragment createOrderFragment = new CreateOrderFragment();
			createOrderFragment.setArguments(b);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, createOrderFragment)
					.commit();
		}
	}
}
