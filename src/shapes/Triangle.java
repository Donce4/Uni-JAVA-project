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
        return (base * height) / 2.0;
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
        int x = this.position.x - 5;
        int y = this.position.y - height - 5;

        int boundHeight = this.height + 10;
        int boundWidth = this.base + 10;

        return new java.awt.Rectangle(x, y, boundWidth, boundHeight);

    }
}
