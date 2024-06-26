package oppeaine;

import ical.CalendarEvent;

import java.util.HashMap;
import java.util.UUID;

public class KasutajaOppeaine {
    private final UUID uuid;
    private int eeldatavTooaeg;
    private int tehtudTooaeg;
    private int veelTooaeg;
    private boolean done;

    private HashMap<String, CalendarEvent> courseEvents;

    /**
     Pattern leiaAinekood = Pattern.compile("[A-Z]{4}\\.[0-9]{2}\\.[0-9]{3}");
     Matcher matcher = leiaAinekood.matcher(summary);

     try  {
     matcher.find();
     Oppeaine oa = AineCache.getAine(matcher.group());
     // System.out.println(eventUid + " " + oa + " " + categories);
     //AineCache.printCache();
     KasutajaOppeaine kasutajaOppeaine = new KasutajaOppeaine(oa);
     System.out.println(kasutajaOppeaine);

     } catch (Exception e) {
     System.out.println("Ei leidnud ainekoodi");

     }
     */

    private UUID parentUUid;

    public KasutajaOppeaine(UUID parentUUid, Integer ects) {
        this.uuid = UUID.randomUUID();
        this.parentUUid = parentUUid;
        this.eeldatavTooaeg = ects * 26; // default method: 1 eap = 26 tundi
        this.tehtudTooaeg = 0;
        this.veelTooaeg = eeldatavTooaeg - tehtudTooaeg;
        this.courseEvents = new HashMap<>();
    }

    public int getEeldatavTooaeg() {
        return eeldatavTooaeg;
    }

    public void setEeldatavTooaeg(int eeldatavTooaeg) {
        this.eeldatavTooaeg = eeldatavTooaeg;
    }

    public int getTehtudTooaeg() {
        return tehtudTooaeg;
    }

    public void setTehtudTooaeg(int tehtudTooaeg) {
        this.tehtudTooaeg = tehtudTooaeg;
    }

    public int getVeelTooaeg() {
        return veelTooaeg;
    }

    public void setVeelTooaeg(int veelTooaeg) {
        this.veelTooaeg = veelTooaeg;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void addEvent(CalendarEvent newEvent) {
        courseEvents.put(newEvent.getCategories(), newEvent);
    }

    public UUID getUuid() {
        return uuid;
    }

    public HashMap<String, CalendarEvent> getCourseEvents() {
        return courseEvents;
    }

    public UUID getParentUUid() {
        return parentUUid;
    }
}
