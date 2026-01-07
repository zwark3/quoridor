import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


class JeuTest {

    Jeu jeu;
    ArrayList<Joueur> joueurs;

    @BeforeEach
    void initJeu() {
        jeu = new Jeu();
        joueurs = jeu.creerJoueurs(4);
        jeu.initialiserJeu(joueurs);
    }

    @Test
    void murValide() {

        // Test valeurs hors plateau
        assertFalse(jeu.murValide(joueurs, 1, 14, 9), "cas dépasse mur horizontalement");
        assertFalse(jeu.murValide(joueurs, 2, -1, 3),"cas dépasse mur verticalement");


        // Test sur placements de mur aux mêmes coordonnées.

        // Horizontalement
        jeu.placerMurHorizontal(1, 1);
        assertFalse(jeu.murValide(joueurs, 1, 1, 1), "cas mur horizontalement superposé");
        jeu.retirerMurHorizontal(1, 1);

        // Verticalement
        jeu.placerMurVertical(5, 3);
        assertFalse(jeu.murValide(joueurs, 2, 5, 3), "cas mur vertical superposé");
        jeu.retirerMurVertical(5, 3);

        // Test de mur qui s'entrechoque (forme de croix +)

        // Horizontalement
        jeu.placerMurHorizontal(1, 1);
        assertFalse(jeu.murValide(joueurs, 2, 1, 1), "cas mur horizontal vertical entrechoque");
        jeu.retirerMurHorizontal(1, 1);

        // Verticalement
        jeu.placerMurVertical(5, 3);
        assertFalse(jeu.murValide(joueurs, 1, 5, 3), "cas mur vertical horizontal entrechoque");
        jeu.retirerMurVertical(5, 3);

        // Test de mur forme --|
        jeu.placerMurVertical(7, 4);
        assertTrue(jeu.murValide(joueurs, 1, 7, 3), "cas mur vertical à gauche mur horizontal");
        jeu.retirerMurVertical(7, 4);

        // Test de mur forme : mur horizontal en dessous d'un mur vertical
        jeu.placerMurHorizontal(7, 2);
        boolean mursCorrectForme2HorizontalInit = jeu.murValide(joueurs, 2, 8, 2);
        assertTrue(mursCorrectForme2HorizontalInit, "cas mur vertical au-dessus mur horizontal");
        jeu.retirerMurHorizontal(7, 2);

        // Mur qui empêche un des joueurs de terminée la partie
        jeu.placerMurHorizontal(7, 5);
        jeu.placerMurVertical(8, 4);
        assertFalse(jeu.murValide(joueurs, 2, 8, 6), "cas mur qui empêche fin de partie");
        jeu.retirerMurHorizontal(7, 5);
        jeu.retirerMurVertical(8, 4);

    }

    @Test
    void existeCheminVersFin() {
        // Plateau de jeu sans aucun mur
        assertTrue(jeu.existeCheminVersFin(joueurs));

        // Mur horizontal et vertical qui ne bloque personne
        jeu.placerMurHorizontal(2, 4);
        jeu.placerMurVertical(4, 3);
        assertTrue(jeu.existeCheminVersFin(joueurs), "cas aucun mur vertical horizontal pas de blocage");
        jeu.retirerMurHorizontal(2,4);
        jeu.retirerMurVertical(4,3);

        // Mur qui bloque un seul joueur
        jeu.placerMurVertical(8, 4);
        jeu.placerMurVertical(8, 6);
        jeu.placerMurHorizontal(7, 5);
        assertFalse(jeu.existeCheminVersFin(joueurs), "cas murs bloque un seul joueur");

        // Mur qui bloque le chemin des 2 joueurs
        for (int colonne = 1; colonne < 8; colonne += 2) {
            jeu.placerMurHorizontal(1, colonne);
        }

        jeu.placerMurVertical(1, 8);
        jeu.placerMurHorizontal(2, 8);

        assertFalse(jeu.existeCheminVersFin(joueurs), "cas murs bloque deux joueurs");

        for (int colonne = 1; colonne < 8; colonne += 2) {
            jeu.retirerMurHorizontal(1, colonne);
        }

        jeu.retirerMurVertical(1,8);
        jeu.retirerMurHorizontal(2,8);


    }

