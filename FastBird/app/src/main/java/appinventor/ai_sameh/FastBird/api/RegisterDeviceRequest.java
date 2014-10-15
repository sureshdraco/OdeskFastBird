package appinventor.ai_sameh.FastBird.api;

import android.os.Build;

/**
 * Created by suresh on 12/10/14.
 */
public class RegisterDeviceRequest {
    private String username, password, deviceid, note;

    public RegisterDeviceRequest(String deviceId, String username, String password) {
        this.username = username;
        this.password = password;
        this.deviceid = deviceId;
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
