package oppeaine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Oppeaine {

    public String text;
    public Integer ECTs;
    public String name;
    public String uuid;

    public HashMap<String, ?> data;

    public Oppeaine(String text) {
        this.text = text;
        this.data = new HashMap<String, Integer>();
        this.getDataFromJSON(text);

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

        //System.out.println(" json jõuab");
        //System.out.println(json);
        JSONObject jo = new JSONObject(json);

        //System.out.println("jo jõuab");
        //System.out.println(jo.get("uuid"));

        this.setUuid(jo.get("uuid")); // EAPd
        this.setECTs(jo.getInt("credits")); // EAPd
        this.setData(jo.getJSONObject("additional_info").getJSONObject("hours").toMap());
        this.setName(jo.getJSONObject("title").get("et").toString());

    }

    private void setUuid(Object uuid) {
        this.uuid = uuid.toString();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getName() + " " + this.getECTs() + " " + this.getData();
    }

}
