package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Klasė, apibrėžianti apskritimo figūrą.
 */
public class Circle extends Shape{

/** Apskritimo spindulys pikseliais. */
    private int radius;
    
    /**
     * Sukuria naują apskritimo objektą.
     * @param position Pradinis taškas (apskritimo centras).
     * @param color    Apskritimo linijos spalva.
     * @param radius   Pradinis apskritimo spindulys.
     */
    public Circle(Point position, Color color, int radius){
        super(position, color);
        this.radius = radius;
    }

    /**
     * Grąžina dabartinį apskritimo spindulį.
     * @return Apskritimo spindulys pikseliais.
     */
    public int getRadius(){
        return this.radius;
    }

    /**
     * Nustato naują apskritimo spindulį.
     * @param newRadius Naujas spindulio dydis.
     */
    public void setRadius(int newRadius){
        this.radius = newRadius;
    }

    @Override
    public void setSize(int width, int height){
        this.radius = (int) Math.sqrt((width * width) + (height * height));
    }

    @Override
    public void draw(Graphics g){
        int x = getPoint().x;
        int y = getPoint().y;
        g.setColor(this.color);
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2); 
    }

    @Override
    public boolean contains(Point mousePoint){
        double distance = position.distance(mousePoint);
        return distance <= radius;
    }

    @Override
    public java.awt.Rectangle getBounds(){
        int x = getPoint().x - radius - 5;
        int y = getPoint().y - radius - 5;
        int side = (this.radius * 2) + 10;
        return new java.awt.Rectangle(x, y, side, side);
    }
}