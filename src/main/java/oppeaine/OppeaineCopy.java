package oppeaine;

import com.google.gson.*;
import ical.CalendarEvent;
import ical.CalendarEventSerializer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

public class OppeaineCopy {

    protected final JsonObject internalJsonData;
    private final String lastUpdated, structCode; // Kõige tähtsamad parameetrid cache'imiseks teeme otse memberiteks

    public OppeaineCopy(JsonObject internalJsonData, String lastUpdated, String structCode) {
        this.internalJsonData = internalJsonData;
        this.lastUpdated = lastUpdated;
        this.structCode = structCode;
    }
}
