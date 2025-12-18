public class MethodesPlateau {

    /* Cette méthode permet de mettre en place graphiquement le tableau (cases et pions) */
    public static void initialiserPlateau(char[][] plateau) {
        for (int ligne = 0; ligne < plateau.length; ligne++)
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {

                // Remplace toutes les colonnes par points (vide)
                plateau[ligne][colonne] = '.';
        }
    }

    public static void initialiserMur(char[][] murs) {
        for (int ligne = 0; ligne < murs.length; ligne++)
            for (int colonne = 0; colonne < murs[ligne].length; colonne++) {
                // Remplace toutes les colonnes par espace (vide)
                murs[ligne][colonne] = '-';
            }
    }

    public static void afficherPlateauDeJeu(char[][] plateau) {
        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {
                System.out.print(plateau[ligne][colonne] + "\t");
            }
            System.out.println();
            System.out.println();
        }
    }

    public static void afficherMur(char[][] plateau) {
        for (char[] plateau1D : plateau) {
            for (char cellule : plateau1D) {
                System.out.print(cellule);
            }
            System.out.println();
        }
    }


    public static void placerMurHorizontal(char[][] murs, int ligne, int cellDepart) {
        murs[ligne - 1][cellDepart - 1] = 'H';
        murs[ligne - 1][cellDepart] = 'H';
    }

    public static void placerMurVertical(char[][] murs, int colonne, int cellDepart) {
        murs[colonne - 1][cellDepart - 1] = 'V';
        murs[colonne][cellDepart - 1] = 'V';
    }

    public static void bougerJoueur(int x, int y) {}

}


