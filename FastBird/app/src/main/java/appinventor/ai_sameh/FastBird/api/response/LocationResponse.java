package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.DataDescription;

/**
 * Created by suresh on 12/10/14.
 */
public class LocationResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<DataDescription> Locations;

        public ArrayList<DataDescription> getLocations() {
            return Locations;
        }

        public String getError() {
            return Error;
        }
    }
}
