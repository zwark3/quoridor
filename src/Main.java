public class Main {
    public static void main(String[] args) {
        char[][] plateau = new char[9][9];
        char[][] murs = new char[9][9];


        MethodesPlateau.initialiserMur(murs);
        MethodesPlateau.placerMurVertical(murs, 1, 1);
        MethodesPlateau.afficherMur(murs);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();


        MethodesPlateau.initialiserPlateau(plateau);
        MethodesPlateau.afficherPlateauDeJeu(plateau);

    }
}

