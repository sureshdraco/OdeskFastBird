package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class UnregisterDeviceRequest {
    private String username, password, deviceid;

    public UnregisterDeviceRequest(String deviceId, String username, String password) {
        this.username = username;
        this.password = password;
        this.deviceid = deviceId;
    }
}
