package appinventor.ai_sameh.FastBird.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.TrackStatus;

/**
 * Created by suresh on 18/10/14.
 */
public class TrackStatusAdapter extends ArrayAdapter<TrackStatus> {
    private final Context context;
    private final List<TrackStatus> trackStatusList;

    public TrackStatusAdapter(Context context, List<TrackStatus> trackStatuses) {
        super(context, R.layout.track_history_row, trackStatuses);
        this.context = context;
        this.trackStatusList = trackStatuses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.track_history_row, null);
            viewHolder = new ViewHolder();
            viewHolder.trackStatusContent = (TextView) convertView.findViewById(R.id.detailContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            TrackStatus trackStatus = trackStatusList.get(position);
            viewHolder.trackStatusContent.setText(Html.fromHtml(trackStatus.toString()));
        return convertView;
    }

    static class ViewHolder {
        private TextView trackStatusContent;
    }
}
