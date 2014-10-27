package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.CashArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.CashInProgressArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.response.CashOnProgressResponse;
import appinventor.ai_sameh.FastBird.api.response.CashOnTheWayListResponse;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class MoneyViewFragment extends Fragment {

    private TextView text;
    private ListView ordersListView;
    private CashArrayAdapter cashArrayAdapter;
    private CashInProgressArrayAdapter cashInProgressArrayAdapter;


    public MoneyViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ordersListView = (ListView) getActivity().findViewById(R.id.order_listView);

        String email = PreferenceUtil.getEmail(getActivity());
        String password = PreferenceUtil.getPassword(getActivity());
        if (getArguments() != null) {
            String tab = getArguments().getString("key");
            if (tab.equals("In - progress")) {
                cashInProgressArrayAdapter = new CashInProgressArrayAdapter(getActivity(), R.layout.cash_card_item);
                ordersListView.setAdapter(cashInProgressArrayAdapter);
                getCashInProgress(email, password);
            } else if (tab.equals("In the Way")) {
                cashArrayAdapter = new CashArrayAdapter(getActivity(), R.layout.cash_card_item, true);
                ordersListView.setAdapter(cashArrayAdapter);
                getCashOnMyWay(email, password);
            } else {
                cashArrayAdapter = new CashArrayAdapter(getActivity(), R.layout.cash_card_item, false);
                ordersListView.setAdapter(cashArrayAdapter);
                getCashHistory(email, password);
            }
        }
    }

    private void getCashOnMyWay(String email, String password) {
        final String cachedOrders = PreferenceUtil.getCashOnMyWayList(getActivity());
        Type listType = new TypeToken<ArrayList<MRBTransactions>>() {
        }.getType();
        ArrayList<MRBTransactions> cachedOrderList = new Gson().fromJson(cachedOrders, listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<MRBTransactions>();
        }
        for (MRBTransactions transaction : cachedOrderList) {
            cashArrayAdapter.add(transaction);
        }
        cashArrayAdapter.notifyDataSetChanged();
        ApiRequests.getCashOnTheWay(getActivity(), new LoginRequest(email, password), new Response.Listener<CashOnTheWayListResponse>() {
            @Override
            public void onResponse(CashOnTheWayListResponse cashOnTheWayListResponse) {
                if (getActivity() != null) {
                    String networkOrders = new Gson().toJson(cashOnTheWayListResponse.getData().getMRBTransactions());
                    if (!networkOrders.equals(cachedOrders)) {
                        PreferenceUtil.saveCashOnMyWayList(getActivity(), networkOrders);
                        cashArrayAdapter.clear();
                        for (MRBTransactions transactions : cashOnTheWayListResponse.getData().getMRBTransactions()) {
                            cashArrayAdapter.add(transactions);
                        }
                        cashArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null)
                    Crouton.showText(getActivity(), "Failed to get list", Style.ALERT);
            }
        });
    }


    private void getCashHistory(String email, String password) {
        final String cashHistory = PreferenceUtil.getCashHistory(getActivity());
        Type listType = new TypeToken<ArrayList<MRBTransactions>>() {
        }.getType();
        ArrayList<MRBTransactions> cachedOrderList = new Gson().fromJson(cashHistory, listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<MRBTransactions>();
        }
        for (MRBTransactions transaction : cachedOrderList) {
            cashArrayAdapter.add(transaction);
        }
        cashArrayAdapter.notifyDataSetChanged();
        ApiRequests.getCashHistory(getActivity(), new LoginRequest(email, password), new Response.Listener<CashOnTheWayListResponse>() {
            @Override
            public void onResponse(CashOnTheWayListResponse cashOnTheWayListResponse) {
                if (getActivity() != null) {
                    String networkOrders = new Gson().toJson(cashOnTheWayListResponse.getData().getMRBTransactions());
                    if (!networkOrders.equals(cashHistory)) {
                        PreferenceUtil.saveCashHistory(getActivity(), networkOrders);
                        cashArrayAdapter.clear();
                        for (MRBTransactions transactions : cashOnTheWayListResponse.getData().getMRBTransactions()) {
                            cashArrayAdapter.add(transactions);
                        }
                        cashArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null)
                    Crouton.showText(getActivity(), "Failed to get list", Style.ALERT);
            }
        });
    }


    private void getCashInProgress(String email, String password) {

        for (Order order : PreferenceUtil.getProgressOrders(getActivity())) {
            cashInProgressArrayAdapter.add(order);
        }
        cashInProgressArrayAdapter.notifyDataSetChanged();
        ApiRequests.getMoneyInProgress(getActivity(), new LoginRequest(email, password), new Response.Listener<CashOnProgressResponse>() {
            @Override
            public void onResponse(CashOnProgressResponse cashOnTheWayListResponse) {
                if (getActivity() != null) {
                    String networkOrders = new Gson().toJson(cashOnTheWayListResponse.getData().getOrders());
                    if (!networkOrders.equals(new Gson().toJson(PreferenceUtil.getProgressOrders(getActivity())))) {
                        PreferenceUtil.saveProgressOrders(getActivity(), cashOnTheWayListResponse.getData().getOrders());
                        cashInProgressArrayAdapter.clear();
                        for (Order order : cashOnTheWayListResponse.getData().getOrders()) {
                            cashInProgressArrayAdapter.add(order);
                        }
                        cashInProgressArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null)
                    Crouton.showText(getActivity(), "Failed to get list", Style.ALERT);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_list_fragment, container, false);
    }

}