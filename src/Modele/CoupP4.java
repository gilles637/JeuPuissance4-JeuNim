package Modele;

public class CoupP4 implements Coup{
    private int colonne;
    private String couleur;

    public CoupP4(int colonne, String couleur) {
        this.colonne = colonne;
        this.couleur = couleur;
    }

    public int getColonne() {
        return colonne;
    }

    public String getCouleur() {
        return couleur;
    }

    @Override
    public String toString() {
        return "colonne : " + (colonne + 1) + " couleur : " + couleur;
    }
}
