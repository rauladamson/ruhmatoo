package oppeaine;

import com.google.gson.*;
import ical.CalendarEvent;
import ical.CalendarEventSerializer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

public class Oppeaine {
    protected final JSONObject internalJsonData; // TODO mis osa sellest vajalik on?

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
       // System.out.println(jsonText);
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

    /**
     * Konstruktor olemasoleva JSON-i põhjal õppeaine loomiseks.
     * NB! On eeldatud, et sellele konstruktorile antakse korrektne JSONObject (ei throwi).
     * @param inputJson Sisendobjekt.
     */
    public Oppeaine(JSONObject inputJson) {
       // System.out.println(inputJson.getClass() + " "  + inputJson);
        this.internalJsonData = new JSONObject(inputJson.toMap());

        if (inputJson.has("parent_code")) {
            this.structCode = inputJson.getString("parent_code");
        } else {
            this.structCode = inputJson.getString("code");
        }
        this.lastUpdated = inputJson.getString("last_update");
    }

    public Oppeaine(JsonObject jsonText) throws JSONException {
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

    public Oppeaine() {
        this.internalJsonData = new JSONObject();
        this.structCode = "";
        this.lastUpdated = "";
    }


    /*public Oppeaine(String code, String name, Integer ecTs) {
        this.internalJsonData = new JSONObject();
        this.internalJsonData.put("code", code);
        this.internalJsonData.put("title", new JSONObject().put("et", name));
        this.internalJsonData.put("credits", ecTs);
        this.structCode = code;
        this.lastUpdated = "";
    }*/

    /**
     * Tagastab õppeaine JSON-i kujul.
     * NB! Veebirakenduse jaoks kasutada võimalusel convertToJsonForDisplay() meetodit.
     * @return Koopia õppeaine kogu infost.
     */
    public JSONObject convertToJson() {
        return new JSONObject(this.internalJsonData.toMap()); // Deep copy JSON objektist
    }

    /**
     * Tagastab aine JSONi ainult omadustega, mida on vaja kasutajale näidata.
     * Teeb JS-is elu lihtsamaks ja saadetavad andmed väiksemaks.
     * @return Oppeaine lihtsustatud JSON kujul.
     */
    public JSONObject convertToJsonForDisplay() {
        JSONObject tagastatavObjekt = new JSONObject();
        tagastatavObjekt.put("EAP", this.getECTs());
        tagastatavObjekt.put("Nimi", this.getName());
        tagastatavObjekt.put("Ainekood", this.getCode());
        tagastatavObjekt.put("hetkeseVersiooniLink", "https://ois2.ut.ee/#/courses/" + this.getCode() + "/details"); // TODO: muuta, et see tagastaks tegelikult hetkese versiooni
        //TODO: Tagastada aine tagasiside, tööjaotus jm vajalik

        return tagastatavObjekt;
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


    public String getCode() {return structCode;}

    public String getLastUpdated() {
        return lastUpdated;
    }

    protected JSONObject getInternalJsonData() {return this.internalJsonData;}


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

    // Peaksime seda planeerija all tegema ma arvan, see on liiga spetsiifiline
    // Arvan, et peaksime tegema nadalas olevate tundide arvutamiseks eraldi meetodi, et me saaks hiljem teha ka planeerija nädala kaupa "bitesized pieces"
    // Kui meil on see meetod olemas, siis saame seda kasutada ka teistes meetodites, kus on vaja leida tehtud tunnid mingil nädalal või leida, kui palju on veel teha jne.

    /*
    public int leiaTehtavadAineTootunnidSemestris() {
        int tootundesemestris = this.getECTs() * 26;
        double keskmiseltNadalas = (double) tootundesemestris / this.getNadalaidSemestris();
        return (int) (tootundesemestris - ( keskmiseltNadalas * this.getNadalaidTehtud()));
    }

    public int leiaTootunnidNadalas(int nadalaNr) {
        // summeeroid kõigi ainete täätunnid sel nädalal
        // kui esimene nädal: IF(H3=TRUE;0;I2 ÷COUNTA(B3:B18))


        int tunde = 0;
        // kui sel nädala on õppetöö tehtud, siis oin tunde 0, muul juhul

        // kui ei ole esimene nädal: IF(H4=TRUE;0;(I2  − J3)÷COUNTA(B4:B18))

        if (this.getNadalaidTehtud() < nadalaNr) {
            /* if (nadalaNr > 1) {
            tunde = this.leiaTehtavadAineTootunnidSemestris()  / (this.getNadalaidSemestris() - nadalaNr)

        }
        else if ((nadalaNr == 1) && ( this.getNadalaidTehtud() == 0)){
        tunde = this.leiaTehtavadAineTootunnidSemestris() / this.getNadalaidSemestris();

        }
        }

        return tunde;
    }
    */


    @Override
    public int hashCode() {
        // TODO: Sarnaselt equals(Oppeaine)-le peaks olema võimalik optimiseerida hashCode-i unikaalsust garanteerivate parameetrite järgi
        return super.hashCode();
    }
}
