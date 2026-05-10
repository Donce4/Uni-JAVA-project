package shapes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

public class GroupShape extends Shape{

    protected ArrayList<Shape> shapes = new ArrayList<>();

    public GroupShape(Point position, Color color, ArrayList<Shape> shapes){
        super(position, color);
        this.shapes = shapes;
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
        if (shapes.isEmpty()) return new java.awt.Rectangle(0, 0, 0, 0);
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (Shape shape : shapes){

            java.awt.Rectangle b = shape.getBounds();

            // Left upper angle
            if (b.x < minX) minX = b.x;
            if (b.y < minY) minY = b.y;

            // Right lower angle (size and angle)
            if (b.x + b.width > maxX) maxX = b.x + b.width;
            if (b.y + b.height > maxY) maxY = b.y + b.height;

        }


        return new java.awt.Rectangle(minX, minY, maxX - minX, maxY - minY);

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
        for (Shape shape : shapes){
            if (shape.contains(p))
                return true;
        }
        return false;
    }

    @Override
    public void draw(Graphics g){
        for (Shape shape : shapes){
            shape.draw(g);
        }
    }
}
