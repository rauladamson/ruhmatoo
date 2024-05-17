package oppeaine;

public class KasutajaOppeaine extends Oppeaine{
    private int eeldatavTooaeg;
    private int tehtudTooaeg;
    private int veelTooaeg;

    public KasutajaOppeaine(Oppeaine oppeaine) {
        super(oppeaine.getInternalJsonData());
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
}
