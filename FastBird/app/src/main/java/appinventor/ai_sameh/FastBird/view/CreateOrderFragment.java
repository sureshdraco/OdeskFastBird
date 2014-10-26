package appinventor.ai_sameh.FastBird.view;

import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.model.Address;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.DataDescription;
import appinventor.ai_sameh.FastBird.api.model.DeliveryTime;
import appinventor.ai_sameh.FastBird.api.request.CreateOrderRequest;
import appinventor.ai_sameh.FastBird.api.response.CreateOrderResponse;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.response.DeliveryTimeResponse;
import appinventor.ai_sameh.FastBird.api.response.DeliveryTypeResponse;
import appinventor.ai_sameh.FastBird.api.response.LocationResponse;
import appinventor.ai_sameh.FastBird.api.response.MyAddressResponse;
import appinventor.ai_sameh.FastBird.api.response.ServiceTypeResponse;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CreateOrderFragment extends Fragment {

    private static final String TAG = CreateOrderFragment.class.getSimpleName();
    private Button submitButton;
    private Spinner pickupAddressSpinner;
    private Spinner serviceTypeSpinner;
    private String email;
    private String password;
    private Gson gson;
    private Spinner deliveryTimeSpinner;
    private Spinner moneyDeliveryTypeSpinner;
    private Spinner locationTypeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_order_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gson = new Gson();
        email = PreferenceUtil.getEmail(getActivity());
        password = PreferenceUtil.getPassword(getActivity());
        initView();
        setupPickAddress();
        setupDeliveryTime();
        setupDeliveryType();
        setupLocations();
        //setupServiceType();
    }

    private void setupLocations() {
        if (PreferenceUtil.getLocations(getActivity()).size() == 0) {
            getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } else {
            updateLocations(PreferenceUtil.getLocations(getActivity()));
        }
        ApiRequests.getLocations(getActivity(), new LoginRequest(email, password), new Response.Listener<LocationResponse>() {
            @Override
            public void onResponse(LocationResponse locationResponse) {
                if (locationResponse.getData().getError() != null) {
                    dismissDialog();
                }
                if (isDataUpdated(PreferenceUtil.getLocations(getActivity()), locationResponse.getData().getLocations())) {
                    updateLocations(locationResponse.getData().getLocations());
                    dismissDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
            }
        });
    }

    private void updateLocations(ArrayList<DataDescription> locations) {
        PreferenceUtil.saveLocations(getActivity(), locations);
        ArrayList<String> list = new ArrayList<String>();
        for (DataDescription location : locations) {
            list.add(location.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(dataAdapter);
    }

    private void dismissDialog() {
        try {
            getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } catch (Exception ex) {
        }
    }

    private void setupPickAddress() {
        if (PreferenceUtil.getMyPickupAddress(getActivity()).size() == 0) {
            getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } else {
            updatePickupAddress(PreferenceUtil.getMyPickupAddress(getActivity()));
        }
        ApiRequests.getMyAddresses(getActivity(), new LoginRequest(email, password), new Response.Listener<MyAddressResponse>() {
            @Override
            public void onResponse(MyAddressResponse myAddressResponse) {
                if(myAddressResponse.getData().getError() != null) {
                    dismissDialog();
                }
                if (isDataUpdated(PreferenceUtil.getMyPickupAddress(getActivity()), myAddressResponse.getData().getAddresses())) {
                    updatePickupAddress(myAddressResponse.getData().getAddresses());
                    dismissDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
            }
        });
    }

    private void updatePickupAddress(ArrayList<Address> addresses) {
        PreferenceUtil.savePickupAddresses(getActivity(), addresses);
        ArrayList<String> addressList = new ArrayList<String>();
        for (Address address : addresses) {
            addressList.add(address.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, addressList);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        pickupAddressSpinner.setAdapter(dataAdapter);
    }

    private void setupDeliveryTime() {
        if (PreferenceUtil.getDeliveryTime(getActivity()).size() == 0) {
            getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } else {
            updateDeliveryTime(PreferenceUtil.getDeliveryTime(getActivity()));
        }
        ApiRequests.getDeliveryTimes(getActivity(), new LoginRequest(email, password), new Response.Listener<DeliveryTimeResponse>() {
            @Override
            public void onResponse(DeliveryTimeResponse deliveryTimeResponse) {
                if (deliveryTimeResponse.getData().getError() != null) {
                    dismissDialog();
                }
                if (isDataUpdated(PreferenceUtil.getDeliveryTime(getActivity()), deliveryTimeResponse.getData().getDeliveryTimes())) {
                    updateDeliveryTime(deliveryTimeResponse.getData().getDeliveryTimes());
                    dismissDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
            }
        });
    }

    private void updateDeliveryTime(ArrayList<DeliveryTime> deliveryTimes) {
        PreferenceUtil.saveDeliveryTime(getActivity(), deliveryTimes);
        ArrayList<String> deliveryTimeList = new ArrayList<String>();
        for (DeliveryTime deliveryTime : deliveryTimes) {
            deliveryTimeList.add(deliveryTime.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, deliveryTimeList);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        deliveryTimeSpinner.setAdapter(dataAdapter);
    }


    private void setupDeliveryType() {
        if (PreferenceUtil.getDeliveryType(getActivity()).size() == 0) {
            getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } else {
            updateDeliveryType(PreferenceUtil.getDeliveryType(getActivity()));
        }
        ApiRequests.getDeliveryTypes(getActivity(), new LoginRequest(email, password), new Response.Listener<DeliveryTypeResponse>() {
            @Override
            public void onResponse(DeliveryTypeResponse deliveryTypeResponse) {
                if (deliveryTypeResponse.getData().getError() != null) {
                    dismissDialog();
                }
                if (isDataUpdated(PreferenceUtil.getDeliveryType(getActivity()), deliveryTypeResponse.getData().getDeliveryTypes())) {
                    updateDeliveryType(deliveryTypeResponse.getData().getDeliveryTypes());
                    dismissDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
            }
        });
    }

    private void updateDeliveryType(ArrayList<DeliveryTime> deliveryTypes) {
        PreferenceUtil.saveDeliveryType(getActivity(), deliveryTypes);
        ArrayList<String> deliveryTimeList = new ArrayList<String>();
        for (DeliveryTime deliveryTime : deliveryTypes) {
            deliveryTimeList.add(deliveryTime.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, deliveryTimeList);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        moneyDeliveryTypeSpinner.setAdapter(dataAdapter);
    }

    private void setupServiceType() {
        if (PreferenceUtil.getServiceTypes(getActivity()).size() == 0) {
            getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } else {
            updateServiceTypes(PreferenceUtil.getServiceTypes(getActivity()));
        }
        ApiRequests.getServiceType(getActivity(), new LoginRequest(email, password), new Response.Listener<ServiceTypeResponse>() {
            @Override
            public void onResponse(ServiceTypeResponse serviceTypeResponse) {
                if(serviceTypeResponse.getData().getError() != null) {
                    dismissDialog();
                }
                if (isDataUpdated(PreferenceUtil.getServiceTypes(getActivity()), serviceTypeResponse.getData().getServiceTypes())) {
                    updateServiceTypes(serviceTypeResponse.getData().getServiceTypes());
                    dismissDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
            }
        });
    }

    private void updateServiceTypes(ArrayList<DataDescription> serviceTypes) {
        PreferenceUtil.saveServiceType(getActivity(), serviceTypes);
        ArrayList<String> serviceTypeList = new ArrayList<String>();
        for (DataDescription serviceType : serviceTypes) {
            serviceTypeList.add(serviceType.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, serviceTypeList);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        serviceTypeSpinner.setAdapter(dataAdapter);
    }

    private boolean isDataUpdated(Object cache, Object network) {
        String cacheString = gson.toJson(cache);
        String networkString = gson.toJson(network);
        return !cacheString.equals(networkString);
    }

    private void initView() {
        final EditText addressTitle = (EditText) getActivity().findViewById(R.id.addressTitle);
        final EditText netTotal = (EditText) getActivity().findViewById(R.id.total);
        final EditText collectionAmount = (EditText) getActivity().findViewById(R.id.collectionAmount);
        final EditText balance = (EditText) getActivity().findViewById(R.id.subTotal);
        final EditText blockNo = (EditText) getActivity().findViewById(R.id.blockNo);
        final EditText buildingNo = (EditText) getActivity().findViewById(R.id.buildingNo);
        final EditText contactName = (EditText) getActivity().findViewById(R.id.contactName);
        final EditText road = (EditText) getActivity().findViewById(R.id.road);
        final EditText phone1 = (EditText) getActivity().findViewById(R.id.phone1);
        final EditText phone2 = (EditText) getActivity().findViewById(R.id.phone2);
        final EditText flatNo = (EditText) getActivity().findViewById(R.id.flatNo);

        final EditText weight = (EditText) getActivity().findViewById(R.id.weight);
        final EditText height = (EditText) getActivity().findViewById(R.id.height);
        final EditText length = (EditText) getActivity().findViewById(R.id.length);
        final EditText width = (EditText) getActivity().findViewById(R.id.width);

        final EditText note = (EditText) getActivity().findViewById(R.id.note);
        final EditText subTotal = (EditText) getActivity().findViewById(R.id.subTotal);
        final EditText discount = (EditText) getActivity().findViewById(R.id.discount);
        final EditText total = (EditText) getActivity().findViewById(R.id.total);
        pickupAddressSpinner = (Spinner) getActivity().findViewById(R.id.pickupAddress);
        final Spinner packageTypeSpinner = (Spinner) getActivity().findViewById(R.id.packageType);
        serviceTypeSpinner = (Spinner) getActivity().findViewById(R.id.serviceType);
        locationTypeSpinner = (Spinner) getActivity().findViewById(R.id.location);
        deliveryTimeSpinner = (Spinner) getActivity().findViewById(R.id.deliveryTime);
        moneyDeliveryTypeSpinner = (Spinner) getActivity().findViewById(R.id.moneyDeliveryType);
        final Spinner paymentMethodTypeSpinner = (Spinner) getActivity().findViewById(R.id.paymentMethod);
        submitButton = (Button) getActivity().findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = PreferenceUtil.getEmail(getActivity());
                String password = PreferenceUtil.getPassword(getActivity());
                String pickupAddress = pickupAddressSpinner.getSelectedItem().toString();
                String packageTypeString = packageTypeSpinner.getSelectedItem().toString();
                String serviceTypeString = serviceTypeSpinner.getSelectedItem().toString();
                String deliveryTimeString = deliveryTimeSpinner.getSelectedItem().toString();
                String moneyDeliveryTypeString = moneyDeliveryTypeSpinner.getSelectedItem().toString();
                String paymentMethodTypeString = paymentMethodTypeSpinner.getSelectedItem().toString();
                String addressTitleString = addressTitle.getText().toString();
                String netTotalString = netTotal.getText().toString();
                String collectionAmountString = collectionAmount.getText().toString();
                String balanceString = balance.getText().toString();
                String blockNoString = blockNo.getText().toString();
                String buildingNoString = buildingNo.getText().toString();
                String contactNameString = contactName.getText().toString();
                String roadString = road.getText().toString();
                String phone1String = phone1.getText().toString();
                String phone2String = phone2.getText().toString();
                String flatNoString = flatNo.getText().toString();
                String noteString = note.getText().toString();
                String widthString = width.getText().toString();
                String heightString = height.getText().toString();
                String lengthString = length.getText().toString();
                String weightString = weight.getText().toString();
                String subTotalString = subTotal.getText().toString();
                String discountString = discount.getText().toString();
                String totalString = total.getText().toString();
                String locationString = locationTypeSpinner.getSelectedItem().toString();
                CreateOrderRequest createOrderRequest = new CreateOrderRequest(username, password, pickupAddress, contactNameString, phone1String, phone2String, flatNoString, buildingNoString, blockNoString, roadString, locationString, noteString, packageTypeString, serviceTypeString, weightString, lengthString, heightString, widthString, deliveryTimeString, moneyDeliveryTypeString, collectionAmountString, paymentMethodTypeString);
                ApiRequests.createOrder(getActivity(), createOrderRequest, new Response.Listener<CreateOrderResponse>() {
                    @Override
                    public void onResponse(CreateOrderResponse createOrderResponse) {
                        Log.d(TAG, createOrderResponse.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Crouton.showText(getActivity(), "Failed to create!", Style.ALERT);
                    }
                });
            }
        });
    }
}