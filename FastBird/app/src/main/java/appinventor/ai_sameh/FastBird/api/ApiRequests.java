package appinventor.ai_sameh.FastBird.api;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appinventor.ai_sameh.FastBird.volley.GsonRequest;
import appinventor.ai_sameh.FastBird.volley.VolleyClient;

/**
 * Created by suresh on 12/10/14.
 */
public class ApiRequests {
    public static final String API_BASE_URL = "http://www.fastbird.org/clients/api/";
    private static final String LOGIN_API_URL = "Clients.svc/ValidateLogin";
    private static final String REGISTER_DEVICE_API_URL = "Clients.svc/AddDevice";
    private static final String UNREGISTER_DEVICE_API_URL = "Clients.svc/RemoveDevice";
    private static final String USER_INFO_API_URL = "Clients.svc/GetMyInformation";

    public static void login(Context context, String email, String password, Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<LoginResponse>(Request.Method.POST, API_BASE_URL + LOGIN_API_URL, new LoginRequest(email, password), LoginResponse.class, null, listener, errorListener));
    }

    public static void getUserInformation(Context context, String email, String password, Response.Listener<UserInfoResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<UserInfoResponse>(Request.Method.POST, API_BASE_URL + USER_INFO_API_URL, new LoginRequest(email, password), UserInfoResponse.class, null, listener, errorListener));
    }

    public static void registerDevice(Context context, RegisterDeviceRequest registerDeviceRequest, Response.Listener<RegisterDeviceResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<RegisterDeviceResponse>(Request.Method.POST, API_BASE_URL + REGISTER_DEVICE_API_URL, registerDeviceRequest, RegisterDeviceResponse.class, null, listener, errorListener));
    }

    public static void unregisterDevice(Context context, UnregisterDeviceRequest unregisterDeviceRequest, Response.Listener<RegisterDeviceResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<RegisterDeviceResponse>(Request.Method.POST, API_BASE_URL + UNREGISTER_DEVICE_API_URL, unregisterDeviceRequest, RegisterDeviceResponse.class, null, listener, errorListener));
    }

}