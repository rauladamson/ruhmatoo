package user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ical.iCalObj;

import java.lang.reflect.Type;
// allikas: https://github.com/Zukkari/java-serialization-template

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uid", user.getUid().toString());
        jsonObject.add("calendars", user.getUserCalendarsAsJson());
        return jsonObject;
    }

}


