package appinventor.ai_sameh.FastBird.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.OrderArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.model.ProgressOrderList;
import appinventor.ai_sameh.FastBird.model.OpenOrder;
import appinventor.ai_sameh.FastBird.util.OrderDialogUtil;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class WithFastBirdOrdersFragment extends Fragment {

    private TextView text;
    private ListView ordersListView;
    private OrderArrayAdapter orderArrayAdapter;
    private SwipeRefreshLayout swipeContainer;
    private OpenOrder openOrderFromNotification = null;
    private Order orderToBeOpened;

    public WithFastBirdOrdersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (PreferenceUtil.getOpenOrder(getActivity()) != null) {
            openOrderFromNotification = PreferenceUtil.getOpenOrder(getActivity());
        }
        initView(view);
        handleNotificationIntent();
    }

    private void handleNotificationIntent() {
        if (openOrderFromNotification != null) {
            if (orderToBeOpened != null) {
                if (openOrderFromNotification.getPage().equals(OpenOrder.NOTIFICATION_EXTRA_PAGE_COMMENTS)) {
                    OrderDialogUtil.openOrderComments(getActivity(), orderToBeOpened.getFBDNumber());
                } else if (openOrderFromNotification.getPage().equals(OpenOrder.NOTIFICATION_EXTRA_PAGE_INFO)) {
                    OrderDialogUtil.openOrderInfo(getActivity(), orderToBeOpened);
                } else if (openOrderFromNotification.getPage().equals(OpenOrder.NOTIFICATION_EXTRA_PAGE_TRACK)) {
                    OrderDialogUtil.openOrderTrack(getActivity(), orderToBeOpened);
                }
            }
            PreferenceUtil.saveOpenOrder(getActivity(), null);
        }
    }

    private void initView(View view) {
        ordersListView = (ListView) view.findViewById(R.id.order_listView);
        final String email = PreferenceUtil.getEmail(getActivity());
        final String password = PreferenceUtil.getPassword(getActivity());
        setupList(email, password);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        ordersListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (ordersListView == null || ordersListView.getChildCount() == 0) ?
                                0 : ordersListView.getChildAt(0).getTop();
                swipeContainer.setEnabled(topRowVerticalPosition >= 0);
            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupList(email, password);
            }
        });
    }

    private void setupList(String email, String password) {
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
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item, false, false);
        ordersListView.setAdapter(orderArrayAdapter);

        final String cachedOrders = PreferenceUtil.getFastBirdPendingOrders(getActivity());
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> cachedOrderList = new Gson().fromJson(PreferenceUtil.getFastBirdPendingOrders(getActivity()), listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<Order>();
        }
        for (Order order : cachedOrderList) {
            if (openOrderFromNotification != null) {
                if (openOrderFromNotification.getOrder().equals(order.getFBDNumber())) {
                    orderToBeOpened = order;
                }
            }
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
                    swipeContainer.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null) {
                    Crouton.showText(getActivity(), getActivity().getString(R.string.no_internet), Style.ALERT);
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    private void getMyOrders(String email, String password) {
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item, true, true);
        ordersListView.setAdapter(orderArrayAdapter);

        final String cachedOrders = PreferenceUtil.getMyPendingOrders(getActivity());
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> cachedOrderList = new Gson().fromJson(PreferenceUtil.getMyPendingOrders(getActivity()), listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<Order>();
        }
        for (Order order : cachedOrderList) {
            if (openOrderFromNotification != null) {
                if (openOrderFromNotification.getOrder().equals(order.getFBDNumber())) {
                    orderToBeOpened = order;
                }
            }
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
                    swipeContainer.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null) {
                    Crouton.showText(getActivity(), getActivity().getString(R.string.no_internet), Style.ALERT);
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    private void getHistoryOrders(String email, String password) {
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item, false, false);
        ordersListView.setAdapter(orderArrayAdapter);
        final String cachedOrders = PreferenceUtil.getMyHistoryOrders(getActivity());
        Type listType = new TypeToken<ArrayList<Order>>() {
        }.getType();
        ArrayList<Order> cachedOrderList = new Gson().fromJson(PreferenceUtil.getMyHistoryOrders(getActivity()), listType);
        if (cachedOrderList == null) {
            cachedOrderList = new ArrayList<Order>();
        }
        for (Order order : cachedOrderList) {
            if (openOrderFromNotification != null) {
                if (openOrderFromNotification.getOrder().equals(order.getFBDNumber())) {
                    orderToBeOpened = order;
                }
            }
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
                    swipeContainer.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (getActivity() != null) {
                    Crouton.showText(getActivity(), getActivity().getString(R.string.no_internet), Style.ALERT);
                    swipeContainer.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.orders_list_fragment, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}