package appinventor.ai_sameh.FastBird.api.response;

/**
 * Created by suresh on 12/10/14.
 */
public class GetClientMoneyResponse {

	private d d;

	public d getData() {
		return d;
	}

	public class d {
		private String Error, MRBTotal, ChequePrice, NetTotal;

		public String getMRBTotal() {
			return MRBTotal;
		}

		public String getChequePrice() {
			return ChequePrice;
		}

		public String getNetTotal() {
			return NetTotal;
		}

		public String getError() {
			return Error;
		}
	}
}
