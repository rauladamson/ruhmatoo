package user;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import ical.CalendarEvent;
import ical.CalendarEventSerializer;
import ical.iCalObj;
import oppeaine.KasutajaOppeaine;

import java.util.HashMap;
import java.util.UUID;

import java.util.ArrayList;

public class User {

    private UUID uid;
    private String name, email, password, role;
    private ArrayList<iCalObj> userCalendars;
    private HashMap<UUID, KasutajaOppeaine> userCourses;
    private Integer totalEcts;

    public User(String name, String email, String password, String role) {
        this.uid = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.userCalendars = new ArrayList<>();
        this.userCourses = new HashMap<>();
        this.totalEcts = 0;
    }

    public User() {
        this.uid = UUID.randomUUID();
        this.userCalendars = new ArrayList<>();
        this.userCourses = new HashMap<>();
        this.totalEcts = 0;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<iCalObj> getUserCalendars() {
        return userCalendars;
    }
    public JsonArray getUserCalendarsAsJson() {
        Gson gson = new GsonBuilder().registerTypeAdapter(CalendarEvent.class, new CalendarEventSerializer()).create();
        JsonArray jsonArray = new JsonArray();
        userCalendars.stream().map(gson::toJsonTree).forEach(jsonArray::add);
        return jsonArray;
    }

    public void setUserCalendars(ArrayList<iCalObj> userCalendars) {
        this.userCalendars = userCalendars;
    }

    public HashMap<UUID, KasutajaOppeaine> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(HashMap<UUID, KasutajaOppeaine> userCourses) {
        this.userCourses = userCourses;
    }

    public void addCourse(KasutajaOppeaine course, CalendarEvent newEvent, Integer ects) {
        this.userCourses.put(course.getUuid(), course);
        course.addEvent(newEvent);
        this.totalEcts += ects;
    }

    public void addCourse(KasutajaOppeaine course, Integer ects) {
        this.userCourses.put(course.getUuid(), course);
        this.totalEcts += ects;
    }

    /* mis kellast mis kellani plaanid tegutseda?

   for e-r:  8 - (geteventsonday: sum duration ) timeblock = 30 min 60 min
   uus event = timeblock
    *
    * */

    @Override
    public String toString() {
        return "Kasutaja [" + uid + "] " + userCourses.size() + " ainet";
    }
    public Integer getTotalECTs() {return this.totalEcts;}


    public static void main(String[] args) {

    }

    public void addCalendar(iCalObj ical) {
        userCalendars.add(ical);
    }
}
