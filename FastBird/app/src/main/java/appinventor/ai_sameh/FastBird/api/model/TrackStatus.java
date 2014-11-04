package appinventor.ai_sameh.FastBird.api.model;

/**
 * Created by suresh on 21/10/14.
 */
public class TrackStatus {
    private String Date, Status;

    public String getDate() {
        return Date;
    }

    public String getStatus() {
        return Status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("");
        sb.append("Date: ").append(Date).append("\n");
        sb.append("Status: ").append(Status).append("\n");
        return sb.toString();
    }
}