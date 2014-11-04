package appinventor.ai_sameh.FastBird.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.CashArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.CashInProgressArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.CommentArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.Comment;
import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.CommentListRequest;
import appinventor.ai_sameh.FastBird.api.request.InsertCommentRequest;
import appinventor.ai_sameh.FastBird.api.request.RegisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.response.CommentListResponse;
import appinventor.ai_sameh.FastBird.api.response.LoginResponse;
import appinventor.ai_sameh.FastBird.api.response.RegisterDeviceResponse;
import appinventor.ai_sameh.FastBird.api.response.UserInfoResponse;
import appinventor.ai_sameh.FastBird.util.NotificationUtil;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CommentActivity extends Activity {
    public static final String TAG = CommentActivity.class.getSimpleName();
    private TextView text;
    private ListView commentListView;
    private CommentArrayAdapter commentArrayAdapter;
    private String fbdNumber;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_view_fragment);
        initView();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;

        switch (id) {
            case ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER:
                dialog = new ActivityProgressIndicator(this, R.style.TransparentDialog);
                break;
        }
        return dialog;
    }

    private void initView() {
        commentListView = (ListView) findViewById(R.id.commentListView);
        if (getIntent().getExtras() != null) {
            fbdNumber = getIntent().getExtras().getString("fbdNumber");
            setupAdapter();
            email = PreferenceUtil.getEmail(this);
            password = PreferenceUtil.getPassword(this);
            getComments();
            Button addComment = (Button) findViewById(R.id.addCommentBtn);
            final EditText commentEditText = (EditText) findViewById(R.id.commentEditText);
            addComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                    ApiRequests.insertComment(getApplicationContext(), new InsertCommentRequest(email, password, fbdNumber, commentEditText.getText().toString()), new Response.Listener<LoginResponse>() {
                        @Override
                        public void onResponse(LoginResponse commentListResponse) {
                            if (!TextUtils.isEmpty(commentListResponse.getData().getError())) {
                                dismissDialog();
                                Crouton.showText(CommentActivity.this, commentListResponse.getData().getError(), Style.ALERT);
                            }
                            commentEditText.setText("");
                            getComments();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            dismissDialog();
                            Crouton.showText(CommentActivity.this, "Unable to add comment!", Style.ALERT);
                        }
                    });
                }
            });
        }
    }

    private void getComments() {
        showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        ApiRequests.getComments(this, new CommentListRequest(email, password, fbdNumber), new Response.Listener<CommentListResponse>() {
            @Override
            public void onResponse(CommentListResponse commentListResponse) {
                dismissDialog();
                if (!TextUtils.isEmpty(commentListResponse.getData().getError())) {
                    Crouton.showText(CommentActivity.this, "Unable to get comments", Style.ALERT);
                    return;
                }
                commentArrayAdapter.clear();
                for (Comment comment : commentListResponse.getData().getComments()) {
                    commentArrayAdapter.add(comment);
                }
                commentArrayAdapter.notifyDataSetChanged();
                commentListView.post(new Runnable() {
                    @Override
                    public void run() {
                        commentListView.setSelection(commentArrayAdapter.getCount() - 1);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
                Crouton.showText(CommentActivity.this, "Unable to get comments", Style.ALERT);
            }
        });
    }

    private void dismissDialog() {
        try {
            dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } catch (Exception ex) {
        }
    }

    private void setupAdapter() {
        commentArrayAdapter = new CommentArrayAdapter(this, R.layout.comment_item);
        commentListView.setAdapter(commentArrayAdapter);
    }
}