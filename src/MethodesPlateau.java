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

    public static void proposeMouvement(char[][] plateau) {
        for (int cherchePionLigne = 0; cherchePionLigne < plateau.length; cherchePionLigne++) {
            for (int cherchePionColonne = 0; cherchePionColonne < plateau[cherchePionLigne].length; cherchePionColonne++) {
                if (plateau[cherchePionLigne][cherchePionColonne] == 'J') {

                }
            }
        }
    }
    */
}


