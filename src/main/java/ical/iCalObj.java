package ical;

import biweekly.component.VEvent;
import biweekly.property.RecurrenceRule;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.*;

public class iCalObj { // klass CalendarEvent objektide hoidmiseks

    private HashMap<String, CalendarEvent> events;
    private URL iCalLink;

    public iCalObj(URL iCalLink,  List<VEvent> eventsInput) {
        this.iCalLink = iCalLink;
        this.events = new HashMap<>();
        for (VEvent event : eventsInput) {this.handleEvent(event);}
    }

    public HashMap<String, CalendarEvent> getEventsMap() {return events;}

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
        events.put(uID, newEvent); // sündmus lisatakse kalendrisse
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray eventsArray = this.events.values().stream()
                .map(CalendarEvent::toJson)
                .collect(JSONArray::new, JSONArray::put, JSONArray::put);

        jsonObject.put("iCalLink", this.iCalLink);
        jsonObject.put("events", eventsArray);

        //System.out.println(jsonObject);
        return jsonObject;
    }

    public JSONArray getEventsMapAsJSON() {

        return this.events.values().stream(). // kõigi kalendrisse listaud sündmuste iteratsioon
                map(CalendarEvent::toJson). // kõik sündmused teisendatakse JSON-formaati (tagastab JSONObject-i)
                collect(JSONArray::new, JSONArray::put, JSONArray::put); // kloodud objektid listaakse JSON-formaadis massiivi (tagastab JSONArray)
    }
}
