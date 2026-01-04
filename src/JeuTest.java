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

        // Création de joueurs
        joueurs = jeu.creerJoueurs(2);
        // Initialiser le jeu
        jeu.initialiserJeu(joueurs);
    }

    @Test
    void murValide() {

        // Test valeurs hors plateau
        boolean murDepassePlateauHorizontal = jeu.murValide(joueurs, 1, 14, 9);
        assertFalse(murDepassePlateauHorizontal);
        boolean murDepassePlateauVertical = jeu.murValide(joueurs, 2, -1, 3);
        assertFalse(murDepassePlateauVertical);


        // Test sur placements de mur aux mêmes coordonnées.

        // Horizontalement
        jeu.placerMurHorizontal(1, 1);
        boolean murVerticalCoordsIdentique = jeu.murValide(joueurs, 1, 1, 1);
        jeu.retirerMurHorizontal(1, 1);
        assertFalse(murVerticalCoordsIdentique);

        // Verticalement
        jeu.placerMurVertical(5, 3);
        boolean murHorizontalCoordsIdentique = jeu.murValide(joueurs, 2, 5, 3);
        jeu.retirerMurVertical(5, 3);
        assertFalse(murHorizontalCoordsIdentique);

        // Test de mur qui s'entrechoque (forme de croix +)

        // Horizontalement
        jeu.placerMurHorizontal(1, 1);
        boolean mursVerticalEntreChoqueCroix = jeu.murValide(joueurs, 2, 1, 1);
        jeu.retirerMurHorizontal(1, 1);
        assertFalse(mursVerticalEntreChoqueCroix);

        // Verticalement
        jeu.placerMurVertical(5, 3);
        boolean mursHorizontalEntreChoqueCroix = jeu.murValide(joueurs, 1, 5, 3);
        jeu.retirerMurVertical(5, 3);
        assertFalse(mursHorizontalEntreChoqueCroix);

        // Test de mur forme --|
        jeu.placerMurVertical(7, 4);
        boolean mursCorrectForme1VerticalInit = jeu.murValide(joueurs, 1, 7, 3);
        jeu.retirerMurVertical(7, 4);
        assertTrue(mursCorrectForme1VerticalInit);

        // Test de mur forme : mur horizontal en dessous d'un mur vertical
        jeu.placerMurHorizontal(7, 2);
        boolean mursCorrectForme2HorizontalInit = jeu.murValide(joueurs, 2, 8, 2);
        jeu.retirerMurHorizontal(7, 2);
        assertTrue(mursCorrectForme2HorizontalInit);

        // Mur qui empêche un des joueurs de terminal la partie
        jeu.placerMurHorizontal(7, 5);
        jeu.placerMurVertical(8, 4);

        boolean murCheminFinExiste = jeu.murValide(joueurs, 2, 8, 6);
        jeu.retirerMurHorizontal(7, 5);
        jeu.retirerMurVertical(8, 4);
        assertFalse(murCheminFinExiste);

    }

    @org.junit.jupiter.api.Test
    void existeCheminVersFin() {
    }
}