package shapes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class GroupShape extends Shape{

    protected ArrayList<Shape> shapes = new ArrayList<>();

    public GroupShape(Point position, Color color){
        super(position, color);
    }
    


    public ArrayList<Shape> getShapes(){
        return this.shapes;
    }

    public void addShape(Shape s){
        this.shapes.add(s);
    }

    public boolean isInternal(Shape s){
        return shapes.contains(s);
    }   
    
@Override
    public java.awt.Rectangle getBounds(){
        // If the group is empty, just return a tiny box
        if (shapes == null || shapes.isEmpty()) {
            return new java.awt.Rectangle(this.position.x, this.position.y, 0, 0);
        }

        // Set extreme starting values so the first shape will instantly override them
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Loop through all children to find the absolute furthest edges
        for (Shape shape : shapes){
            java.awt.Rectangle b = shape.getBounds();

            if (b.x < minX) minX = b.x;
            if (b.y < minY) minY = b.y;

            // The right edge is X + Width. The bottom edge is Y + Height.
            if (b.x + b.width > maxX) maxX = b.x + b.width;
            if (b.y + b.height > maxY) maxY = b.y + b.height;
        }

        // Create the massive square covering all extreme points
        return new java.awt.Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    public void setColor(Color color) {
        super.setColor(color);
        for (Shape s : shapes) {
            s.setColor(color);
        }
    }

    @Override
    public void setSize(int width, int height){

    }

    @Override
    public void moveBy(int dx, int dy) {
        super.moveBy(dx, dy); // Move groups own invisible anchor
        for (Shape shape : shapes) { // Tell every single shape to move
            shape.moveBy(dx, dy);
        }
    }


@Override
    public boolean contains(Point p){
        // We get the big bounding box, and ask Java if the point is inside it!
        return getBounds().contains(p);
    }

    @Override
    public void draw(Graphics g){
        for (Shape shape : shapes){
            shape.draw(g);
        }
    }
}
