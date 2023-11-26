package Modele;

public interface PlateauJeu {
    public void initialiser();
    public boolean partieTerminee();
    public void gererCoup(Coup coup) throws CoupInvalideException;
    public void initialiserContrainte(int choix);
    public boolean estGagne();
}
