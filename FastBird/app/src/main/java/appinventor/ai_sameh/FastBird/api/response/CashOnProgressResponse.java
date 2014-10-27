package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.MRBTransactions;
import appinventor.ai_sameh.FastBird.api.model.Order;

/**
 * Created by suresh on 12/10/14.
 */
public class CashOnProgressResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<Order> Orders;

        public ArrayList<Order> getOrders() {
            return Orders;
        }

        public String getError() {
            return Error;
        }
    }
}
