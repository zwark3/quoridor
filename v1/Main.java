import java.beans.MethodDescriptor;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        char[][] plateau = new char[9][9];

        MethodesJeu.initialiserPlateau(plateau, 2);
        MethodesJeu.afficherPlateauDeJeu(plateau);

    }
}

