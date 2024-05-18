package ical;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

public class RecurringEvent extends CalendarEvent  { // Tegemist on CalendarEvent alamklassiga, mis realiseerib liidest CalendarInterface

    private ArrayList<Date> occurrences;

    public RecurringEvent(String uid, String summary, String location, String description, String categories, Date start,  Long duration, ArrayList<Date> occurrences) {
        super(uid, summary, location, description, categories, start, duration);
        this.occurrences = occurrences;
        this.findEnd();
    }

    public ArrayList<Date> getOccurrences() {
        return occurrences;
    }

    public void addOccurrence(Date occurrence) {
        this.occurrences.add(occurrence);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("recurring", true);
        ArrayList<String> occurrenceStrings = new ArrayList<>();
        for (Date occurrence : this.occurrences) {
            occurrenceStrings.add(occurrence.toLocaleString());
        }
        jsonObject.put("occurrences", occurrenceStrings);
        return jsonObject;
    }


    @Override
    public Date getStart() { // TODO lisada esimene toimumisaeg
        return super.getStart();
    }

    @Override
    public Date getEnd() { // TODO lisada viimane toimumisaeg
        return super.getEnd();
    }

    @Override
    public void findEnd() {this.setEnd(occurrences.get(occurrences.size() - 1));} // viimane toimumisaeg määrtaakse lõpukuupäevaks

    @Override
    public boolean isRecurring() {return true;}

}
