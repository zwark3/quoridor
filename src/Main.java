import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Jeu moteur = new Jeu(4);

        ArrayList<Joueur> tabJoueurs = new ArrayList<>();

        tabJoueurs.add(new Joueur("J1", "R", "\u001B[31m", new int[]{0, 4}, 10));
        tabJoueurs.add(new Joueur("J2", "B", "\u001B[36m", new int[]{8, 4}, 10));
        tabJoueurs.add(new Joueur("J3", "V", "\u001B[32m", new int[]{4, 0}, 10));
        tabJoueurs.add(new Joueur("J4", "J", "\u001B[93m", new int[]{4, 8}, 10));

        moteur.initialiserJeu(tabJoueurs);

        moteur.afficherPlateauJeu(tabJoueurs);

        moteur.bougerPion(tabJoueurs.getFirst(), moteur.coordsProchainMouvement(tabJoueurs.getFirst(), "G"));
        System.out.println(Arrays.toString(tabJoueurs.getFirst().coordsPion));
        moteur.afficherPlateauJeu(tabJoueurs);

    }
}

