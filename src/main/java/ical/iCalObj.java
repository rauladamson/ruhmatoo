package ical;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateStart;
import biweekly.property.RecurrenceRule;
import biweekly.util.Duration;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class iCalObj { // klass CalendarEvent objektide hoidmiseks

    //private HashMap<String, CalendarEvent> events;
    private ArrayList<CalendarEvent> events;
    private URL iCalLink;

    public iCalObj(URL iCalLink,  List<VEvent> eventsInput) {
        this.iCalLink = iCalLink;
        this.events = new ArrayList<>();
        for (VEvent event : eventsInput) {this.handleEvent(event);}
    }

    public ArrayList<CalendarEvent> getEvents() {return events;}

    public JsonArray getEventsJSON() {
        Gson gson = new GsonBuilder().registerTypeAdapter(CalendarEvent.class, new CalendarEventSerializer()).create();
        JsonArray jsonArray = new JsonArray();
        events.stream().map(gson::toJsonTree).forEach(jsonArray::add);
        return jsonArray;
    }



    public URL getiCalLink() {
        return iCalLink;
    }

    void handleEvent(VEvent event) {

        String uID = event.getUid().getValue(); // sündmuse unikaalne ID
        String summary = event.getSummary().getValue(); // sündmuse kirjeldus (nimetus ÕIS-is)
        String location = event.getLocation() != null ? event.getLocation().getValue() : "-"; // sündmuse toimumiskoht
        String description = event.getDescription().getValue().toString(); // sündmuse kirjeldus
        String categories = event.getCategories().get(0).getValues().get(0); // sündmuse kategooria

        Date startDate = event.getDateStart().getValue(); // sündmuse algusaeg
        Long duration = event.getDuration() != null ? event.getDuration().getValue().toMillis() : 0; // sündmuse kestvus, null-safe

        RecurrenceRule recurrenceRule = event.getRecurrenceRule(); // sündmuse kordumise reegel

        ArrayList<Date> occurenceDates = new ArrayList<>(); // luuakse tühi massiiv sündmuse toimumisaegade hoidmiseks
        if (recurrenceRule != null) { // kui sündmusel on kordumise reegel (recurrenceRule), siis lisatakse kõik toimumisajad loodud massiivi
            DateIterator iterator = recurrenceRule.getDateIterator(startDate, TimeZone.getDefault()); // luuakse uus DateIterator objekt
            while (iterator.hasNext()) { // kuni iteratoril on järgmine element
                Date occurrence = iterator.next(); // leitakse järgmine element
                occurenceDates.add(occurrence); // ja lisatakse see massiivi
            }
        }

        //System.out.println(summary + " " + description + " " + occurenceDates.size());

        // TODO mis juhtub siis, kui aegu polegi ehk occurenceDates.size() == 0? (hetkel eeldame, et on alati vähemalt 1, aga kas saab olla ka ilma ajata sündmusi?)
        CalendarEvent newEvent = (occurenceDates.size() > 1) ? // kontrollitakse, kas sündmusel on rohkem kui 1 toimumisaeg
                new RecurringEvent(uID, summary, location, description, categories, startDate, duration, occurenceDates) :  // kui jah, siis luuakse uus RecurringEvent objekt ning kõik toiumisajad lisatakse sellele
                new OneTimeEvent(uID, summary, location, description, categories, startDate, duration); // kui ei, siis luuakse uus OneTimeEvent objekt
        events.add(newEvent); // sündmus lisatakse kalendrisse
    }


// allikas: https://github.com/Zukkari/java-serialization-template
    public JSONObject toJson() {
        Gson gson = new GsonBuilder().registerTypeAdapter(iCalObj.class, new iCalObjSerializer()).create();
        return new JSONObject(gson.toJson(this));
    }

    public String saveToFile() {
        //System.out.println("Saving to file");

        ICalendar ical = new ICalendar();

        for (CalendarEvent event : this.getEvents()) {

            for (Date occurenceDate: event.getOccurrences()) {
                VEvent vEvent = new VEvent();
                vEvent.setSummary(event.getSummary());
                vEvent.setLocation(event.getLocation());
                vEvent.setDateStart(new DateStart(occurenceDate, false));

                Duration duration = Duration.builder()    // Create a Duration instance
                        .hours((int) (event.getDuration() / (1000 * 60 * 60)))
                        .minutes((int) ((event.getDuration() / (1000 * 60)) % 60))
                        .seconds((int) ((event.getDuration() / 1000) % 60))
                        .build();

                vEvent.setDuration(duration);
                ical.addEvent(vEvent);
            }
        }

        return Biweekly.write(ical).go();
    }


}
