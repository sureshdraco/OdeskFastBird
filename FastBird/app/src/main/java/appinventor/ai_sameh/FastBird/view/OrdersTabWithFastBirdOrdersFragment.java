package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.R;

public class OrdersTabWithFastBirdOrdersFragment extends Fragment {

	private ViewPager viewPager;
	private CashPagerAdapter viewPagerAdapter;
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
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initFragments();
		initView();
	}

	private void initFragments() {
		fragments = new ArrayList<Fragment>();
		WithFastBirdOrdersFragment withFastBirdOrdersFragment = new WithFastBirdOrdersFragment();
		Bundle b = new Bundle();
		b.putString("key", "With Fast Bird");
		withFastBirdOrdersFragment.setArguments(b);
		fragments.add(withFastBirdOrdersFragment);
		WithFastBirdOrdersFragment withMeFragment = new WithFastBirdOrdersFragment();
		b = new Bundle();
		b.putString("key", "Draft");
		withMeFragment.setArguments(b);
		fragments.add(withMeFragment);
		WithFastBirdOrdersFragment orderHistoryFragment = new WithFastBirdOrdersFragment();
		b = new Bundle();
		b.putString("key", "History");
		orderHistoryFragment.setArguments(b);
		fragments.add(orderHistoryFragment);
		fragmentTitle = new ArrayList<String>();
		fragmentTitle.add("With Fast Bird");
		fragmentTitle.add("Draft");
		fragmentTitle.add("History");
	}

	private void initView() {
		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		viewPagerAdapter = new CashPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(viewPagerAdapter);
	}

	class CashPagerAdapter extends FragmentPagerAdapter {
		public CashPagerAdapter(FragmentManager fm) {
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