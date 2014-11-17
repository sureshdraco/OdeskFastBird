package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class ForgetPasswordRequest extends LoginRequest {
    private String cpr, mobileno;

    public ForgetPasswordRequest(String username, String password, String cpr, String mobileno) {
        super(username, password);
        this.cpr = cpr;
        this.mobileno = mobileno;
    }
}
