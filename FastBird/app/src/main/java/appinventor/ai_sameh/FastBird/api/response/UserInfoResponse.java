package appinventor.ai_sameh.FastBird.api.response;

/**
 * Created by suresh on 12/10/14.
 */
public class UserInfoResponse {

    private d d;

    public d getData() {
        return d;
    }

    public class d {
        private String Error;
        private String BankAccountName, BankAccountNo, BankName, CPR, CRNo, ClientId, CompanyName, Country, CountryId, Credits, Email, FaxNo, FirstName, LastName, Mobile, Phone1, Phone2, DiscountPercent;

        public String getError() {
            return Error;
        }

        public String getBankAccountName() {
            return BankAccountName;
        }

        public String getBankAccountNo() {
            return BankAccountNo;
        }

        public String getBankName() {
            return BankName;
        }

        public String getCPR() {
            return CPR;
        }

        public String getCRNo() {
            return CRNo;
        }

        public String getClientId() {
            return ClientId;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public String getCountry() {
            return Country;
        }

        public String getCountryId() {
            return CountryId;
        }

        public String getCredits() {
            return Credits;
        }

        public String getEmail() {
            return Email;
        }

        public String getFaxNo() {
            return FaxNo;
        }

        public String getFirstName() {
            return FirstName;
        }

        public String getLastName() {
            return LastName;
        }

        public String getMobile() {
            return Mobile;
        }

        public String getPhone1() {
            return Phone1;
        }

        public String getPhone2() {
            return Phone2;
        }

        public String getDiscountPercent() {
            return DiscountPercent;
        }
    }
}
