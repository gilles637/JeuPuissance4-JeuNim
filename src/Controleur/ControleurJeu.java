package Controleur;

import Modele.*;
import Vue.Ihm;

public abstract class ControleurJeu {

    protected Joueur joueur1;
    protected Joueur joueur2;
    protected Joueur joueurActuel;
    protected Ihm ihm;
    protected PlateauJeu plateauJeu;

    public ControleurJeu(Ihm ihm){
        this.ihm = ihm;
        this.ihm.setControleurJeu(this);

    }


    public void jouer(){

        plateauJeu.initialiser();
        plateauJeu.initialiserContrainte(demanderContrainte());
        joueur2.setStrategieIA(plateauJeu);
        joueurActuel = joueur2;

        while (!plateauJeu.partieTerminee()){
            nouvelleManche();
        }
        ihm.affichageEtats(etatsPartie());
        if (plateauJeu.estGagne()){
            ihm.afficherVaiqueur(joueurActuel);
            joueurActuel.gagnePartie();
        }else{
            ihm.afficherEgalite();
        }
        if (ihm.demanderRecommencer()){
            jouer();
        }else{
            ihm.afficherNombreVictoires(joueur1,joueur2);
        }
    }

    protected abstract int demanderContrainte();

    private void changementJoueur(){
        if (joueurActuel.equals(joueur1)){
            joueurActuel=joueur2;
        }else{
            joueurActuel=joueur1;
        }
    }

    protected int numJoueur(){
        if (joueurActuel.equals(joueur1)) {
            return 1;
        }
        return 2;
    }

    public void gererCoup(Coup coup){
        try{
            plateauJeu.gererCoup(coup);
            if (joueurActuel.isIa()){
                ihm.afficherCoupIA(coup.toString());
            }
        }catch(CoupInvalideException e){
            ihm.message(e.getMessage());
            affichageJoueurActif(plateauJeu.toString(),joueurActuel);
        }
    }

    private void nouvelleManche(){
        changementJoueur();
        if (joueurActuel.isIa()){
            gererCoup(joueurActuel.getCoupIa(plateauJeu));
        }else{
            affichageJoueurActif(etatsPartie(),joueurActuel);
        }
    }

    protected abstract void affichageJoueurActif(String etatsPartie, Joueur joueurActuel);

    protected abstract String etatsPartie();

}
