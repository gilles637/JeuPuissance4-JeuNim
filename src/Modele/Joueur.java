package Modele;

import java.util.Objects;


public abstract class Joueur {
    private boolean ia;
    private String nom;
    private int nbPartiesGagnees;
    protected StrategieIA strategieIA;

    /**
     * @param nom du joueur à créer
     */
    public Joueur(String nom, boolean ia) {
        this.nom = nom;
        this.ia = ia;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the nbPartiesGagnees
     */
    public int getNbPartiesGagnees() {
        return nbPartiesGagnees;
    }

    /**
     * incrémente le nombre de parties gagnées par le joueur
     */
    public void gagnePartie() {
        nbPartiesGagnees++;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return nbPartiesGagnees == joueur.nbPartiesGagnees && Objects.equals(nom, joueur.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, nbPartiesGagnees);
    }

    abstract public Coup getCoupIa(PlateauJeu plateauJeu);

    public boolean isIa() {
        return ia;
    }

    abstract public void setStrategieIA(PlateauJeu plateauJeu);
}

