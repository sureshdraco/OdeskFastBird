package appinventor.ai_sameh.FastBird.api.model;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by suresh on 21/10/14.
 */
public class MRBTransactions {
	private String Date, Id, TotalAmounts;
	private ArrayList<MoneyDetail> Details;

	public String getDate() {
		return Date;
	}

	public String getId() {
		return Id;
	}

	public String getTotalAmounts() {
		return TotalAmounts;
	}

	public ArrayList<MoneyDetail> getDetails() {
		return Details;
	}

	public boolean isInvalidAmount() {
		if (!TextUtils.isEmpty(TotalAmounts)) {
			try {
				if (Float.parseFloat(TotalAmounts) != 0) {
					return false;
				}
			} catch (NumberFormatException e) {
				return true;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "MRBTransactions{" +
				"Date='" + Date + '\'' +
				", Id='" + Id + '\'' +
				", TotalAmounts='" + TotalAmounts + '\'' +
				", Details=" + Details +
				'}';
	}
}