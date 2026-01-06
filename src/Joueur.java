public class Joueur {

    String nomJ;
    String pion;
    String couleurPion;
    int[] coordsPion;
    int nombreMurs;

    Joueur(String nomJ, String pion, String couleurPion, int[] coordsPion, int nombreMurs) {
        this.nomJ = nomJ;
        this.pion = pion;
        this.couleurPion = couleurPion;
        this.coordsPion = coordsPion;
        this.nombreMurs = nombreMurs;
    }

}
