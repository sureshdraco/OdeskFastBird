package appinventor.ai_sameh.FastBird.api.model;

/**
 * Created by suresh on 25/10/14.
 */
public class DataDescription {
	private String Description, Id, Price, LocationId;
	private boolean Local;

	public boolean isLocal() {
		return Local;
	}

	public String getDescription() {
		return Description;
	}

	public String getId() {
		return Id;
	}

	public String getPrice() {
		return Price;
	}

	public String getLocationId() {
		return LocationId;
	}
}
