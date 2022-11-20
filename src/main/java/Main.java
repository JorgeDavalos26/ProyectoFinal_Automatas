import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame obj = new JFrame();
        obj.setTitle("Programa del Padrino");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Automata automata = new Automata();
        obj.add(automata);

        obj.pack();
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);

    }


}
