package Modele;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Grille implements PlateauJeu{

    private String [][] lesJetons;
    private List<String [][]> lesJetonsCoupPrecedent;
    private final String VIDE = "     ";
    private final String ROUGE = "  R  ";
    private final String JAUNE = "  J  ";
    private int [] nombreRotation = new int[2];

    public void initialiserContrainte(int choix){
        if(choix == 1){
            nombreRotation[0] = 4;
            nombreRotation[1] = 4;
        }else{
            nombreRotation[0] = 0;
            nombreRotation[1] = 0;
        }
    }

    public Grille(){
        lesJetons= new String[7][7];
        lesJetonsCoupPrecedent = new ArrayList<>();
        sauvegardeCoupPrecedent();
    }

    public void initialiser(){
        for (int i =0 ; i<7; i++){
            for (int j=0; j<7; j++){
                lesJetons[i][j]= VIDE;
            }
        }
    }
    public String toString(){
        String res="   1     2     3     4     5     6     7   \n|";
        for (int i =0 ; i<7; i++){
            for (int j=0; j<7; j++){
                res += lesJetons[i][j] +"|";
            }
            res+="\n|";
        }
        //enlever le dernier baton
        return res.substring(0,res.length()-1);
    }

    private boolean estPlein(){
        boolean plein = true;
        for (String jetons : lesJetons[0]){
            if (VIDE.equals(jetons)){
                plein = false;
                break;
            }
        }
        return plein;
    }

    public boolean partieTerminee() {

        return estGagne(1) || estGagne(2) || estPlein();
    }

    public boolean estGagne(){
        return estGagne(1) || estGagne(2);
    }

    public boolean estGagne(int numJoueur){
        return testerDiagonale(numJoueur) || testerHorizontale(numJoueur) || testerVerticale(numJoueur);
    }


    private boolean testerDiagonale(int numJoueur) {
        for (int numDiagonale = 3; numDiagonale < 10; numDiagonale++)
        {
            String diagonale = getNemeDiagonaleBasGaucheHautDroite(numDiagonale);
            if (testerNJetons(diagonale, color(numJoueur),4)){
                return true;
            }
        }
        for (int numDiagonale = -3; numDiagonale < 4; numDiagonale++)
        {
            String diagonale = getNemeDiagonaleHautGaucheBasDroite(numDiagonale);
            if (testerNJetons(diagonale,color (numJoueur), 4)){
                return true;
            }
        }
        return false;
    }


    /**
     * diagonale de sens haut gauche vers bas droite
     * -6 correspond à la diagonale qui coupe seulement la case [6][0]
     * 0 correspond à la plus grande diagonale
     * 6 correspond à la diagonale qui coupe seulement la case [0][6]
     * @param n entre -6 et 6
     * @return string du contenu des cases
     */
    private String getNemeDiagonaleHautGaucheBasDroite(int n){
        int decalageBas = 0;
        int decalageDroite = 0;
        if (n>0){
            decalageDroite = n;
        }else{
            decalageBas = -n;
        }
        String diagonale = "";

        for (int i = 0; decalageBas+i < 7 && decalageDroite + i < 7; i++) {
            diagonale += lesJetons[decalageBas+i][decalageDroite+i];
        }
        return diagonale;

    }

    /**
     * diagonale de sens bas gauche vers haut droite
     * 0 correspond à la diagonale qui coupe seulement la case [0][0]
     * 6 correspond à la plus grande diagonale
     * 12 correspond à la diagonale qui coupe seulement la case [6][6]
     * @param n entre 0 et 12
     * @return string du contenu des cases
     */
    private String getNemeDiagonaleBasGaucheHautDroite(int n){
        int decalageHaut = 0;
        int decalageDroite = 0;
        if (n>6){
            decalageDroite = n-6;
        }else{
            decalageHaut = n%6;
        }
        String diagonale = "";

        diagonale="";
        for (int i = 0; 6-(decalageHaut+i) > 0 && 6-(decalageDroite+i) > 0; i++)
        {
            diagonale += lesJetons[6-(decalageHaut+i)][decalageDroite+i];
        }
        return diagonale;
    }


    private boolean testerNJetons(String string, String couleur, int n){
        String nJetons=getNStringColor(couleur, n);
        return string.contains(nJetons);
    }

    private int getLignePlacementColonne(int colonne) throws Exception {
        for (int i=6; i>0;i--){
            if (VIDE.equals(lesJetons[i][colonne])){
                return i;
            }
        }
        throw new Exception("colonne Pleine");
    }


    public int getValeurCoup(int colonne, int numJoueur) {
        //trouver l'emplacement du jeton que l'on fait tomber
        int ligne = 0;
        try {
            ligne = getLignePlacementColonne(colonne);
        } catch (Exception e) {
            return 0; // la colonne est rempli
        }

        //stock max de jetons que l'on peut alligner pour chaque joeueur
        int[] compteur = new int[3];

        //Pour chaque joueur
        for (int numJoueurVariable = 1; numJoueurVariable < 3; numJoueurVariable++){
            try{
                gererCoup(new CoupP4(colonne,color(numJoueurVariable)));
                compteur[numJoueurVariable] = compter(-1,-1, ligne, colonne, color(numJoueurVariable))+compter(1,1, ligne, colonne, color(numJoueurVariable)) + 1; // +1 car la case initial n'est pas compter dans les directions
                compteur[numJoueurVariable] = max(compteur[numJoueurVariable], compter(-1,0, ligne, colonne, color(numJoueurVariable))+compter(1,0, ligne, colonne, color(numJoueurVariable)) + 1);
                compteur[numJoueurVariable] = max(compteur[numJoueurVariable], compter(-1,1, ligne, colonne, color(numJoueurVariable))+compter(1,-1, ligne, colonne, color(numJoueurVariable)) + 1);
                compteur[numJoueurVariable] = max(compteur[numJoueurVariable], compter(0,-1, ligne, colonne, color(numJoueurVariable))+compter(0,1, ligne, colonne, color(numJoueurVariable)) + 1);
                annulerCoup();
            }catch (Exception e){
                return 0; //colonne pleine
            }
        }

        //appliquerBareme
        for (int i = 1; i < 3; i++){
            compteur[i] = (compteur[i]-1)*2;
        }

        //ajout +1 pour la couleur du joueur
        if (numJoueur == 1){
            compteur[1]++;
        }else{
            compteur[2]++;
        }

        //selection du max
        int valeurCoup = max(compteur[1],compteur[2]);

        return valeurCoup;
    }


    private int compter(int deltaColonne, int deltaLigne, int ligne, int colonne, String color) {
        // si depasse ligne ou couleur correspond pas
        if (ligne+deltaLigne > 6 || ligne+deltaLigne < 0 ||
            colonne+deltaColonne > 6 || colonne+deltaColonne <0 ||
            !lesJetons[ligne+deltaLigne][colonne+deltaColonne].equals(color)){
            return 0;
        }else {
            return 1 + compter (deltaColonne, deltaLigne, ligne+deltaLigne,colonne+deltaColonne, color );
        }
    }

    //IA joueur 2
    private String getNStringColor(String color,int n){
        if (n == 0) {
            return "";
        }
        return color+getNStringColor(color,n-1);
    }


    private boolean testerHorizontale(int numJoueur) {
        for (int i=6; 0<=i;i--){
            if (testerNJetons(getNiemeLigne(i), color(numJoueur), 4)){
                return true;
            }
        }
        return false;
    }

    private String getNiemeLigne(int n){
        String ligne="";
        for (int j=0; j<7;j++){
            ligne += lesJetons[n][j];
        }
        return ligne;
    }

    private boolean testerVerticale(int numJoueur){
        for (int i=6; 0<=i;i--){
            if (testerNJetons(getNemeColonne(i),color(numJoueur), 4)){
                return true;
            }
        }
        return false;
    }

    private String getNemeColonne(int n){
        String colonne="";
        for (int j=0; j<7;j++){
            colonne += lesJetons[j][n];
        }
        return colonne;
    }

    public void gererCoup(Coup coup) throws CoupInvalideException  {
        if (coup instanceof CoupRotationP4){
            CoupRotationP4 coupRotationP4 = (CoupRotationP4) coup;

            if (coupRotationP4.isHorraire()){
                rotation(true);
                sauvegardeCoupPrecedent();
            }else{
                rotation(false);
                sauvegardeCoupPrecedent();
            }
        }else{
            CoupP4 coupP4 = (CoupP4) coup;
            int colonne = coupP4.getColonne();
            String couleur = coupP4.getCouleur();
            if (VIDE.equals(lesJetons [0][colonne])){
                lesJetons[0][colonne]= couleur;
                graviter(colonne,0);
                sauvegardeCoupPrecedent();
            }else {
                throw new CoupInvalideException("Le coup est invalide, rejouez");
            }
        }
    }

    private void sauvegardeCoupPrecedent(){
        lesJetonsCoupPrecedent.add(copyGrille(lesJetons));
    }

    public void annulerCoup(){
        lesJetons = copyGrille(lesJetonsCoupPrecedent.get(lesJetonsCoupPrecedent.size()-2));
        lesJetonsCoupPrecedent.remove(lesJetonsCoupPrecedent.size()-1);
    }

    private String[][] copyGrille (String[][] input){
        String[][] grilleASauvegarder = new String[7][7];
        for (int ligne = 0; ligne < 7; ligne++){
            for (int colonne = 0; colonne < 7; colonne++){
                grilleASauvegarder[ligne][colonne] = input[ligne][colonne];
            }
        }
        return grilleASauvegarder;
    }


    public String color(int numJoueur) {
        if (1==numJoueur){
            return ROUGE;
        }
        else {
            return JAUNE;
        }
    }


    private void graviter(int colonne, int ligne){
        for (int i=6; i>ligne;i--){
            if (VIDE.equals(lesJetons[i][colonne])){
                lesJetons[i][colonne]=lesJetons[ligne][colonne];
                lesJetons[ligne][colonne]=VIDE;
                break;
            }
        }
    }

    private void rotation(boolean horraire) {
        //si choix rotation = 0 --> horraire sinon antihorraire
        int deltaRotationColonne = 0;
        int deltaRotationLigne = 0;
        if (horraire){
            deltaRotationColonne = 0;
            deltaRotationLigne = 6;
        }else {
            deltaRotationColonne = 6;
            deltaRotationLigne = 0;
        }


        String[][] nouvelleGrille = new String[7][7];
        for (int ligne = 0; ligne < 7 ; ligne++){
            for (int colonne = 0 ; colonne < 7 ; colonne++ ){
                nouvelleGrille[ligne][colonne]=lesJetons[abs(deltaRotationLigne-colonne)][abs(deltaRotationColonne-ligne)];
            }
        }

        lesJetons=nouvelleGrille;

        for (int i = 6 ; i > -1 ; i--){
            for (int j = 0; j < 7 ; j++){
                graviter(j,i);
            }
        }
    }


    public int[] getNombreRotation() {
        return nombreRotation;
    }

}
