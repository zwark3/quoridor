/**
 * La classe MethodesPlateau contient des méthodes propres à
 * la mise en place du plateau et des murs
 */

public class MethodesPlateau {

    /**
     * Remplace chacune des cases du plateau par des '.' et met en place les pions.
     *
     * @param plateau le plateau de jeu
     *
     */
    public static void initialiserPlateau(char[][] plateau) {
        for (int ligne = 0; ligne < plateau.length; ligne++)
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {

                // Remplace toutes les colonnes par points (vide)
                plateau[ligne][colonne] = '.';
        }
    }

    /**
     * Initialise un mur en remplacant chacune de ses cases par des espaces.
     *
     * @param mur le mur qu'on veut initialiser
     */
    public static void initialiserMur(char[][] mur) {
        for (int ligne = 0; ligne < mur.length; ligne++)
            for (int colonne = 0; colonne < mur[ligne].length; colonne++) {
                // Remplace toutes les colonnes par espace (vide)
                mur[ligne][colonne] = ' ';
            }
    }

    /**
     * Remplace chacune des cases des tableaux de murs horizontaux et verticaux par des espaces (vide).
     *
     * @param mursH le mur horizontal
     *
     * @param mursV le mur vertical
     */
    public static void initialiserMursHV(char[][] mursH, char[][] mursV) {
        initialiserMur(mursH);
        initialiserMur(mursV);
    }

    /** Affiche le plateau de jeu, avec les murs horizontaux, verticaux, pions, numéros de lignes (chiffres et lettres).
     *
     * @param plateau le plateau de jeu qu'on va afficher
     *
     * @param mursH les murs horizontaux qu'on doit représenter
     *
     * @param mursV les murs verticaux qu'on va afficher
     */
    public static void afficherPlateauDeJeu(char[][] plateau, char[][] mursH, char[][] mursV) {
        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {
                System.out.print(plateau[ligne][colonne] + "\t");

                if (colonne < 8) {
                    System.out.print(mursV[ligne][colonne] + "\t");
                }
            }

            System.out.println();
            if (ligne < 8) {
                for (int colonneMurH = 0; colonneMurH < mursH[ligne].length; colonneMurH++) {
                    System.out.print(mursH[ligne][colonneMurH] + "\t\t");
                }
            }

            System.out.println();
        }
    }


    /**
     * Met en place un mur horizontal à l'emplacement indiqué.
     *
     * @param mursH le mur horizontal où le mur horizontal sera placé
     *
     * @param ligne le numéro de la ligne de séparation ; le mur horizontal sera situé entre cette ligne et la prochaine
     *
     * @param colonne le numéro de la colonne où débute le mur horizontal
     */
    public static void placerMurHorizontal(char[][] mursH, int ligne, int colonne) {
        mursH[ligne - 1 ][colonne - 1] = '-';
        mursH[ligne - 1][colonne] = '-';
    }

    /**
     * Met en place un mur vertical à l'emplacement indiqué.
     *
     * @param mursV le mur vertical où sera placé le mur vertical.
     *
     * @param ligne le numéro de la ligne où débute le mur vertical
     *
     * @param colonne le numéro de la colonne de séparation ; le mur vertical sera situé entre cette ligne et la prochaine
     */
    public static void placerMurVertical(char[][] mursV, int ligne, int colonne) {
        mursV[ligne - 1][colonne - 1] = '|';
        mursV[ligne][colonne - 1] = '|';
    }


    public static void bougerJoueur(char pion, int[][] posActuelle, char nouvellePos) {
        switch (nouvellePos) {
            case 'G':
            case 'H':
            case 'D':
            case 'B':
        }
    }

}


