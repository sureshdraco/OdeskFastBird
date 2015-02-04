package appinventor.ai_sameh.FastBird.api.request;

/**
 * Created by suresh on 12/10/14.
 */
public class RegisterRequest extends TokenRequest {
	private String email, password, firstname, lastname, companyname, cprno, crno, mobile, phone1, phone2, faxno, confirmationcode;

	public RegisterRequest(String email, String password, String firstname, String lastname, String companyname, String cprno, String crno, String mobile, String phone1,
			String phone2, String faxno, String confirmationcode) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.companyname = companyname;
		this.cprno = cprno;
		this.crno = crno;
		this.mobile = mobile;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.faxno = faxno;
		this.confirmationcode = confirmationcode;
	}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
