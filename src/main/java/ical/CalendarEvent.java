package ical;

import java.net.URL;
import java.util.Date;
import java.util.TimeZone;
import biweekly.property.RecurrenceRule;
import biweekly.util.Duration;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import org.json.JSONObject;

public class CalendarEvent {

    private URL iCalLink;
    private String summary;
    private Date start;
    private Date end;
    private RecurrenceRule recurrenceRule;

    public CalendarEvent(URL iCalLink, String summary, Date start, Date end, RecurrenceRule recurrenceRule) {
        this.iCalLink = iCalLink;
        this.summary = summary;
        this.start = start;
        this.end = end;
        this.recurrenceRule = recurrenceRule;
    }

    public String getSummary() {
        return summary;
    }

    public Date getStart() {
        return start;
    }

    public URL getiCalLink() {
        return iCalLink;
    }

    public void setiCalLink(URL iCalLink) {
        this.iCalLink = iCalLink;
    }

    public Date getEnd() {
        return end;
    }

    public RecurrenceRule getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void calculateEndFromDuration(Duration duration) {
        if (end == null && duration != null) {
            long durationMillis = duration.toMillis();
            this.end = new Date(start.getTime() + durationMillis);
        }
    }

    public DateIterator getRecurrenceIterator() {
        if (recurrenceRule != null) {
            return recurrenceRule.getDateIterator(start, TimeZone.getDefault());
        }
        return null;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("summary", getSummary());
        jsonObject.put("start", getStart().toString());
        jsonObject.put("end", getEnd().toString());
        return jsonObject;
    }

}


