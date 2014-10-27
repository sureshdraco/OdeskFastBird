package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class CommentListRequest extends LoginRequest {
    private String fbdNumber;

    public CommentListRequest(String username, String password, String fbdNumber) {
        super(username, password);
        this.fbdNumber = fbdNumber;
    }
}
