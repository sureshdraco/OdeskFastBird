package appinventor.ai_sameh.FastBird.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.model.ImageCacheManager;
import appinventor.ai_sameh.FastBird.util.NotificationItem;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by suresh on 18/10/14.
 */
public class NotificationsHeaderAdapter extends ArrayAdapter<String> {
	private final Context context;

	public NotificationsHeaderAdapter(Context context) {
		super(context, R.layout.notification_list_header);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.notification_list_header, null);
			viewHolder = new ViewHolder();
			viewHolder.notificationTitle = (TextView) convertView.findViewById(R.id.notifHeader);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (viewHolder != null && viewHolder.notificationTitle != null) {
			viewHolder.notificationTitle.setText(getItem(position));
		}
		return convertView;
	}

	static class ViewHolder {
		private TextView notificationTitle;
	}
}
