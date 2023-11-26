package Modele;



public class Tas implements PlateauJeu{

    /**
     * représente l'état courant de la partie
     */
    private int[] lesTas;
    int limite;

    /**
     * crée un ensemble de nbTas tas avec 0 allumettes dans chaque tas
     * @param nbTas le nombre de tas de la partie
     */
    public Tas(int nbTas){
        lesTas = new int[nbTas];
    }

    /**
     * ajoute les allumettes dans chacun des tas de la manière suivante :
     * dans le ième tas, on place 2*i - 1 allumettes.
     */
    public void initialiser(){
        for (int i = 0; i < lesTas.length; i++) {
            lesTas[i] = 2 * i + 1;
        }
    }

    @Override
    public void initialiserContrainte(int choix) {
        limite = choix;
    }

    /**
     * Retourne vrai si la partie est terminée et faux sinon
     * @return
     */
    public boolean partieTerminee() {
        return nbAllumette() == 0;
    }

    /**
     * Nombre d'allumettes par tas
     */
    public int nbAllumettes(int numeroTas) {
        return lesTas[numeroTas - 1];
    }

    /**
     * Retourne le nombre total d'allumettes de la partie
     */
    public int nbAllumette(){

    int total = 0;
    for (int nb : lesTas)
        total+=nb;
    return total;
    }

    /**
     * retourne l'état de la partie sous forme d'une chaîne de caractères constituées des batons correspondant au nombre d'allumettes pour chaque tas.
     *
     */
    @Override
    public String toString() {
        String s="";
        for (int nbAllumettes : lesTas) {
            for (int i = 1; i <= nbAllumettes; i++) {
                s+="| ";
            }
           s+="\n";
        }
        return s;
    }

    /**
     * modifie l'état de la partie en fonction du coup passé en paramètre
     * @param coup
     * @throws CoupInvalideException si le coup est invalide
     */
    public void gererCoup(Coup coup) throws CoupInvalideException {
        CoupNim coupNim = (CoupNim)coup;
        int numeroTas= coupNim.getNumeroTas();
        int nb = coupNim.getNbAllumettes();

        if (limite != 0 && nb > limite){
            throw  new CoupInvalideException("Le nombre d'allumete dépasse la limte autourisé (limite actuel = "+ limite +" )");
        }

        if (numeroTas >= 1 &&
                numeroTas <= lesTas.length &&
                nb >= 1 &&
                nb <= nbAllumettes(numeroTas)) {
            lesTas[numeroTas - 1] -= nb;

        } else {
            throw new CoupInvalideException("Le coup est invalide, rejouez");
        }
    }

    @Override
    public boolean estGagne() {
        return partieTerminee();
    }

    public int nbTas(){
        return lesTas.length + 1;
    }
}
