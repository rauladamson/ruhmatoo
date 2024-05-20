package ical;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.DateStart;
import biweekly.property.RecurrenceRule;
import biweekly.util.Duration;
import biweekly.util.com.google.ical.compat.javautil.DateIterator;
import com.google.gson.JsonArray;
import oppeaine.AineCache;
import oppeaine.KasutajaOppeaine;
import oppeaine.Oppeaine;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import user.User;
import user.UserCache;

public class iCalObj { // klass CalendarEvent objektide hoidmiseks

    //private HashMap<String, CalendarEvent> events;
    private ArrayList<CalendarEvent> events;
    private URL iCalLink;
    private UUID uuid;

    public iCalObj(URL iCalLink,  List<VEvent> eventsInput) {
        this.iCalLink = iCalLink;
        this.events = new ArrayList<>();
        for (VEvent event : eventsInput) {this.handleEvent(event);}
        this.uuid = UUID.randomUUID();
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

    public UUID getUuid() {return uuid;}

    void handleEvent(VEvent event) {

        String eventUid = event.getUid().getValue(); // sündmuse unikaalne ID
        String summary = event.getSummary().getValue(); // sündmuse kirjeldus (nimetus ÕIS-is)
        String location = event.getLocation() != null ? event.getLocation().getValue() : "-"; // sündmuse toimumiskoht
        String description = event.getDescription().getValue(); // sündmuse kirjeldus
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
                new RecurringEvent(eventUid, summary, location, description, categories, startDate, duration, occurenceDates) :  // kui jah, siis luuakse uus RecurringEvent objekt ning kõik toiumisajad lisatakse sellele
                new OneTimeEvent(eventUid, summary, location, description, categories, startDate, duration); // kui ei, siis luuakse uus OneTimeEvent objekt
        events.add(newEvent); // sündmus lisatakse kalendrisse


        User user = UserCache.getUser();
        HashMap<UUID, KasutajaOppeaine> userCourses = user.getUserCourses();

        try  {
            String[] split = summary.split(" ");
            Oppeaine oa = AineCache.getAine(split[split.length - 1]);
           //System.out.println(oa);
            if(!userCourses.containsKey(oa.getCode())) {user.addCourse(new KasutajaOppeaine(UUID.fromString(oa.getProperty("uuid")), oa.getECTs()),
                    newEvent,
                    oa.getECTs());}

        } catch (Exception e) {
            System.out.println("Ei leidnud ainekoodi");
        }
    }


// allikas: https://github.com/Zukkari/java-serialization-template
    public JSONObject toJson() {
        Gson gson = new GsonBuilder().registerTypeAdapter(iCalObj.class, new iCalObjSerializer()).create();
        return new JSONObject(gson.toJson(this));
    }

    private ICalendar generateICal(){
        ICalendar ical = new ICalendar();

        for (CalendarEvent event : this.getEvents()) {

            //System.out.println(event.getSummary() + " " + event.getDescription() + " " + event.getOccurrences().size());
            for (Date occurenceDate: event.getOccurrences()) {
                VEvent vEvent = new VEvent();
                vEvent.setSummary(event.getSummary());
                vEvent.setLocation(event.getLocation());
                vEvent.setDateStart(new DateStart(occurenceDate, true));
                //System.out.println(event.getDuration());

                Duration duration = Duration.builder()    // Create a Duration instance
                        .hours((int) (event.getDuration() / (1000 * 60 * 60)))
                        .minutes((int) ((event.getDuration() / (1000 * 60)) % 60))
                        .seconds((int) ((event.getDuration() / 1000) % 60))
                        .build();

                vEvent.setDuration(duration);
                ical.addEvent(vEvent);
            }
        }

        return ical;
    }

    public File saveToFile(boolean temp)  {
        File tempFile;
        if (temp) {tempFile = new File(System.getProperty("user.dir") + "/src/main/webapp/", "calendar.ics");}
        else {
            String uuid = UUID.randomUUID().toString();
            String filename = "calendar_" + uuid + ".ics";
            tempFile = new File(System.getProperty("user.dir") + "/src/main/webapp/ics-files/", filename);
        }
        ICalendar ical = generateICal();

        try (FileWriter writer = new FileWriter(tempFile)) {writer.write( Biweekly.write(ical).go());}
        catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

}
