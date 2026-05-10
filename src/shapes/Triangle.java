package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Klasė, apibrėžianti lygiašonio trikampio figūrą.
 * Paveldi abstrakčią {@link Shape} klasę ir realizuoja trikampiui būdingą 
 * piešimo, dydžio keitimo, ribų skaičiavimo bei ploto skaičiavimo logiką.
 */
public class Triangle extends Shape {

    /** Trikampio pagrindo plotis (X ašyje) ir aukštis (Y ašyje). */
    int base, height;
    
    /**
     * Sukuria naują trikampio objektą.
     * @param position Pradinis taškas (trikampio kairysis apatinis kampas, jei matmenys teigiami).
     * @param color    Trikampio linijų spalva.
     * @param base     Pradinis trikampio pagrindo ilgis.
     * @param height   Pradinis trikampio aukštis.
     */
    public Triangle(Point position, Color color, int base, int height){
        super(position, color);
        this.base = base;
        this.height = height;
    }

    /**
     * Grąžina trikampio pagrindo ilgį.
     * @return Pagrindo ilgis pikseliais.
     */
    public int getBase(){
        return this.base;
    }

    /**
     * Grąžina trikampio aukštį.
     * @return Aukštis pikseliais.
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Nustato naują trikampio pagrindo ilgį.
     * @param newBase Naujas pagrindo ilgis.
     */
    public void setBase(int newBase){
        this.base = newBase;
    }

    /**
     * Nustato naują trikampio aukštį.
     * @param newHeight Naujas aukštis.
     */
    public void setHeight(int newHeight){
        this.height = newHeight;
    }
    
    /**
     * Pagalbinis matematinis metodas, apskaičiuojantis bet kokio trikampio, 
     * sudaryto iš trijų taškų (A, B, C), plotą.
     * Naudojamas taško priklausomumo (contains) algoritmui.
     * @param a Pirmas taškas.
     * @param b Antras taškas.
     * @param c Trečias taškas.
     * @return Trikampio plotas (visada teigiamas dėl Math.abs).
     */
    private double calculateArea(Point a, Point b, Point c){
        return Math.abs((a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2.0);
    }
    
    /**
     * Pagalbinis metodas, apskaičiuojantis pagrindinio šios figūros trikampio plotą 
     * pagal jo pagrindą ir aukštį (S = (a * h) / 2).
     * @return Pagrindinio trikampio plotas.
     */
    private double getTriangleArea(){
        return Math.abs((base * height) / 2.0);
    }
    
    /**
     * Dinamiškai nustato trikampio dydį pagal pelės tempimo atstumą.
     * @param width  Atstumas X ašyje nuo pradinio paspaudimo taško (tampa pagrindu).
     * @param height Atstumas Y ašyje nuo pradinio paspaudimo taško (tampa aukščiu).
     */
    @Override
    public void setSize(int width, int height){
        this.base = width;
        this.height = height;
    }

    /**
     * Nupiešia trikampį drobėje naudodamas poligoną (daugiakampį).
     * Apskaičiuoja tris trikampio viršūnes: apatinę kairę, apatinę dešinę ir viršūnę centre.
     * @param g Grafinis kontekstas (Graphics objektas).
     */
    @Override
    public void draw(Graphics g){

        int x = getPoint().x;
        int y = getPoint().y;

        // Trijų trikampio viršūnių X koordinatės
        int[] xPoints = {x, x + base, x + (base / 2)};
        // Trijų trikampio viršūnių Y koordinatės
        int[] yPoints = {y, y, y - height};

        g.setColor(this.color);
        g.drawPolygon(xPoints, yPoints, 3);
    }
    
    /**
     * Patikrina, ar nurodytas taškas (pvz., pelės koordinatės) yra trikampio viduje.
     * Naudojamas geometrinis ploto metodas: jei taškas yra trikampio viduje, tai 
     * trijų mažesnių trikampių (sudarytų iš tikrinamo taško ir trikampio kraštinių) 
     * plotų suma turi būti lygi pagrindinio trikampio plotui.
     * @param mousePoint Taškas, kurį norima patikrinti.
     * @return true, jei taškas yra trikampio viduje, kitu atveju - false.
     */
    @Override
    public boolean contains(Point mousePoint){

        // Trikampio viršūnės
        Point a = this.position;
        Point b = new Point(this.position.x + base, this.position.y);
        Point c = new Point(this.position.x + (base / 2), this.position.y - height);

        // Trijų sub-trikampių plotai
        double area1 = calculateArea(mousePoint, a, b);
        double area2 = calculateArea(mousePoint, b, c);
        double area3 = calculateArea(mousePoint, c, a);

        // Dėl paklaidų dirbant su double slankaus kablelio skaičiais, 
        // lyginama su maža paklaida (0.1), o ne tiksliai su 0.
        return Math.abs(getTriangleArea() - (area1 + area2 + area3)) < 0.1;
    }

    /**
     * Apskaičiuoja ir grąžina minimalų stačiakampį (bounding box), kuris visiškai 
     * apgaubia šį trikampį. Pridedama po 5 pikselius iš visų pusių atrankos rėmeliui.
     * Dinamiškai prisitaiko prie neigiamų pagrindo ir aukščio reikšmių.
     * @return {@link java.awt.Rectangle} objektas, atvaizduojantis figūros ribas.
     */
    @Override
    public java.awt.Rectangle getBounds(){
        // Suranda tikrąsias mažiausias X ir Y koordinates
        int minX = Math.min(this.position.x, this.position.x + base);
        int minY = Math.min(this.position.y, this.position.y - height);

        int boundHeight = Math.abs(this.height) + 10;
        int boundWidth = Math.abs(this.base) + 10;

        return new java.awt.Rectangle(minX - 5, minY - 5, boundWidth, boundHeight);
    }
}