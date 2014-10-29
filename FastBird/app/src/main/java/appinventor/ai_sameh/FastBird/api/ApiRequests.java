package appinventor.ai_sameh.FastBird.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import appinventor.ai_sameh.FastBird.api.model.ProgressOrderList;
import appinventor.ai_sameh.FastBird.api.request.CommentListRequest;
import appinventor.ai_sameh.FastBird.api.request.CreateOrderRequest;
import appinventor.ai_sameh.FastBird.api.request.InsertCommentRequest;
import appinventor.ai_sameh.FastBird.api.request.LoginRequest;
import appinventor.ai_sameh.FastBird.api.request.RegisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.request.ServiceTypePriceRequest;
import appinventor.ai_sameh.FastBird.api.request.ServiceTypeRequest;
import appinventor.ai_sameh.FastBird.api.request.UnregisterDeviceRequest;
import appinventor.ai_sameh.FastBird.api.response.CashOnProgressResponse;
import appinventor.ai_sameh.FastBird.api.response.CashOnTheWayListResponse;
import appinventor.ai_sameh.FastBird.api.response.CommentListResponse;
import appinventor.ai_sameh.FastBird.api.response.CreateOrderResponse;
import appinventor.ai_sameh.FastBird.api.response.DeliveryTimeResponse;
import appinventor.ai_sameh.FastBird.api.response.DeliveryTypeResponse;
import appinventor.ai_sameh.FastBird.api.response.LocationResponse;
import appinventor.ai_sameh.FastBird.api.response.LoginResponse;
import appinventor.ai_sameh.FastBird.api.response.MyAddressResponse;
import appinventor.ai_sameh.FastBird.api.response.PackageTypeResponse;
import appinventor.ai_sameh.FastBird.api.response.RegisterDeviceResponse;
import appinventor.ai_sameh.FastBird.api.response.ServiceTypePriceResponse;
import appinventor.ai_sameh.FastBird.api.response.ServiceTypeResponse;
import appinventor.ai_sameh.FastBird.api.response.UserInfoResponse;
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
    private static final String FAST_BIRD_PENDING_ORDERS = "NOrder.svc/ListInProgressOrders";
    private static final String ME_PENDING_ORDERS = "NOrder.svc/ListPendingOrders";
    private static final String GET_CASH_ON_THE_WAY = "MoneyCollection.svc/GetCashOnTheWay";
    private static final String IN_PROGRESS_MONEY = "NOrder.svc/ListReturnedMoneyOrders";
    private static final String CREATE_ORDER = "NOrder.svc/Create";
    private static final String MY_PICK_ADDRRESS = "Clients.svc/ListMyAddresses";
    private static final String DELIVERY_TIME = "Masters.svc/ListDeliveryTimes";
    private static final String PACKAGE_TYPES = "Masters.svc/ListPackageTypes";
    private static final String COMMENT_LIST = "NOrder.svc/ListOrderComments";
    private static final String INSERT_COMMENT = "NOrder.svc/InsertOrderComment";
    private static final String SERVICE_TYPE = "Masters.svc/ListServiceTypes";
    private static final String SERVICE_TYPE_PRICE = "Masters.svc/GetServiceTypePrice";
    private static final String LOCATIONS = "Masters.svc/ListLocations";
    private static final String DELIVERY_TYPE = "Masters.svc/ListMoneyDeliveryTypes";
    private static final String MONEY_HISTORY = "MoneyCollection.svc/GetCashHistory";
    private static final String USER_INFO_API_URL = "Clients.svc/GetMyInformation";
    private static final String CHANGE_PASSWORD = "Clients.svc/ChangePassword";

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

    public static void getFastBirdPendingOrders(Context context, LoginRequest loginRequest, Response.Listener<ProgressOrderList> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<ProgressOrderList>(Request.Method.POST, API_BASE_URL + FAST_BIRD_PENDING_ORDERS, loginRequest, ProgressOrderList.class, null, listener, errorListener));
    }

    public static void getMyOrders(Context context, LoginRequest loginRequest, Response.Listener<ProgressOrderList> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<ProgressOrderList>(Request.Method.POST, API_BASE_URL + ME_PENDING_ORDERS, loginRequest, ProgressOrderList.class, null, listener, errorListener));
    }

    public static void getCashOnTheWay(Context context, LoginRequest loginRequest, Response.Listener<CashOnTheWayListResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<CashOnTheWayListResponse>(Request.Method.POST, API_BASE_URL + GET_CASH_ON_THE_WAY, loginRequest, CashOnTheWayListResponse.class, null, listener, errorListener));
    }

    public static void getCashHistory(Context context, LoginRequest loginRequest, Response.Listener<CashOnTheWayListResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<CashOnTheWayListResponse>(Request.Method.POST, API_BASE_URL + MONEY_HISTORY, loginRequest, CashOnTheWayListResponse.class, null, listener, errorListener));
    }

    public static void getMoneyInProgress(Context context, LoginRequest loginRequest, Response.Listener<CashOnProgressResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<CashOnProgressResponse>(Request.Method.POST, API_BASE_URL + IN_PROGRESS_MONEY, loginRequest, CashOnProgressResponse.class, null, listener, errorListener));
    }

    public static void createOrder(Context context, CreateOrderRequest createOrderRequest, Response.Listener<CreateOrderResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<CreateOrderResponse>(Request.Method.POST, API_BASE_URL + CREATE_ORDER, createOrderRequest, CreateOrderResponse.class, null, listener, errorListener));
    }

    public static void getMyAddresses(Context context, LoginRequest myAddressRequest, Response.Listener<MyAddressResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<MyAddressResponse>(Request.Method.POST, API_BASE_URL + MY_PICK_ADDRRESS, myAddressRequest, MyAddressResponse.class, null, listener, errorListener));
    }

    public static void getDeliveryTimes(Context context, LoginRequest deliveryTimeRequest, Response.Listener<DeliveryTimeResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<DeliveryTimeResponse>(Request.Method.POST, API_BASE_URL + DELIVERY_TIME, deliveryTimeRequest, DeliveryTimeResponse.class, null, listener, errorListener));
    }

    public static void getDeliveryTypes(Context context, LoginRequest deliveryTypeRequest, Response.Listener<DeliveryTypeResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<DeliveryTypeResponse>(Request.Method.POST, API_BASE_URL + DELIVERY_TYPE, deliveryTypeRequest, DeliveryTypeResponse.class, null, listener, errorListener));
    }

    public static void getServiceType(Context context, ServiceTypeRequest serviceTypeRequest, Response.Listener<ServiceTypeResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<ServiceTypeResponse>(Request.Method.POST, API_BASE_URL + SERVICE_TYPE, serviceTypeRequest, ServiceTypeResponse.class, null, listener, errorListener));
    }

    public static void getServiceTypePrice(Context context, ServiceTypePriceRequest serviceTypePriceRequest, Response.Listener<ServiceTypePriceResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<ServiceTypePriceResponse>(Request.Method.POST, API_BASE_URL + SERVICE_TYPE_PRICE, serviceTypePriceRequest, ServiceTypePriceResponse.class, null, listener, errorListener));
    }

    public static void getLocations(Context context, LoginRequest serviceTypeRequest, Response.Listener<LocationResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<LocationResponse>(Request.Method.POST, API_BASE_URL + LOCATIONS, serviceTypeRequest, LocationResponse.class, null, listener, errorListener));
    }

    public static void getPackageTypes(Context context, LoginRequest packageTypeRequest, Response.Listener<PackageTypeResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<PackageTypeResponse>(Request.Method.POST, API_BASE_URL + PACKAGE_TYPES, packageTypeRequest, PackageTypeResponse.class, null, listener, errorListener));
    }

    public static void getComments(Context context, CommentListRequest commentListRequest, Response.Listener<CommentListResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<CommentListResponse>(Request.Method.POST, API_BASE_URL + COMMENT_LIST, commentListRequest, CommentListResponse.class, null, listener, errorListener));
    }

    public static void insertComment(Context context, InsertCommentRequest commentListRequest, Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<LoginResponse>(Request.Method.POST, API_BASE_URL + INSERT_COMMENT, commentListRequest, LoginResponse.class, null, listener, errorListener));
    }

    public static void changePassword(Context context, ChangePasswordRequest changePasswordRequest, Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener) {
        VolleyClient.getInstance(context).getRequestQueue().add(new GsonRequest<LoginResponse>(Request.Method.POST, API_BASE_URL + CHANGE_PASSWORD, changePasswordRequest, LoginResponse.class, null, listener, errorListener));
    }
}