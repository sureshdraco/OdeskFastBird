package appinventor.ai_sameh.FastBird.api.response;

import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.api.model.DataDescription;

/**
 * Created by suresh on 12/10/14.
 */
public class PackageTypeResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private ArrayList<DataDescription> PackageTypes;

        public ArrayList<DataDescription> getPackageTypes() {
            return PackageTypes;
        }

        public String getError() {
            return Error;
        }
    }
}
