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
import appinventor.ai_sameh.FastBird.adapter.OrderArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.model.ProgressOrderList;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class WithFastBirdOrdersFragment extends Fragment {

    private TextView text;
    private ListView ordersListView;
    private OrderArrayAdapter orderArrayAdapter;


    public WithFastBirdOrdersFragment() {
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
            if (tab.equals("With Me")) {
                getMyOrders(email, password);
            } else if (tab.equals("History")) {
                getHistoryOrders(email, password);
            } else {
                getFastBirdOrders(email, password);
            }
        }
    }

    private void getFastBirdOrders(String email, String password) {
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item, true);
        ordersListView.setAdapter(orderArrayAdapter);

        final String cachedOrders = PreferenceUtil.getFastBirdPendingOrders(getActivity());
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> cachedOrderList = new Gson().fromJson(PreferenceUtil.getFastBirdPendingOrders(getActivity()), listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<Order>();
        }
        for (Order order : cachedOrderList) {
            orderArrayAdapter.add(order);
        }
        orderArrayAdapter.notifyDataSetChanged();
        ApiRequests.getFastBirdPendingOrders(getActivity(), new LoginRequest(email, password), new Response.Listener<ProgressOrderList>() {
            @Override
            public void onResponse(ProgressOrderList progressOrderList) {
                if (getActivity() != null) {
                    String networkOrders = new Gson().toJson(progressOrderList.getData().getOrderList());
                    if (!networkOrders.equals(cachedOrders)) {
                        PreferenceUtil.saveFastBirdPendingOrders(getActivity(), networkOrders);
                        orderArrayAdapter.clear();
                        for (Order order : progressOrderList.getData().getOrderList()) {
                            orderArrayAdapter.add(order);
                        }
                        orderArrayAdapter.notifyDataSetChanged();
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

    private void getMyOrders(String email, String password) {
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item, false);
        ordersListView.setAdapter(orderArrayAdapter);

        final String cachedOrders = PreferenceUtil.getMyPendingOrders(getActivity());
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> cachedOrderList = new Gson().fromJson(PreferenceUtil.getMyPendingOrders(getActivity()), listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<Order>();
        }
        for (Order order : cachedOrderList) {
            orderArrayAdapter.add(order);
        }
        orderArrayAdapter.notifyDataSetChanged();
        ApiRequests.getMyOrders(getActivity(), new LoginRequest(email, password), new Response.Listener<ProgressOrderList>() {
            @Override
            public void onResponse(ProgressOrderList progressOrderList) {
                if (getActivity() != null) {
                    String networkOrders = new Gson().toJson(progressOrderList.getData().getOrderList());
                    if (!networkOrders.equals(cachedOrders)) {
                        PreferenceUtil.saveMyPendingOrders(getActivity(), networkOrders);
                        orderArrayAdapter.clear();
                        for (Order order : progressOrderList.getData().getOrderList()) {
                            orderArrayAdapter.add(order);
                        }
                        orderArrayAdapter.notifyDataSetChanged();
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

    private void getHistoryOrders(String email, String password) {
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item, false);
        ordersListView.setAdapter(orderArrayAdapter);
        final String cachedOrders = PreferenceUtil.getMyHistoryOrders(getActivity());
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> cachedOrderList = new Gson().fromJson(PreferenceUtil.getMyHistoryOrders(getActivity()), listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<Order>();
        }
        for (Order order : cachedOrderList) {
            orderArrayAdapter.add(order);
        }
        orderArrayAdapter.notifyDataSetChanged();
        ApiRequests.getOrderHistory(getActivity(), new LoginRequest(email, password), new Response.Listener<ProgressOrderList>() {
            @Override
            public void onResponse(ProgressOrderList progressOrderList) {
                if (getActivity() != null) {
                    String networkOrders = new Gson().toJson(progressOrderList.getData().getOrderList());
                    if (!networkOrders.equals(cachedOrders)) {
                        PreferenceUtil.saveMyHistoryOrders(getActivity(), networkOrders);
                        orderArrayAdapter.clear();
                        for (Order order : progressOrderList.getData().getOrderList()) {
                            orderArrayAdapter.add(order);
                        }
                        orderArrayAdapter.notifyDataSetChanged();
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