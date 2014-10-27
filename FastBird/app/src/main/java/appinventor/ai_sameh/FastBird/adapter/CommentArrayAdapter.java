package appinventor.ai_sameh.FastBird.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.Comment;
import appinventor.ai_sameh.FastBird.api.model.Order;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {
    private List<Comment> commentList = new ArrayList<Comment>();
    private Context context;

    static class CommentViewHolder {
        TextView commentText;
        ImageView avatar;
    }

    public CommentArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    @Override
    public void add(Comment comment) {
        commentList.add(comment);
        super.add(comment);
    }

    @Override
    public int getCount() {
        return this.commentList.size();
    }

    @Override
    public Comment getItem(int index) {
        return this.commentList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CommentViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.comment_item, parent, false);
            viewHolder = new CommentViewHolder();
            viewHolder.commentText = (TextView) row.findViewById(R.id.commentText);
            viewHolder.avatar = (ImageView) row.findViewById(R.id.commentImage);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CommentViewHolder) row.getTag();
        }
        Comment comment = getItem(position);
        viewHolder.commentText.setText(comment.getComment());
        if(comment.getEntryMode() == 1) {
            viewHolder.avatar.setImageResource(R.drawable.user_avatar);
        } else {
            viewHolder.avatar.setImageResource(R.drawable.admin_avatar);
        }
        return row;
    }
}