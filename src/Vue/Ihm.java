package Vue;

import Controleur.ControleurJeu;
import Modele.CoupNim;
import Modele.CoupP4;
import Modele.CoupRotationP4;
import Modele.Joueur;

import java.util.Scanner;

public class Ihm {

    Scanner input;
    ControleurJeu controleurJeu;

    public Ihm() {
        input = new Scanner(System.in);
    }

    public void setControleurJeu(ControleurJeu controleurJeu) {
        this.controleurJeu = controleurJeu;
    }

    public String demanderNomJoueur(int numJoueur) {
        while (true) {
            System.out.println("Entrer un nom pour le joueur numero " + numJoueur);
            if (input.hasNext()) {
                String reponse = input.next();
                input.nextLine();
                return reponse;
            }
        }
    }

    public int demanderNbTas() {
        while (true){
            System.out.println("Entrer un nombre de tas :");
            if (input.hasNext()){
                if (input.hasNextInt()) {
                    int reponse = input.nextInt();
                    if (reponse > 0) {
                        input.nextLine();
                        return reponse;
                    }
                }
                System.out.println("valeur incorrecte, veuillez saisir un entier positif");
                input.nextLine();
            }
        }
    }

    public void affichageEtats(String etat){
        System.out.println("Etats de la partie :\n" + etat);
    }

    public void affichageJoueurActifP4(String etat, Joueur joueurActuel, String couleur) {
        while (true) {
            affichageEtats(etat);
            System.out.println(joueurActuel.getNom() + " : à vous de jouer, séléctioner une colonne entre 1 et 7");
            if (input.hasNext()) {
                if (input.hasNextInt()){
                    int n = input.nextInt()-1;
                    if (n>-1 && n<7) {
                        input.nextLine();
                        controleurJeu.gererCoup(new CoupP4(n,couleur));
                        break;
                    }
                }
                message("valeur invalide, entrer un entier entre 1 et 7");
                input.nextLine();
            }

        }
    }

    public void affichageJoueurActifNim(String etat, Joueur joueurActuel) {
        while (true) {
            affichageEtats(etat);
            System.out.println(joueurActuel.getNom() + " : à vous de jouer un coup sous la forme 'm n' où m est le tas choisi et n le nombre d'allumettes à retirer dans ce tas");
            if (input.hasNext()){
                if (input.hasNextInt()){
                    int m = input.nextInt();
                    if (input.hasNextInt()){
                        int n = input.nextInt();
                        input.nextLine();
                        controleurJeu.gererCoup(new CoupNim(m,n));
                        break;
                    }
                }
                message("valeur invalide, veuillez entrer une valeur valide");
                input.nextLine();
            }
        }
    }

    public void message(String message) {
        System.out.println(message);
    }

    public void afficherVaiqueur(Joueur joueurActuel) {
        System.out.println("Le joueur "+joueurActuel.getNom()+" à gagner la partie");
    }

    public boolean demanderRecommencer() {
        while (true) {
            System.out.println("Voulez vous recommencer");
            if (input.hasNext()) {
                String reponse = input.next().toLowerCase();
                if (reponse.equals("oui") || reponse.equals("o") || reponse.equals("yes") || reponse.equals("y")) {
                    return true;
                } else if (reponse.equals("non") || reponse.equals("n") || reponse.equals("no")) {
                    return false;
                }
            }else{
                System.out.println("Erreur, veuillez choisir 'oui' ou 'non'");
                input.nextLine();
            }
        }
    }

    public void afficherNombreVictoires(Joueur joueur1, Joueur joueur2) {
        System.out.println("Nombre de victoires :\n" +
                joueur1.getNom() + " : " + joueur1.getNbPartiesGagnees() + "\n" +
                joueur2.getNom() + " : " + joueur2.getNbPartiesGagnees());
        if (joueur1.getNbPartiesGagnees()==joueur2.getNbPartiesGagnees()){
            System.out.println("Ex aequo \n ");
        }else if (joueur1.getNbPartiesGagnees()<joueur2.getNbPartiesGagnees()) {
            System.out.println("Le vaiqueur est "+joueur2.getNom());
        }else{
            System.out.println("Le vaiqueur est "+joueur1.getNom());
        }
    }

