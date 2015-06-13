package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class GetConfirmationCodeRequest extends TokenRequest {
    private String mobileno;

    public GetConfirmationCodeRequest(String mobileno) {
        this.mobileno = mobileno;
    }
}
