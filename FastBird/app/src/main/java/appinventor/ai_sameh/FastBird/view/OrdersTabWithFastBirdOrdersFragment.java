package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appinventor.ai_sameh.FastBird.R;


public class OrdersTabWithFastBirdOrdersFragment extends Fragment {
 
 
	private FragmentTabHost mTabHost;
 
 
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	} 
 
 
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
 
 
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(),
				R.id.menu_settings);

		Bundle b = new Bundle();
		b.putString("key", "With Fast Bird");
		mTabHost.addTab(mTabHost.newTabSpec("with_fast_bird").setIndicator("With Fast Bird"),
				WithFastBirdOrdersFragment.class, b);
		// 
		b = new Bundle();
		b.putString("key", "With Me");
		mTabHost.addTab(mTabHost.newTabSpec("with_me")
				.setIndicator("With Me"), WithFastBirdOrdersFragment.class, b);
		return mTabHost;
	} 
} 