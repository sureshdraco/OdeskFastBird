package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class InsertCommentRequest extends CommentListRequest {
    private String comment;

    public InsertCommentRequest(String username, String password, String fbdNumber, String comment) {
        super(username, password, fbdNumber);
        this.comment = comment;
    }
}
