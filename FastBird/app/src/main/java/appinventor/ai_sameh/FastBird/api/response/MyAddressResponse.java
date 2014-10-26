package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.Address;

/**
 * Created by suresh on 12/10/14.
 */
public class MyAddressResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<Address> Addresses;

        public ArrayList<Address> getAddresses() {
            return Addresses;
        }

        public String getError() {
            return Error;
        }
    }
}
