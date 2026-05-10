package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Triangle extends Shape {

    int base, height;
    public Triangle(Point position, Color color, int base, int height){
        super(position, color);
        this.base = base;
        this.height = height;
    }


    public int getBase(){
        return this.base;
    }

    public int getHeight(){
        return this.height;
    }

    public void setBase(int newBase){
        this.base = newBase;
    }

    public void setHeight(int newHeight){
        this.height = newHeight;
    }
    
    // Helper methods
    private double calculateArea(Point a, Point b, Point c){
        return Math.abs((a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0);
    }
    
    private double getTriangleArea(){
        return Math.abs((base * height) / 2.0);
    }
    
    @Override
    public void setSize(int width, int height){
        this.base = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g){

        int x = getPoint().x;
        int y = getPoint().y;

        int[] xPoints = {x, x + base, x + (base / 2)};
        int[] yPoints = {y, y, y - height};

        g.setColor(this.color);
        g.drawPolygon(xPoints, yPoints, 3);
    }
    @Override
    public boolean contains(Point mousePoint){

        Point a = this.position;
        Point b = new Point(this.position.x + base, this.position.y);
        Point c = new Point(this.position.x + (base / 2), this.position.y - height);

        double area1 = calculateArea(mousePoint, a, b);
        double area2 = calculateArea(mousePoint, b, c);
        double area3 = calculateArea(mousePoint, c, a);


        return Math.abs(getTriangleArea() - (area1 + area2 + area3)) < 0.1;
    }

    @Override
    public java.awt.Rectangle getBounds(){
        // Find the true lowest X and Y values
        int minX = Math.min(this.position.x, this.position.x + base);
        int minY = Math.min(this.position.y, this.position.y - height);

        int boundHeight = Math.abs(this.height) + 10;
        int boundWidth = Math.abs(this.base) + 10;

        return new java.awt.Rectangle(minX - 5, minY - 5, boundWidth, boundHeight);
    }
}
