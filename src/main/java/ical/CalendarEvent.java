package ical;

import java.util.Date;
import org.json.JSONObject;

public class CalendarEvent implements CalendarInterface {  // Klass realiseerib liidest CalendarInterface

    private String uid, summary, location, description, categories;
    Date start;
    Date end;
    Long duration;


    public CalendarEvent(String uid, String summary, String location, String description, String categories, Date start, Long duration) {
        this.uid = uid;
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.categories = categories;
        this.duration = duration;
        this.start = start;
    }

    public String getSummary() {
        return summary;
    }
    public void setEnd(Date end) {this.end = end;}

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", this.uid);
        jsonObject.put("summary", this.getSummary());
        jsonObject.put("start", this.getStart().toLocaleString());
        //System.out.println(this.getStart().toLocaleString());
        jsonObject.put("end", this.getEnd().toString());
        jsonObject.put("location", this.location);
        jsonObject.put("description", this.description);
        jsonObject.put("categories", this.categories);
        jsonObject.put("duration", this.duration.toString());
        return jsonObject;
    }

    public Date getStart() {return this.start;}
    public Date getEnd() {return this.end;}
    public void findEnd() {}
}


