import java.util.ArrayList;

public class Jeu {
    String[][] plateau;
    String[][] mursH;
    String[][] mursV;

    Jeu(int nombreJoueurs) {
        this.plateau = new String[9][9];
        this.mursH = new String[8][9];
        this.mursV = new String[9][8];
    }

    public void initialiserJeu(ArrayList<Joueur> tabJoueurs) {
        for (int ligne = 0; ligne < this.plateau.length; ligne++) {
            for (int colonne = 0; colonne < this.plateau[ligne].length; colonne++) {
                this.plateau[ligne][colonne] = ".";
            }
        }

        initialiserMur(this.mursH);
        initialiserMur(this.mursV);
        placerJoueur(tabJoueurs);
    }

    public void initialiserMur(String[][] mur) {
        for (int ligne = 0; ligne < mur.length; ligne++) {
            for (int colonne = 0; colonne < mur[ligne].length; colonne++) {
                mur[ligne][colonne] = " ";
            }
        }
    }

    public void placerJoueur(ArrayList<Joueur> tabJoueurs) {
        for (Joueur joueur : tabJoueurs) {
            this.plateau[joueur.coordsPion[0]][joueur.coordsPion[1]] = joueur.pion;
        }
    }

    public void afficherPlateauJeu(ArrayList<Joueur> tabJoueurs) {
        for (int ligne = 0; ligne < this.plateau.length; ligne++) {
            for (int colonne = 0; colonne < this.plateau[ligne].length; colonne++) {

                if (!this.plateau[ligne][colonne].equals(".")) {
                    for (Joueur tabJoueur : tabJoueurs)
                        if (tabJoueur.pion.equals(this.plateau[ligne][colonne]))
                            System.out.print(tabJoueur.couleurPion + tabJoueur.pion + "\u001B[0m \t");
                }
                else {
                    System.out.print(this.plateau[ligne][colonne] + "\t");
                }

                /* Affiche les murs verticaux */
                if (colonne < 8) {
                    System.out.print(this.mursV[ligne][colonne] + "\t");
                }
            }

            System.out.println();

            if (ligne < 8) {
                for (int colonneMurH = 0; colonneMurH < this.mursH[ligne].length; colonneMurH++) {
                    System.out.print(this.mursH[ligne][colonneMurH] + "\t\t");
                }
            }

            System.out.println();
        }
    }

    public void placerMurHorizontal(int ligne, int colonne) {
        this.mursH[ligne - 1 ][colonne - 1] = "—";
        this.mursH[ligne - 1][colonne] = "—";
    }

    public void placerMurVertical(int ligne, int colonne) {
        this.mursV[ligne - 1][colonne - 1] = "|";
        this.mursV[ligne][colonne - 1] = "|";
    }

    public int[] coordsProchainMouvement(Joueur joueur, String typeMouvement) {
        int[] coordsMvmt = new int[2];

        switch (typeMouvement.toUpperCase().trim()) {
            case "G":
                coordsMvmt[0] = joueur.coordsPion[0];
                coordsMvmt[1] = joueur.coordsPion[1] - 1;
                break;
            case "H":
                coordsMvmt[0] = joueur.coordsPion[0] - 1;
                coordsMvmt[1] = joueur.coordsPion[1];
                break;
            case "D":
                coordsMvmt[0] = joueur.coordsPion[0];
                coordsMvmt[1] = joueur.coordsPion[1] + 1;
                break;
            case "B":
                coordsMvmt[0] = joueur.coordsPion[0] + 1;
                coordsMvmt[1] = joueur.coordsPion[1];
        }

        return coordsMvmt;
    }

    public void bougerPion(Joueur joueur, int[] coordsProchainMvmt) {
        this.plateau[coordsProchainMvmt[0]][coordsProchainMvmt[1]] = joueur.pion;
        this.plateau[joueur.coordsPion[0]][joueur.coordsPion[1]] = ".";

        joueur.coordsPion[0] = coordsProchainMvmt[0];
        joueur.coordsPion[1] = coordsProchainMvmt[1];

    }

}
