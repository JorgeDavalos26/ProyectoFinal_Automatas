import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame obj = new JFrame();
        obj.setTitle("Proyecto final de Automatas");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.getContentPane().setPreferredSize(new Dimension(1600, 1000));

        Automata automata = new Automata();
        obj.add(automata);

        obj.pack();
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);

    }


}
