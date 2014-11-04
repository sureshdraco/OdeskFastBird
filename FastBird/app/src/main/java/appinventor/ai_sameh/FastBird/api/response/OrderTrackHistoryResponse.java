package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.TrackStatus;

/**
 * Created by suresh on 12/10/14.
 */
public class OrderTrackHistoryResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error, CurrentStatus;

        private ArrayList<TrackStatus> History;

        public ArrayList<TrackStatus> getHistory() {
            return History;
        }

        public String getCurrentStatus() {
            return CurrentStatus;
        }

        public String getError() {
            return Error;
        }
    }
}
