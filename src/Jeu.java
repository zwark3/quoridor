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

        if (typeMur == 1) {

            if (!this.mursH[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                System.out.println("Erreur : un mur horizontal est déjà présent ! ");
                return false;
            }

            if (ligneUtil > 1) {
                if (!this.mursV[ligneUtil - 1][colonneUtil - 1].equals(" ") && this.mursV[ligneUtil - 2][colonneUtil - 1].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur vertical !");
                    return false;
                }
            }
            else {
                if (!this.mursV[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur vertical !");
                    return false;
                }
            }

            placerMurHorizontal(ligneUtil, colonneUtil);

        } else {

            if (!this.mursV[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                System.out.println("Erreur : un mur vertical est déjà présent ! ");
                return false;
            }

            if (colonneUtil > 1) {
                if (!this.mursH[ligneUtil - 1][colonneUtil - 1].equals(" ") && this.mursH[ligneUtil - 1][colonneUtil - 2].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur horizontal ! !");
                    return false;
                }
            }

            else {
                if (!this.mursH[ligneUtil - 1][colonneUtil - 1].equals(" ")) {
                    System.out.println("Erreur : intersection avec un mur horizontal !");
                    return false;
                }
            }

            placerMurVertical(ligneUtil, colonneUtil);
        }

        cheminValide = existeCheminVersFin(listeJoueurs);

        if (typeMur == 1) {
            retirerMurHorizontal(ligneUtil, colonneUtil);
        }
        else {
            retirerMurVertical(ligneUtil, colonneUtil);
        }

        if (!cheminValide) {
            System.out.println("Erreur : ce mur bloque un joueur !");
            return false;
        }

        return true;
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

    public void retirerMurHorizontal(int ligne, int colonne) {
        this.mursH[ligne - 1 ][colonne - 1] = " ";
        this.mursH[ligne - 1][colonne] = " ";
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

    public void retirerMurVertical(int ligne, int colonne) {
        this.mursV[ligne - 1 ][ligne - 1] = " ";
        this.mursV[colonne][colonne - 1] = " ";
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
        ArrayList<int[]> listeCoupsLegaux = new ArrayList<>();

        int lignePionJ = coordsPion[0];
        int colonnePionJ = coordsPion[1];

        int[] mvmtGauche = {lignePionJ, colonnePionJ - 1};
        int[] mvmtDroit = {lignePionJ, colonnePionJ + 1};

        int[] mvmtGaucheSaut = {lignePionJ, colonnePionJ - 2};
        int[] mvmtDroitSaut = {lignePionJ, colonnePionJ + 2};

        int[] mvmtHaut = {lignePionJ - 1, colonnePionJ};
        int[] mvmtBas = {lignePionJ + 1, colonnePionJ};

        int[] mvmtHautSaut = {lignePionJ - 2, colonnePionJ};
        int[] mvmtBasSaut = {lignePionJ + 2, colonnePionJ};



        // Mouvement à gauche : on vérifie que le pion n'est pas aux extrémités du plateau.
        if (colonnePionJ > 0 && !murBloqueMouvement(coordsPion, mvmtGauche)) {
            // On regarde si un autre pion est à gauche du joueur.
            if (!this.plateau[lignePionJ][colonnePionJ - 1].equals(".")) {
                // On regarde si on peut sauter par-dessus le pion et qu'il n'y a pas un mur qui entrave le saut.
                if ((colonnePionJ - 2 > -1 && !murBloqueMouvement(mvmtGauche, mvmtGaucheSaut)) ) {
                    listeCoupsLegaux.add(mvmtGaucheSaut);
                }
            }
            else
                listeCoupsLegaux.add(mvmtGauche);
        }
        // // Mouvement en haut : on vérifie que le pion n'est pas aux extrémités du plateau.
        if (lignePionJ > 0 && !murBloqueMouvement(coordsPion, mvmtHaut)) {
            // On regarde si un autre pion est en haut du joueur.
            if (!this.plateau[lignePionJ - 1][colonnePionJ].equals(".")) {
                // On regarde si on peut sauter par-dessus le pion et qu'il n'y a pas un mur qui entrave le saut.
                if (lignePionJ - 2 > -1 && !murBloqueMouvement(mvmtHaut, mvmtHautSaut)) {
                    listeCoupsLegaux.add(mvmtHautSaut);
                }
            }
            else
                listeCoupsLegaux.add(mvmtHaut);
        }
        // // Mouvement à droite : on vérifie que le pion n'est pas aux extrémités du plateau.
        if (colonnePionJ < TAILLE_PLATEAU - 1 && !murBloqueMouvement(coordsPion, mvmtDroit)) {
            // On regarde si un autre pion est à droite du joueur.
            if (!this.plateau[lignePionJ][colonnePionJ + 1].equals(".")) {
                // On regarde si on peut sauter par-dessus le pion et qu'il n'y a pas un mur qui entrave le saut.
                if (colonnePionJ + 2 < TAILLE_PLATEAU && !murBloqueMouvement(mvmtDroit, mvmtDroitSaut)) {
                    listeCoupsLegaux.add(mvmtDroitSaut);
                }

            } else
                listeCoupsLegaux.add(mvmtDroit);
        }
        // // Mouvement en bas : on vérifie que le pion n'est pas aux extrémités du plateau.
        if (lignePionJ < TAILLE_PLATEAU - 1 && !murBloqueMouvement(coordsPion, mvmtBas))
            // On regarde si un autre pion est en bas du joueur.
            if (!this.plateau[lignePionJ + 1][colonnePionJ].equals(".")) {
                // On regarde si on peut sauter par-dessus le pion et qu'il n'y a pas un mur qui entrave le saut.
                if (lignePionJ + 2 < TAILLE_PLATEAU && !murBloqueMouvement(mvmtBas, mvmtBasSaut)) {
                    listeCoupsLegaux.add(mvmtBasSaut);
                }
            } else
                listeCoupsLegaux.add(mvmtBas);

        return listeCoupsLegaux;
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

    /**
     * Vérifie si le mouvement que veut effectuer le joueur est valide
     *
     * @param coordsPion
     * @param typeMouvement
     * @param coordsMvmt
     * @return
     */
    public boolean mouvementValide(int[] coordsPion, String typeMouvement, int[] coordsMvmt) {
        List<String> mvmtCorrects = Arrays.asList("G", "H", "D", "B");

        // Le mouvement n'existe pas
        if (!mvmtCorrects.contains(typeMouvement)) {
            System.out.println("Erreur : type de mouvement invalide !");
            return false;
        }
        // Le pion sera en dehors du plateau.
        else if (coordsMvmt[0] == -1 || coordsMvmt[0] >= TAILLE_PLATEAU || coordsMvmt[1] == -1 || coordsMvmt[1] >=TAILLE_PLATEAU) {
            System.out.println("Erreur : le pion sort du plateau ! ");
            return false;
        }
        // Un mur bloque le joueur.
        else if(murBloqueMouvement(coordsPion, coordsMvmt)) {
            System.out.println("Erreur : un mur bloque le passage ! ");
            return false;
        }
        // Le mouvement est valide.
        else {
            return true;
        }
    }

    /**
     * Renvoie les coordonnées du prochain mouvement effectué à un pion.
     *
     * @param coordsJoueur la position actuelle du pion
     *
     * @param typeMouvement le mouvement que le pion effectue (G : gauche, H : Haut, D : Droit, B : Bas)
     *
     * @return un tableau contenant les coordonnées du pion après mouvement
     */
    public int[] coordsProchainMouvement(int[] coordsJoueur, String typeMouvement) {
        int[] coordsMvmt = new int[2];

        switch (typeMouvement) {
            case "G":
                coordsMvmt[0] = coordsJoueur[0];
                coordsMvmt[1] = coordsJoueur[1] - 1;

                if (coordsMvmt[1] > 0 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[1]--;
                break;

            case "H":
                coordsMvmt[0] = coordsJoueur[0] - 1;
                coordsMvmt[1] = coordsJoueur[1];

                if (coordsMvmt[0] > 0 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[0]--;
                break;

            case "D":
                coordsMvmt[0] = coordsJoueur[0];
                coordsMvmt[1] = coordsJoueur[1] + 1;

                if (coordsMvmt[1] < TAILLE_PLATEAU - 1 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[1]++;
                break;

            case"B":
                coordsMvmt[0] = coordsJoueur[0] + 1;
                coordsMvmt[1] = coordsJoueur[1];

                if (coordsMvmt[0] < TAILLE_PLATEAU - 1 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[0]++;
                break;
        }

        return coordsMvmt;
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
    public boolean mancheFinie(ArrayList<Joueur> joueurs) {
        if (joueurs.get(0).coordsPion[0] == TAILLE_PLATEAU - 1 || joueurs.get(1).coordsPion[0] == 0)
                return true;

         if (joueurs.size() == 4)
             return joueurs.get(2).coordsPion[0] == 8 || joueurs.get(3).coordsPion[0] == 0;

        return false;
    }

}
