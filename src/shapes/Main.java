package shapes;

import java.io.File;
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

        frame.add(canvas, BorderLayout.CENTER);

        JPanel toolbar = new JPanel(); 

        JButton selectButton = new JButton("Select");
        JButton rectButton = new JButton("Rectangle");
        JButton circleButton = new JButton("Circle");
        JButton triangleButton = new JButton("Triangle");
        JButton groupButton = new JButton("Group"); 
        JButton unGroupButton = new JButton("Ungroup");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton clearButton = new JButton("Clear");
        JButton colorButton = new JButton("Color");

        toolbar.add(selectButton);
        toolbar.add(rectButton);
        toolbar.add(circleButton);
        toolbar.add(triangleButton);

        toolbar.add(new JLabel(" | ")); 
        toolbar.add(groupButton);
        toolbar.add(unGroupButton);

        toolbar.add(new JLabel(" | ")); // Another spacer
        toolbar.add(saveButton);
        toolbar.add(loadButton);

        toolbar.add(new JLabel(" | ")); // Another spacer
        toolbar.add(clearButton);

        toolbar.add(new JLabel(" | "));
        toolbar.add(colorButton);


        // 3. Wire up the Tool buttons using your new setTool method
        selectButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.SELECT));
        rectButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.RECTANGLE));
        circleButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.CIRCLE));
        triangleButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.TRIANGLE));
        clearButton.addActionListener(e -> canvas.clearCanvas());


        saveButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String path = file.getAbsolutePath();
                        // Automatically append .bin if the user forgot to type it
                        if (!path.endsWith(".bin")) {
                            path += ".bin";
                        }
                        canvas.saveToFile(path);
                    }
                });

        loadButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        canvas.loadFromFile(file.getAbsolutePath());
                    }
                });

        groupButton.addActionListener(e -> {
            canvas.groupSelectedShapes();
            canvas.repaint();
        });

        unGroupButton.addActionListener(e ->{
            canvas.ungroupSelectedShapes();
            canvas.repaint();
        });

        colorButton.addActionListener(e -> {
            // This pops up the standard Windows/Mac color picker!
            Color chosenColor = JColorChooser.showDialog(frame, "Choose a Color", Color.BLACK);
            if (chosenColor != null) {
                canvas.changeColor(chosenColor);
            }
        });


        frame.add(toolbar, BorderLayout.NORTH); 

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

}


