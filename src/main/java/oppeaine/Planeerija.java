package oppeaine;

import java.util.ArrayList;
import java.util.List;

public class Planeerija {
    private List<Oppeaine> planeeritavOppeainelist;
    private int nadalateArvSemestris;

    public Planeerija() {
        this.planeeritavOppeainelist = new ArrayList<>();
    }

    public void lisaOppeaine(Oppeaine oppeaine) {
        this.planeeritavOppeainelist.add(oppeaine);
    }

    public int arvutaTootundeNadalas() {
        int totalWorkHours = 0;
        for (Oppeaine oppeaine : planeeritavOppeainelist) {
            totalWorkHours += oppeaine.leiaTootunnidNadalas();
        }
        return totalWorkHours;
    }

    public int arvutaTootundetehtud() {
        int totalWorkHours = 0;
        for (Oppeaine oppeaine : planeeritavOppeainelist) {
            totalWorkHours += oppeaine.leiaTootunnidNadalasDone();
        }
        return totalWorkHours;
    }

    public int arvutaTegemata() {
        return arvutaTootundeNadalas() - arvutaTootundetehtud();

    }

    /* nadalateArvSemestris: mitu nädalat kokku on ehk mitme nädala peale õppetöö jaguneb
    *
    * */

    public int leiaTootunnidNadalas() {
        // sum kõik tunnid sel nädalal
        return 0;
    }

    public int leiaTootunnidNadalasDone(int nadalaNr) {
        // sum kõik tehtud tunnid sel nädalal
        return 0;
    }


    public  void main(String[] args) {

    }
}
