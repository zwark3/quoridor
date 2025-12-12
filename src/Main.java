public class Main {
    public static void main(String[] args) {
        char[][] plateau = new char[9][9];
        MethodesJeu.initialiserPlateau(plateau, 2);
        MethodesJeu.afficherPlateauDeJeu(plateau);
    }
}

