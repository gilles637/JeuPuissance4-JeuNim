package Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategieIANim implements StrategieIA{

    @Override
    public Coup getCoupIa(PlateauJeu plateauJeu) {
        Tas tas = (Tas)plateauJeu;

        //liste des coups
        List<Coup> listeCoups = new ArrayList<>();


        //pour chaque tas
        for (int numTas = 1; numTas < tas.nbTas(); numTas++){
            //si il reste des allumettes
            if (tas.nbAllumettes(numTas) != 0){
                int numAllumetteMax = tas.nbAllumettes(numTas);
                if (tas.limite == 0) {
                    for (int numAllumette = 1; numAllumette <= numAllumetteMax; numAllumette++){
                        listeCoups.add(new CoupNim(numTas,numAllumette));
                    }
                }else {
                    for (int numAllumette = 1; numAllumette <= numAllumetteMax && numAllumette <= tas.limite; numAllumette++){
                        listeCoups.add(new CoupNim(numTas,numAllumette));
                    }
                }
            }
        }
        Random random = new Random();
        return listeCoups.get(random.nextInt(listeCoups.size()));
    }
}
