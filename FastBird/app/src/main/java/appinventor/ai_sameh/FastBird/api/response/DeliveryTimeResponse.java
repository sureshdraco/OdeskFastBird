package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.DeliveryTime;

/**
 * Created by suresh on 12/10/14.
 */
public class DeliveryTimeResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<DeliveryTime> DeliveryTimes;

        public ArrayList<DeliveryTime> getDeliveryTimes() {
            return DeliveryTimes;
        }

        public String getError() {
            return Error;
        }
    }
}
