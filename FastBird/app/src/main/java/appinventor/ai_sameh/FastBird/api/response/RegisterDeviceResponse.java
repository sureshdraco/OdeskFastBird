package appinventor.ai_sameh.FastBird.api.response;

/**
 * Created by suresh on 12/10/14.
 */
public class RegisterDeviceResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;

        public String getError() {
            return Error;
        }
    }
}
