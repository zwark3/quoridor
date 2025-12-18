import java.util.Scanner;

/**
 * La classe MethodesAffichage s'occupe de l'affichage
 * du menu et des interactions avec l'utilisateur (mouvement des pions, placement des murs, ...)
 */
public class MethodesAffichage {

    /**
     * Affiche le menu du jeu, s'occupe des différentes
     * interactions avec l'utilisateur (choix du jeu, mouvement des pions)
     * et qu'ils concordent correctement avec la logique du jeu.
     */
    public static void menuPresentation() {

        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        int choixUtilisateur, nombreJoueurs;
        String menuPresentation = """
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
            choixUtilisateur = scan.nextInt();
        } while (choixUtilisateur < 1 || choixUtilisateur > 5);

        // L'tuilisateur a pris un choixUtilisateur correct. Le programme continue normalement.
        boolean sortirMenu = false;

        while (!sortirMenu) {
            switch(choixUtilisateur) {
                case 1:
                    do {
                        System.out.println("Choisir le nombre de joueurs (2 ou 4)");
                        nombreJoueurs = scan.nextInt();
                    } while (nombreJoueurs < 2 || nombreJoueurs > 4);
                    sortirMenu = true;
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
                         Le premier joueur qui atteint l'une des cases opposés à sa rangée de départ gagne.
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
                    sortirMenu = true;
                    break;
                default:
                    System.out.println("Erreur ! Mauvaise information rentrée ");
                    break;
            }

            if (!sortirMenu) {
                System.out.println(menuPresentation);
                choixUtilisateur = scan.nextInt();
            }

        }

    }
}
