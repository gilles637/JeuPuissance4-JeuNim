package Controleur;

import Modele.*;
import Vue.Ihm;

public class ControleurJeuNim extends ControleurJeu{

    private Tas tas;
    private int limite;



    public ControleurJeuNim(Ihm ihm) {
        super(ihm);
        tas = new Tas(ihm.demanderNbTas());
        super.plateauJeu = tas;
        boolean contreIa = ihm.demanderJouerContreIA();
        joueur1 = new JoueurNim(ihm.demanderNomJoueur(1),false);
        if (!contreIa){
            joueur2 = new JoueurNim(ihm.demanderNomJoueur(2),false);
        }else{
            joueur2 = new JoueurNim("IA",true);
        }
    }


    @Override
    protected String etatsPartie() {
        return tas.toString();
    }

    @Override
    protected void affichageJoueurActif(String etatsPartie, Joueur joueurActuel) {
        ihm.affichageJoueurActifNim(etatsPartie,joueurActuel);
    }

    @Override
    protected int demanderContrainte() {
        return  ihm.demanderLimiteNim();
    }
}
