package appinventor.ai_sameh.FastBird.api.model;

import java.util.ArrayList;

/**
 * Created by suresh on 21/10/14.
 */
public class Comment {
    private String Comment, Date;
    private int EntryMode;

    public Comment(String comment, String date, int entryMode) {
        Comment = comment;
        Date = date;
        EntryMode = entryMode;
    }

    public String getComment() {
        return Comment;
    }

    public String getDate() {
        return Date;
    }

    public int getEntryMode() {
        return EntryMode;
    }
}