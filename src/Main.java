public class Main {
    public static void main(String[] args) {
        char[][] plateau = new char[9][9];
        char[][] murs = new char[9][9];

        MethodesPlateau.initialiserPlateau(plateau, 2);
        MethodesPlateau.afficherPlateauDeJeu(plateau);

        /*
         MethodesPlateau.initialiserPlateau(murs);
        MethodesPlateau.initialiserPlateau(plateau);
        MethodesPlateau.placerMurHorizontal(murs, 0, 7);
        MethodesPlateau.placerMurVertical(murs, 0, 0);
        MethodesPlateau.afficherMur(murs);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        MethodesPlateau.afficherPlateauDeJeu(plateau);
        */
    }
}

