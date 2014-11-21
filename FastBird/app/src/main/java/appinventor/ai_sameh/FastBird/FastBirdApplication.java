package appinventor.ai_sameh.FastBird;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by suresh on 18/10/14.
 */
public class FastBirdApplication extends Application {
	public static Context appContext;

	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
	}

	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		// Get tracker.
		Tracker t = getTracker(TrackerName.APP_TRACKER);
		// Enable Advertising Features.
		t.enableAdvertisingIdCollection(true);
	}

	synchronized Tracker getTracker(TrackerName trackerId) {
		if (!mTrackers.containsKey(trackerId)) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker("UA-56885681-1")
					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker) : null;
			mTrackers.put(trackerId, t);
		}
		return mTrackers.get(trackerId);
	}

}
