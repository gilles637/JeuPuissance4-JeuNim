package Modele;

public class CoupRotationP4 implements Coup {
    private boolean horraire;

    public CoupRotationP4(boolean horraire) {
        this.horraire = horraire;
    }

    public boolean isHorraire() {
        return horraire;
    }

    @Override
    public String toString() {
        if (horraire){
            return "rotation horraire";
        }
        return "rotation antihorraire";
    }
}
