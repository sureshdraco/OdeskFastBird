package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class ServiceTypeRequest extends LoginRequest {
    private String deliverylocation, pickuplocation;

    public ServiceTypeRequest(String username, String password, String deliverylocation, String pickuplocation) {
        super(username, password);
        this.deliverylocation = deliverylocation;
        this.pickuplocation = pickuplocation;
    }
}
