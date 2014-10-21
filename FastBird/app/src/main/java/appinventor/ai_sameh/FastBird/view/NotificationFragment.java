package appinventor.ai_sameh.FastBird.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.util.NotificationItem;

public class NotificationFragment extends Fragment {


    private ListView notificationsListView;
    private ArrayList<NotificationItem> notificationItemArrayList = new ArrayList<NotificationItem>();
    private NotificationsAdapter notificationsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notifications_fragment, container, false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationsListView = (ListView) getActivity().findViewById(R.id.notificationsList);
        notificationItemArrayList = new ArrayList<NotificationItem>();
        notificationsAdapter = new NotificationsAdapter(getActivity(), notificationItemArrayList);
        notificationsListView.setAdapter(notificationsAdapter);
        updateNotifications();
    }

    private void updateNotifications() {
        notificationItemArrayList.clear();
        Type listType = new TypeToken<ArrayList<NotificationItem>>() {
        }.getType();
        notificationItemArrayList = new Gson().fromJson(PreferenceUtil.getNotificationList(getActivity()), listType);
        notificationsAdapter.clear();
        notificationsAdapter.addAll(notificationItemArrayList);
        notificationsAdapter.notifyDataSetChanged();
    }

    private String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    BroadcastReceiver notificationsBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getActivity() != null) {
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