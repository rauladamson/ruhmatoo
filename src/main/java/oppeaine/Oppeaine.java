package oppeaine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

public class Oppeaine {
    private final JSONObject internalJsonData;

    /**
     * Konstruktor olemasoleva JSON-i põhjal õppeaine loomiseks.
     * @param jsonText JSON andmed, String formaadis.
     * @throws JSONException Kui JSON-i objekti ei saa luua (ilmselt string ei ole õiges vormingus), visatakse edasi JSONException.
     */
    public Oppeaine(String jsonText) throws JSONException {
        try {
            this.internalJsonData = new JSONObject(jsonText);
        } catch (JSONException e) {
            throw e;
        }
    }

    public JSONObject convertToJson() {
        return new JSONObject(this.internalJsonData.toMap()); // Deep copy JSON objektist
    }

    /**
     * Konstruktor olemasoleva JSON-i põhjal õppeaine loomiseks.
     * NB! On eeldatud, et sellele konstruktorile antakse korrektne JSONObject (ei throwi).
     * @param inputJson Sisendobjekt.
     */
    public Oppeaine(JSONObject inputJson) {
        this.internalJsonData = new JSONObject(inputJson.toMap());
    }

    /**
     * Sisemiste parameetrite muutmiseks. Ei luba lisada uusi parameetreid.
     * NB! Mitte kasutadda, kui just absoluutselt pole vaja. Õppeainete haldamisega peaks tegelema AineCache.
     * @param key Võti.
     * @param newValue Uus väärtus.
     * @throws NoSuchElementException Viskab, kui võtmega antud parameetrit pole juba olemas.
     */
    public void modifyInternalData(String key, String newValue) throws NoSuchElementException {
        if (internalJsonData.opt(key) != null) {
            internalJsonData.put(key, newValue);
        } else {
            throw new NoSuchElementException("Väärtust '" + key + "' ei ole õppeaines olemas.");
        }
    }

    /**
     * Tagastab õppeaine mingi omaduse väärtuse Stringina.
     * @param key Väärtuse võti, nt "ect" või "uuid".
     * @return Omaduse väärtus. Tagastab null, kui vasavat väärtust olemas ei ole.
     */
    public String getProperty(String key) {
        return internalJsonData.opt(key).toString();
    }

    public String getName() {
        return internalJsonData.getJSONObject("title").getString("et");
    }

    public Integer getECTs() {
        return internalJsonData.getInt("credits");
    }

    public String getCode() {
        if (internalJsonData.has("parent_code")) {
            return internalJsonData.getString("parent_code");
        } else {
            return internalJsonData.getString("code");
        }
    }

    /**
     * toString meetod.
     * @return Tagastab inimloetavas vormis aine info. JSON-i saaamiseks kasutada Oppeaine.ConvertToJson().
     */
    @Override
    public String toString() {
        return this.getCode() + " " + this.getName() + " (" + this.getECTs() + " EAP) [" + this.getProperty("uuid") + "]";
    }


}
