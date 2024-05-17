package oppeaine;

import java.util.ArrayList;
import java.util.List;

public class Planeerija {
    private List<KasutajaOppeaine> planeeritavOppeainelist;
    private int nadalateArvSemestris;

    // Peab oppeaine lisama planeerijasse via lisaOppeaine meetod, loogika plaan selline: saame oppeaine objekti, teeme sellest KasutajaOppeaine objekti ja lisame selle planeerijasse
    // Aga saame ka anda KasutajaOppeainele kasutaja poolt määratud töötunnid, või nende coefficient (kui on olemas)
    // TODO Pole veel constructorit teinud, kus saaks määrata argumendid manuaalselt

    public Planeerija() {
        this.planeeritavOppeainelist = new ArrayList<>();
    }

    public void lisaOppeaine(Oppeaine oppeaine) {
        this.planeeritavOppeainelist.add(new KasutajaOppeaine(oppeaine));
    }

    public int arvutaTootundeNadalas() {
        int kokkuTootunde = 0;
        for (KasutajaOppeaine kasutajaOppeaine : planeeritavOppeainelist) {
            kokkuTootunde += leiaTootunnidNadalas(kasutajaOppeaine);
        }
        return kokkuTootunde;
    }

    public int arvutaTootundetehtud() {
        int kokkuTootunde = 0;
        for (KasutajaOppeaine kasutajaOppeaine : planeeritavOppeainelist) {
            kokkuTootunde += leiaTootunnidNadalasTehtud(kasutajaOppeaine);
        }
        return kokkuTootunde;
    }

    public int arvutaTegemata() {
        return arvutaTootundeNadalas() - arvutaTootundetehtud();
    }

    /* nadalateArvSemestris: mitu nädalat kokku on ehk mitme nädala peale õppetöö jaguneb
    *
    * */

    public void setNadalateArvSemestris(int nadalateArvSemestris) {
        this.nadalateArvSemestris = 0; //default method peab veel välja mõtlema.
    }

    public int leiaTootunnidNadalas(Oppeaine oppeaine) {
        // sum kõik tunnid sel nädalal
        return 0;
    }

    public int leiaTootunnidNadalasTehtud(Oppeaine oppeaine) {
        // sum kõik tehtud tunnid sel nädalal
        return 0;
    }

    public  void main(String[] args) {

    }
}
