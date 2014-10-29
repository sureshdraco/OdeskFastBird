package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class ServiceTypePriceRequest extends ServiceTypeRequest {
    private String weight, length, height, width, servicetype;

    public ServiceTypePriceRequest(String username, String password, String deliverylocation, String pickuplocation, String weight, String length, String height, String width, String servicetype) {
        super(username, password, deliverylocation, pickuplocation);
        this.weight = weight;
        this.length = length;
        this.height = height;
        this.width = width;
        this.servicetype = servicetype;
    }
}
