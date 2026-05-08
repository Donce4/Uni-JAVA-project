package test;
 
import vector3d.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Test {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> createAndShowGUI());
        // SwingUtilities.invokeLater(new Runnable() {
        //     @Override
        //     public void run() {
        //         createAndShowGUI();
        //     }
        // });


        // Vector3d v1 = null;
        // Vector3d v2 = null;
        // Vector3d v3 = null;
        // Color red = new Color(255, 0, 0);
        // try {

        //     ShapeFactory sphereFactory = new SphereFactory();
        //     ShapeFactory pyramidFactory = new SquarePyramidFactory();
        //     ShapeFactory prismFactory = new RectangularPrismFactory();

        //     v1 = sphereFactory.createShape(5, 5, 5, red, 5.0);
        //     v2 = pyramidFactory.createShape(5, 5, 5, red, 5.0, 5.0);
        //     v3 = prismFactory.createShape(5, 5, 5, red, 5.0, 5.0, 5.0);

        //     Vector3d v1_copy = (Vector3d) v1.clone();

        //     v1_copy.getColor().setG(200);

        //     System.out.println("Original color: " + v1.getColor().getR() + " " + v1.getColor().getG() + " "
        //             + v1.getColor().getB());
        //     System.out.println("Clone color: " + v1_copy.getColor().getR() + " " + v1_copy.getColor().getG() + " "
        //             + v1_copy.getColor().getB() + "\n");

        //     System.out.println("Original address: " + System.identityHashCode(v1.getColor()));
        //     System.out.println("Clone address: " + System.identityHashCode(v1_copy.getColor()) + "\n");

        //     // System.out.println(
        //     // "Sphere: " + v1.toString() +
        //     // "\nArea: " + v1.calculateArea() +
        //     // "\nVolume: " + v1.calculateVolume() + "\n");
        //     // System.out.println(
        //     // "Square Pyramid: " + v2.toString() +
        //     // "\nArea: " + v2.calculateArea() +
        //     // "\nVolume: " + v2.calculateVolume() + "\n");

        //     // System.out.println(
        //     // "Rectangular Prism: " + v3.toString() +
        //     // "\nArea: " + v3.calculateArea() +
        //     // "\nVolume: " + v3.calculateVolume() + "\n");

        //     Vector3d badSphere = sphereFactory.createShape(5, 5, 5, red, -10.0);

        // } catch (InvalidDimensionException e) {
        //     System.err.println("Error: " + e.getMessage() + " Wrong number: " + e.getBadValue());
        //     // Dont do anythng
        // } catch (Exception e) {
        //     System.err.println("Something went wrong" + e);
        // } finally {
        //     System.out.println("The process of creating figures is finished");
        // }

    }

private static void createAndShowGUI() {

    List<Vector3d> shapesToSave = new ArrayList<>();
    JFrame frame = new JFrame("Shape Manager");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 300);
    frame.setLayout(new FlowLayout());

    JTextField xField = new JTextField("0", 3);
    JTextField yField = new JTextField("0", 3);
    JTextField zField = new JTextField("0", 3);
    JTextField dimField = new JTextField("5.0", 5);
    JLabel label = new JLabel("X, Y, Z and Dimension:");

    JButton addSphereButton = new JButton("Add Sphere");
    
    JButton saveButton = new JButton("Save to File");
    JButton loadButton = new JButton("Load and View");

    frame.add(label);
    frame.add(xField);
    frame.add(yField);
    frame.add(zField);
    frame.add(new JLabel("Size:"));
    frame.add(dimField);
    frame.add(addSphereButton);
    frame.add(new JSeparator());
    frame.add(saveButton);
    frame.add(loadButton);

    frame.setVisible(true);

    addSphereButton.addActionListener(e -> {
        try {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            int z = Integer.parseInt(zField.getText());
            double dim = Double.parseDouble(dimField.getText());
            vector3d.Color red = new vector3d.Color(255, 0, 0);
            Vector3d newSphere = new SphereFactory().createShape(x, y, z, red, dim);
            shapesToSave.add(newSphere);

            JOptionPane.showMessageDialog(frame, "Sphere added! Total shapes: " + shapesToSave.size());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    });
        frame.add(saveButton);
        frame.add(loadButton);

        frame.setVisible(true);

        saveButton.addActionListener(e -> {
            new Thread(() -> {
                try(FileOutputStream fos = new FileOutputStream("figuros.bin");
                ObjectOutputStream oos = new ObjectOutputStream(fos)){
                    oos.writeObject(shapesToSave);
                }catch (Exception ex){
                    System.err.println("Error while saving file: " + ex);
                }
            }).start();
        });

        loadButton.addActionListener(e -> {
            new Thread(() -> {
                try(FileInputStream fis = new FileInputStream("figuros.bin");
                ObjectInputStream ois = new ObjectInputStream(fis)){
                    @SuppressWarnings("unchecked")
                    List<Vector3d> loadedShapes = (List<Vector3d>) ois.readObject();
                    StringBuilder message = new StringBuilder();
                    message.append("Užkrauta figūrų: ").append(loadedShapes.size()).append("\n\n");
                    for (Vector3d shape : loadedShapes) {
                        message.append("Figūra: ").append(shape.toString()).append("\n");
                        message.append(String.format("Plotas: %.2f\n", shape.calculateArea()));
                        message.append(String.format("Tūris: %.2f\n", shape.calculateVolume()));
                        message.append("\n");
                    }
                    SwingUtilities.invokeLater(() -> {
                        JTextArea textArea = new JTextArea(message.toString());
                        textArea.setEditable(false);
                        textArea.setMargin(new Insets(5, 5, 5, 5));
                        
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(400, 250));
                        
                        JOptionPane.showMessageDialog(frame, scrollPane, "Užkrautos figūros", JOptionPane.INFORMATION_MESSAGE);
                    });
                }catch (Exception ex){
                    System.err.println("Error while loading file: " + ex);
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Klaida užkraunant: " + ex.getMessage(), "Klaida", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
        });
    }
}