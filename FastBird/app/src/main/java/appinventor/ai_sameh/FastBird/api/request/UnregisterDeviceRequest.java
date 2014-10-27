package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class UnregisterDeviceRequest extends LoginRequest{
    private String deviceid;

    public UnregisterDeviceRequest(String username, String password, String deviceid) {
        super(username, password);
        this.deviceid = deviceid;
    }
}
