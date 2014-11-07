package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.R;

public class MoneyTabFragment extends Fragment {
	private ViewPager viewPager;
	private OrderPagerAdapter viewPagerAdapter;
	private ArrayList<Fragment> fragments;
	private ArrayList<String> fragmentTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.view_pager, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initFragments();
		initView();
	}

	private void initFragments() {
		fragments = new ArrayList<Fragment>();
		MoneyViewFragment cashInProgressFragment = new MoneyViewFragment();
		Bundle b = new Bundle();
		b.putString("key", "In - progress");
		cashInProgressFragment.setArguments(b);
		fragments.add(cashInProgressFragment);
		MoneyViewFragment cashInTheWayFragment = new MoneyViewFragment();
		b = new Bundle();
		b.putString("key", "In the Way");
		cashInTheWayFragment.setArguments(b);
		fragments.add(cashInTheWayFragment);
		MoneyViewFragment cashHistoryFragment = new MoneyViewFragment();
		b = new Bundle();
		b.putString("key", "History");
		cashHistoryFragment.setArguments(b);
		fragments.add(cashHistoryFragment);
		fragmentTitle = new ArrayList<String>();
		fragmentTitle.add("In - progress");
		fragmentTitle.add("In the Way");
		fragmentTitle.add("History");
	}

	private void initView() {
		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		viewPagerAdapter = new OrderPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(viewPagerAdapter);
	}

	class OrderPagerAdapter extends FragmentPagerAdapter {
		public OrderPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			return fragments.get(i);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragmentTitle.get(position);
		}
	}
}