    public void afficherEgalite() {
        System.out.println("Egalité :");
    }

    public int choixJeu() {
        while (true) {
            System.out.println("Choisir un jeu:\n" +
                    "1 - P4\n"+
                    "2 - Nim");
            if (input.hasNext()) {
                if (input.hasNextInt()){
                    int n = input.nextInt();
                    if (n>0 && n<3) {
                        input.nextLine();
                        return n;
                    }
                }
                message("valeur invalide, veuillez entrer soit 1 soit 2");
                input.nextLine();
            }

        }
    }

    public int demanderLimiteNim() {
        while (true){
            System.out.println("Entrer un nombre maximum d'allumettes que l'on peut retirer à chaque coup (0 = sans limite)");
            if (input.hasNext()){
                if (input.hasNextInt()) {
                    int reponse = input.nextInt();
                    if (reponse > -1) {
                        input.nextLine();
                        return reponse;
                    }
                }
                System.out.println("valeur incorrecte, veuillez saisir un entier positif");
                input.nextLine();
            }
        }
    }

    public void demanderRotationHorraireOuAntihorraire(String etat) {
        while (true) {
            affichageEtats(etat);
            System.out.println("Voulez vous faire une rotation horraire ou antihorraire");
            if (input.hasNext()) {
                String reponse = input.next().toLowerCase();
                if (reponse.equals("horraire") || reponse.equals("h")){
                    controleurJeu.gererCoup(new CoupRotationP4(true));
                    break;
                } else if (reponse.equals("antihorraire") || reponse.equals("a")){
                    controleurJeu.gererCoup(new CoupRotationP4(false));
                    break;
                }
            }else{
                System.out.println("Erreur, veuillez choisir 'horraire' ou 'antihorraire'");
                input.nextLine();
            }
        }
    }

    public boolean demanderNouveauJetonOuRotation() {
        while (true) {
            System.out.println("Voulez vous faire:\n" +
                    "1 - Inserer un nouveau Jeton\n"+
                    "2 - Rotation");
            if (input.hasNext()) {
                if (input.hasNextInt()){
                    int n = input.nextInt();
                    if (n>0 && n<3) {
                        input.nextLine();
                        return n == 1;
                    }
                }
                message("valeur invalide, veuillez entrer soit 1 soit 2");
                input.nextLine();
            }

        }
    }

    public int demanderJeuRotation() {
        while (true) {
            System.out.println("Voulez vous faire une partie :\n" +
                    "1 - Avec rotation\n"+
                    "2 - Sans rotation");
            if (input.hasNext()) {
                if (input.hasNextInt()){
                    int n = input.nextInt();
                    if (n>0 && n<3) {
                        input.nextLine();
                        return n ;
                    }
                }
                message("valeur invalide, veuillez entrer soit 1 soit 2");
                input.nextLine();
            }

        }
    }

    public void afficherNombreRotationRestante(Joueur joueur, int i) {
        System.out.println(joueur.getNom() + " : il vous reste " + i + " rotation(s) restantes");
    }

    public boolean demanderJouerContreIA() {
        while (true) {
            System.out.println("Voulez vous faire une partie :\n" +
                    "1 - A deux Joueurs\n"+
                    "2 - Contre une IA");
            if (input.hasNext()) {
                if (input.hasNextInt()){
                    int n = input.nextInt();
                    if (n>0 && n<3) {
                        input.nextLine();
                        return n == 2;
                    }
                }
                message("valeur invalide, veuillez entrer soit 1 soit 2");
                input.nextLine();
            }

        }
    }

    public void afficherCoupIA(String coup) {
        System.out.println("L'IA à jouer le coup :\n" + coup);
    }
}
