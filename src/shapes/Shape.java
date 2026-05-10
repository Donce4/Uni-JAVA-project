package shapes;
 
import java.awt.Color; 
import java.awt.Graphics;  
import java.awt.Point; 
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Abstrakti bazinė klasė, atstovaujanti bet kokią nupiešiamą figūrą drobėje.
 */
public abstract class Shape implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** Bazinė figūros pozicija (x, y koordinatės), nuo kurios pradedamas piešimas. */
    protected Point position; 
    
    /** Pagrindinė figūros kontūro spalva. */
    protected Color color; 

    /**
     * Pagrindinis konstruktorius, priskiriantis pradinę poziciją ir spalvą.
     * @param position Pradinis figūros taškas (koordinatės).
     * @param color    Figūros spalva.
     */
    public Shape(Point position, Color color){ 
        this.position = position; 
        this.color = color; 
    }

    /**
     * Grąžina figūros bazinės pozicijos kopiją.
     * @return Point objektas su figūros x ir y koordinatėmis.
     */
    public Point getPoint(){
        return new Point(this.position);
    }
    
    /**
     * Grąžina dabartinę figūros spalvą.
     * @return Color objektas.
     */
    public Color getColor(){
        return this.color;
    }
    
    /**
     * Nustato naują figūros bazinę poziciją.
     * @param newPos Naujos x ir y koordinatės.
     */
    public void setPoint(Point newPos){
        this.position.x = newPos.x;
        this.position.y = newPos.y;
    }
    
    /**
     * Nustato naują figūros spalvą.
     * @param color Nauja figūros spalva.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Perkelia figūrą nurodytu atstumu nuo dabartinės jos pozicijos.
     * @param dx Poslinkis X ašyje (pikseliais). 
     * @param dy Poslinkis Y ašyje (pikseliais). 
     */
    public void moveBy(int dx, int dy){
        this.position.translate(dx, dy);
    }

    /**
     * Atsakingas už figūros atvaizdavimą.
     * @param g Grafinis kontekstas (Graphics objektas).
     */
    public abstract void draw(Graphics g); 

    /**
     * Patikrina, ar duotas taškas patenka į šios figūros užimamą plotą.
     * @param mousePoint Taškas, kurį norima patikrinti.
     * @return true, jei taškas priklauso figūrai, kitu atveju - false.
     */
    public abstract boolean contains(Point mousePoint); 

    /**
     * Grąžina mažiausią įmanomą stačiakampį (bounding box).
     * @return Rectangle objektas, atvaizduojantis figūros ribas.
     */
    public abstract Rectangle getBounds();

    /**
     * Dinamiškai keičia figūros dydį pagal tempimo atstumą.
     * @param width  Atstumas X ašyje nuo figūros pradinės pozicijos.
     * @param height Atstumas Y ašyje nuo figūros pradinės pozicijos.
     */
    public abstract void setSize(int width, int height);

}