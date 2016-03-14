package appinventor.ai_sameh.FastBird.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.R;

/**
 * Created by suresh on 18/10/14.
 */
public class ServiceTypeAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> serviceTypeList;

    public ServiceTypeAdapter(Context context, List<String> serviceTypeList) {
        super(context, R.layout.service_type_row, R.id.detailContent, serviceTypeList);
        this.context = context;
        this.serviceTypeList = serviceTypeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.service_type_row, null);
            viewHolder = new ViewHolder();
            viewHolder.serviceType = (TextView) convertView.findViewById(R.id.detailContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            String trackStatus = serviceTypeList.get(position);
            viewHolder.serviceType.setText(Html.fromHtml(trackStatus.toString()));
        return convertView;
    }

    static class ViewHolder {
        private TextView serviceType;
    }
}
