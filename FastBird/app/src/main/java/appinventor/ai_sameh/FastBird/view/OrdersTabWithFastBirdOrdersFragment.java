package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.model.OpenOrder;

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
		setupTitleBar();
		initFragments();
		initView();
		handleNotificationIntent();
	}

	private void handleNotificationIntent() {
		if (PreferenceUtil.getOpenOrder(getActivity()) != null) {
			OpenOrder openOrder = PreferenceUtil.getOpenOrder(getActivity());
			if (openOrder.getOrderTab().equals(OpenOrder.NOTIFICATION_EXTRA_ORDER_SHIPMENTS)) {
				viewPager.setCurrentItem(0, true);
			} else if (openOrder.getOrderTab().equals(OpenOrder.NOTIFICATION_EXTRA_ORDER_PICKUP)) {
				viewPager.setCurrentItem(1, true);
			} else {
				viewPager.setCurrentItem(2, true);
			}
		}
	}

	private void setupTitleBar() {
		((TextView) getActivity().findViewById(R.id.viewTitle)).setText("ORDERS");
		getActivity().findViewById(R.id.balance).setVisibility(View.GONE);
		getActivity().findViewById(R.id.balanceProgress).setVisibility(View.GONE);
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
		b.putString("key", "With Me");
		withMeFragment.setArguments(b);
		fragments.add(withMeFragment);
		WithFastBirdOrdersFragment orderHistoryFragment = new WithFastBirdOrdersFragment();
		b = new Bundle();
		b.putString("key", "History");
		orderHistoryFragment.setArguments(b);
		fragments.add(orderHistoryFragment);
		fragmentTitle = new ArrayList<String>();
		fragmentTitle.add("Shipments");
		fragmentTitle.add("Pickup");
		fragmentTitle.add("History");
	}

	private void initView() {
		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		viewPagerAdapter = new CashPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(viewPagerAdapter);
		PagerTabStrip pagerTabStrip = (PagerTabStrip) getActivity().findViewById(R.id.pager_title_strip);
		pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.fast_bird_orange));
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