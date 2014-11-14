package appinventor.ai_sameh.FastBird.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.w3c.dom.Text;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.FastBirdApplication;
import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.response.GetClientMoneyResponse;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MoneyTabFragment extends Fragment {
	private ViewPager viewPager;
	private OrderPagerAdapter viewPagerAdapter;
	private ArrayList<Fragment> fragments;
	private ArrayList<String> fragmentTitle;
	private TextView balanceTextView;

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
		setupTitleBar();
		initFragments();
		initView();
	}

	private void setupTitleBar() {
		((TextView) getActivity().findViewById(R.id.viewTitle)).setText("MONEY");
		setupBalance();
		balanceTextView = (TextView) getActivity().findViewById(R.id.balance);
		if (PreferenceUtil.getClientMoney(getActivity()).equals("-")) {
			getActivity().findViewById(R.id.balanceProgress).setVisibility(View.VISIBLE);
			balanceTextView.setVisibility(View.GONE);
		} else {
			getActivity().findViewById(R.id.balanceProgress).setVisibility(View.GONE);
			balanceTextView.setText(getActivity().getResources().getString(R.string.money, PreferenceUtil.getClientMoney(getActivity())));
			balanceTextView.setVisibility(View.VISIBLE);
		}
	}

	private void setupBalance() {
		String email = PreferenceUtil.getEmail(getActivity());
		String password = PreferenceUtil.getPassword(getActivity());
		ApiRequests.getClientMoney(getActivity(), new LoginRequest(email, password), new Response.Listener<GetClientMoneyResponse>() {
			@Override
			public void onResponse(GetClientMoneyResponse getClientMoneyResponse) {
				if (getClientMoneyResponse.getData().getError() == null) {
					PreferenceUtil.saveClientMoney(FastBirdApplication.appContext, getClientMoneyResponse.getData().getMRBTotal());
					if (isAdded()) {
						balanceTextView.setText(getActivity().getResources().getString(R.string.money, PreferenceUtil.getClientMoney(getActivity())));
						balanceTextView.setVisibility(View.VISIBLE);
						getActivity().findViewById(R.id.balanceProgress).setVisibility(View.GONE);
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
			}
		});
	}

	private void initFragments() {
		fragments = new ArrayList<Fragment>();
		MoneyViewFragment cashInProgressFragment = new MoneyViewFragment();
		Bundle b = new Bundle();
		b.putString("key", "In - Progress");
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
		fragmentTitle.add("Fast Bird");
		fragmentTitle.add("To Bank");
		fragmentTitle.add("History");
	}

	private void initView() {
		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		viewPagerAdapter = new OrderPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(viewPagerAdapter);
		PagerTabStrip pagerTabStrip = (PagerTabStrip) getActivity().findViewById(R.id.pager_title_strip);
		pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.fast_bird_orange));
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