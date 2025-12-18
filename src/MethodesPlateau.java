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
    public static void initialiserPlateau(char[][] plateau, int nbreJoueurs) {
        for (int ligne = 0; ligne < plateau.length; ligne++) {
            for (int colonne = 0; colonne < plateau[ligne].length; colonne++) {
                plateau[ligne][colonne] = '.';
            }
        }

        // Place les pions en fonction du nombre de joueur
        plateau[0][plateau.length / 2] = 'R';
        plateau[plateau.length - 1][plateau.length / 2] = 'J';

        if (nbreJoueurs == 4) {
            plateau[plateau.length / 2][0] = 'B';
            plateau[plateau.length / 2][plateau.length - 1] = 'O';

        }

    }

    /**
     * Initialise un mur en remplacant chacune de ses cases par des ' ' (espace).
     *
     * @param mur le mur qu'on veut initialiser
     */
    public static void initialiserMur(char[][] mur) {
        for (int ligne = 0; ligne < mur.length; ligne++)
            for (int colonne = 0; colonne < mur[ligne].length; colonne++) {
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

                /* Affiche les murs verticaux */
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

    /**
     * Effectue à un pion un changement de position.
     *
     * @param plateauJeu le plateau de jeu où on va changer la place du pion
     *
     * @param pion le pion qu'on va bouger
     *
     * @param mouvement le mouvement a effectué ('G': Gauche, 'H': Haut, 'D': Droit, 'B': Bas);
     *
     */
    public static void bougerJoueur(char[][] plateauJeu, char pion, char mouvement) {
        int ligneP = 0, colonneP = 0;

        for (int ligne = 0; ligne < plateauJeu.length; ligne++) {
            for (int colonne = 0; colonne < plateauJeu[ligne].length; colonne++) {
                if (plateauJeu[ligne][colonne] == pion) {
                    ligneP = ligne;
                    colonneP = colonne;
                }
            }
        }
        switch (mouvement) {
            case 'G':
                plateauJeu[ligneP][colonneP - 1] = plateauJeu[ligneP][colonneP];
                break;
            case 'H':
                plateauJeu[ligneP - 1][colonneP] = plateauJeu[ligneP][colonneP];
                break;
            case 'D':
                plateauJeu[ligneP][colonneP + 1] = plateauJeu[ligneP][colonneP];
                break;
            case 'B':
                plateauJeu[ligneP + 1][colonneP] = plateauJeu[ligneP][colonneP];
                break;
        }

        plateauJeu[ligneP][colonneP] = '.';
    }



}


