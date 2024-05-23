package oppeaine;

import ical.CalendarEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


import user.User;

import ical.CalendarEvent;
import ical.CalendarEventSerializer;
import ical.iCalObj;
import oppeaine.KasutajaOppeaine;

public class Planeerija {
    private List<KasutajaOppeaine> planeeritavOppeainelist;
    private int nadalateArvSemestris;

    private ArrayList<iCalObj> allCalendars;
    private List<CalendarEvent> events;

    // Peab oppeaine lisama planeerijasse via lisaOppeaine meetod, loogika plaan selline: saame oppeaine objekti, teeme sellest KasutajaOppeaine objekti ja lisame selle planeerijasse
    // Aga saame ka anda KasutajaOppeainele kasutaja poolt määratud töötunnid, või nende coefficient (kui on olemas)
    // TODO Pole veel constructorit teinud, kus saaks määrata argumendid manuaalselt


    public Planeerija() {
        this.planeeritavOppeainelist = new ArrayList<>();
        this.events = new ArrayList<>();
        this.allCalendars = new ArrayList<>();
    }

    public void lisaOppeaine(Oppeaine oppeaine) {
        this.planeeritavOppeainelist.add(new KasutajaOppeaine(UUID.fromString(oppeaine.getProperty("uuid")), oppeaine.getECTs()));
    }

    // Testing potential fix for the previous usage by forcing the calculate workload method to set the workloads

    public void calculateWorkload() {
        for (KasutajaOppeaine oppeaine : planeeritavOppeainelist) {
            int eeldatavTooaeg = 0;
            int tehtudTooaeg = 0;
            int veelTooaeg = 0;

            for (CalendarEvent event : oppeaine.getCourseEvents().values()) {
                System.out.println("Processing event: " + event.getCategories() + ", Work amount: " + event.getWorkAmount());
                eeldatavTooaeg += event.getWorkAmount();
                if (event.isDone()) {
                    tehtudTooaeg += event.getWorkAmount();
                } else {
                    veelTooaeg += event.getWorkAmount();
                }
            }

            oppeaine.setEeldatavTooaeg(eeldatavTooaeg);
            oppeaine.setTehtudTooaeg(tehtudTooaeg);
            oppeaine.setVeelTooaeg(veelTooaeg);

            System.out.println("After calculating workload:");
            System.out.println("Expected Workload: " + oppeaine.getEeldatavTooaeg());
            System.out.println("Completed Workload: " + oppeaine.getTehtudTooaeg());
            System.out.println("Remaining Workload: " + oppeaine.getVeelTooaeg());
        }
    }

    public int arvutaTootundekokku() {
        int kokkuTootunde = 0;
        for (KasutajaOppeaine kasutajaOppeaine : planeeritavOppeainelist) {
            HashMap<String, CalendarEvent> eventHashMap = kasutajaOppeaine.getCourseEvents();
            List<CalendarEvent> events = new ArrayList<>(eventHashMap.values());
            kokkuTootunde += arvutaTegemata(events);
        }
        return kokkuTootunde;
    }

    // Eksisteerib kergem viis, kokku - tehtud = tegemata ehk to do. Aga for now eventid on kõik done == false so.
    public int arvutaTootundetehtud() {
        int kokkuTootunde = 0;
        for (KasutajaOppeaine kasutajaOppeaine : planeeritavOppeainelist) {
            HashMap<String, CalendarEvent> eventHashMap = kasutajaOppeaine.getCourseEvents();
            List<CalendarEvent> events = new ArrayList<>(eventHashMap.values());
            kokkuTootunde += arvutaTehtud(events);
        }
        return kokkuTootunde;
    }
    public int arvutaTootundetodo() {
        return arvutaTootundekokku() - arvutaTootundetehtud();
    }

    public int arvutaTehtud(List<CalendarEvent> events ) {
        int tooTehtud = 0;
        for (CalendarEvent event : events) {
            if (event.isDone()) {
                tooTehtud += event.getWorkAmount();
            }
        }
        return tooTehtud;
    }

    public int arvutaTegemata(List<CalendarEvent> events) {
        int tooTodo = 0;
        for (CalendarEvent event : events) {
            if (!event.isDone()) {
                tooTodo += event.getWorkAmount();
            }
        }
        return tooTodo;
    }

    /* nadalateArvSemestris: mitu nädalat kokku on ehk mitme nädala peale õppetöö jaguneb
    *
    * */

    public void setNadalateArvSemestris(int nadalateArvSemestris) {
        this.nadalateArvSemestris = 0; //default method peab veel välja mõtlema.
    }

    public int leiaTootunnidNadalas() {
        // sum kõik tunnid sel nädalal
        return 0;
    }

    public int leiaTootunnidNadalasTehtud() {
        // sum kõik tehtud tunnid sel nädalal
        return 0;
    }

    public void printWorkload() {
        System.out.println("Kokku töötunde: " + arvutaTootundetehtud());
        System.out.println("Tehtud töötunde: " + arvutaTootundetehtud() );
        System.out.println("Tegemata töötunde: " + arvutaTootundetodo() );
    }

    public void processEvents(List<CalendarEvent> events) {
        // Logic to process the events and plan accordingly
        for (CalendarEvent event : events) {
            // Example: Print event details
            System.out.println("Event: " + event.getWorkAmount());
        }
    }

    public void main(String[] args) {
    }

    public void addCourses(HashMap<UUID, KasutajaOppeaine> userCourses) {
        System.out.println("Adding courses to planner, Number of courses: " + userCourses.size());
        planeeritavOppeainelist.addAll(userCourses.values());
        System.out.println("Courses added to planner, Total courses in planner: " + planeeritavOppeainelist.size());
    }
}
