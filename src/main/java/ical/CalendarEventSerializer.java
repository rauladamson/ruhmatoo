package ical;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

// allikas: https://github.com/Zukkari/java-serialization-template


public class CalendarEventSerializer implements JsonSerializer<CalendarEvent> {
    @Override
    public JsonObject serialize(CalendarEvent event, Type type, JsonSerializationContext jsonSerializationContext) {
        // Create new JSON object which will act
        // as a root to our tree (this represents Kasutaja object itself)
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("summary", event.getSummary());
        jsonObject.addProperty("duration", event.getDuration());
        jsonObject.addProperty("uid", event.getUid());
        jsonObject.addProperty("start", event.getStart().toString());
        jsonObject.addProperty("description", event.getDescription());
        jsonObject.addProperty("end", event.getEnd().toString());
        jsonObject.addProperty("location", event.getLocation());
        jsonObject.addProperty("categories", event.getCategories());
        jsonObject.addProperty("recurring", event.isRecurring());
        jsonObject.addProperty("occurrences", event.getOccurrences().toString());

        return jsonObject;
    }

}