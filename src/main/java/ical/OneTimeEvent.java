package ical;

import org.json.JSONObject;
import java.util.Date;

public class OneTimeEvent extends CalendarEvent { // Tegemist on CalendarEvent alamklassiga, mis realiseerib liidest CalendarInterface

    public OneTimeEvent(String uid, String summary, String location, String description, String categories, Date start, Long duration) {
        super(uid, summary, location, description, categories, start, duration);
        this.findEnd();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("recurring", false);
        return jsonObject;
    }

    public void calculateEndFromDuration() {
        if (end == null && this.duration != null) {
            this.setEnd(new Date(start.getTime() + this.duration));
        }
    }

    @Override
    public void findEnd() {this.calculateEndFromDuration();} // lõppkuupäev määratakse kestvuse põhjal

}
