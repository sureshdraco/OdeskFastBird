package appinventor.ai_sameh.FastBird.util;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public class TypefaceCache {

  public static final String ROBOTO_LIGHT = "roboto_light";
  public static final String ROBOTO_BOLD = "roboto_bold";
  public static final String ROBOTO_THIN = "roboto_thin";
  public static final String ROBOTO_REGULAR = "roboto_regular";

  private static Map<String, Typeface> typefaceCache;
  private static boolean isInitiated = false;

  public static void init(Context context) {
    typefaceCache = new HashMap<String, Typeface>();
    typefaceCache.put(ROBOTO_LIGHT,
        Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf"));
    typefaceCache.put(ROBOTO_BOLD,
        Typeface.createFromAsset(context.getAssets(), "fonts/roboto_bold.ttf"));
    typefaceCache.put(ROBOTO_REGULAR,
        Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf"));
    typefaceCache.put(ROBOTO_THIN,
        Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf"));
    isInitiated = true;
  }

  public static Typeface getTypeface(String typeface) {
    if (!isInitiated) {
      throw new IllegalStateException("Not initiated");
    }
    return typefaceCache.get(typeface);
  }
}
