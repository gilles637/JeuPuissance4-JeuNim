package Modele;

public class JoueurNim extends Joueur{


    public JoueurNim(String nom, boolean estIa) {
        super(nom, estIa);
    }

    @Override
    public Coup getCoupIa(PlateauJeu plateauJeu) {
        return strategieIA.getCoupIa(plateauJeu);
    }

    @Override
    public void setStrategieIA(PlateauJeu plateauJeu) {
        if (this.isIa()){
            this.strategieIA = new StrategieIANim();
        }
    }
}
