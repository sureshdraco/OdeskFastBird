package appinventor.ai_sameh.FastBird.api.response;


import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.DataDescription;

/**
 * Created by suresh on 12/10/14.
 */
public class CreateOrderResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error, FBDNumber, FastPayCode;

        public String getFBDNumber() {
            return FBDNumber;
        }

        public String getFastPayCode() {
            return FastPayCode;
        }

        public String getError() {
            return Error;
        }
    }
}
