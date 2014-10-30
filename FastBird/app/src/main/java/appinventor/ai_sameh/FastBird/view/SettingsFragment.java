package appinventor.ai_sameh.FastBird.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import appinventor.ai_sameh.FastBird.FastBirdApplication;
import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.ChangePasswordRequest;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.response.GetClientMoneyResponse;
import appinventor.ai_sameh.FastBird.api.response.LoginResponse;
import appinventor.ai_sameh.FastBird.api.response.RegisterDeviceResponse;
import appinventor.ai_sameh.FastBird.api.request.UnregisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.response.UserInfoResponse;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

	private static final String TAG = SettingsFragment.class.getSimpleName();
	private TextView firstName, lastName, country, mobile;
	private TextView email, bankName, credits;

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.settings, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView();
		updateUi();
		ApiRequests.getUserInformation(getActivity(), PreferenceUtil.getEmail(getActivity()), PreferenceUtil.getPassword(getActivity()), new Response.Listener<UserInfoResponse>() {
			@Override
			public void onResponse(UserInfoResponse userInfoResponse) {
				cacheResponse(userInfoResponse);
				updateUi();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				Crouton.showText(getActivity(), "Failed", Style.ALERT);
			}
		});
	}

	private void cacheResponse(UserInfoResponse userInfoResponse) {
		if (userInfoResponse == null) {
			Crouton.makeText(getActivity(), "Unable to get user information.", Style.ALERT);
			return;
		}
		PreferenceUtil.saveEmail(getActivity(), userInfoResponse.getData().getEmail());
		PreferenceUtil.savePhone(getActivity(), userInfoResponse.getData().getPhone1());
		PreferenceUtil.saveCountry(getActivity(), userInfoResponse.getData().getCountry());
		PreferenceUtil.saveFirstName(getActivity(), userInfoResponse.getData().getFirstName());
		PreferenceUtil.saveLastName(getActivity(), userInfoResponse.getData().getLastName());
		PreferenceUtil.saveBankName(getActivity(), userInfoResponse.getData().getBankName());
		PreferenceUtil.saveCredits(getActivity(), userInfoResponse.getData().getCredits());
		PreferenceUtil.saveDiscountPrice(getActivity(), userInfoResponse.getData().getDiscountPercent());
		((MainActivity) getActivity()).updateBalance();
	}

	private void initView() {
		firstName = (TextView) getActivity().findViewById(R.id.firstName);
		lastName = (TextView) getActivity().findViewById(R.id.lastName);
		country = (TextView) getActivity().findViewById(R.id.country);
		mobile = (TextView) getActivity().findViewById(R.id.mobile);
		email = (TextView) getActivity().findViewById(R.id.email);
		bankName = (TextView) getActivity().findViewById(R.id.bankName);
		credits = (TextView) getActivity().findViewById(R.id.credits);
		getActivity().findViewById(R.id.changePassword).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changePassword();
			}
		});
		getActivity().findViewById(R.id.withdrawBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getClientMoney();
			}
		});
		View logout = getActivity().findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String email = PreferenceUtil.getEmail(getActivity());
				String password = PreferenceUtil.getPassword(getActivity());
				if (!TextUtils.isEmpty(PreferenceUtil.getRegistrationId(getActivity()))) {
					ApiRequests.unregisterDevice(getActivity(), new UnregisterDeviceRequest(email, password, PreferenceUtil.getRegistrationId(getActivity())),
							new Response.Listener<RegisterDeviceResponse>() {
								@Override
								public void onResponse(RegisterDeviceResponse registerDeviceResponse) {
									unregisterFromGcm();
								}
							}, new Response.ErrorListener() {
								@Override
								public void onErrorResponse(VolleyError volleyError) {
									unregisterFromGcm();
								}
							});
				}
				PreferenceUtil.saveUserLoggedIn(getActivity(), false);
				getActivity().finish();
				startActivity(new Intent(getActivity(), LoginActivity.class));
			}
		});
	}

	private void changePassword() {
		LayoutInflater li = LayoutInflater.from(getActivity());
		View promptsView = li.inflate(R.layout.change_password_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText currentPassword = (EditText) promptsView
				.findViewById(R.id.currentPassword);
		final EditText password = (EditText) promptsView
				.findViewById(R.id.password);

		// set dialog message
		alertDialogBuilder
				.setCancelable(true)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog, int id) {
								String user = PreferenceUtil.getEmail(getActivity());
								getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
								ApiRequests.changePassword(getActivity(), new ChangePasswordRequest(user, currentPassword.getText().toString(), password.getText()
										.toString()), new Response.Listener<LoginResponse>() {
									@Override
									public void onResponse(LoginResponse loginResponse) {
										getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
										if (loginResponse.getData().getError() == null) {
											Crouton.showText(getActivity(), "Password Changed", Style.INFO);
											PreferenceUtil.savePassword(getActivity(), password.getText().toString());
										} else {
											Crouton.showText(getActivity(), "Failed", Style.ALERT);
										}
										dialog.dismiss();
									}
								}, new Response.ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError volleyError) {
										getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
										Crouton.showText(getActivity(), "Failed", Style.ALERT);
										dialog.dismiss();
									}
								});
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

	private void getClientMoney() {
		getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
		String email = PreferenceUtil.getEmail(getActivity());
		String password = PreferenceUtil.getPassword(getActivity());
		ApiRequests.getClientMoney(getActivity(), new LoginRequest(email, password), new Response.Listener<GetClientMoneyResponse>() {
			@Override
			public void onResponse(GetClientMoneyResponse getClientMoneyResponse) {
				getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
				if (getClientMoneyResponse.getData().getError() != null) {
					Crouton.showText(getActivity(), getClientMoneyResponse.getData().getError(), Style.ALERT);
					return;
				}
				showConfirmWithdrawDialog(getClientMoneyResponse.getData().getMRBTotal(), getClientMoneyResponse.getData().getChequePrice(), getClientMoneyResponse.getData()
						.getChequePrice());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
				Crouton.showText(getActivity(), "Failed", Style.ALERT);
			}
		});
	}

	private void showConfirmWithdrawDialog(final String mrbTotal, final String chequePrice, final String netTotal) {
		LayoutInflater li = LayoutInflater.from(getActivity());
		View promptsView = li.inflate(R.layout.withdraw_money_confirm_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		((TextView) promptsView.findViewById(R.id.mrbTotal)).setText(getString(R.string.withdraw_money_mrbtotal, mrbTotal));
		((TextView) promptsView.findViewById(R.id.chequePrice)).setText(getString(R.string.withdraw_money_checque_price, chequePrice));
		((TextView) promptsView.findViewById(R.id.netTotal)).setText(getString(R.string.withdraw_money_net_total, netTotal));

		// set dialog message
		alertDialogBuilder
				.setCancelable(true)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog, int id) {
								getActivity().showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
								String email = PreferenceUtil.getEmail(getActivity());
								String password = PreferenceUtil.getPassword(getActivity());
								ApiRequests.withdrawClientMoney(getActivity(), new LoginRequest(email, password), new Response.Listener<LoginResponse>() {
									@Override
									public void onResponse(LoginResponse withdrawMoneyResponse) {
										getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
										if (withdrawMoneyResponse.getData().getError() != null) {
											Crouton.showText(getActivity(), withdrawMoneyResponse.getData().getError(), Style.ALERT);
											return;
										}
										Crouton.showText(getActivity(), "Success!", Style.INFO);
										dialog.dismiss();
									}
								}, new Response.ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError volleyError) {
										getActivity().dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
										Crouton.showText(getActivity(), "Failed", Style.ALERT);
									}
								});
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

	private void unregisterFromGcm() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					GoogleCloudMessaging.getInstance(FastBirdApplication.appContext).unregister();
					PreferenceUtil.storeRegistrationId(FastBirdApplication.appContext, "");
				} catch (IOException e) {
					Log.e(TAG, e.toString());
				}

			}
		}).start();
	}

	private void updateUi() {
		firstName.setText(PreferenceUtil.getFirstName(getActivity()));
		lastName.setText(PreferenceUtil.getLastName(getActivity()));
		country.setText(PreferenceUtil.getCountry(getActivity()));
		mobile.setText(PreferenceUtil.getPhone(getActivity()));
		email.setText(PreferenceUtil.getEmail(getActivity()));
		bankName.setText(PreferenceUtil.getBankName(getActivity()));
		credits.setText(PreferenceUtil.getCredits(getActivity()));
	}
}
