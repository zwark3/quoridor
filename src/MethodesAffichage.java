import java.util.Scanner;

/* Cette méthode affiche le menu de présentation avec les différents choix possibles. */
public class MethodesAffichage {
    public static void menuPresentation() {
        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        int choix;
        String menuPresentation = """
                             * * * * * * * * * * * * * * * * * * * *
                             * BIENVENUE SUR LE JEU DU QUORIDOR    *
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
            choix = scan.nextInt();
        } while (choix < 1 || choix > 5);

        // L'tuilisateur a pris un choix correct.
        while (choix == 3 || choix == 4) {
            switch(choix) {
                case 1:
                case 2:
                case 3:
                    String reglesPresentation = """
                             REGLES DU JEU DU QUORIDOR
                             
                             PRESENTATION
                             
                             Le quoridor est un jeu de tour à tour qui se joue sur un plateau de 81 cases (9*9).
                             Le jeu peut se jouer à 2 ou 4 joueurs.
                           
                             Chaque joueur se voit attribuer un pion, qui commence au milieu de sa
                             rangée. ainsi qu'un nombre de murs.
                             
                             MOUVEMENT DES PIONS
                             
                             Les pions bougent une case à la fois, horizontalement, verticalement, devant ou derrière.
                             Les pions doivent esquiver les murs. Quand deux pions sont face à face et qu'aucun mur ne 
                             les bloque, le joueur actuel peut sauter par-dessus le pion ennemi. 
                             
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
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Erreur ! ");
                    break;
            }

            System.out.println(menuPresentation);
        }

        System.out.println("À bientôt ! ");
    }


}
