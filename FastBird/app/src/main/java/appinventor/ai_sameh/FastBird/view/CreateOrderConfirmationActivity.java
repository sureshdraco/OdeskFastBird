package appinventor.ai_sameh.FastBird.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import appinventor.ai_sameh.FastBird.FastBirdApplication;
import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.adapter.CashArrayAdapter;
import appinventor.ai_sameh.FastBird.adapter.CashInProgressArrayAdapter;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.DataDescription;
import appinventor.ai_sameh.FastBird.api.model.DeliveryTime;
import appinventor.ai_sameh.FastBird.api.request.CreateOrderRequest;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.request.ServiceTypePriceRequest;
import appinventor.ai_sameh.FastBird.api.response.CreateOrderResponse;
import appinventor.ai_sameh.FastBird.api.response.GetClientCreditResponse;
import appinventor.ai_sameh.FastBird.api.response.GetClientMoneyResponse;
import appinventor.ai_sameh.FastBird.api.response.LoginResponse;
import appinventor.ai_sameh.FastBird.api.response.ServiceTypePriceResponse;
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
        setupCollectionAmountField();
        if (PreferenceUtil.getClientCredits((getApplicationContext())).equals("-")) {
            setupBalance();
        } else if (PreferenceUtil.getClientMoney(getApplicationContext()).equals("-")) {
            setupMoneyBalance();
        } else
            getServiceTypePrice();
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
        total = (EditText) findViewById(R.id.total);
        collectionAmount = (EditText) findViewById(R.id.collectionAmount);
        discount = (EditText) findViewById(R.id.discount);
        discount.setText(PreferenceUtil.getUserInfo(this).getData().getDiscountPercent() + "%");
        float deliveryTimePrice = getDeliverTimePrice();
        float deliveryTypePrice = getDeliverTypePrice();
        subtotalValue = serviceTypePrice + deliveryTimePrice + deliveryTypePrice;
        float discountValue = Float.parseFloat(PreferenceUtil.getUserInfo(this).getData().getDiscountPercent());
        subTotal.setText(String.valueOf(subtotalValue));
        float totalValue = subtotalValue - (subtotalValue * (discountValue / 100));

        total.setText(String.valueOf(totalValue < 0 ? 0 : totalValue));
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
            //vip
            createOrderRequest.setPaymentmethod("1");
        } else {
            try {
                if (subtotalValue <= Float.parseFloat(PreferenceUtil.getClientCredits(getApplicationContext()))) {
                    createOrderRequest.setPaymentmethod(String.valueOf(0));
                } else if (subtotalValue <= Float.parseFloat(PreferenceUtil.getClientMoney(getApplicationContext()))) {
                    createOrderRequest.setPaymentmethod(String.valueOf(1));
                } else if (isLocal) {
                    if (subtotalValue > Float.parseFloat(collectionAmount.getText().toString())) {
                        refillAccount();
                        return false;
                    }
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
                        .fromHtml("You have no Fast Bird Credit. <br>You can either pay per shipment or get package of credits.<br><br>*A fair usage policy applies – <a href='http://fastbird.net/terms-condition/'>click me</a>"));
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
        if (!checkRules()) return;
        showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        ApiRequests.createOrder(this, createOrderRequest, new Response.Listener<CreateOrderResponse>() {
            @Override
            public void onResponse(CreateOrderResponse createOrderResponse) {
                dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                if (createOrderResponse.getData().getError() != null) {
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
                                finish();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
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
                        } catch (Exception ex) {
                            unableToGetPrice();
                            return;
                        }
                        initView();
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

}