package oppeaine;

public class KasutajaOppeaine {
    private int eeldatavTooaeg;
    private int tehtudTooaeg;
    private int veelTooaeg;
    private boolean done;

    private Oppeaine algAine;


    public KasutajaOppeaine(Oppeaine oppeaine) {
        this.algAine = oppeaine;
        this.eeldatavTooaeg = oppeaine.getECTs() * 26; // default method: 1 eap = 26 tundi
        this.tehtudTooaeg = 0;
        this.veelTooaeg = eeldatavTooaeg - tehtudTooaeg;
    }

    public int getEeldatavTooaeg() {
        return eeldatavTooaeg;
    }

    public void setEeldatavTooaeg(int eeldatavTooaeg) {
        this.eeldatavTooaeg = eeldatavTooaeg;
    }

    public int getTehtudTooaeg() {
        return tehtudTooaeg;
    }

    public void setTehtudTooaeg(int tehtudTooaeg) {
        this.tehtudTooaeg = tehtudTooaeg;
    }

    public int getVeelTooaeg() {
        return veelTooaeg;
    }

    public void setVeelTooaeg(int veelTooaeg) {
        this.veelTooaeg = veelTooaeg;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
