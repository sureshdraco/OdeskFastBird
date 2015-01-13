package appinventor.ai_sameh.FastBird.util;

import appinventor.ai_sameh.FastBird.model.OpenOrder;

/**
 * Created by suresh on 18/10/14.
 */
public class NotificationItem {
    private String notificationMessage;
    private String date, title, imageUrl, fullMessage;
    private OpenOrder openOrder;

    public NotificationItem(OpenOrder openOrder, String notificationMessage, String date, String title, String imageUrl, String fullMessage) {
        this.openOrder = openOrder;
        this.notificationMessage = notificationMessage;
        this.date = date;
        this.title = title;
        this.imageUrl = imageUrl;
        this.fullMessage = fullMessage;
    }

    public OpenOrder getOpenOrder() {
        return openOrder;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getFullMessage() {
        return fullMessage;
    }
}
