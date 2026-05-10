package shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.datatransfer.FlavorListener;
import java.awt.Graphics;

public class Rectangle extends Shape{

    private int length, width;
    public Rectangle(Point position, Color color, int length, int width){
        super(position, color);
        this.length = length;
        this.width = width;
    }

    
    public int getLength(){
        return this.length;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public void setWidth(int newWidth){
        this.width = newWidth;
    }
    
    public void setLength(int newLength){
        this.length = newLength;
    }

    @Override
    public void draw(Graphics g){

        int x = getPoint().x;
        int y = getPoint().y;
        g.setColor(this.color);
        g.drawRect(x, y, width, length);
    }

    @Override
    public boolean contains(Point mousePoint) {
        return (mousePoint.x >= position.x && mousePoint.x <= position.x + width && 
                mousePoint.y >= position.y && mousePoint.y <= position.y + length);
    }

    @Override
    public java.awt.Rectangle getBounds(){
        int x = getPoint().x - 5;
        int y = getPoint().y - 5;

        // Since we add 5 pixels on x and y, the lengths increase by 10
        int boundWidth = this.width + 10;
        int boundLength = this.length + 10;

        return new java.awt.Rectangle(x, y, boundWidth, boundLength);

    }

    
}