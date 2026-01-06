/**
 * La classe Joueur renferme et définit des proprietés propres aux joueurs qui joue au jeu.
 */
public class Joueur {

    String nomJ;
    String pion;
    String couleurPion;
    int[] coordsPion;
    int nombreMurs;

    Joueur(String nomJ, String pion, String couleurPion, int[] coordsPion) {
        this.nomJ = nomJ;
        this.pion = pion;
        this.couleurPion = couleurPion;
        this.coordsPion = coordsPion;
        this.nombreMurs = 20;
    }

}
