package oppeaine;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Oppeaine {

    public String text;
    public Integer ECTs;
    public String name;

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

        JSONObject jo = new JSONObject(json);

        System.out.println(jo.get("uuid"));
        this.setECTs(jo.getInt("credits")); // EAPd
        this.setData(jo.getJSONObject("additional_info").getJSONObject("hours").toMap());
        this.setName(jo.getJSONObject("title").get("et").toString());
        
        
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getName() + " " + this.getECTs() + " " + this.getData();
    }

}
