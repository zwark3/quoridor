import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jeu {
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

        for (Joueur joueur : tabJoueurs) {
            this.plateau[joueur.coordsPion[0]][joueur.coordsPion[1]] = joueur.pion;
        }
    }


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

    public boolean murValide(int ligneMur, int colonneMur) {

        // Le pion se retrouvera en dehors du plateau.
        if (ligneMur < 1 || ligneMur > 9 || colonneMur < 1 || colonneMur > 9) {
            System.out.println("Erreur : valeurs hors du plateau !");
            return false;
        }
        // Un mur est déjà présent.
            else if (this.mursH[ligneMur - 1][colonneMur - 1].equals("—") || this.mursH[ligneMur - 1][colonneMur].equals("—")
                || this.mursV[ligneMur - 1][colonneMur - 1].equals("|") || this.mursV[ligneMur][colonneMur - 1].equals("|")) {
            System.out.println("Erreur : un mur est déjà présent ! ");
            return false;

        } else {
            return true;
        }
    }

    public void placerMurHorizontal(Joueur joueur, int ligne, int colonne) {
        this.mursH[ligne - 1 ][colonne - 1] = "—";
        this.mursH[ligne - 1][colonne] = "—";

        // On enlève un mur au joueur.
        joueur.nombreMurs--;
    }

    public void placerMurVertical(Joueur joueur, int ligne, int colonne) {
        this.mursV[ligne - 1][colonne - 1] = "|";
        this.mursV[ligne][colonne - 1] = "|";

        // On enlève un mur au joueur.
        joueur.nombreMurs--;
    }

    public boolean murBloqueMouvement(Joueur joueur, int[] coordsMvmt) {

        boolean mvmtVertical = joueur.coordsPion[0] != coordsMvmt[0];

        int ligne = joueur.coordsPion[0];
        int colonne = joueur.coordsPion[1];

        int ligneArv = coordsMvmt[0];
        int colonneArv = coordsMvmt[1];

        // Mouvement vertical : on regarde les murs horizontaux
        if (mvmtVertical) {
            // On va de bas en haut : il suffit juste de regarder s'il y a un mur aux coordonnées du mouvement final.
            if (ligne - ligneArv > 0)
                return !this.mursH[ligneArv][colonneArv].equals(" ");
                // On va de haut en bas : on doit regarder s'il y a un mur aux coordonnées du mouvement initial
            else
                return !this.mursH[ligne][colonne].equals(" ");
        }
        // Mouvement horizontal : on regarde les murs verticaux
        else {
            // On va de droite à gauche : il suffit juste de regarder s'il y a un mur aux coordonnées du mouvement final.
            if (colonne - colonneArv > 0)
                return !this.mursV[ligneArv][colonneArv].equals(" ");
                // On va de gauche à droite : on doit regarder s'il y a un mur aux coordonnées du mouvement initial
            else
                return !this.mursV[ligne][colonne].equals(" ");
        }
    }

    /*
    public ArrayList<int[]> coupsLegauxPion(Joueur joueur) {
        ArrayList<int[]> listeCoupsLegaux = new ArrayList<>();

        int lignePionJ = joueur.coordsPion[0];
        int colonnePionJ = joueur.coordsPion[1];

        int[] mvmtGauche = {lignePionJ, colonnePionJ - 1};
        int[] mvmtHaut = {lignePionJ - 1, colonnePionJ};
        int[] mvmtDroit = {lignePionJ, colonnePionJ + 1};
        int[] mvmtBas = {lignePionJ + 1, colonnePionJ};

        // Mouvement à gauche
        if (colonnePionJ != 0 && !murBloqueMouvement(joueur, mvmtGauche))
            listeCoupsLegaux.add(mvmtGauche);
        // Mouvement haut
        if (lignePionJ != 0 && !murBloqueMouvement(joueur, mvmtHaut))
            listeCoupsLegaux.add(mvmtHaut);
        // Mouvement à droite
        if (colonnePionJ != 8 && !murBloqueMouvement(joueur, mvmtDroit))
            listeCoupsLegaux.add(mvmtDroit);
        // Mouvement en bas
        if (lignePionJ != 8 && !murBloqueMouvement(joueur, mvmtBas))
            listeCoupsLegaux.add(mvmtBas);

        return listeCoupsLegaux;
    }

     */

    public boolean mouvementValide(Joueur joueur, String typeMouvement, int[] coordsMvmt) {
        List<String> mvmtCorrects = Arrays.asList("G", "H", "D", "B");

        // Le mouvement n'existe pas
        if (!mvmtCorrects.contains(typeMouvement)) {
            System.out.println("Erreur : type de mouvement invalide !");
            return false;
        }
        // Le pion sera en dehors du plateau
        else if (coordsMvmt[0] == -1 || coordsMvmt[0] == 9 || coordsMvmt[1] == -1 | coordsMvmt[1] == 9) {
            System.out.println("Erreur : le pion sort du plateau ! ");
            return false;
        }
        // Un mur bloque le joueur
        else if(murBloqueMouvement(joueur, coordsMvmt)) {
            System.out.println("Erreur : un mur bloque le passage ! ");
            return false;
        }
        // Le mouvement est valide
        else {
            return true;
        }
    }

    public int[] coordsProchainMouvement(Joueur joueur, String typeMouvement) {
        int[] coordsMvmt = new int[2];

        switch (typeMouvement) {
            case "G":
                coordsMvmt[0] = joueur.coordsPion[0];
                coordsMvmt[1] = joueur.coordsPion[1] - 1;

                if (coordsMvmt[1] != -1 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[1]--;
                break;

            case "H":
                coordsMvmt[0] = joueur.coordsPion[0] - 1;
                coordsMvmt[1] = joueur.coordsPion[1];

                if (coordsMvmt[0] != -1 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[0]--;
                break;

            case "D":
                coordsMvmt[0] = joueur.coordsPion[0];
                coordsMvmt[1] = joueur.coordsPion[1] + 1;

                if (coordsMvmt[1] != 9 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[1]++;
                break;

            case"B":
                coordsMvmt[0] = joueur.coordsPion[0] + 1;
                coordsMvmt[1] = joueur.coordsPion[1];

                if (coordsMvmt[0] != 9 && !this.plateau[coordsMvmt[0]][coordsMvmt[1]].equals("."))
                        coordsMvmt[0]++;
                break;
        }

        return coordsMvmt;
    }

    public void bougerPion(Joueur joueur, int[] coordsProchainMvmt) {
        this.plateau[coordsProchainMvmt[0]][coordsProchainMvmt[1]] = joueur.pion;
        this.plateau[joueur.coordsPion[0]][joueur.coordsPion[1]] = ".";

        joueur.coordsPion[0] = coordsProchainMvmt[0];
        joueur.coordsPion[1] = coordsProchainMvmt[1];
    }

    public boolean mancheFinie(ArrayList<Joueur> joueurs) {
        if (joueurs.get(0).coordsPion[0] == 8 || joueurs.get(1).coordsPion[0] == 0)
                return true;

         if (joueurs.size() == 4)
             return joueurs.get(2).coordsPion[0] == 8 || joueurs.get(3).coordsPion[0] == 0;

        return false;
    }

}
