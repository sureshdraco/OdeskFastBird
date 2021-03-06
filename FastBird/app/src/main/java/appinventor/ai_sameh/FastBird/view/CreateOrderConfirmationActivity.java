package appinventor.ai_sameh.FastBird.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.FastBirdApplication;
import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.ServiceTypeAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.DataDescription;
import appinventor.ai_sameh.FastBird.api.model.DeliveryTime;
import appinventor.ai_sameh.FastBird.api.request.CreateOrderRequest;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.request.ServiceTypePriceRequest;
import appinventor.ai_sameh.FastBird.api.request.ServiceTypeRequest;
import appinventor.ai_sameh.FastBird.api.response.CreateOrderResponse;
import appinventor.ai_sameh.FastBird.api.response.GetClientCreditResponse;
import appinventor.ai_sameh.FastBird.api.response.GetClientMoneyResponse;
import appinventor.ai_sameh.FastBird.api.response.ServiceTypePriceResponse;
import appinventor.ai_sameh.FastBird.api.response.ServiceTypeResponse;
import appinventor.ai_sameh.FastBird.util.DecimalUtil;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CreateOrderConfirmationActivity extends Activity {

	private static final String TAG = CreateOrderConfirmationActivity.class.getSimpleName();
	private EditText subTotal, total, discount, collectionAmount;
	private CreateOrderRequest createOrderRequest;
	private float serviceTypePrice;
	private boolean updateOrder = false;
	private float subtotalValue;
	private View collecitonAmountContainer;
	private boolean isLocal;
	private Spinner serviceTypeSpinner;

	public CreateOrderConfirmationActivity() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent() != null) {
			updateOrder = getIntent().getBooleanExtra(CreateOrderFragment.UPDATE_ORDER, false);
		}
		setContentView(R.layout.confirm_create_order);
		createOrderRequest = updateOrder ? PreferenceUtil.getPendingUpdateOrderRequest(this) : PreferenceUtil.getPendingCreateOrderRequest(this);
		initView();
		setupServiceType();
		setupCollectionAmountField();
		if (PreferenceUtil.getClientCredits((getApplicationContext())).equals("-")) {
			setupBalance();
		} else if (PreferenceUtil.getClientMoney(getApplicationContext()).equals("-")) {
			setupMoneyBalance();
		}
	}

	private void setupCollectionAmountField() {
		collecitonAmountContainer = findViewById(R.id.collectionAmountContainer);
		for (DataDescription location : PreferenceUtil.getLocations(getApplicationContext())) {
			if (location.getId().equals(createOrderRequest.getLocation())) {
				if (location.isLocal()) {
					isLocal = true;
					collecitonAmountContainer.setVisibility(View.VISIBLE);
				} else {
					isLocal = false;
					collecitonAmountContainer.setVisibility(View.GONE);
				}
			}
		}
	}

	private void initView() {
		subTotal = (EditText) findViewById(R.id.subTotal);
		serviceTypeSpinner = (Spinner) findViewById(R.id.serviceType);
		total = (EditText) findViewById(R.id.total);
		collectionAmount = (EditText) findViewById(R.id.collectionAmount);
		if (updateOrder) {
			collectionAmount.setText(DecimalUtil.formatDecimal(createOrderRequest.getCollectionamount()));
		}
		discount = (EditText) findViewById(R.id.discount);
		discount.setText(PreferenceUtil.getUserInfo(this).getData().getDiscountPercent() + "%");
		serviceTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String serviceTypeString = PreferenceUtil.getServiceTypes(getApplicationContext()).get(serviceTypeSpinner.getSelectedItemPosition()).getId();
				createOrderRequest.setServicetype(serviceTypeString);
				try {
					serviceTypePrice = Float.parseFloat(serviceTypePrices.get(serviceTypeSpinner.getSelectedItemPosition()));
					prefillFields();
				} catch (Exception e) {
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		final Button createButton = (Button) findViewById(R.id.submitButton);
		createButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLocal)
					createOrderRequest.setCollectionamount(collectionAmount.getText().toString());
				if (updateOrder) {
					updateOrder();
				} else {
					createOrder();
				}
			}
		});
		createButton.setEnabled(false);
		createButton.setText(updateOrder ? "Update Order" : "Create Order");
		CheckBox terms = (CheckBox) findViewById(R.id.checkboxTerms);
		terms.setMovementMethod(LinkMovementMethod.getInstance());
		terms.setText(Html.fromHtml("I agree to the FBD <a href='http://fastbird.net/terms-condition/'>Terms of Service and Privacy Policy</a>"));
		terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				createButton.setEnabled(isChecked);
			}
		});
	}

	private void prefillFields() {
		float deliveryTimePrice = getDeliverTimePrice();
		float deliveryTypePrice = getDeliverTypePrice();
		subtotalValue = serviceTypePrice + deliveryTimePrice + deliveryTypePrice;
		float discountValue = Float.parseFloat(PreferenceUtil.getUserInfo(this).getData().getDiscountPercent());
		subTotal.setText(String.valueOf(subtotalValue));
		float totalValue = subtotalValue - (subtotalValue * (discountValue / 100));

		total.setText(String.valueOf(totalValue < 0 ? 0 : totalValue));
	}

	private void setupMoneyBalance() {
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		String email = PreferenceUtil.getEmail(getApplicationContext());
		String password = PreferenceUtil.getPassword(getApplicationContext());
		ApiRequests.getClientMoney(getApplicationContext(), new LoginRequest(email, password), new Response.Listener<GetClientMoneyResponse>() {
			@Override
			public void onResponse(GetClientMoneyResponse getClientMoneyResponse) {
				if (getClientMoneyResponse.getData().getError() == null) {
					PreferenceUtil.saveClientMoney(FastBirdApplication.appContext, getClientMoneyResponse.getData().getMRBTotal());
					getServiceTypePrice();
				} else {
					dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					unableToGetPrice();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
				unableToGetPrice();
			}
		});
	}

	private void updateOrder() {
		if (!checkRules()) return;
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		ApiRequests.updateOrder(this, PreferenceUtil.getPendingUpdateOrderRequest(getApplicationContext()),
				new Response.Listener<CreateOrderResponse>() {
					@Override
					public void onResponse(CreateOrderResponse createOrderResponse) {
						dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						if (createOrderResponse.getData().getError() != null) {
							Crouton.showText(CreateOrderConfirmationActivity.this, createOrderResponse.getData().getError(), Style.ALERT);
							return;
						}
						showUpdatedDialog();
						Log.d(TAG, createOrderResponse.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						Crouton.showText(CreateOrderConfirmationActivity.this, "Failed to update!", Style.ALERT);
					}
				});
	}

	private void setupBalance() {
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		String email = PreferenceUtil.getEmail(getApplicationContext());
		String password = PreferenceUtil.getPassword(getApplicationContext());
		ApiRequests.getGetClientCredits(getApplicationContext(), new LoginRequest(email, password), new Response.Listener<GetClientCreditResponse>() {
			@Override
			public void onResponse(GetClientCreditResponse getClientCreditResponse) {
				if (getClientCreditResponse.getData().getError() == null) {
					PreferenceUtil.saveClientCredits(FastBirdApplication.appContext, getClientCreditResponse.getData().getCredit());
					if (PreferenceUtil.getClientMoney(getApplicationContext()).equals("-")) {
						setupMoneyBalance();
					} else
						getServiceTypePrice();
				} else {
					dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					unableToGetPrice();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
				unableToGetPrice();
			}
		});
	}

	private boolean checkRules() {
		if (PreferenceUtil.getUserInfo(getApplicationContext()).getData().isVIP()) {
			// vip
			createOrderRequest.setPaymentmethod("1");
		} else {
			try {
				if (subtotalValue <= Float.parseFloat(PreferenceUtil.getClientCredits(getApplicationContext()))) {
					createOrderRequest.setPaymentmethod(String.valueOf(0));
				} else if (subtotalValue <= Float.parseFloat(PreferenceUtil.getClientMoney(getApplicationContext()))) {
					createOrderRequest.setPaymentmethod(String.valueOf(1));
				} else if (isLocal && !TextUtils.isEmpty(collectionAmount.getText().toString()) && subtotalValue <= Float.parseFloat(collectionAmount.getText().toString())) {
					createOrderRequest.setPaymentmethod(String.valueOf(1));
					return true;
				} else {
					refillAccount();
					return false;
				}
			} catch (NumberFormatException ex) {
				Crouton.showText(this, "Invalid Client Credits!", Style.ALERT);
				return false;
			}
		}
		return true;
	}

	private void refillAccount() {
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.low_credit_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setMessage("FBD Credit");
		((TextView) promptsView.findViewById(R.id.content))
				.setText(Html
						.fromHtml(
								"You have no Fast Bird Credit. <br>You can either pay per shipment or get package of credits.<br><br>*A fair usage policy applies – <a href='http://fastbird.net/terms-condition/'>click me</a>"));
		((TextView) promptsView.findViewById(R.id.content)).setMovementMethod(LinkMovementMethod.getInstance());
		// set dialog message
		alertDialogBuilder
				.setCancelable(true)
				.setPositiveButton("Buy credit",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog, int id) {
								startActivity(new Intent(CreateOrderConfirmationActivity.this, WebViewActivity.class));

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	private void createOrder() {
		if (!checkRules()) {
			Crouton.showText(this, "Failed!", Style.ALERT);
			return;
		}
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		ApiRequests.createOrder(this, createOrderRequest, new Response.Listener<CreateOrderResponse>() {
			@Override
			public void onResponse(CreateOrderResponse createOrderResponse) {
				if (createOrderResponse.getData().getError() != null) {
					dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					Crouton.showText(CreateOrderConfirmationActivity.this, createOrderResponse.getData().getError(), Style.ALERT);
					return;
				}
				showCompletedDialog(createOrderResponse.getData().getFBDNumber(), createOrderResponse.getData().getFastPayCode());
				Log.d(TAG, createOrderResponse.toString());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
				Crouton.showText(CreateOrderConfirmationActivity.this, "Failed to create!", Style.ALERT);
			}
		});
	}

	private void showCompletedDialog(String fbdNumber, String fastBirdNumber) {
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.order_completion_info, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		((TextView) promptsView.findViewById(R.id.fbdnumber)).setText(getString(R.string.fbd_number, fbdNumber));
		((TextView) promptsView.findViewById(R.id.fastBirdNumber)).setText(getString(R.string.fast_bird_number, fastBirdNumber));

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog, int id) {
								setResult(1);
								finish();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
		dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
	}

	private void showUpdatedDialog() {
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.order_updated_info, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog, int id) {
								finish();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}

	private void getServiceTypePrice() {
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		String user = PreferenceUtil.getEmail(getApplicationContext());
		String password = PreferenceUtil.getPassword(getApplicationContext());
		ApiRequests.getServiceTypePrice(getApplicationContext(),
				new ServiceTypePriceRequest(user, password, createOrderRequest.getLocation(), createOrderRequest.getPickupAddressLocationId(), createOrderRequest.getWeight(),
						createOrderRequest.getLength(), createOrderRequest.getHeight(), createOrderRequest.getWidth(), createOrderRequest.getServicetype()),
				new Response.Listener<ServiceTypePriceResponse>() {
					@Override
					public void onResponse(ServiceTypePriceResponse serviceTypePriceResponse) {
						dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						if (serviceTypePriceResponse.getData().getError() != null) {
							unableToGetPrice();
						}
						try {
							serviceTypePrice = Float.parseFloat(serviceTypePriceResponse.getData().getServiceTypePrice());
							prefillFields();
						} catch (Exception ex) {
							unableToGetPrice();
							return;
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						unableToGetPrice();
					}
				});
	}

	private void unableToGetPrice() {
		try {
			dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		} catch (Exception e) {
		}
		Crouton.showText(this, "Unable to get prices!", Style.ALERT);
		findViewById(R.id.submitButton).setVisibility(View.GONE);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
			}
		}, 1000);
	}

	private float getDeliverTimePrice() {
		String selectedDeliveryTime = createOrderRequest.getDeliverytime();
		for (DeliveryTime deliveryTime : PreferenceUtil.getDeliveryTime(getApplicationContext())) {
			if (selectedDeliveryTime.equals(deliveryTime.getId())) {
				try {
					return Float.parseFloat(deliveryTime.getPrice());
				} catch (Exception ex) {
				}
			}
		}
		return 0f;
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

	private float getDeliverTypePrice() {
		String selectedDeliveryType = createOrderRequest.getMoneydeliverytype();
		for (DeliveryTime deliveryType : PreferenceUtil.getDeliveryType(getApplicationContext())) {
			if (selectedDeliveryType.equals(deliveryType.getId())) {
				try {
					return Float.parseFloat(deliveryType.getPrice());
				} catch (Exception ex) {
				}
			}
		}
		return 0f;
	}

	private void setupServiceType() {
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		String pickupLocation = createOrderRequest.getPickupAddressLocationId();
		String deliveryLocation = createOrderRequest.getLocation();
		ApiRequests.getServiceType(getApplicationContext(),
				new ServiceTypeRequest(PreferenceUtil.getEmail(getApplicationContext()), PreferenceUtil.getPassword(getApplicationContext()), deliveryLocation, pickupLocation),
				new Response.Listener<ServiceTypeResponse>() {
					@Override
					public void onResponse(ServiceTypeResponse serviceTypeResponse) {
						PreferenceUtil.saveServiceTypes(getApplicationContext(), serviceTypeResponse.getData().getServiceTypes());
						for (DataDescription type : serviceTypeResponse.getData().getServiceTypes()) {
							getServiceTypePrice(type.getId());
						}
						if (serviceTypeResponse.getData().getServiceTypes().isEmpty()) {
							dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
					}
				});
	}

	private List<String> serviceTypePrices = new ArrayList<>(20);

	private void getServiceTypePrice(String serviceType) {
		showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		String user = PreferenceUtil.getEmail(getApplicationContext());
		String password = PreferenceUtil.getPassword(getApplicationContext());
		ApiRequests.getServiceTypePrice(getApplicationContext(),
				new ServiceTypePriceRequest(user, password, createOrderRequest.getLocation(), createOrderRequest.getPickupAddressLocationId(), createOrderRequest.getWeight(),
						createOrderRequest.getLength(), createOrderRequest.getHeight(), createOrderRequest.getWidth(), serviceType),
				new Response.Listener<ServiceTypePriceResponse>() {
					@Override
					public void onResponse(ServiceTypePriceResponse serviceTypePriceResponse) {
						if (serviceTypePriceResponse.getData().getError() != null) {
							unableToGetPrice();
							return;
						}
						try {
							serviceTypePrices.add(String.valueOf(serviceTypePriceResponse.getData().getServiceTypePrice()));
							if (PreferenceUtil.getServiceTypes(getApplicationContext()).size() == serviceTypePrices.size()) {
								dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
								updateServiceTypes(PreferenceUtil.getServiceTypes(getApplicationContext()));
							}
						} catch (Exception ex) {
							unableToGetPrice();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						unableToGetPrice();
					}
				});
	}

	private void updateServiceTypes(ArrayList<DataDescription> serviceTypes) {
		ArrayList<String> serviceTypeList = new ArrayList<>();
		int i = 0;
		for (DataDescription serviceType : serviceTypes) {
			serviceTypeList.add(serviceType.getDescription() + " (" + serviceTypePrices.get(i) + ")");
			i++;
		}
		ServiceTypeAdapter dataAdapter = new ServiceTypeAdapter(getApplicationContext(), serviceTypeList);
		serviceTypeSpinner.setAdapter(dataAdapter);
	}

}