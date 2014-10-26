package appinventor.ai_sameh.FastBird.api.model;

import java.util.ArrayList;

/**
 * Created by suresh on 12/10/14.
 */
public class ProgressOrderList {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<Order> Orders;

        public ArrayList<Order> getOrderList() {
            return Orders;
        }

        public String getError() {
            return Error;
        }
    }
}
