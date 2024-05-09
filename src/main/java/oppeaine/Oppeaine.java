package oppeaine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

public class Oppeaine {
    private final JSONObject internalJsonData;

    // TODO: Cache'imiseks peaks olema veel võimalik optimiseerida lastUpdated ja structCode'i, kuna nende
    // TODO: Stringidel on vastavad teadaolevad piirangud (esimene on kuupäev, teine teatud pikkuse ja struktuuriga).
    private final String lastUpdated, structCode; // Kõige tähtsamad parameetrid cache'imiseks teeme otse memberiteks

    // Hetkel on klass mõeldud olema immutable - s.t. kui õppeainet uuendada, siis vahetada see uue objektiga välja.
    // Kas oleks parem teha niimodi, et objektide uuendamisel otsitakse erinevusi ja siis hoopis muudetakse ainult see, mida vaja?

    // TODO: Teha konstruktorid protected'iks, et vältida AineCache'ist määda hiilimist(?)
    //       (siis muidugi peaks AineCache'i tegema selle alamklassiks, vüi leidma mingi loogilisema
    //        lahenduse, et ainult AineCache saaks konstruktoreid kutsuda)

    /**
     * Konstruktor olemasoleva JSON-i põhjal õppeaine loomiseks.
     * @param jsonText JSON andmed, String formaadis.
     * @throws JSONException Kui JSON-i objekti ei saa luua (ilmselt string ei ole õiges vormingus), visatakse edasi JSONException.
     */
    public Oppeaine(String jsonText) throws JSONException {
        try {
            this.internalJsonData = new JSONObject(jsonText);

            if (internalJsonData.has("parent_code")) {
                this.structCode = internalJsonData.getString("parent_code");
            } else {
                this.structCode = internalJsonData.getString("code");
            }
            this.lastUpdated = internalJsonData.getString("last_update");
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

        if (inputJson.has("parent_code")) {
            this.structCode = inputJson.getString("parent_code");
        } else {
            this.structCode = inputJson.getString("code");
        }
        this.lastUpdated = inputJson.getString("last_update");
    }

    /**
     * Sisemiste parameetrite muutmiseks. Ei luba lisada uusi parameetreid.
     * NB! Mitte kasutada, kui just absoluutselt pole vaja. Õppeainete haldamisega peaks tegelema AineCache.
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
        // Vana kood on viidud konstruktorisse
        return structCode;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * toString meetod.
     * @return Tagastab inimloetavas vormis aine info. JSON-i saaamiseks kasutada Oppeaine.ConvertToJson().
     */
    @Override
    public String toString() {
        return this.getCode() + " " + this.getName() + " (" + this.getECTs() + " EAP) [" + this.getProperty("uuid") + "]";
    }

    public boolean equals(Oppeaine aine) {
        // EKSPERIMENTAALNE:
        // Iseenesest peaks olema võimalik võrrelda aineid ainult nende koodi ja viimase uuenduse järgi, mis peaks
        // olema kiirem kui terve objekti võrdlemine (aine on päris suur!)
        // ning see peaks olema parem kui default equals meetod, kuna siis ei pea looma iga kord uut ainet. (nt kui
        // cache'i laadimisel leitakse sama aine)

        // TODO: Enne implementatsiooni sättida ainele mingid minimaalsed vajalikud parameetrid - nt pealkiri, kood, UUID, EAPd jne
        // TODO: Muidu oleks võimalik lisada väga ,,õhukesi" aineid (hetkel ainsad vajalikud parameetrid kood ja lastUpdated)
        // TODO: Seda peaks ilmselt kontrollima konstruktoris

        /*
            return (this.lastUpdated).equals(aine.lastUpdated) structCode
        */

        return super.equals(aine);
    }

    @Override
    public int hashCode() {
        // TODO: Sarnaselt equals(Oppeaine)-le peaks olema võimalik optimiseerida hashCode-i unikaalsust garanteerivate parameetrite järgi
        return super.hashCode();
    }
}
