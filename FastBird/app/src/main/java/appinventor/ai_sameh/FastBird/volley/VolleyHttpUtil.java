package appinventor.ai_sameh.FastBird.volley;

import com.android.volley.Request;

public class VolleyHttpUtil {
	public static final String HTTP_VERB_GET = "GET";
	public static final String HTTP_VERB_POST = "POST";
	public static final String HTTP_VERB_PUT = "PUT";
	public static final String HTTP_VERB_DELETE = "DELETE";
	public static final String HTTP_VERB_PATCH = "PATCH";

	public static String getHttpVerbFor(int methodType) {
		switch (methodType) {
		case Request.Method.POST:
			return HTTP_VERB_POST;
		case Request.Method.GET:
			return HTTP_VERB_GET;
		case Request.Method.PATCH:
			return HTTP_VERB_PATCH;
		case Request.Method.PUT:
			return HTTP_VERB_PUT;
		case Request.Method.DELETE:
			return HTTP_VERB_DELETE;
		default:
			return "";
		}
	}
}
