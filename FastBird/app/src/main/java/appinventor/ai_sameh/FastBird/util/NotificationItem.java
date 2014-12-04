package appinventor.ai_sameh.FastBird.util;

import java.util.Date;

/**
 * Created by suresh on 18/10/14.
 */
public class NotificationItem {
    private String notificationMessage;
    private String date, title, iconFileName;

    public NotificationItem(String notificationMessage, String date, String title, String iconFileName) {
        this.notificationMessage = notificationMessage;
        this.date = date;
        this.title = title;
        this.iconFileName = iconFileName;
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

    public String getIconFileName() {
        return iconFileName;
    }
}
