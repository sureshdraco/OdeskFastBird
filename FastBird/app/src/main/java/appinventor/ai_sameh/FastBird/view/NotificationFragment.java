package appinventor.ai_sameh.FastBird.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.NotificationsAdapter;
import appinventor.ai_sameh.FastBird.adapter.SeparatedNotifListAdapter;
import appinventor.ai_sameh.FastBird.util.NotificationItem;
import appinventor.ai_sameh.FastBird.util.NotificationUtil;

public class NotificationFragment extends Fragment {

    private ListView notificationsListView;
    private ArrayList<NotificationItem> recentNotificationItemArrayList = new ArrayList<NotificationItem>();
    private ArrayList<NotificationItem> olderNotificationItemArrayList = new ArrayList<NotificationItem>();
    private NotificationsAdapter recentNotificationsAdapter, olderNotificationsAdapter;
    private Button clearNotifBtn;
    private SeparatedNotifListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notifications_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTitleBar();
        initview();
        updateNotifications();
    }

    private void setupTitleBar() {
        ((TextView) getActivity().findViewById(R.id.viewTitle)).setText("NOTIFICATION");
        getActivity().findViewById(R.id.balance).setVisibility(View.GONE);
        getActivity().findViewById(R.id.balanceProgress).setVisibility(View.GONE);
    }

    private void initList() {
        notificationsListView = (ListView) getActivity().findViewById(R.id.notificationsList);
        adapter = new SeparatedNotifListAdapter(getActivity());
        recentNotificationsAdapter = new NotificationsAdapter(getActivity(), recentNotificationItemArrayList);
        olderNotificationsAdapter = new NotificationsAdapter(getActivity(), olderNotificationItemArrayList);
        adapter.addSection("RECENT", recentNotificationsAdapter);
        adapter.addSection("OLDER", olderNotificationsAdapter);
        notificationsListView.setAdapter(adapter);
    }

    private void initview() {
        clearNotifBtn = (Button) getActivity().findViewById(R.id.clearNotif);
        clearNotifBtn.setText("Clear");
        clearNotifBtn.setBackground(getResources().getDrawable(R.drawable.red_button));
        recentNotificationItemArrayList = new ArrayList<>();
        clearNotifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.saveNotificationsList(getActivity(), "[]");
                NotificationUtil.clearNotifications(getActivity());
                updateNotifications();
            }
        });
        initList();
    }

    private void updateNotifications() {
        recentNotificationItemArrayList.clear();
        Type listType = new TypeToken<ArrayList<NotificationItem>>() {
        }.getType();
        recentNotificationItemArrayList = new Gson().fromJson(PreferenceUtil.getNotificationList(getActivity()), listType);
        recentNotificationsAdapter.clear();
        recentNotificationsAdapter.addAll(recentNotificationItemArrayList);
        recentNotificationsAdapter.notifyDataSetChanged();

        olderNotificationsAdapter.clear();
        olderNotificationsAdapter.addAll(recentNotificationItemArrayList);
        olderNotificationsAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    BroadcastReceiver notificationsBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getActivity() != null) {
                updateNotifications();
            }
        }
    };

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(notificationsBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceUtil.resetNotificationCount(getActivity());
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(notificationsBroadcastReceiver, new IntentFilter(PreferenceUtil.NOTIFICATIONS_UPDATED_BROADCAST));
    }
}