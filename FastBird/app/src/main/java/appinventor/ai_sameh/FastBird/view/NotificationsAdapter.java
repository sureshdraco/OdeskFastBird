package appinventor.ai_sameh.FastBird.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.util.NotificationItem;

/**
 * Created by suresh on 18/10/14.
 */
public class NotificationsAdapter extends ArrayAdapter<NotificationItem> {
    private final Context context;
    private final List<NotificationItem> menuItems;

    public NotificationsAdapter(Context context, List<NotificationItem> menuItems) {
        super(context, R.layout.notifications_row, menuItems);
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notifications_row, null);
            viewHolder = new ViewHolder();
            viewHolder.notificationTitle = (TextView) convertView.findViewById(R.id.notificationTitle);
            viewHolder.notificationContent = (TextView) convertView.findViewById(R.id.notificationContent);
            viewHolder.notificationDate = (TextView) convertView.findViewById(R.id.notificationDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewHolder.notificationContent != null) {
            NotificationItem menuItem = menuItems.get(position);
            viewHolder.notificationContent.setText(menuItem.getNotificationMessage());
            viewHolder.notificationTitle.setText(menuItem.getTitle());
            viewHolder.notificationDate.setText(menuItem.getDate().toString());
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView notificationTitle, notificationContent, notificationDate;
    }
}
