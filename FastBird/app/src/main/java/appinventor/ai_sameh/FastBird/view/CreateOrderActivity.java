package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import appinventor.ai_sameh.FastBird.R;

public class CreateOrderActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_order);
		if (savedInstanceState == null) {
			CreateOrderFragment createOrderFragment = new CreateOrderFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, createOrderFragment)
					.commit();
		}
	}
}
