package Controleur;

import Modele.Coup;
import Modele.Grille;
import Modele.Joueur;
import Modele.JoueurP4;
import Vue.Ihm;

public class ControleurJeuP4 extends ControleurJeu{

    private Grille grille;

    public ControleurJeuP4(Ihm ihm) {
        super(ihm);
        grille = new Grille();
        super.plateauJeu = grille;
        boolean contreIa = ihm.demanderJouerContreIA();
        joueur1 = new JoueurP4(ihm.demanderNomJoueur(1),false,1);
        if (!contreIa){
            joueur2 = new JoueurP4(ihm.demanderNomJoueur(2),false,2);
        }else{
            joueur2 = new JoueurP4("IA",true,2);
        }
    }

    @Override
    protected String etatsPartie() {
        return grille.toString();
    }

    @Override
    protected void affichageJoueurActif(String etatsPartie, Joueur joueurActuel) {
        String couleur = grille.color(numJoueur());
        // if true == NouveauJeton sinon rotation
        if (grille.getNombreRotation()[numJoueur()-1]==0) {
            ihm.affichageJoueurActifP4(etatsPartie,joueurActuel,couleur);
        }else{
            ihm.affichageEtats(etatsPartie);
            //ajouterUnJeton
            if (ihm.demanderNouveauJetonOuRotation()){
                ihm.affichageJoueurActifP4(etatsPartie,joueurActuel,couleur);
            }else{
                if (grille.getNombreRotation()[numJoueur()-1] < 0){
                    //erreur
                    ihm.message("Erreur, vous n'avez plus de rotation disponible");
                    affichageJoueurActif(etatsPartie,joueurActuel);
                }else{
                    ihm.demanderRotationHorraireOuAntihorraire(etatsPartie);
                    grille.getNombreRotation()[numJoueur()-1]--;
                    ihm.afficherNombreRotationRestante(joueurActuel,grille.getNombreRotation()[numJoueur()-1]);
                }
            }
        }
    }

    @Override
    protected int demanderContrainte() {
        return ihm.demanderJeuRotation();
    }

}
