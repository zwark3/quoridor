public class Main {
    public static void main(String[] args) {
        char[][] plateau = new char[9][9];
        char[][] mursV = new char[9][8];
        char[][] mursH = new char[8][9];
        MethodesPlateau.initialiserPlateau(plateau);
        MethodesPlateau.initialiserMursHV(mursH, mursV);
        MethodesPlateau.placerMurHorizontal(mursH, 1, 1);
        MethodesPlateau.placerMurVertical(mursH, 2, 8);
        MethodesPlateau.afficherPlateauDeJeu(plateau, mursH, mursV);
    }
}

