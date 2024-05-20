package oppeaine;

import com.google.gson.*;
import java.lang.reflect.Type;

// allikas: https://github.com/Zukkari/java-serialization-template
public class OppeaineSerializer  implements JsonSerializer<Oppeaine>{
    @Override
    public JsonObject serialize(Oppeaine oa, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        Gson gson = new Gson();

       jsonObject.add("internalJsonData", gson.toJsonTree(oa.getInternalJsonData().toMap()));
       jsonObject.addProperty("lastUpdated", oa.getLastUpdated());
       jsonObject.addProperty("structCode", oa.getCode());

        return jsonObject;
    }
}