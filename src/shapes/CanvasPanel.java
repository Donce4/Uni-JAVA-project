package shapes;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Group;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;


// Responsible for drawing whatever is currently in its list

public class CanvasPanel extends JPanel{

    protected ArrayList<Shape> shapes = new ArrayList<>();
    private Point lastMousePoint = null; 
    protected Shape currShape = null;
    protected Color currentColor = Color.BLACK;
    protected ArrayList<Shape> selectedShapes = new ArrayList<>(); // Creates selected shapes ArrayList
    protected enum ToolType{
        SELECT, RECTANGLE, CIRCLE, TRIANGLE;
    }
    ToolType currTool = ToolType.SELECT;

    public CanvasPanel(){
        this.setFocusable(true);

        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // Check for both Backspace and Delete keys
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE || 
                    e.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    deleteSelectedShapes();
                }
            }
        });
        MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    requestFocusInWindow();
                    if (currTool == ToolType.SELECT){
                        findSelectedShape(e.getPoint());
                            if (currShape != null){
                                if (!e.isControlDown()){
                                    selectedShapes.clear();
                                    selectedShapes.add(currShape);
                                }
                                else if(selectedShapes.contains(currShape)){
                                    selectedShapes.remove(currShape);
                                }
                                else{
                                    selectedShapes.add(currShape);
                                }
                                lastMousePoint = e.getPoint(); // Remember beginning
                            }
                            else{
                                selectedShapes.clear();
                            }
                    }
                    else if (currTool == ToolType.RECTANGLE || currTool == ToolType.CIRCLE || currTool == ToolType.TRIANGLE){
                            currShape = createShape(currTool, e.getPoint());
                            if (currShape != null) {
                                addShape(currShape);
                                lastMousePoint = e.getPoint();
                            }
                        }
                }
                @Override
                public void mouseDragged(MouseEvent e){
                    if (currTool == ToolType.SELECT){
                        if (currShape != null && lastMousePoint != null){
        
                            Point currentPoint = e.getPoint();
        
                            int dx = currentPoint.x - lastMousePoint.x;
                            int dy = currentPoint.y - lastMousePoint.y;
                            
                            for (Shape s : selectedShapes){
                                s.moveBy(dx, dy);
                            }
        
                            lastMousePoint = currentPoint; // Reset
                            repaint();
                        }
                    }
                    else if (currTool == ToolType.RECTANGLE || currTool == ToolType.CIRCLE || currTool == ToolType.TRIANGLE){
                        if (currShape != null){

                            int width = e.getX() - lastMousePoint.x;
                            int height = e.getY() - lastMousePoint.y;

                            currShape.setSize(width, height);

                            repaint();

                        }
                }
            }
        };
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    public void setTool(ToolType newTool) {
        this.currTool = newTool;
        this.selectedShapes.clear(); // Good practice to deselect when switching tools
        repaint();
    }
    private Shape createShape(ToolType type, Point p) {
        switch (type) {
            case RECTANGLE: return new Rectangle(p, this.currentColor, 0, 0);
            case CIRCLE:    return new Circle(p, this.currentColor, 0);
            case TRIANGLE:  return new Triangle(p, this.currentColor, 0, 0);
            default: return null;
        }
    }

    public void ungroupSelectedShapes(){
        if (selectedShapes.size() == 1 && selectedShapes.get(0) instanceof GroupShape){
            
            GroupShape group = (GroupShape) selectedShapes.get(0); // Get that group
            ArrayList<Shape> children = group.getShapes(); // Unwrap it to ArrayList
            shapes.addAll(children); // Add all of them
            shapes.remove(group); // Remove the group
            selectedShapes.clear(); // Clear the selected items


        }
    }

    public void groupSelectedShapes(){
        if (selectedShapes.size() > 1){

            Point originalPos = selectedShapes.get(0).getPoint(); // Create reference
            Point groupPos = new Point(originalPos.x, originalPos.y);

            GroupShape group = new GroupShape(groupPos, Color.BLACK); // We create a new group

            for (Shape s : selectedShapes){ // Transfer all the shapes from shape list to a group list
                group.addShape(s);
            }
            shapes.removeAll(selectedShapes);
            shapes.add(group); // Add a single shape as a group
            selectedShapes.clear(); // We clear the selected shapes
            selectedShapes.add(group);
            currShape = group;
            repaint();
        }
    }
    
    public void addShape(Shape s){
        shapes.add(s);
        repaint();
    }
    public void deleteSelectedShapes() {
        this.shapes.removeAll(selectedShapes);
        this.selectedShapes.clear();
        this.currShape = null;
        this.repaint();
    }

    public void clearCanvas() {
        this.shapes.clear();
        this.selectedShapes.clear();
        this.currShape = null;
        this.repaint();
    }

    public void findSelectedShape(Point p){
        for (int i = shapes.size() - 1; i >= 0; --i){
            Shape s = shapes.get(i);
            if (s.contains(p)){
                currShape = s;

                // DO something I guess
                repaint();
                return;
            }
        }
        currShape = null;
    }

    public void changeColor(Color newColor) {
        this.currentColor = newColor;
        for (Shape s : selectedShapes) {
            s.setColor(newColor);
        }
        this.repaint();
    }

    public void saveToFile(String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(this.shapes); 
            System.out.println("Shapes successfully saved to " + filepath);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Tells Java to trust that we are reading an ArrayList<Shape>
    public void loadFromFile(String filepath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            // Read the object and cast it back to an ArrayList<Shape>
            this.shapes = (ArrayList<Shape>) ois.readObject(); 
            
            // Clean up the canvas state just in case
            this.selectedShapes.clear(); 
            this.currShape = null;
            this.currTool = ToolType.SELECT;
            
            this.repaint(); // Redraw the newly loaded shapes!
            System.out.println("Shapes successfully loaded from " + filepath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g); // Draw background
        for (Shape shape : shapes)
        {
            shape.draw(g);
        }
        Color oldColor = g.getColor();
        g.setColor(Color.RED);

        for (Shape s : selectedShapes){
            java.awt.Rectangle b = s.getBounds();
            g.drawRect(b.x, b.y, b.width, b.height);
        }
        g.setColor(oldColor);
    }
}