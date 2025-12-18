public class Main {
    public static void main(String[] args) {
        char[][] plateau = new char[9][9];
        char[][] mursV = new char[9][8];
        char[][] mursH = new char[8][9];
        MethodesPlateau.initialiserPlateau(plateau, 2);
        MethodesPlateau.initialiserMursHV(mursH, mursV);
        MethodesPlateau.placerMurHorizontal(mursH, 5, 4);
        MethodesPlateau.afficherPlateauDeJeu(plateau, mursH, mursV);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        MethodesPlateau.bougerJoueur(plateau, 'R', 'D');
    }
}

