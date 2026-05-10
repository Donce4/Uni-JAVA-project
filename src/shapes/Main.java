package shapes;

import java.awt.Color;
import java.awt.Point;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> createAndShowGUI());

    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Shape editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        CanvasPanel canvas = new CanvasPanel();
        canvas.addShape(new Rectangle(new Point(10, 15), Color.BLACK, 50, 50));

        frame.add(canvas);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}


