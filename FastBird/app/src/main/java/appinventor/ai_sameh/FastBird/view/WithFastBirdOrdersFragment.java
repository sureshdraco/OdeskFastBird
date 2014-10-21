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

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.OrderArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.LoginRequest;
import appinventor.ai_sameh.FastBird.api.Order;
import appinventor.ai_sameh.FastBird.api.ProgressOrderList;
import appinventor.ai_sameh.FastBird.model.Card;
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
        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.orders_card_item);
        ordersListView.setAdapter(orderArrayAdapter);
        String email = PreferenceUtil.getEmail(getActivity());
        String password = PreferenceUtil.getPassword(getActivity());
        if (getArguments() != null) {
            String tab = getArguments().getString("key");
            if (tab.equals("With Me")) {
                getMyOrders(email, password);
            } else {
                getFastBirdOrders(email, password);
            }
        }
    }

    private void getFastBirdOrders(String email, String password) {
        ApiRequests.getFastBirdPendingOrders(getActivity(), new LoginRequest(email, password), new Response.Listener<ProgressOrderList>() {
            @Override
            public void onResponse(ProgressOrderList progressOrderList) {
                if (getActivity() != null) {
                    for (Order order : progressOrderList.getData().getOrderList()) {
                        orderArrayAdapter.add(order);
                    }
                    orderArrayAdapter.notifyDataSetChanged();
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
        ApiRequests.getMyOrders(getActivity(), new LoginRequest(email, password), new Response.Listener<ProgressOrderList>() {
            @Override
            public void onResponse(ProgressOrderList progressOrderList) {
                if (getActivity() != null) {
                    for (Order order : progressOrderList.getData().getOrderList()) {
                        orderArrayAdapter.add(order);
                    }
                    orderArrayAdapter.notifyDataSetChanged();
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