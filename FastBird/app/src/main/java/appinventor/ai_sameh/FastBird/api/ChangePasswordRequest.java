package appinventor.ai_sameh.FastBird.api;

import appinventor.ai_sameh.FastBird.api.request.TokenRequest;

/**
 * Created by suresh on 29/10/14.
 */
public class ChangePasswordRequest extends TokenRequest{
    private String username, oldpassword, newpassword;

    public ChangePasswordRequest(String username, String oldpassword, String newpassword) {
        this.username = username;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }
}
