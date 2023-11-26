package Modele;

import java.util.ArrayList;
import java.util.List;

public class StrategieIAP4 implements StrategieIA{



    @Override
    public Coup getCoupIa(PlateauJeu plateauJeu) {
        Grille grille = (Grille) plateauJeu;

        if (grille.getNombreRotation()[1] > 0){ //IA joueur 2
            //tester si rotation gagne
            try {
                grille.gererCoup(new CoupRotationP4(true));
            }catch(Exception e){
                //aucun throw sur une rotation
            }
            if(grille.estGagne(2)){
                grille.annulerCoup();
                grille.getNombreRotation()[1]--;
                return new CoupRotationP4(true);
            }
            grille.annulerCoup();
            try {
                grille.gererCoup(new CoupRotationP4(false));
            }catch(Exception e){
                //aucun throw sur une rotation
            }
            if(grille.estGagne()){
                grille.annulerCoup();
                grille.getNombreRotation()[1]--;
                return new CoupRotationP4(false);
            }
            grille.annulerCoup();
        }

        //liste des coups
        List<List<Coup>> classementCoups = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            classementCoups.add(new ArrayList<>());
        }

        //trouver la valeur de chaque coup
        for (int colonne = 0; colonne < 7; colonne++){
            int valeurCoup = grille.getValeurCoup(colonne,2);
            classementCoups.get(valeurCoup).add(new CoupP4(colonne,grille.color(2)));
        }
        for (int i = 7; i > 0 ; i--){
            if (!classementCoups.get(i).isEmpty()){
                try {
                    grille.gererCoup(classementCoups.get(i).get(0));
                } catch (CoupInvalideException e) {
                    continue;
                }
                //teste si rotaion win
                if (grille.getNombreRotation()[1] > 0){ //IA joueur 2
                    try {
                        grille.gererCoup(new CoupRotationP4(true));
                    }catch(Exception e){
                        //aucun throw sur une rotation
                    }
                    if(grille.estGagne(1)){
                        grille.annulerCoup();
                        classementCoups.get(i).remove(0);
                        continue;
                    }
                    grille.annulerCoup();

                    try {
                        grille.gererCoup(new CoupRotationP4(false));
                    }catch(Exception e){
                        //aucun throw sur une rotation
                    }
                    if(grille.estGagne(1)){
                        grille.annulerCoup();
                        classementCoups.get(i).remove(0);
                        continue;
                    }
                    grille.annulerCoup();
                }
                grille.annulerCoup();
                return classementCoups.get(i).get(0);
            }
        }

        return null; // arrive jamais
    }

}
