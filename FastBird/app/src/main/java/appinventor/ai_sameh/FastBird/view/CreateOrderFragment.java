package appinventor.ai_sameh.FastBird.view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import appinventor.ai_sameh.FastBird.FastBirdApplication;
import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.model.Address;
import appinventor.ai_sameh.FastBird.api.model.DataDescription;
import appinventor.ai_sameh.FastBird.api.model.Order;
import appinventor.ai_sameh.FastBird.api.request.CreateOrderRequest;
import appinventor.ai_sameh.FastBird.api.request.GetLocationByBlockNoRequest;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.request.UpdateOrderRequest;
import appinventor.ai_sameh.FastBird.api.response.GetClientCreditResponse;
import appinventor.ai_sameh.FastBird.api.response.LocationResponse;
import appinventor.ai_sameh.FastBird.api.response.MyAddressResponse;
import appinventor.ai_sameh.FastBird.api.response.PackageTypeResponse;
import appinventor.ai_sameh.FastBird.model.OpenOrder;
import appinventor.ai_sameh.FastBird.util.Constant;
import appinventor.ai_sameh.FastBird.util.Keyboard;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class CreateOrderFragment extends Fragment {

	private static final String TAG = CreateOrderFragment.class.getSimpleName();
	private Button submitButton;
	private Spinner pickupAddressSpinner;
	private String email;
	private String password;
	private Gson gson;
	private AutoCompleteTextView locationTypeSpinner;
	private Spinner packageTypeSpinner;
	private EditText collectionAmount, blockNo, buildingNo, contactName, flatNo, weight, height, road, phone1, phone2, length, width, note, subTotal, discount,
			total;
	private Float collectionAmountFloat = 0f;
	private TextView balanceTextView;
	private View weightContainer;
	private View heightContainer;
	private View lengthContainer;
	private View widthContainer;
	private View moneyDeliveryTypeContainer;
	private View packageTypeContainer;
	private boolean isLocal = true;
	private static final String PACKAGE_TYPE_LOCAL = "ff842bdf-e10a-48ee-9cd4-25417a49a789";
	private static final String MONEY_DELIVERY_TYPE_LOCAL = "86734c8f-84f0-4652-9e86-43b42e77b5dd";
	private TextView roadTextView;
	private TextView blockNoTextView;
	private TextView buildNoTextView;
	private Button clearButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.create_order_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (getArguments() != null) {
			if (getArguments().getBoolean(UPDATE_ORDER)) {
				this.order = PreferenceUtil.getSelectedOrder(getActivity());
				this.updateOrder = true;
			}
		}
		if (!updateOrder) {
			setupTitleBar();
		}
		gson = new Gson();
		email = PreferenceUtil.getEmail(getActivity());
		password = PreferenceUtil.getPassword(getActivity());
		initView();
		setupPickAddress();
		setupLocations();
		setupUpdateOrderData();
	}

	private void setupTitleBar() {
		((TextView) getActivity().findViewById(R.id.viewTitle)).setText("New SHIPMENT");
		setupBalance();
		balanceTextView = (TextView) getActivity().findViewById(R.id.balance);
		if (PreferenceUtil.getClientCredits(getActivity()).equals("-")) {
			getActivity().findViewById(R.id.balanceProgress).setVisibility(View.VISIBLE);
			balanceTextView.setVisibility(View.GONE);
		} else {
			getActivity().findViewById(R.id.balanceProgress).setVisibility(View.GONE);
			balanceTextView.setText(getActivity().getResources().getString(R.string.credit, PreferenceUtil.getClientCredits(getActivity())));
			balanceTextView.setVisibility(View.VISIBLE);
		}
	}

	private void setupBalance() {
		String email = PreferenceUtil.getEmail(getActivity());
		String password = PreferenceUtil.getPassword(getActivity());
		ApiRequests.getGetClientCredits(getActivity(), new LoginRequest(email, password), new Response.Listener<GetClientCreditResponse>() {
			@Override
			public void onResponse(GetClientCreditResponse getClientCreditResponse) {
				if (getClientCreditResponse.getData().getError() == null) {
					PreferenceUtil.saveClientCredits(FastBirdApplication.appContext, getClientCreditResponse.getData().getCredit());
					if (isAdded()) {
						balanceTextView.setText(getActivity().getResources().getString(R.string.credit, PreferenceUtil.getClientCredits(getActivity())));
						balanceTextView.setVisibility(View.VISIBLE);
						getActivity().findViewById(R.id.balanceProgress).setVisibility(View.GONE);
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
			}
		});
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
				if (getActivity() == null) {
					return;
				}
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
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		locationTypeSpinner.setAdapter(dataAdapter);
		setupLocalFields();
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
				if (getActivity() == null) {
					return;
				}
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
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
				if (getActivity() == null) {
					return;
				}
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
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, addressList);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pickupAddressSpinner.setAdapter(dataAdapter);
	}

	private boolean isDataUpdated(Object cache, Object network) {
		String cacheString = gson.toJson(cache);
		String networkString = gson.toJson(network);
		return !cacheString.equals(networkString);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (!updateOrder) {
		} else {
			getActivity().finish();
			PreferenceUtil.saveOpenOrder(getActivity(), new OpenOrder("xxx", "info", "pending"));
		}
	}

	private void initView() {
		collectionAmount = (EditText) getActivity().findViewById(R.id.collectionAmount);
		blockNo = (EditText) getActivity().findViewById(R.id.blockNo);
		if (!updateOrder)
			blockNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean b) {
					if (!b) {
						String username = PreferenceUtil.getEmail(getActivity());
						String password = PreferenceUtil.getPassword(getActivity());
						getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
						ApiRequests.getLocationByBlockNo(getActivity(), new GetLocationByBlockNoRequest(username, password, blockNo.getText().toString()),
								new Response.Listener<LocationResponse>() {
							@Override
							public void onResponse(LocationResponse locationResponse) {
								if (getActivity() == null) {
									return;
								}
								try {
									getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
								} catch (Exception ignore) {
								}

								if (locationResponse.getData().getError() != null || locationResponse.getData().getLocations().isEmpty()) {
									return;
								}
								ArrayList<DataDescription> locationList = locationResponse.getData().getLocations();
								DataDescription location = locationResponse.getData().getLocations().get(0);
								for (int i = 0; i < locationList.size(); i++) {
									if (location.getId().equals(locationList.get(i).getId())) {
										locationTypeSpinner.setSelection(i);
										locationTypeSpinner.setText(location.getDescription());
										setupLocalFields();
										break;
									}
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError volleyError) {
								try {
									getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
								} catch (Exception ignored) {
								}
							}
						});
					}
				}
			});
		buildingNo = (EditText) getActivity().findViewById(R.id.buildingNo);
		contactName = (EditText) getActivity().findViewById(R.id.contactName);
		road = (EditText) getActivity().findViewById(R.id.road);
		phone1 = (EditText) getActivity().findViewById(R.id.phone1);
		phone2 = (EditText) getActivity().findViewById(R.id.phone2);
		flatNo = (EditText) getActivity().findViewById(R.id.flatno);

		weight = (EditText) getActivity().findViewById(R.id.weight);
		height = (EditText) getActivity().findViewById(R.id.height);
		length = (EditText) getActivity().findViewById(R.id.length);
		width = (EditText) getActivity().findViewById(R.id.width);

		weightContainer = getActivity().findViewById(R.id.weightContainer);
		heightContainer = getActivity().findViewById(R.id.heightContainer);
		lengthContainer = getActivity().findViewById(R.id.lengthContainer);
		widthContainer = getActivity().findViewById(R.id.widthContainer);
		moneyDeliveryTypeContainer = getActivity().findViewById(R.id.moneyDeliveryTypeContainer);
		packageTypeContainer = getActivity().findViewById(R.id.packageTypeContainer);

		roadTextView = (TextView) getActivity().findViewById(R.id.roadTextView);
		blockNoTextView = (TextView) getActivity().findViewById(R.id.blockNoTextView);
		buildNoTextView = (TextView) getActivity().findViewById(R.id.buildNoTextView);
		note = (EditText) getActivity().findViewById(R.id.note);
		subTotal = (EditText) getActivity().findViewById(R.id.subTotal);
		discount = (EditText) getActivity().findViewById(R.id.discount);
		total = (EditText) getActivity().findViewById(R.id.total);
		pickupAddressSpinner = (Spinner) getActivity().findViewById(R.id.pickupAddress);
		packageTypeSpinner = (Spinner) getActivity().findViewById(R.id.packageType);
		locationTypeSpinner = (AutoCompleteTextView) getActivity().findViewById(R.id.location);
		// deliveryTimeSpinner = (Spinner) getActivity().findViewById(R.id.deliveryTime);
		submitButton = (Button) getActivity().findViewById(R.id.submitButton);
		clearButton = (Button) getActivity().findViewById(R.id.clearBtn);
		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				contactName.setText("");
				phone1.setText("");
				phone2.setText("");
				flatNo.setText("");
				flatNo.setText("");
				buildingNo.setText("");
				road.setText("");
				blockNo.setText("");
				locationTypeSpinner.setText("");
				note.setText("");
				width.setText("");
				weight.setText("");
				height.setText("");
				length.setText("");
			}
		});
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

				String pickupAddressLocationId = PreferenceUtil.getMyPickupAddress(getActivity()).get(pickupAddressSpinner.getSelectedItemPosition()).getLocationId();
				String pickupAddress = PreferenceUtil.getMyPickupAddress(getActivity()).get(pickupAddressSpinner.getSelectedItemPosition()).getId();

				String packageTypeString = "ff842bdf-e10a-48ee-9cd4-25417a49a789";
				String deliveryTimeString = "0";
				String moneyDeliveryTypeString = "0";
				String locationString = "";
				for (DataDescription dataDescription : PreferenceUtil.getLocations(getActivity())) {
					if (dataDescription.getDescription().equals(locationTypeSpinner.getText().toString())) {
						locationString = dataDescription.getId();
					}
				}

				String collectionAmountString = updateOrder ? order.getCollectionAmount() : String.valueOf(collectionAmountFloat);
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
				// try {
				// Float price = Float.parseFloat((String) deliveryTimeSpinner.getSelectedItem());
				// Log.d(TAG, String.valueOf(price));
				// } catch (Exception ex) {
				//
				// }
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
				if (!isLocal && TextUtils.isEmpty(lengthString)) {
					length.setError(getActivity().getString(R.string.error_required));
					length.requestFocus();
					return;
				}
				if (!isLocal && TextUtils.isEmpty(weightString)) {
					weight.setError(getActivity().getString(R.string.error_required));
					weight.requestFocus();
					return;
				}
				if (!isLocal) {
					try {
						Float weightFloat = Float.parseFloat(weightString);
						if (weightFloat < 10f) {
							weight.setError(getActivity().getString(R.string.internation_weight_error));
							weight.requestFocus();
							return;
						}
					} catch (Exception e) {
						weight.setError(getActivity().getString(R.string.error_required));
						weight.requestFocus();
						return;
					}
				}
				if (!isLocal && TextUtils.isEmpty(heightString)) {
					height.setError(getActivity().getString(R.string.error_required));
					height.requestFocus();
					return;
				}
				if (!isLocal && TextUtils.isEmpty(widthString)) {
					width.setError(getActivity().getString(R.string.error_required));
					width.requestFocus();
					return;
				}

				if (updateOrder) {
					UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(username, password, pickupAddress, contactNameString, phone1String,
							phone2String, flatNoString,
							buildingNoString, blockNoString, roadString, locationString, noteString, packageTypeString, weightString, lengthString,
							heightString, widthString, deliveryTimeString, moneyDeliveryTypeString, collectionAmountString, String.valueOf(0), order.getFBDNumber(),
							pickupAddressLocationId);
					PreferenceUtil.savePendingUpdateOrderRequest(getActivity(), updateOrderRequest);
					Intent intent = new Intent(getActivity(), CreateOrderConfirmationActivity.class);
					intent.putExtra(CreateOrderFragment.UPDATE_ORDER, true);
					startActivityForResult(intent, 1);
				} else {
					CreateOrderRequest createOrderRequest = new CreateOrderRequest(username, password, pickupAddress, contactNameString, phone1String, phone2String, flatNoString,
							buildingNoString, blockNoString, roadString, locationString, noteString, packageTypeString, weightString, lengthString,
							heightString,
							widthString, deliveryTimeString, moneyDeliveryTypeString, collectionAmountString, String.valueOf(0), pickupAddressLocationId);
					PreferenceUtil.savePendingCreateOrderRequest(getActivity(), createOrderRequest);
					Intent intent = new Intent(getActivity(), CreateOrderConfirmationActivity.class);
					startActivityForResult(intent, 1);
				}

			}
		});
		locationTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				setupLocalFields();
			}
		});
		locationTypeSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					setupLocalFields();
				}
			}
		});
		note.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == EditorInfo.IME_NULL || id == EditorInfo.IME_ACTION_NEXT) {
					if (getActivity() != null) {
						Keyboard.hideKeyboard(getActivity(), note);
					}
					return true;
				}
				return false;
			}
		});

		width.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == EditorInfo.IME_NULL || id == EditorInfo.IME_ACTION_NEXT) {
					if (getActivity() != null) {
						Keyboard.hideKeyboard(getActivity(), width);
					}
					return true;
				}
				return false;
			}
		});

	}

	private void setupLocalFields() {
		if (TextUtils.isEmpty(locationTypeSpinner.getText())) {
			return;
		}
		for (DataDescription location : PreferenceUtil.getLocations(getActivity())) {
			if (location.getDescription().equals(locationTypeSpinner.getText().toString())) {
				if (location.isLocal()) {
					handleLocalFields();
				} else {
					handleInternational();
				}
				getView().findViewById(R.id.formContainer).setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	private void handleInternational() {
		isLocal = false;
		widthContainer.setVisibility(View.VISIBLE);
		getView().findViewById(R.id.packageSizeHeader).setVisibility(View.VISIBLE);
		weightContainer.setVisibility(View.VISIBLE);
		heightContainer.setVisibility(View.VISIBLE);
		lengthContainer.setVisibility(View.VISIBLE);
		buildNoTextView.setText("Address 1 (*)");
		roadTextView.setText("Address 2 (*)");
		getView().findViewById(R.id.blockNoContainer).setVisibility(View.GONE);
		blockNo.setText("0");
		flatNo.setVisibility(View.GONE);
		flatNo.setText("0");
	}

	private void handleLocalFields() {
		isLocal = true;
		width.setText("0");
		weight.setText("0");
		height.setText("0");
		length.setText("0");
		getView().findViewById(R.id.packageSizeHeader).setVisibility(View.GONE);
		getView().findViewById(R.id.blockNoContainer).setVisibility(View.VISIBLE);
		widthContainer.setVisibility(View.GONE);
		weightContainer.setVisibility(View.GONE);
		heightContainer.setVisibility(View.GONE);
		lengthContainer.setVisibility(View.GONE);
		moneyDeliveryTypeContainer.setVisibility(View.GONE);
		packageTypeContainer.setVisibility(View.GONE);
		buildNoTextView.setText("Building No (*)");
		roadTextView.setText("Road (*)");
		blockNoTextView.setText("Block No (*)");
		flatNo.setVisibility(View.VISIBLE);
	}

	public static final String UPDATE_ORDER = "updateOrder";

	private Order order;
	private boolean updateOrder = false;

	private void setupUpdateOrderData() {
		contactName.setText(updateOrder ? order.getDeliveryAddressTitle() : Constant.DEBUG ? "contact" : "");
		phone1.setText(updateOrder ? order.getDeliveryPhone1() : Constant.DEBUG ? "phone1" : "");
		phone2.setText(updateOrder ? order.getDeliveryPhone2() : Constant.DEBUG ? "phone2" : "");
		flatNo.setText(updateOrder ? order.getDeliveryFlatNo() : Constant.DEBUG ? "12" : "");
		buildingNo.setText(updateOrder ? order.getDeliveryBuildingNo() : Constant.DEBUG ? "123" : "");
		road.setText(updateOrder ? order.getDeliveryRoad() : Constant.DEBUG ? "road23" : "");
		blockNo.setText(updateOrder ? order.getDeliveryBlockNo() : Constant.DEBUG ? "34" : "");
		note.setText(updateOrder ? order.getDeliveryNotes() : Constant.DEBUG ? "note 23" : "");
		length.setText(updateOrder ? order.getLength() : Constant.DEBUG ? "43" : "");
		weight.setText(updateOrder ? order.getWeight() : Constant.DEBUG ? "12" : "");
		width.setText(updateOrder ? order.getWidth() : Constant.DEBUG ? "2" : "");
		height.setText(updateOrder ? order.getHeight() : Constant.DEBUG ? "4" : "");
		if (updateOrder) {
			locationTypeSpinner.setText(order.getDeliveryLocation());
			locationTypeSpinner.setEnabled(false);
		}
	}
}