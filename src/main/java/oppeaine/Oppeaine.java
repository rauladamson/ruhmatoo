package oppeaine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Oppeaine {

    private Integer ECTs;
    private String text, name, uuid;
    private HashMap<String, ?> data;

    public Oppeaine(String text) {
        this.text = text;
        this.data = new HashMap<String, Integer>();
        this.getDataFromJSON(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUuid() {
        return uuid;
    }

    public void setData(HashMap<String, ?> data) {
        this.data = data;
    }

    public void setECTs(Integer eCTs) {
        this.ECTs = eCTs;
    }

    public void setData(Map<String, ?> map) {
        this.data = new HashMap<>(map);
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ?> getData() {
        return data;
    }

    public Integer getECTs() {
        return ECTs;
    }

    public String getName() {
        return name;
    }

    public void getDataFromJSON(String json) {

        JSONObject jo = new JSONObject(json);  // luuakse uus JSON objekt (sõne json-iks)

        this.setUuid(jo.get("uuid")); // EAPd
        this.setECTs(jo.getInt("credits")); // EAPd
        this.setData(jo.getJSONObject("additional_info").getJSONObject("hours").toMap());
        this.setName(jo.getJSONObject("title").get("et").toString());
    }

    private void setUuid(Object uuid) {
        this.uuid = uuid.toString();
    }

    private void returnObjAsJSON(Oppeaine oa) { //TODO
    }

    private Oppeaine readObjFromJSON(String json) { //TODO
        return null;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getName() + " " + this.getECTs() + " " + this.getData();
    }


}
