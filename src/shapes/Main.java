package shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.BorderLayout;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> createAndShowGUI());

    }

    private static void createAndShowGUI(){
        JFrame frame = new JFrame("Shape editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        CanvasPanel canvas = new CanvasPanel();
        canvas.addShape(new Rectangle(new Point(10, 15), Color.BLACK, 50, 50));

        frame.add(canvas, BorderLayout.CENTER);

        JPanel toolbar = new JPanel(); 

        JButton groupButton = new JButton("Group"); 
        JButton unGroupButton = new JButton("Ungroup");

        toolbar.add(unGroupButton);
        toolbar.add(groupButton); 

        groupButton.addActionListener(e -> {
            canvas.groupSelectedShapes();
            canvas.repaint();
        });

        unGroupButton.addActionListener(e ->{
            canvas.ungroupSelectedShapes();
            canvas.repaint();
        });



        frame.add(toolbar, BorderLayout.NORTH); 

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

}


