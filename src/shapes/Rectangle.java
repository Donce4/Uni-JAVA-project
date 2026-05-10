package shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;

/**
 * Klasė, apibrėžianti stačiakampio figūrą.
 */
public class Rectangle extends Shape{

/** Stačiakampio aukštis (ilgis) Y ašyje. */
    private int length;
    
    /** Stačiakampio plotis X ašyje. */
    private int width;
    
    
    /**
     * Sukuria naują stačiakampio objektą.
     * @param position Pradinis taškas (stačiakampio piešimo pradžia).
     * @param color    Stačiakampio linijos spalva.
     * @param length   Pradinis aukštis (ilgis).
     * @param width    Pradinis plotis.
     */
    public Rectangle(Point position, Color color, int length, int width){
        super(position, color);
        this.length = length;
        this.width = width;
    }

    /**
     * Grąžina dabartinį stačiakampio aukštį (ilgį).
     * @return Stačiakampio aukštis.
     */
    public int getLength(){
        return this.length;
    }
    
    /**
     * Grąžina dabartinį stačiakampio plotį.
     * @return Stačiakampio plotis.
     */
    public int getWidth(){
        return this.width;
    }
    
    /**
     * Nustato naują stačiakampio plotį.
     * @param newWidth Naujas plotis.
     */
    public void setWidth(int newWidth){
        this.width = newWidth;
    }
    
    /**
     * Nustato naują stačiakampio aukštį (ilgį).
     * @param newLength Naujas aukštis.
     */
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

        int boundWidth = Math.abs(this.width) + 10;
        int boundLength = Math.abs(this.length) + 10;

        return new java.awt.Rectangle(drawX - 5, drawY - 5, boundWidth, boundLength);
    }
}