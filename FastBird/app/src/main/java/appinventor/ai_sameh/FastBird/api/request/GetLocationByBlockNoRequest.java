package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class GetLocationByBlockNoRequest extends LoginRequest {
    private String blockno;

    public GetLocationByBlockNoRequest(String username, String password, String blockno) {
        super(username, password);
        this.blockno = blockno;
    }
}
