package appinventor.ai_sameh.FastBird.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.request.CommentListRequest;
import appinventor.ai_sameh.FastBird.api.response.GetOrderResponse;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by suresh.kumar on 2015-03-10.
 */
public class SearchActivity extends Activity {
	EditText searchText;
	private View searchResult;
	private ProgressBar progressBar;
	private View orderDetail;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		orderDetail = findViewById(R.id.relDropdownContainer);
		searchText = (EditText) findViewById(R.id.searchText);
		searchResult = findViewById(R.id.searchOrderResult);
		searchResult.setVisibility(View.GONE);
		searchResult.findViewById(R.id.closeBtn).setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
	}

	public void onClickSearchBtn(View view) {
		progressBar.setVisibility(View.VISIBLE);
		orderDetail.setVisibility(View.GONE);
		ApiRequests.searchOrders(this, new CommentListRequest(PreferenceUtil.getEmail(this), PreferenceUtil.getPassword(this), searchText.getText().toString().trim()),
				new Response.Listener<GetOrderResponse>() {
					@Override
					public void onResponse(GetOrderResponse getOrderResponse) {
						progressBar.setVisibility(View.GONE);
						orderDetail.setVisibility(View.VISIBLE);
						if (getOrderResponse.getData().getError() == null) {
							OrderInfoDialog.setupOrderDetailUi(getApplicationContext(), searchResult, getOrderResponse.getData().getOrder());
							searchResult.setVisibility(View.VISIBLE);
						} else {
							Crouton.showText(SearchActivity.this, getOrderResponse.getData().getError(), Style.ALERT);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						progressBar.setVisibility(View.GONE);
						orderDetail.setVisibility(View.VISIBLE);
						searchResult.setVisibility(View.GONE);
						Crouton.showText(SearchActivity.this, "Order not found!", Style.ALERT);
					}
				});
	}
}
