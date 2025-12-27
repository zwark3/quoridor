/**
 * La classe MethodesPlateau contient des méthodes propres à
 * la mise en place du plateau et des murs
 */

public class MethodesPlateau {

    /**
     * Met en place l'affichage graphique du plateau (murs, pions)
     *
     * @param plateau le plateau de jeu
     *
     * @param mursH le mur horizontal
     *
     * @param mursV le mur vertical
     *
     * @param nbreJoueurs le nombre de joueurs
     */
    public static void initialiserPlateau(char[][] plateau, char[][] mursH, char[][] mursV, int nbreJoueurs) {
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

        initialiserMur(mursH);
        initialiserMur(mursV);

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
     *
     */
    public static void placerMurVertical(char[][] mursV, int ligne, int colonne) {
        mursV[ligne - 1][colonne - 1] = '|';
        mursV[ligne][colonne - 1] = '|';
    }

    /**
     * Obtiens les coordonnées d'un pion.
     *
     * @param plateauJeu le plateau de jeu
     *
     * @param pion le pion dont on veut les coordonnées.
     *
     * @return un tableau contenant les coordonnées du pion (ligne, colonne)
     */
    public static int[] coordsPion(char[][] plateauJeu, char pion) {
        int[] coordsPion = new int[2];

        boolean pionTrouve;
        int ligne, colonne;

        pionTrouve = false;
        ligne = 0;
        while (ligne < plateauJeu.length && !pionTrouve) {
            colonne = 0;
            while (colonne < plateauJeu[ligne].length && !pionTrouve ) {
                if (plateauJeu[ligne][colonne] == (pion)) {
                    pionTrouve = true;

                    coordsPion[0] = ligne;
                    coordsPion[1] = colonne;
                }
                colonne++;
            }
            ligne++;
        }

        return coordsPion;

    }

    /**
     * Renvoie les coordonnées du prochain mouvement effectué à un pion.
     *
     * @param positionPion la position actuelle du pion
     *
     * @param typeMouvement le mouvement que le pion effectue (G : gauche, H : Haut, D : Droit, B : Bas)
     *
     * @return un tableau contenant les coordonnées du pion après mouvement
     */

    public static int[] coordsProchainMvmt(int[] positionPion, char typeMouvement) {
        int[] nouvellePos;
        nouvellePos = new int[2];

        switch (typeMouvement) {
            case 'G':
                nouvellePos[0] = positionPion[0];
                nouvellePos[1] = positionPion[1] - 1;
                break;
            case 'H':
                nouvellePos[0] = positionPion[0] - 1;
                nouvellePos[1] = positionPion[1];
                break;
            case 'D':
                nouvellePos[0] = positionPion[0];
                nouvellePos[1] = positionPion[1] + 1;
                break;
            case 'B':
                nouvellePos[0] = positionPion[0] + 1;
                nouvellePos[1] = positionPion[1];
                break;
        }

        return nouvellePos;
    }

    /**
     * Permet de bouger un pion sur une case précise
     *
     * @param plateau le plateau de jeu où on effectue le mouvement
     *
     * @param ancienCoords les anciennes coordonnées du pion avant le mouvement
     *
     * @param nouveauCoords les nouvelles coordonnées du pion après mouvement
     *
     */
    public static void bougerPion(char[][] plateau, int[] ancienCoords, int[] nouveauCoords) {
        plateau[nouveauCoords[0]][nouveauCoords[1]] = plateau[ancienCoords[0]][ancienCoords[1]];
        plateau[ancienCoords[0]][ancienCoords[1]] = '.';
    }

    public static boolean mouvementPermis(char[][] mursH, char[][] mursV, int[] ancienCoords, int[] nouveauCoords) {
        boolean mouvementPermis = true;
        // On regarde les murs horizontaux
        if (ancienCoords[0] != nouveauCoords[0]) {
            if (mursH[nouveauCoords[0] - 1][nouveauCoords[1]] == '-')
                mouvementPermis = false;
        }
        // On regarde les murs verticaux
        else {
            if ((mursV[nouveauCoords[0]][nouveauCoords[1] - 1] == '|') || (mursV[nouveauCoords[0]][nouveauCoords[1]] == '|')) {
                mouvementPermis = false;
            }
        }
        return mouvementPermis;
    }

}


