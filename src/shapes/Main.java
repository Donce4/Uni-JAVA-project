package shapes;

import java.io.File;
import java.awt.Color;
import java.awt.Point;
import java.awt.BorderLayout;

import javax.swing.*;

/**
 * Pagrindinė programos klasė (Entry Point), atsakinga už grafinės naudotojo sąsajos (GUI) 
 * inicijavimą ir atvaizdavimą. Ši klasė sukuria pagrindinį langą (JFrame), įrankių juostą 
 * (Toolbar) ir susieja mygtukų paspaudimus su atitinkama {@link CanvasPanel} logika.
 */
public class Main {

    /**
     * Numatytasis konstruktorius.
     */
    public Main() {}
    
    /**
     * Pagrindinis metodas, paleidžiantis Java programą.
     * @param args Komandinės eilutės argumentai (nenaudojami).
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> createAndShowGUI());

    }

    /**
     * Sukuria ir atvaizduoja pagrindinį programos langą bei visus jo elementus.
     * Konfigūruoja {@link BorderLayout} išdėstymą: drobė (Canvas) dedama į centrą, 
     * o įrankių juosta su mygtukais – į viršų (NORTH).
     * Taip pat čia priskiriami (wire-up) mygtukų klausytojai (ActionListeners), 
     * kurie kviečia atitinkamus drobės metodus.
     */
    private static void createAndShowGUI(){
        // Sukuriamas pagrindinis langas
        JFrame frame = new JFrame("Shape editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Sukuriama ir pridedama pagrindinė piešimo drobė
        CanvasPanel canvas = new CanvasPanel();
        frame.add(canvas, BorderLayout.CENTER);

        // --- ĮRANKIŲ JUOSTOS (TOOLBAR) SUKŪRIMAS ---
        JPanel toolbar = new JPanel(); 

        // Mygtukų instancijų kūrimas
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

        // Įrankių (Tool) mygtukų pridėjimas
        toolbar.add(selectButton);
        toolbar.add(rectButton);
        toolbar.add(circleButton);
        toolbar.add(triangleButton);

        toolbar.add(new JLabel(" | ")); // Skirtukas
        
        // Grupavimo mygtukų pridėjimas
        toolbar.add(groupButton);
        toolbar.add(unGroupButton);

        toolbar.add(new JLabel(" | ")); // Skirtukas
        
        // Išsaugojimo ir užkrovimo mygtukų pridėjimas
        toolbar.add(saveButton);
        toolbar.add(loadButton);

        toolbar.add(new JLabel(" | ")); // Skirtukas
        
        // Drobės išvalymo mygtuko pridėjimas
        toolbar.add(clearButton);

        toolbar.add(new JLabel(" | ")); // Skirtukas
        
        // Spalvų pasirinkimo mygtuko pridėjimas
        toolbar.add(colorButton);


        // --- VEIKSMŲ SUSIEJIMAS (ACTION LISTENERS) ---
        
        // Įrankių perjungimo logika
        selectButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.SELECT));
        rectButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.RECTANGLE));
        circleButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.CIRCLE));
        triangleButton.addActionListener(e -> canvas.setTool(CanvasPanel.ToolType.TRIANGLE));
        
        // Išvalymo logika
        clearButton.addActionListener(e -> canvas.clearCanvas());

        // Išsaugojimo į failą logika naudojant JFileChooser
        saveButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String path = file.getAbsolutePath();
                        // Automatiškai pridedamas .bin prievardis, jei vartotojas jo neparašė
                        if (!path.endsWith(".bin")) {
                            path += ".bin";
                        }
                        canvas.saveToFile(path);
                    }
                });

        // Failo atstatymo (užkrovimo) logika
        loadButton.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        canvas.loadFromFile(file.getAbsolutePath());
                    }
                });

        // Grupavimo veiksmai
        groupButton.addActionListener(e -> {
            canvas.groupSelectedShapes();
            canvas.repaint();
        });

        // Išgrupavimo veiksmai
        unGroupButton.addActionListener(e ->{
            canvas.ungroupSelectedShapes();
            canvas.repaint();
        });

        // Spalvos pasirinkimo lango (JColorChooser) iškvietimas
        colorButton.addActionListener(e -> {
            // Iššaukia standartinį operacinės sistemos spalvų parinkiklį
            Color chosenColor = JColorChooser.showDialog(frame, "Choose a Color", Color.BLACK);
            if (chosenColor != null) {
                canvas.changeColor(chosenColor);
            }
        });

        // Įrankių juostos pridėjimas lango viršuje
        frame.add(toolbar, BorderLayout.NORTH); 

        // Lango centravimas ekrane ir atvaizdavimas
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

}