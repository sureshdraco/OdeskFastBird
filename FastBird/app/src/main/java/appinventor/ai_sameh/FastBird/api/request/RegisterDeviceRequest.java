package appinventor.ai_sameh.FastBird.api.request;

import android.os.Build;

/**
 * Created by suresh on 12/10/14.
 */
public class RegisterDeviceRequest extends LoginRequest {
    private String deviceid, note;

    public RegisterDeviceRequest(String username, String password, String deviceid) {
        super(username, password);
        this.deviceid = deviceid;
        this.note = getDeviceName();
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
