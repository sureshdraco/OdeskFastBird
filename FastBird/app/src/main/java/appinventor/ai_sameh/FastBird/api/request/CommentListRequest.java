package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class CommentListRequest extends LoginRequest {
    private String fbdnumber;

    public CommentListRequest(String username, String password, String fbdnumber) {
        super(username, password);
        this.fbdnumber = fbdnumber;
    }
}
