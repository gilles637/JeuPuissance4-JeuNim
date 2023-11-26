package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class JoueurP4 extends Joueur{

    public JoueurP4(String nom, boolean estIa, int numJoueur) {
        super(nom, estIa);
    }

    public void setStrategieIA(PlateauJeu plateauJeu) {
        if (this.isIa()){
            this.strategieIA = new StrategieIAP4();
        }
    }


    @Override
    public Coup getCoupIa(PlateauJeu plateauJeu) {
        return strategieIA.getCoupIa(plateauJeu);
    }
}