    @Test
    void MurBloqueMouvement() {

        Joueur J1 = joueurs.getFirst();
        ArrayList<int[]> listeMouvementBasPasBloque = jeu.filtrerCoupsParDirection("B", J1.coordsPion, jeu.determineCoupsLegauxPion(J1.coordsPion));
        int[] mouvementBasPasBloque = listeMouvementBasPasBloque.getFirst();
        assertFalse(jeu.murBloqueMouvement(J1.coordsPion, mouvementBasPasBloque), "cas mouvement sans mur vers le bas");

        Joueur J2 = joueurs.get(1);
        ArrayList<int[]> listeMouvementGauchePasBloque = jeu.filtrerCoupsParDirection("G", J2.coordsPion, jeu.determineCoupsLegauxPion(J2.coordsPion));
        int[] mouvementGauchePasBloque = listeMouvementGauchePasBloque.getFirst();
        assertFalse(jeu.murBloqueMouvement(J2.coordsPion, mouvementGauchePasBloque), "cas mouvement sans mur vers la gauche");


        Joueur J3 = joueurs.get(2);
        ArrayList<int[]> listeMouvementHorizontalBloque = jeu.filtrerCoupsParDirection("D", J3.coordsPion, jeu.determineCoupsLegauxPion(J3.coordsPion));
        int[] mouvementHorizontalBloque = listeMouvementHorizontalBloque.getFirst();
        jeu.placerMurVertical(J3.coordsPion[0] + 1, J3.coordsPion[1] + 1);
        assertTrue(jeu.murBloqueMouvement(J3.coordsPion, mouvementHorizontalBloque), "cas mouvement mur bloque horizontalement vers droite");

        Joueur J4 = joueurs.get(3);
        ArrayList<int[]> listeMouvementVerticalBloque = jeu.filtrerCoupsParDirection("H", J4.coordsPion, jeu.determineCoupsLegauxPion(J4.coordsPion));
        int[] mouvementVerticalBloque = listeMouvementVerticalBloque.getFirst();
        jeu.placerMurHorizontal(J4.coordsPion[0], J4.coordsPion[1]);
        assertTrue(jeu.murBloqueMouvement(J4.coordsPion, mouvementVerticalBloque), "cas mouvement mur bloque verticalement en haut");
    }

    @Test
    void determineCoupsLegauxPion() {
        for (Joueur joueur : joueurs) {
            assertEquals(3, jeu.determineCoupsLegauxPion(joueur.coordsPion).size(), "cas chaque joueur débute avec 3 mouvements possibles");
        }

        Joueur J1 = joueurs.getFirst();
        jeu.placerMurHorizontal(J1.coordsPion[0] + 1, J1.coordsPion[1] + 1);
        assertEquals(2, jeu.determineCoupsLegauxPion(J1.coordsPion).size(), "cas mur horizontal bloque mouvement");
        jeu.retirerMurHorizontal(J1.coordsPion[0] + 1, J1.coordsPion[1] + 1);

        Joueur J2 = joueurs.get(1);
        jeu.placerMurVertical(8 , 4);
        assertEquals(2, jeu.determineCoupsLegauxPion(J2.coordsPion).size(), "cas mur vertical bloque mouvement");
        jeu.retirerMurHorizontal(8, 4);

        jeu.placerMurVertical(8 , 4);
        jeu.placerMurVertical(8, 5);
        assertEquals(1, jeu.determineCoupsLegauxPion(J2.coordsPion).size(), "cas deux murs verticaux un mouvement restant");
    }

}