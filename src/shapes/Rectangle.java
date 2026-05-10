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
    public void setSize(int width, int height){

        this.width = width;
        this.length = height;

    }

    @Override
    public void draw(Graphics g){

        int drawX = width < 0 ? position.x + width : position.x;
        int drawY = length < 0 ? position.y + length : position.y;
        
        g.setColor(this.color);
        g.drawRect(drawX, drawY, Math.abs(width), Math.abs(length));
    }

    @Override
    public boolean contains(Point mousePoint) {

        int drawX = width < 0 ? position.x + width : position.x;
        int drawY = length < 0 ? position.y + length : position.y;

        return (mousePoint.x >= drawX && mousePoint.x <= drawX + Math.abs(width) && 
                mousePoint.y >= drawY && mousePoint.y <= drawY + Math.abs(length));
    }

    @Override
    public java.awt.Rectangle getBounds(){
        int drawX = width < 0 ? position.x + width : position.x;
        int drawY = length < 0 ? position.y + length : position.y;

        // Since we add 5 pixels on x and y, the lengths increase by 10
        int boundWidth = Math.abs(this.width) + 10;
        int boundLength = Math.abs(this.length) + 10;

        return new java.awt.Rectangle(drawX - 5, drawY - 5, boundWidth, boundLength);
    }

    
}