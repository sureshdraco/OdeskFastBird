package appinventor.ai_sameh.FastBird.util;

import java.util.Date;

/**
 * Created by suresh on 18/10/14.
 */
public class NotificationItem {
    private String notificationMessage;
    private String date, title;

    public NotificationItem(String title, String date, String notificationMessage) {
        this.notificationMessage = notificationMessage;
        this.date = date;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public String getDate() {
        return date;
    }
}
