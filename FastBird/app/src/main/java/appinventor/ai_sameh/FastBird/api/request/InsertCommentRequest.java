package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class InsertCommentRequest extends CommentListRequest {
    private String comment;

    public InsertCommentRequest(String username, String password, String fbdnumber, String comment) {
        super(username, password, fbdnumber);
        this.comment = comment;
    }
}
