package Main;

import Controleur.ControleurJeu;
import Controleur.ControleurJeuNim;
import Controleur.ControleurJeuP4;
import Vue.Ihm;

public class Main {

    public static void main(String[] args) {
        Ihm ihm = new Ihm();
        //ControleurJeu controleurJeu= ControleurJeu.getControleur(ihm,ihm.choixJeu()) ;
        ControleurJeu controleurJeu;
        if (ihm.choixJeu()==1){
            controleurJeu = new ControleurJeuP4(ihm);
        }else{
            controleurJeu = new ControleurJeuNim(ihm);
        }
        controleurJeu.jouer();


    }
}
