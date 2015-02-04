package appinventor.ai_sameh.FastBird.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import appinventor.ai_sameh.FastBird.PreferenceUtil;
import appinventor.ai_sameh.FastBird.R;
import appinventor.ai_sameh.FastBird.api.ApiRequests;
import appinventor.ai_sameh.FastBird.api.request.ForgetPasswordRequest;
import appinventor.ai_sameh.FastBird.api.response.LoginResponse;
import appinventor.ai_sameh.FastBird.util.Constant;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView, mFirstName, mLastName, mPhone1, mPhone2, mFax, mCompanyName, mCprNo, mCrNo, mConfirmPwd, mMobile;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceUtil.getUserLoggedIn(this)) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        setContentView(R.layout.activity_register);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mCompanyName = (EditText) findViewById(R.id.companyName);
        mConfirmPwd = (EditText) findViewById(R.id.confirmPassword);
        mCprNo = (EditText) findViewById(R.id.cpr);
        mCrNo = (EditText) findViewById(R.id.crno);
        mFax = (EditText) findViewById(R.id.faxno);
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mMobile = (EditText) findViewById(R.id.mobile);
        mPhone1 = (EditText) findViewById(R.id.phone1);
        mPhone2 = (EditText) findViewById(R.id.phone2);
        mCrNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        if (Constant.DEBUG) mEmailView.setText("sales@fastbird.org");
        if (Constant.DEBUG) mPasswordView.setText("123456789");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtil.getUserLoggedIn(this)) {
            finish();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form. If there are form errors (invalid email, missing fields, etc.), the errors are presented and no
     * actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            // mAuthTask = new UserLoginTask(email, password);
            // mAuthTask.execute((Void) null);
            ApiRequests.login(getApplicationContext(), email, password, new Response.Listener<LoginResponse>() {
                @Override
                public void onResponse(LoginResponse loginResponse) {
                    if (TextUtils.isEmpty(loginResponse.getData().getError())) {
                        loginUser(email, password);
                    } else {
                        showProgress(false);
                        Crouton.showText(RegisterActivity.this, loginResponse.getData().getError(), Style.ALERT);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e(TAG, volleyError.toString());
                    showProgress(false);
                    Crouton.showText(RegisterActivity.this, "Failed to login!", Style.ALERT);
                }
            });
        }
    }

    private void loginUser(String email, String password) {
        showProgress(false);
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        PreferenceUtil.saveEmail(getApplicationContext(), email);
        PreferenceUtil.savePassword(getApplicationContext(), password);
        PreferenceUtil.saveUserLoggedIn(getApplicationContext(), true);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
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

    public void onClickForgetPassword(View view) {
        // Creating alert Dialog with one Button
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View changePasswordDialog = LayoutInflater.from(this).inflate(R.layout.change_password, null);
        // Setting Dialog Title
        alertDialog.setTitle("Forget Password");
        alertDialog.setView(changePasswordDialog);
        alertDialog.setPositiveButton("Get Password", null);
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button b = alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        EditText mobileNumber = (EditText) changePasswordDialog.findViewById(R.id.mobileNumber);
                        EditText cpr = (EditText) changePasswordDialog.findViewById(R.id.cpr);
                        if (TextUtils.isEmpty(mobileNumber.getText().toString())) {
                            mobileNumber.requestFocus();
                            mobileNumber.setError("Required");
                            return;
                        }
                        if (TextUtils.isEmpty(cpr.getText().toString())) {
                            cpr.requestFocus();
                            cpr.setError("Required");
                            return;
                        }
                        showDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                        ApiRequests.forgetPassword(RegisterActivity.this,
                                new ForgetPasswordRequest(PreferenceUtil.getEmail(getApplicationContext()), PreferenceUtil.getPassword(getApplicationContext()), cpr.getText()
                                        .toString(), mobileNumber.getText().toString()), new Response.Listener<LoginResponse>() {
                                    @Override
                                    public void onResponse(LoginResponse loginResponse) {
                                        alertDialog1.dismiss();
                                        dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                                        if (loginResponse.getData().getError() != null) {
                                            Crouton.showText(RegisterActivity.this, loginResponse.getData().getError(), Style.ALERT);
                                            return;
                                        }
                                        Crouton.showText(RegisterActivity.this, "Success!", Style.INFO);

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        alertDialog1.dismiss();
                                        dismissDialog(ActivityProgressIndicator.ACTIVITY_PROGRESS_LOADER);
                                        Crouton.showText(RegisterActivity.this, getString(R.string.no_internet), Style.ALERT);
                                    }
                                });
                    }
                });
            }
        });
        alertDialog1.show();
    }

}
