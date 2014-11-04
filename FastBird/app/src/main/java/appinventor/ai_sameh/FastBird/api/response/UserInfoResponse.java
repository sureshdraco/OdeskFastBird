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
            return BankAccountName == null ? "" : BankAccountName;
        }

        public String getBankAccountNo() {
            return BankAccountNo == null ? "" : BankAccountNo;
        }

        public String getBankName() {
            return BankName == null ? "" : BankName;
        }

        public String getCPR() {
            return CPR == null ? "" : CPR;
        }

        public String getCRNo() {
            return CRNo == null ? "" : CRNo;
        }

        public String getClientId() {
            return ClientId == null ? "" : ClientId;
        }

        public String getCompanyName() {
            return CompanyName == null ? "" : CompanyName;
        }

        public String getCountry() {
            return Country == null ? "" : Country;
        }

        public String getCountryId() {
            return CountryId;
        }

        public String getCredits() {
            return Credits == null ? "" : Credits;
        }

        public String getEmail() {
            return Email == null ? "" : Email;
        }

        public String getFaxNo() {
            return FaxNo == null ? "" : FaxNo;
        }

        public String getFirstName() {
            return FirstName == null ? "" : FirstName;
        }

        public String getLastName() {
            return LastName == null ? "" : LastName;
        }

        public String getMobile() {
            return Mobile == null ? "" : Mobile;
        }

        public String getPhone1() {
            return Phone1 == null ? "" : Phone1;
        }

        public String getPhone2() {
            return Phone2 == null ? "" : Phone2;
        }

        public String getDiscountPercent() {
            return DiscountPercent == null ? "" : DiscountPercent;
        }
    }
}
