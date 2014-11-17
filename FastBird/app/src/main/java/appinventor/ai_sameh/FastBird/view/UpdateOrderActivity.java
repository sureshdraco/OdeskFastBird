package appinventor.ai_sameh.FastBird.view;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import appinventor.ai_sameh.FastBird.R;

public class UpdateOrderActivity extends ActionBarActivity {

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
