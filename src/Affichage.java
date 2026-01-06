import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * La classe Affichage s'occupe des interactions avec l'utilisateur et de l'affichage du jeu et de son déroulé,
 * et est chargé d'appliquer les méthodes prédéfinies dans les deux autres classes.
 *
 */
public class Affichage {

    public static Scanner sc = new Scanner(System.in).useDelimiter("\n");

    public static void menuPresentation() {
        int choixMenu, rejouer;
        String menuPresentation =
                """
                * * * * * * * * * * * * * * * * * * * *
                *   BIENVENUE SUR LE JEU DU QUORIDOR  *
                * 1. Joueur contre joueur             *
                * 2. Joueur contre robot              *
                * 3. Règles du jeu                    *
                * 4. Informations                     *
                * 5. Quitter                          *
                *                                     *
                *           Faites un choix.          *
                * * * * * * * * * * * * * * * * * * * *
                """;
        do {
            System.out.println(menuPresentation);
            choixMenu = sc.nextInt();
        } while (choixMenu < 1 || choixMenu > 5);

        boolean quitterJeu = false;

        // L'utilisateur a écrit une valeur correcte
        while (!quitterJeu) {
            switch (choixMenu) {
                case 1:
                    do {
                        lancerJeu();
                        System.out.println("Voulez-vous rejouer ? (1 pour oui, 0 ou autre pour non) : ");
                        rejouer = sc.nextInt();
                    } while (rejouer == 1);
                    break;
                case 2:
                    break;
                case 3:
                    String reglesPresentation =
                                    """
                                    REGLES DU JEU DU QUORIDOR
                                    
                                    PRESENTATION
                                    
                                    Le quoridor est un jeu de tour à tour qui se joue sur un plateau de 81 cases (9*9).
                                    Le jeu peut se jouer à 2 ou 4 joueurs.
                                    
                                    Chaque joueur se voit attribuer un pion, qui commence au milieu de sa
                                    rangée, ainsi qu'un nombre de murs.
                                    
                                    MOUVEMENT DES PIONS
                                    
                                    Les pions bougent une case à la fois, horizontalement, verticalement, devant ou derrière.
                                    Les pions doivent esquiver les murs. Quand deux pions sont face à face et qu'aucun mur
                                    ne les bloque, le joueur actuel peut sauter par-dessus le pion ennemi.
                                    
                                    POSITIONNEMENT DES MURS
                                    Les murs sont des élements plats de deux cases de large placées entre deux groupes de deux
                                    carrées.
                                    Ces barrières peuvent facilier la progession du jouer ou bloquer celle de l'adversaire.
                                    Un mur ne peut pas complètement entraver un joueur : un accès à la ligne de but doit rester libre.
                                    
                                    
                                    BUT
                                    Le premier joueur qui atteint l'une des cases opposées à sa rangée de départ gagne.
                                    """;
                    System.out.println(reglesPresentation);
                    break;
                case 4:
                    String information = """
                            Jeu du Quoridor. Fait par Mirko Marchesi.
                            Projet de  Cheema Mohammad et Pansan Abdel-Malik
                            © Gigamic. Tous droits réservés.
                            """;
                    System.out.println(information);
                    break;
                case 5:
                    quitterJeu = true;
                    break;
            }
            if (!quitterJeu) {
                System.out.println(menuPresentation);
                choixMenu = sc.nextInt();
            }
        }

        System.out.println("Au revoir ! ");
    }

    private static void lancerJeu() {
        System.out.println("JOUEUR CONTRE JOUEUR");
        int nombreJoueurs;

        do {
            System.out.print("Entrez le nombre de joueurs (compris entre 2 et 4) :  ");
            nombreJoueurs = sc.nextInt();
        } while (nombreJoueurs < 2 || nombreJoueurs > 4 );

        // Début du jeu
        int indxJoueurActuel;
        Joueur joueurActuel;

        int choix;
        boolean joueurPossedeMurs;

        Jeu moteurJeu = new Jeu();

        ArrayList<Joueur> listeJoueurs = moteurJeu.creerJoueurs(nombreJoueurs);

        moteurJeu.initialiserJeu(listeJoueurs);

        int tour = 0;

        // Boucle de jeu
        while (!moteurJeu.partieTerminee(listeJoueurs)) {

            // Détermination du joueur actuel
            indxJoueurActuel = tour % listeJoueurs.size();
            joueurActuel = listeJoueurs.get(indxJoueurActuel);

            joueurPossedeMurs = (joueurActuel.nombreMurs > 0);

            System.out.println();

            // Information sur chaque joueur à chaque tour
            for (Joueur joueur : listeJoueurs) {
                System.out.println(joueur.nomJ + " (" + joueur.pion + ") | murs restants : " + joueur.nombreMurs);
                System.out.println(joueur.nomJ + " coordonnées possible : " + Arrays.deepToString(moteurJeu.determineCoupsLegauxPion(joueur.coordsPion).toArray()));
            }

            System.out.println();

            // Affichage du tour et du joueur actuel
            System.out.println("Tour nº" + (tour + 1));
            System.out.println("Joueur actuel : " + joueurActuel.nomJ);

            System.out.println();

            moteurJeu.afficherPlateauJeu(listeJoueurs);

            System.out.println();

            do {
                System.out.print(joueurActuel.nomJ + " (" + joueurActuel.couleurPion + joueurActuel.pion + "\u001B[0m) : bouger votre pion ou placer un mur (1 pour bouger, 2 pour mur) : ");
                choix = sc.nextInt();
                if (choix == 2 && !joueurPossedeMurs)
                    System.out.println(joueurActuel.nomJ + " vous n'avez plus de murs ! ");
            } while ((choix < 1 || choix > 2) || choix == 2 && !joueurPossedeMurs);

            // Choix correct
            sc.nextLine();

            // L'utilisateur veut bouger son pion
            if (choix == 1) {
                String mvmtPion;

                ArrayList<int[]> coupsLegaux = moteurJeu.determineCoupsLegauxPion(joueurActuel.coordsPion);
                ArrayList<int[]> candidats;

                do {
                    System.out.print("Choissisez une direction (G -> GAUCHE, H -> HAUT, D -> DROITE, B -> BAS) : ");
                    mvmtPion = sc.nextLine().toUpperCase().trim();
                    candidats = moteurJeu.filtrerCoupsParDirection(mvmtPion, joueurActuel.coordsPion, coupsLegaux);
                } while (!moteurJeu.mouvementValide(mvmtPion, candidats));

                moteurJeu.bougerPion(joueurActuel, candidats.getFirst());
            }

            // L'utilisateur veut placer un mur
            else {
                int typeMur, ligneMur, colonneMur;
                do {
                    System.out.print("Placez un mur (1 -> horizontal, 2 -> vertical) : ");
                    typeMur = sc.nextInt();
                } while (typeMur < 1 || typeMur > 2);

                do {
                    System.out.print("Écrivez le numéro de la ligne : ");
                    ligneMur = sc.nextInt();

                    System.out.print("Écrivez le numéro de la colonne : ");
                    colonneMur = sc.nextInt();
                } while (!moteurJeu.murValide(listeJoueurs, typeMur, ligneMur, colonneMur));

                // Le mur a été placé de manière correcte.

                if (typeMur == 1) {
                    moteurJeu.placerMurHorizontal(ligneMur, colonneMur);
                } else {
                    moteurJeu.placerMurVertical(ligneMur, colonneMur);
                }

                // On enlève un mur au joueur.
                joueurActuel.nombreMurs--;
            }

            tour++;
        }

        // Fin de partie
        System.out.println("Nombre de coups total : " + (tour + 1));

    }

}