package shapes;
 
import java.awt.Color; // For colors
import java.awt.Graphics;  //For the whole GUI
import java.awt.Point; //For coordinates
import java.awt.event.MouseEvent;
import java.awt.Rectangle;

public abstract class Shape{
    protected Point position; // Sukuriamos pirminės x,y koordinatės
    protected Color color; // Sukuriama pirminė spalva


    public Shape(Point position, Color color){ // Constructor to create the object itself

        this.position = position; // It creates the position
        this.color = color; // It creates the color
    }

    
    public Point getPoint(){
        return this.position;
    }
    public Color getColor(){
        return this.color;
    }
    
    public void setPoint(Point newPos){
        this.position.x = newPos.x;
        this.position.y = newPos.y;
    }

    public void moveBy(int dx, int dy){
        this.position.translate(dx, dy);
    }

    public abstract void draw(Graphics g); // This is abstract method that force every subclass to implement this method

    public abstract boolean contains(Point mousePoint); // This abstract method checks if somebody pressed on the figure

    public abstract Rectangle getBounds();

}