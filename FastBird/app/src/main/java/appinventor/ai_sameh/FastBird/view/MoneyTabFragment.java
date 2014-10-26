package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appinventor.ai_sameh.FastBird.R;


public class MoneyTabFragment extends Fragment {
 
 
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
		b.putString("key", "In - progress");
		mTabHost.addTab(mTabHost.newTabSpec("In_progress").setIndicator("In - progress"),
				MoneyViewFragment.class, b);
        b = new Bundle();
        b.putString("key", "In the Way");
        mTabHost.addTab(mTabHost.newTabSpec("in_the_way")
                .setIndicator("In the Way"), MoneyViewFragment.class, b);
        b = new Bundle();
        b.putString("key", "History");
        mTabHost.addTab(mTabHost.newTabSpec("history")
                .setIndicator("History"), MoneyViewFragment.class, b);
        return mTabHost;
	} 
} 