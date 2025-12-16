public class MethodesPlateau {

    /* Cette méthode permet de mettre en place graphiquement le tableau (cases et pions) */
    public static void initialiserPlateau(char[][] plateau, int nbreJoueurs) {
        for (int ligne = 0; ligne < plateau.length; ligne++)
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {

                // Remplace toutes les colonnes par points (vide)
                plateau[ligne][colonne] = '.';
                // Place les pions en fonction du nombre de joueur
                plateau[0][plateau.length / 2] = 'R';
                plateau[plateau.length - 1][plateau.length / 2] = 'J';

                if (nbreJoueurs == 4) {
                    plateau[plateau.length / 2][0] = 'B';
                    plateau[plateau.length / 2][plateau.length - 1] = 'O';
                }
        }
    }

    /*
    public static void afficherPlateauDeJeu(char[][] plateau) {

        // Affiche les coordonnées des colonnes du plateau
        for (char lettreColonne = 'A'; lettreColonne < 'J'; lettreColonne++)
            System.out.print(lettreColonne);

        System.out.println();

        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++)
                System.out.print(plateau[ligne][colonne]);

            // Affiche les coordonnées des lignes du plateau
            System.out.println("\t" + (plateau.length - ligne));
        }
    }
    */

    public static void afficherPlateauDeJeu(char[][] plateau) {
        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {
                System.out.print(plateau[ligne][colonne] + "\t");
            }
            System.out.println();
            System.out.println();
        }
    }

    /*
    public static void afficherMur(char[][] plateau) {
        for (char[] chars : plateau) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
    }
     */


    public static void placerMurHorizontal(char[][] murs, int ligne, int cellDepart) {
        murs[ligne][cellDepart] = 'H';
        murs[ligne][cellDepart + 1] = 'H';
    }

    public static void placerMurVertical(char[][] murs, int colonne, int cellDepart) {
        murs[colonne][cellDepart] = 'V';
        murs[colonne + 1][cellDepart] = 'V';
    }

    public static void bougerJoueur(int x, int y) {}

}


