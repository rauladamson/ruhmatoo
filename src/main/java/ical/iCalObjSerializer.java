package ical;

// allikas: https://github.com/Zukkari/java-serialization-template

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class iCalObjSerializer implements JsonSerializer<iCalObj> {
    @Override
    public JsonElement serialize(iCalObj iCalObj, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", iCalObj.getUuid().toString());
        jsonObject.addProperty("iCalLink", iCalObj.getiCalLink().toString());
        jsonObject.add("events", iCalObj.getEventsJSON());
        return jsonObject;
    }

}