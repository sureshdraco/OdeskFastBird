package appinventor.ai_sameh.FastBird.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import appinventor.ai_sameh.FastBird.api.request.ServiceTypeRequest;
import appinventor.ai_sameh.FastBird.api.response.CreateOrderResponse;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.response.DeliveryTimeResponse;
import appinventor.ai_sameh.FastBird.api.response.DeliveryTypeResponse;
import appinventor.ai_sameh.FastBird.api.response.LocationResponse;
import appinventor.ai_sameh.FastBird.api.response.MyAddressResponse;
import appinventor.ai_sameh.FastBird.api.response.PackageTypeResponse;
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
    private int previousPickup = -1;
    private int previousLocation = -1;
    private Spinner packageTypeSpinner;
    private Spinner paymentMethodTypeSpinner;
    private EditText addressTitle, collectionAmount, blockNo, buildingNo, contactName, flatNo, weight, height, road, phone1, phone2, length, width, note, subTotal, discount, total;
    private Float collectionAmountFloat = 0f;

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
        setupPackageTypes();
        setupPaymentMethod();
        setupServiceType();
        setupTestData();
    }

    private void setupPaymentMethod() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Credit");
        list.add("Cash on delivery");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        paymentMethodTypeSpinner.setAdapter(dataAdapter);
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


    private void setupPackageTypes() {
        if (PreferenceUtil.getPackageTypes(getActivity()).size() == 0) {
            getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        } else {
            updatePackageType(PreferenceUtil.getPackageTypes(getActivity()));
        }
        ApiRequests.getPackageTypes(getActivity(), new LoginRequest(email, password), new Response.Listener<PackageTypeResponse>() {
            @Override
            public void onResponse(PackageTypeResponse packageTypeResponse) {
                if (packageTypeResponse.getData().getError() != null) {
                    dismissDialog();
                }
                if (isDataUpdated(PreferenceUtil.getPackageTypes(getActivity()), packageTypeResponse.getData().getPackageTypes())) {
                    updatePackageType(packageTypeResponse.getData().getPackageTypes());
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

    private void updatePackageType(ArrayList<DataDescription> packageTypes) {
        PreferenceUtil.savePackageTypes(getActivity(), packageTypes);
        ArrayList<String> list = new ArrayList<String>();
        for (DataDescription packageType : packageTypes) {
            list.add(packageType.getDescription());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        packageTypeSpinner.setAdapter(dataAdapter);
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
                dismissDialog();
                if (isDataUpdated(PreferenceUtil.getMyPickupAddress(getActivity()), myAddressResponse.getData().getAddresses())) {
                    updatePickupAddress(myAddressResponse.getData().getAddresses());
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
                dismissDialog();
                if (isDataUpdated(PreferenceUtil.getDeliveryTime(getActivity()), deliveryTimeResponse.getData().getDeliveryTimes())) {
                    updateDeliveryTime(deliveryTimeResponse.getData().getDeliveryTimes());
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
                dismissDialog();
                if (isDataUpdated(PreferenceUtil.getDeliveryType(getActivity()), deliveryTypeResponse.getData().getDeliveryTypes())) {
                    updateDeliveryType(deliveryTypeResponse.getData().getDeliveryTypes());
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
        if (pickupAddressSpinner.getSelectedItemPosition() == -1) {
            return;
        }
        if (locationTypeSpinner.getSelectedItemPosition() == -1) {
            return;
        }
        getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
        String pickupLocation = PreferenceUtil.getMyPickupAddress(getActivity()).get(pickupAddressSpinner.getSelectedItemPosition()).getLocationId();
        String deliveryLocation = PreferenceUtil.getLocations(getActivity()).get(locationTypeSpinner.getSelectedItemPosition()).getId();
        ApiRequests.getServiceType(getActivity(), new ServiceTypeRequest(email, password, deliveryLocation, pickupLocation), new Response.Listener<ServiceTypeResponse>() {
            @Override
            public void onResponse(ServiceTypeResponse serviceTypeResponse) {
                dismissDialog();
                updateServiceTypes(serviceTypeResponse.getData().getServiceTypes());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissDialog();
            }
        });
    }

    private void updateServiceTypes(ArrayList<DataDescription> serviceTypes) {
        PreferenceUtil.saveServiceTypes(getActivity(), serviceTypes);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Crouton.showText(getActivity(), "Order Created", Style.INFO);
    }

    private void initView() {
        addressTitle = (EditText) getActivity().findViewById(R.id.addressTitle);
        collectionAmount = (EditText) getActivity().findViewById(R.id.collectionAmount);
        blockNo = (EditText) getActivity().findViewById(R.id.blockNo);
        buildingNo = (EditText) getActivity().findViewById(R.id.buildingNo);
        contactName = (EditText) getActivity().findViewById(R.id.contactName);
        road = (EditText) getActivity().findViewById(R.id.road);
        phone1 = (EditText) getActivity().findViewById(R.id.phone1);
        phone2 = (EditText) getActivity().findViewById(R.id.phone2);
        flatNo = (EditText) getActivity().findViewById(R.id.flatNo);

        weight = (EditText) getActivity().findViewById(R.id.weight);
        height = (EditText) getActivity().findViewById(R.id.height);
        length = (EditText) getActivity().findViewById(R.id.length);
        width = (EditText) getActivity().findViewById(R.id.width);

        note = (EditText) getActivity().findViewById(R.id.note);
        subTotal = (EditText) getActivity().findViewById(R.id.subTotal);
        discount = (EditText) getActivity().findViewById(R.id.discount);
        total = (EditText) getActivity().findViewById(R.id.total);
        pickupAddressSpinner = (Spinner) getActivity().findViewById(R.id.pickupAddress);
        packageTypeSpinner = (Spinner) getActivity().findViewById(R.id.packageType);
        serviceTypeSpinner = (Spinner) getActivity().findViewById(R.id.serviceType);
        locationTypeSpinner = (Spinner) getActivity().findViewById(R.id.location);
        deliveryTimeSpinner = (Spinner) getActivity().findViewById(R.id.deliveryTime);
        moneyDeliveryTypeSpinner = (Spinner) getActivity().findViewById(R.id.moneyDeliveryType);
        paymentMethodTypeSpinner = (Spinner) getActivity().findViewById(R.id.paymentMethod);
        submitButton = (Button) getActivity().findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createOrder();
                } catch (Exception e) {
                    Crouton.showText(getActivity(), "Failed! Check missing fields.", Style.ALERT);
                }
            }

            private void createOrder() throws Exception {
                String username = PreferenceUtil.getEmail(getActivity());
                String password = PreferenceUtil.getPassword(getActivity());

                String pickupAddress = PreferenceUtil.getMyPickupAddress(getActivity()).get(pickupAddressSpinner.getSelectedItemPosition()).getLocationId();
                String packageTypeString = PreferenceUtil.getPackageTypes(getActivity()).get(packageTypeSpinner.getSelectedItemPosition()).getId();
                String serviceTypeString = PreferenceUtil.getServiceTypes(getActivity()).get(serviceTypeSpinner.getSelectedItemPosition()).getId();
                String deliveryTimeString = PreferenceUtil.getDeliveryTime(getActivity()).get(deliveryTimeSpinner.getSelectedItemPosition()).getId();
                String moneyDeliveryTypeString = PreferenceUtil.getDeliveryType(getActivity()).get(moneyDeliveryTypeSpinner.getSelectedItemPosition()).getId();
                String paymentMethodTypeString = String.valueOf(paymentMethodTypeSpinner.getSelectedItemPosition());
                String locationString = (String) PreferenceUtil.getLocations(getActivity()).get(locationTypeSpinner.getSelectedItemPosition()).getId();

                String addressTitleString = addressTitle.getText().toString();
                String collectionAmountString = String.valueOf(collectionAmountFloat);
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
                try {
                    Float price = Float.parseFloat((String) deliveryTimeSpinner.getSelectedItem());
                    Log.d(TAG, String.valueOf(price));
                } catch (Exception ex) {

                }
                if (TextUtils.isEmpty(addressTitleString)) {
                    addressTitle.setError(getActivity().getString(R.string.error_required));
                    addressTitle.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(contactNameString)) {
                    contactName.setError(getActivity().getString(R.string.error_required));
                    contactName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(phone1String)) {
                    phone1.setError(getActivity().getString(R.string.error_required));
                    phone1.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(buildingNoString)) {
                    buildingNo.setError(getActivity().getString(R.string.error_required));
                    buildingNo.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(roadString)) {
                    road.setError(getActivity().getString(R.string.error_required));
                    road.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(blockNoString)) {
                    blockNo.setError(getActivity().getString(R.string.error_required));
                    blockNo.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(lengthString)) {
                    length.setError(getActivity().getString(R.string.error_required));
                    length.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(weightString)) {
                    weight.setError(getActivity().getString(R.string.error_required));
                    weight.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(heightString)) {
                    height.setError(getActivity().getString(R.string.error_required));
                    height.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(widthString)) {
                    width.setError(getActivity().getString(R.string.error_required));
                    width.requestFocus();
                    return;
                }

                CreateOrderRequest createOrderRequest = new CreateOrderRequest(username, password, pickupAddress, contactNameString, phone1String, phone2String, flatNoString, buildingNoString, blockNoString, roadString, locationString, noteString, packageTypeString, serviceTypeString, weightString, lengthString, heightString, widthString, deliveryTimeString, moneyDeliveryTypeString, collectionAmountString, paymentMethodTypeString);
                PreferenceUtil.savePendingCreateOrderRequest(getActivity(), createOrderRequest);
                getActivity().startActivityForResult(new Intent(getActivity(), CreateOrderConfirmationActivity.class), 1);
            }
        });
        pickupAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousPickup != position) {
                    setupServiceType();
                }
                previousPickup = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        locationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (previousLocation != position) {
                    setupServiceType();
                }
                previousLocation = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupTestData() {
        addressTitle.setText("address");
        contactName.setText("contact");
        phone1.setText("phone1");
        phone2.setText("phone2");
        flatNo.setText("12");
        buildingNo.setText("123");
        road.setText("road23");
        blockNo.setText("34");
        note.setText("note 23");
        length.setText("43");
        weight.setText("12");
        width.setText("2");
        height.setText("4");
    }
}