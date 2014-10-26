package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.DataDescription;

/**
 * Created by suresh on 12/10/14.
 */
public class ServiceTypeResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<DataDescription> ServiceTypes;

        public ArrayList<DataDescription> getServiceTypes() {
            return ServiceTypes;
        }

        public String getError() {
            return Error;
        }
    }
}
