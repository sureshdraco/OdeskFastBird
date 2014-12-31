package appinventor.ai_sameh.FastBird.api.model;

import appinventor.ai_sameh.FastBird.util.TimestampUtil;

/**
 * Created by suresh on 21/10/14.
 */
public class TrackStatus {
    private String Date, Status, ShipmentTrackingDetails;

    public String getComments() {
        return ShipmentTrackingDetails;
    }

    public String getDate() {
        return Date;
    }

    public String getStatus() {
        return Status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("");
        sb.append("Date: ").append(TimestampUtil.getFastBirdDateString(Date)).append("<br/>");
        sb.append("Status: ").append(Status).append("<br/>");
        sb.append("<b>Comments:</b> ").append(ShipmentTrackingDetails == null ? "" : ShipmentTrackingDetails).append("<br/>");
        return sb.toString();
    }
}