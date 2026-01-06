import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jeu {
    public static final int TAILLE_PLATEAU = 9;

    String[][] plateau;
    String[][] mursH;
    String[][] mursV;

    Jeu() {
        this.plateau = new String[9][9];
        this.mursH = new String[8][9];
        this.mursV = new String[9][8];
    }


    public ArrayList<Joueur> creerJoueurs(int nombreJoueurs) {
        ArrayList<Joueur> listeJoueurs = new ArrayList<>();

        listeJoueurs.add(new Joueur("J1", "R", "\u001B[31m", new int[]{0, 4}, 10));
        listeJoueurs.add(new Joueur("J2", "B", "\u001B[36m", new int[]{8, 4}, 10));

        if (nombreJoueurs == 4) {
            listeJoueurs.add(new Joueur("J3", "V", "\u001B[32m", new int[]{4, 0}, 10));
            listeJoueurs.add(new Joueur("J4", "J", "\u001B[93m", new int[]{4, 8}, 10));
            // Change le nombre de murs
            for (Joueur joueur : listeJoueurs) {
                joueur.nombreMurs = 5;
            }
        }

        return listeJoueurs;
    }

    /**
     * Initialise les différents tableaux (joueurs, murs horizontaux, murs verticaux et plateau).
     *
     * @param tabJoueurs la liste des joueurs
     */
    public void initialiserJeu(ArrayList<Joueur> tabJoueurs) {

        for (String[] elePlateau : this.plateau) {
            Arrays.fill(elePlateau, ".");
        }

        for (String[] murHoriz : this.mursH) {
            Arrays.fill(murHoriz, " ");
        }

        for (String[] mursVert : this.mursV) {
            Arrays.fill(mursVert, " ");
        }

        // Met en place les différents pions sur le plateau de jeu
        for (Joueur joueur : tabJoueurs) {
            this.plateau[joueur.coordsPion[0]][joueur.coordsPion[1]] = joueur.pion;
        }
    }


    /** Affiche le plateau de jeu, avec les murs horizontaux, verticaux, pions, numéros de lignes (chiffres et lettres).
     *
     * @param tabJoueurs la liste des joueurs.
     */
    public void afficherPlateauJeu(ArrayList<Joueur> tabJoueurs) {
        for (int ligne = 0; ligne < this.plateau.length; ligne++) {
            for (int colonne = 0; colonne < this.plateau[ligne].length; colonne++) {

                if (!this.plateau[ligne][colonne].equals(".")) {
                    for (Joueur tabJoueur : tabJoueurs)
                        if (tabJoueur.pion.equals(this.plateau[ligne][colonne]))
                            System.out.print(tabJoueur.couleurPion + tabJoueur.pion + "\u001B[0m \t");
                }
                else {
                    System.out.print(this.plateau[ligne][colonne] + "\t");
                }

                /* Affiche les murs verticaux */
                if (colonne < 8) {
                    System.out.print(this.mursV[ligne][colonne] + "\t");
                }
            }

            System.out.println();

            if (ligne < 8) {
                for (int colonneMurH = 0; colonneMurH < this.mursH[ligne].length; colonneMurH++) {
                    System.out.print(this.mursH[ligne][colonneMurH] + "\t\t");
                }
            }

            System.out.println();
        }
    }

    /**
     * Met en place un mur horizontal à l'emplacement indiqué.
     *
     * @param ligne le numéro de la ligne de séparation ; le mur horizontal sera situé entre cette ligne et la prochaine
     *
     * @param colonne le numéro de la colonne avec laquelle commence le mur horizontal
     */
    public void placerMurHorizontal(int ligne, int colonne) {
        this.mursH[ligne - 1 ][colonne - 1] = "—";
        this.mursH[ligne - 1][colonne] = "—";

    }

    /**
     * Met en place un mur vertical à l'emplacement indiqué.
     *
     * @param ligne le numéro de la ligne avec laquelle commence le mur vertical
     *
     * @param colonne le numéro de la colonne de séparation ; le mur vertical sera situé entre cette ligne et la prochaine
     *
     */
    public void placerMurVertical(int ligne, int colonne) {
        this.mursV[ligne - 1][colonne - 1] = "|";
        this.mursV[ligne][colonne - 1] = "|";
    }

    /**
     * Enlève un mur horizontal à emplacement indiqué.
     * Cette fonction n'est pas directement utilisée dans les interactions avec l'utilisateur : elle est utilisée pour la vérification de chemins
     * valides et pour faciliter les jeux de test.
     *
     * @param ligne le numéro de la ligne de séparation ; le mur horizontal sera situé entre cette ligne et la prochaine
     *
     * @param colonne le numéro de la colonne avec laquelle commence le mur horizontal
     */
    public void retirerMurHorizontal(int ligne, int colonne) {
        this.mursH[ligne - 1 ][colonne - 1] = " ";
        this.mursH[ligne - 1][colonne] = " ";
    }

    /**
     * Enlève un mur vertical à emplacement indiqué.
     * Cette fonction n'est pas directement utilisée dans les interactions avec l'utilisateur : elle est utilisée pour la vérification de chemins
     * valides et pour faciliter les jeux de test.
     *
     * @param ligne le numéro de la ligne avec laquelle commence le mur vertical
     *
     * @param colonne le numéro de la colonne de séparation ; le mur vertical sera situé entre cette ligne et la prochaine
     */

    public void retirerMurVertical(int ligne, int colonne) {
        this.mursV[ligne - 1 ][colonne - 1] = " ";
        this.mursV[ligne][colonne - 1] = " ";
    }

    /**
     * Vérifie si un mur choisi par l'utilisateur est posable.
     *
     * @param listeJoueurs la liste des joueurs
     *
     * @param typeMur le type de mur que l'utilisateur veut poser (horizontal ou vertical)
     *
     * @param ligneUtil la ligne du mur que l'utilisateur veut poser.
     *
     * @param colonneUtil la colonne du mur que l'utilisateur veut poser
     *
     * @return vrai (vrai) si le mur peut être posé, faux (false) sinon
     */
    public boolean murValide(ArrayList<Joueur> listeJoueurs, int typeMur, int ligneUtil, int colonneUtil) {

        boolean cheminValide;

        // Le pion se retrouvera en dehors du plateau.
        if (ligneUtil <= 0 || ligneUtil > TAILLE_PLATEAU - 1 || colonneUtil <= 0 || colonneUtil > TAILLE_PLATEAU - 1) {
            System.out.println("Erreur : valeurs hors du plateau !");
            return false;
        }

        // Si le type de mur est horizontal
        if (typeMur == 1) {

            // On regarde si un mur a déjà été placée à cet endroit.
            if (!this.mursH[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                System.out.println("Erreur : un mur horizontal est déjà présent ! ");
                return false;
            }

            // ligneUtil - 2 peut lancer un IndexOutOfBoundsException. Il faut donc séparer le cas où la ligne est supérieure à 1.
            if (ligneUtil > 1) {
                /* Intersection avec un mur vertical : on regarde s'il y a un mur vertical à cette case. Un mur vertical prennant deux lignes,
                on regarde si la case (ligne) avant ne contient pas de mur vertical (dûe à la représentation). */
                if (!this.mursV[ligneUtil - 1][colonneUtil - 1].equals(" ") && this.mursV[ligneUtil - 2][colonneUtil - 1].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur vertical !");
                    return false;
                }
            }
            else {
                /* Intersection avec un mur vertical. Seulement, dans ce cas, on regarde uniquement s'il y a un mur vertical à la case où on veut poser
                le mur horizontal. */
                if (!this.mursV[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur vertical !");
                    return false;
                }
            }

            /* Aucune intersection ne s'est passé. Il faut quand même regarder si le mur empêche un joueur d'arriver à la fin.
            Pour ce faire, on place un mur horizontal "temporaire" le temps du test. */
            placerMurHorizontal(ligneUtil, colonneUtil);

        }
        // Si le type de mur est vertical
        else {

            // On regarde si un mur vertical a déjà été placée à cet emplacement.
            if (!this.mursV[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                System.out.println("Erreur : un mur vertical est déjà présent ! ");
                return false;
            }

            /* colonneUtil - 2 peut engendrer un IndexOutOfBounds. Il faut donc séparer le cas dans lequel le mur est à l'extrémité du plateau */
            if (colonneUtil > 1) {
                /* Intersection avec un mur horizontal : on regarde s'il y a un mur horizontal à cette case. Un mur vertical prennant deux colonnes,
                on regarde si la case (colonne) avant ne contient pas de mur horizontal (dûe à la représentation). */
                if (!this.mursH[ligneUtil - 1][colonneUtil - 1].equals(" ") && this.mursH[ligneUtil - 1][colonneUtil - 2].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur horizontal ! !");
                    return false;
                }
            }

            else {
                /* Intersection avec un mur horizontal. Dans ce cas, on regarde uniquement s'il y a un mur horizontal à la case où on veut poser
                le mur vertical. */
                if (!this.mursH[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur horizontal !");
                    return false;
                }
            }

            /* Aucune intersection. Il faut quand même regarder si le mur empêche un joueur d'arriver à la fin.
            Pour ce faire, on place un mur vertical "temporaire" le temps du test. */
            placerMurVertical(ligneUtil, colonneUtil);
        }

        cheminValide = existeCheminVersFin(listeJoueurs);

        // On retire le mur peu importe si un chemin est valide ou non, étant donné que la variable cheminValide a déjà été affectée.
        if (typeMur == 1)
            retirerMurHorizontal(ligneUtil, colonneUtil);
        else
            retirerMurVertical(ligneUtil, colonneUtil);

        if (!cheminValide) {
            System.out.println("Erreur : ce mur bloque un joueur !");
            return false;
        }

        return true;
    }

    /** Vérifie si un mur entrave le mouvement d'un joueur
     *
     * @param coordsDep les coordonnées initiales du joueur
     *
     * @param coordsMvmt les coordonnées du joueur après mouvement
     *
     * @return vrai (true) si un mur obstrue le mouvement du joueur, faux sinon
     */
    public boolean murBloqueMouvement(int[] coordsDep, int[] coordsMvmt) {

        boolean mvmtVertical = coordsDep[0] != coordsMvmt[0];

        int ligne = coordsDep[0];
        int colonne = coordsDep[1];

        int ligneArv = coordsMvmt[0];
        int colonneArv = coordsMvmt[1];

        // Mouvement vertical : on regarde les murs horizontaux
        if (mvmtVertical) {
            // On va de bas en haut : il suffit juste de regarder s'il y a un mur aux coordonnées du mouvement final.
            if (ligne > ligneArv)
                return !this.mursH[ligneArv][colonneArv].equals(" ");
                // On va de haut en bas : on doit regarder s'il y a un mur aux coordonnées du mouvement initial
            else
                return !this.mursH[ligne][colonne].equals(" ");
        }
        // Mouvement horizontal : on regarde les murs verticaux
        else {
            // On va de droite à gauche : il suffit juste de regarder s'il y a un mur aux coordonnées du mouvement final.
            if (colonne > colonneArv)
                return !this.mursV[ligneArv][colonneArv].equals(" ");
                // On va de gauche à droite : on doit regarder s'il y a un mur aux coordonnées du mouvement initial
            else
                return !this.mursV[ligne][colonne].equals(" ");
        }
    }

    /**
     * Retourne les mouvements légaux que peut effectuer un pion en fonction de sa position actuelle et des différents murs.
     *
     * @param coordsPion les coordonnées du pion
     *
     * @return les coups légaux que peut effectuer le pion.
     */
    public ArrayList<int[]> coupsLegauxPion(int[] coordsPion) {
        ArrayList<int[]> coupsAutorisesPion = new ArrayList<>();
        int[][] directionsPossibles = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        int deplacementVertical, deplacementHorizontal;

        for (int[] direction : directionsPossibles) {
            int[] coordsCoupLegal = Arrays.copyOf(coordsPion, coordsPion.length);

            deplacementVertical = direction[0];
            deplacementHorizontal = direction[1];

            coordsCoupLegal[0] += deplacementVertical;
            coordsCoupLegal[1] += deplacementHorizontal;

            if ((coordsCoupLegal[0] > -1 && coordsCoupLegal[0] < TAILLE_PLATEAU
                    && coordsCoupLegal[1] > -1 && coordsCoupLegal[1] < TAILLE_PLATEAU)
                    && !murBloqueMouvement(coordsPion, coordsCoupLegal)) {

                if (!this.plateau[coordsCoupLegal[0]][coordsCoupLegal[1]].equals(".")) {
                    int[] saut = {
                            coordsCoupLegal[0] + deplacementVertical,
                            coordsCoupLegal[1] + deplacementHorizontal
                    };

                    if (saut[0] > -1 && saut[0] < TAILLE_PLATEAU
                            && saut[1] > -1 && saut[1] < TAILLE_PLATEAU) {

                        if (!murBloqueMouvement(coordsCoupLegal, saut)) {
                            coupsAutorisesPion.add(saut);
                        } else {
                            int[] d1 = {coordsCoupLegal[0], coordsCoupLegal[1]};
                            int[] d2 = {coordsCoupLegal[0], coordsCoupLegal[1]};

                            if (deplacementVertical == 0) {
                                d1[0] -= 1;
                                d2[0] += 1;
                            } else {
                                d1[1] -= 1;
                                d2[1] += 1;
                            }

                            if (!murBloqueMouvement(coordsCoupLegal, d1)) {
                                coupsAutorisesPion.add(d1);
                            }

                            if (!murBloqueMouvement(coordsCoupLegal, d2)) {
                                coupsAutorisesPion.add(d2);
                            }
                        }
                    }
                }

                coupsAutorisesPion.add(coordsCoupLegal);
            }
        }

        return coupsAutorisesPion;
    }


    /**
     * Vérifie qu'il y a toujours au moins un chemin vers la fin pour chaque joueur.
     * @param listeJoueurs la liste des joueurs.
     * @return vrai (true) s'il existe un chemin pour arriver à la fin, faux (false) sinon.
     */
    public boolean existeCheminVersFin(ArrayList<Joueur> listeJoueurs) {

        int ligneGagnante, colonneGagnante;
        boolean[][] mursVisites;

        // On doit vérifier pour chaque joueur s'ils peuvent arriver à leur but.
        for (Joueur joueur : listeJoueurs) {

            mursVisites  = new boolean[9][9];
            for (boolean[] lignesMursVisites : mursVisites) {
                Arrays.fill(lignesMursVisites, false);
            }

            int rangJoueur = listeJoueurs.indexOf(joueur);
            ligneGagnante = -1;
            colonneGagnante = -1;

            switch (rangJoueur) {
                case 0:
                    ligneGagnante = TAILLE_PLATEAU - 1;
                    break;
                case 1:
                    ligneGagnante = 0;
                    break;
                case 2:
                    colonneGagnante = TAILLE_PLATEAU - 1;
                    break;
                case 3:
                    colonneGagnante = 0;
                    break;
            }

            if (!rechercheChemin(joueur.coordsPion, mursVisites, ligneGagnante, colonneGagnante))
                return false;
        }

        return true;

    }

    /**
     * Regarde récursivement si un chemin existe pour atteindre la fin.
     * Cette fonction va d'abord commencer en prenant les coordonnées actuelles du joueur et regarder les coups légaux. Pour chaque coup légal, elle
     * regarde si elle y est déjà allé grâce au tableau de mursVisites. Si la case n'a jamais été explorée, alors on la marque comme visitée. Si elle
     * a déjà été visitée, alors on l'ignore. Puis on recommence ce processus. La fonction s'arrête lorsqu'une des cases visitées correspond à la
     * condition de victoire du pion ou que toutes les cases ont été visitées sans jamais atteindre la victoire.
     *
     * @param coords les coordonnées actuelles du joueur
     *
     * @param murCasesVisites les différentes cases qui vont être marquées visitées.
     *
     * @param ligneG la ligne que doit atteindre le pion pour remporter la partie.
     *
     * @param colonneG la colonne que doit atteindre le pion pour remporter la partie.
     *
     * @return vrai (true) s'il existe au moins un chemin, faux (false) sinon.
     */
    public boolean rechercheChemin(int[] coords, boolean[][] murCasesVisites, int ligneG, int colonneG) {
        ArrayList<int[]> listeCoupsAutorises = coupsLegauxPion(coords);

        for (int[] tabCoupLegal : listeCoupsAutorises) {

            if (!murCasesVisites[tabCoupLegal[0]][tabCoupLegal[1]]) {
                if (ligneG != -1 && tabCoupLegal[0] == ligneG || colonneG != -1 && tabCoupLegal[1] == colonneG)
                    return true;

                murCasesVisites[tabCoupLegal[0]][tabCoupLegal[1]] = true;

                if (rechercheChemin(tabCoupLegal, murCasesVisites, ligneG, colonneG)) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean mouvementValide(String typeMouvement, ArrayList<int[]> mouvementPossibles) {
        List<String> mvmtCorrects = Arrays.asList("G", "H", "D", "B");

        // Le mouvement n'existe pas
        if (!mvmtCorrects.contains(typeMouvement)) {
            System.out.println("Erreur : type de mouvement invalide !");
            return false;
        }
        // Le pion sera en dehors du plateau.
        if (mouvementPossibles.isEmpty()) {
            System.out.println("Erreur : mouvement impossible ! Un mur peut obstruer le passage, ou le pion sort du plateau ! ");
            return false;
        }
        // Le mouvement est valide.
        return true;
    }

    public ArrayList<int[]> obtenirCoupsDansDirection(String typeMouvement, int[] coordsPion, ArrayList<int[]> coupsAutorises) {
        ArrayList<int[]> candidats = new ArrayList<>();

        switch (typeMouvement) {
            case "G":
                for (int[] coups : coupsAutorises)
                    if (coups[1] < coordsPion[1])
                        candidats.add(coups);
                break;
            case "H":
                for (int[] coups : coupsAutorises)
                    if (coups[0] < coordsPion[0])
                        candidats.add(coups);
                break;
            case "D":
                for (int[] coups : coupsAutorises)
                    if (coups[1] > coordsPion[1])
                        candidats.add(coups);
                break;
            case "B":
                for (int[] coups : coupsAutorises)
                    if (coups[0] > coordsPion[0])
                        candidats.add(coups);
                break;
        }

        return candidats;
    }

    /**
     * Bouge le pion sur la case voulue.
     *
     * @param joueur l'objet joueur
     *
     * @param coordsProchainMvmt les coordonnées du mouvement où le pion doit aller
     */
    public void bougerPion(Joueur joueur, int[] coordsProchainMvmt) {
        this.plateau[coordsProchainMvmt[0]][coordsProchainMvmt[1]] = joueur.pion;
        this.plateau[joueur.coordsPion[0]][joueur.coordsPion[1]] = ".";

        joueur.coordsPion[0] = coordsProchainMvmt[0];
        joueur.coordsPion[1] = coordsProchainMvmt[1];
    }

    /**
     * Regarde si un des joueurs a gagné.
     *
     * @param joueurs la liste des joueurs
     *
     * @return vrai (true) si un joueur a atteint sa case gagnante, faux (false) sinon.
     */
    public boolean partieEstTerminee(ArrayList<Joueur> joueurs) {
        if (joueurs.get(0).coordsPion[0] == TAILLE_PLATEAU - 1 || joueurs.get(1).coordsPion[0] == 0)
                return true;

         if (joueurs.size() == 4)
             return joueurs.get(2).coordsPion[0] == TAILLE_PLATEAU - 1 || joueurs.get(3).coordsPion[0] == 0;

        return false;
    }

}
