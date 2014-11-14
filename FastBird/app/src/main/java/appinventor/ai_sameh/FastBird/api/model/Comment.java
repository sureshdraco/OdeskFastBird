package appinventor.ai_sameh.FastBird.api.model;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;

import appinventor.ai_sameh.FastBird.util.TimestampUtil;

/**
 * Created by suresh on 21/10/14.
 */
public class Comment implements Comparable<Comment> {
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

	@Override
	public int compareTo(@NonNull Comment comment) {
		try {
			return TimestampUtil.getFastBirdDate(getDate()).compareTo(TimestampUtil.getFastBirdDate(comment.getDate()));
		} catch (ParseException e) {
			return -1;
		}
	}
}