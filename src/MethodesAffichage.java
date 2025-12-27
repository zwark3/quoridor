import java.util.Scanner;

/**
 * La classe MethodesAffichage s'occupe de l'affichage
 * du menu et des interactions avec l'utilisateur (mouvement des pions, placement des murs, ...)
 */
public class MethodesAffichage {

    private static final Scanner scan = new Scanner(System.in).useDelimiter("\n");

    /**
     * Affiche le menu du jeu, s'occupe des différentes
     * interactions avec l'utilisateur (choix du jeu, mouvement des pions)
     * et qu'ils concordent correctement avec la logique du jeu.
     */
    public static void menuPresentation() {
        int choixMenu;
        String menuOptions = """
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

        System.out.println(menuOptions);
        choixMenu = scan.nextInt();

        while (choixMenu < 1 || choixMenu > 5) {
            System.out.println("Erreur : valeur incorrecte !");
            System.out.println(menuOptions);
            choixMenu = scan.nextInt();
        }

        // L'utilisateur a fait un choix correct
        boolean sortirBoucle = false;

        while (!sortirBoucle) {
            switch (choixMenu) {
                case 1:
                    lancerJeuJoueurs();
                    break;
                case 2:
                    break;
                case 3:
                    String reglesPresentation = """
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
                    sortirBoucle = true;
                    break;
                default:
                    System.out.println("Erreur ! Valeur incorrecte ! ");
                    break;
            }

            if (!sortirBoucle) {
                System.out.println(menuOptions);
                choixMenu = scan.nextInt();
            }
        }
    }

    public static void lancerJeuJoueurs() {

        int nombreJoueurs;
        int tour;
        int action;
        int typeMur;

        int ligne, colonne;

        System.out.print("Entrer le nombre de joueurs : (2 ou 4)");
        nombreJoueurs = scan.nextInt();

        while (nombreJoueurs != 2 && nombreJoueurs != 4) {
            System.out.print("Nombre de joueurs incorrect ! Réesayer : ");
            nombreJoueurs = scan.nextInt();
        }

        char[][] plateau = new char[9][9];
        char[][] mursV = new char[9][8];
        char[][] mursH = new char[8][9];

        MethodesPlateau.initialiserPlateau(plateau, mursH, mursV, nombreJoueurs);

        char pionActuel;
        char mvmtPion;

        String joueurActuel;

        tour = 0;

        while (tour < 50) {

            MethodesPlateau.afficherPlateauDeJeu(plateau, mursH, mursV);

            if (tour % 2 == 0) {
                joueurActuel = "Joueur 1";
                pionActuel = 'R';
            }
            else {
                joueurActuel = "Joueur 2";
                pionActuel = 'J';
            }

            System.out.print(joueurActuel + " (" + pionActuel + ") : déplacer votre pion ou placer un mur (écrivez 1 pour pion, 2 pour mur)");
            action = scan.nextInt();

            while (action != 1 && action != 2) {
                System.out.print("Action incorrecte ! Veuillez réesayer : ");
                action = scan.nextInt();
            }

            if (action == 1) {
                System.out.print(joueurActuel + " (" + pionActuel + ") bougez votre pion (G -> Gauche, H -> Haut, D -> Droit, B -> Bas) : ");
                mvmtPion = scan.next().charAt(0);
                MethodesPlateau.bougerPion(plateau, MethodesPlateau.coordsPion(plateau, pionActuel),
                        MethodesPlateau.coordsProchainMvmt(MethodesPlateau.coordsPion(plateau, pionActuel), mvmtPion));

            } else {
                System.out.print(joueurActuel + " (" + pionActuel + ") voulez-vous placer un mur horizontal ou vertical ? " +
                        "(1 pour horizontal, 2 pour vertical) : ");
                typeMur = scan.nextInt();

                while (typeMur != 1 && typeMur != 2) {
                    System.out.print("Type de mur incorrect ! Réesayer :  ");
                    typeMur = scan.nextInt();
                }

                do {
                    System.out.print("Écrivez le numéro de la ligne :");
                    ligne = scan.nextInt();
                } while (ligne < 1 || ligne > 8);

                do {
                    System.out.print("Écrivez le numéro de la colonne :");
                    colonne = scan.nextInt();
                } while (colonne < 1 || colonne > 8);

                if (typeMur == 1) {
                    MethodesPlateau.placerMurHorizontal(mursH, ligne, colonne);
                } else {
                    MethodesPlateau.placerMurVertical(mursV, ligne, colonne);
                }

            }

            tour++;
        }
    }

}
