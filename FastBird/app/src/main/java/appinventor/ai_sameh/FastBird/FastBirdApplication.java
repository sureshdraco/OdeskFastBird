package appinventor.ai_sameh.FastBird;

import android.app.Application;
import android.content.Context;

/**
 * Created by suresh on 18/10/14.
 */
public class FastBirdApplication  extends Application{
    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
