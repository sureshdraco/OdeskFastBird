package appinventor.ai_sameh.FastBird.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import appinventor.ai_sameh.FastBird.FastBirdApplication;
import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.RegisterDeviceResponse;
import appinventor.ai_sameh.FastBird.api.UnregisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.UserInfoResponse;
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
                Crouton.showText(getActivity(), String.valueOf(volleyError.networkResponse.statusCode), Style.ALERT);
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
        View logout = getActivity().findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = PreferenceUtil.getEmail(getActivity());
                String password = PreferenceUtil.getPassword(getActivity());
                if (!TextUtils.isEmpty(PreferenceUtil.getRegistrationId(getActivity()))) {
                    ApiRequests.unregisterDevice(getActivity(), new UnregisterDeviceRequest(PreferenceUtil.getRegistrationId(getActivity()), email, password), new Response.Listener<RegisterDeviceResponse>() {
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
