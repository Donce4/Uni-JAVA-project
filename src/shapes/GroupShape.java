package shapes;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

/**
 * Klasė, realizuojanti „Composite“ (Kompozito) projektavimo šabloną.
 * Ji leidžia apjungti kelias figūras (objektus, paveldimus iš {@link Shape}) į vieną grupę.
 * Ši grupė pati elgiasi kaip vientisa figūra: ją galima piešti, perkelti, keisti jos spalvą 
 * ar tikrinti ribas. Visi veiksmai, atlikti su grupe, yra automatiškai perduodami visoms 
 * jos vidinėms figūroms.
 */
public class GroupShape extends Shape{

    /** Sąrašas, kuriame saugomos visos šiai grupei priklausančios figūros. */
    protected ArrayList<Shape> shapes = new ArrayList<>();

    /**
     * Sukuria naują, tuščią figūrų grupę.
     * @param position Pradinis taškas (bazinis inkaras), pagal kurį orientuojama grupė.
     * @param color    Pradinė grupės spalva.
     */
    public GroupShape(Point position, Color color){
        super(position, color);
    }
    
    /**
     * Grąžina visų šios grupės vidinių figūrų sąrašą.
     * Naudojama figūrų išgrupavimui (ungroup) ir grąžinimui atgal į drobę.
     * @return Vidinio {@link ArrayList} sąrašo nuoroda.
     */
    public ArrayList<Shape> getShapes(){
        return this.shapes;
    }

    /**
     * Prideda naują figūrą į šią grupę.
     * @param s Figūra, kuri bus įtraukta į grupę.
     */
    public void addShape(Shape s){
        this.shapes.add(s);
    }

    /**
     * Patikrina, ar nurodyta figūra priklauso šiai grupei.
     * @param s Tikrinama figūra.
     * @return true, jei figūra yra grupėje, kitu atveju - false.
     */
    public boolean isInternal(Shape s){
        return shapes.contains(s);
    }   
    
    /**
     * Apskaičiuoja bendrą stačiakampį (bounding box), kuris apgaubia visas grupei 
     * priklausančias figūras. Jei grupė tuščia, grąžina minimalų stačiakampį.
     * @return {@link java.awt.Rectangle} objektas, atvaizduojantis visos grupės ribas.
     */
    @Override
    public java.awt.Rectangle getBounds(){
        // Jei grupė tuščia, grąžina nulinio dydžio stačiakampį
        if (shapes == null || shapes.isEmpty()) {
            return new java.awt.Rectangle(this.position.x, this.position.y, 0, 0);
        }

        // Nustatomos kraštutinės pradinės reikšmės, kurias pirma figūra iškart perrašys
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Ciklas per visas vidines figūras, ieškant absoliučių kraštinių ribų
        for (Shape shape : shapes){
            java.awt.Rectangle b = shape.getBounds();

            if (b.x < minX) minX = b.x;
            if (b.y < minY) minY = b.y;

            // Dešinysis kraštas yra X + Plotis. Apatinis kraštas yra Y + Aukštis.
            if (b.x + b.width > maxX) maxX = b.x + b.width;
            if (b.y + b.height > maxY) maxY = b.y + b.height;
        }

        // Sukuriamas vienas didelis stačiakampis, dengiantis visus kraštutinius taškus
        return new java.awt.Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    /**
     * Nustato naują spalvą visai grupei.
     * Veiksmas deleguojamas visoms vidinėms figūroms – jų spalva taip pat pakeičiama.
     * @param color Nauja spalva.
     */
    @Override
    public void setColor(Color color) {
        super.setColor(color);
        for (Shape s : shapes) {
            s.setColor(color);
        }
    }

    /**
     * Grupės dydžio keitimas tempiant pelę nėra palaikomas, todėl šis metodas 
     * paliktas tuščias, kad patenkintų abstrakčios klasės {@link Shape} reikalavimus.
     */
    @Override
    public void setSize(int width, int height){

    }

    /**
     * Perkelia visą grupę nurodytu atstumu X ir Y ašyse.
     * Veiksmas deleguojamas visoms vidinėms figūroms, išlaikant jų tarpusavio proporcijas ir atstumus.
     * @param dx Poslinkis X ašyje (pikseliais).
     * @param dy Poslinkis Y ašyje (pikseliais).
     */
    @Override
    public void moveBy(int dx, int dy) {
        super.moveBy(dx, dy); // Perkeliamas grupės nematomas bazinis taškas
        for (Shape shape : shapes) { // Kiekviena vidinė figūra perkeliama individualiai
            shape.moveBy(dx, dy);
        }
    }

    /**
     * Patikrina, ar nurodytas taškas patenka į grupės ribas.
     * Naudojamas bendras grupės stačiakampis (bounding box), todėl grupę galima 
     * pažymėti paspaudus bet kurioje jos ploto vietoje (net ir tuščioje erdvėje tarp figūrų).
     * @param p Tikrinamas taškas (pvz., pelės koordinatės).
     * @return true, jei taškas yra grupės ribose, kitu atveju - false.
     */
    @Override
    public boolean contains(Point p){
        // Paimamas bendras bounding box ir patikrinama, ar taškas yra jo viduje!
        return getBounds().contains(p);
    }

    /**
     * Nupiešia visą figūrų grupę drobėje.
     * Kviečiamas kiekvienos vidinės figūros individualus draw() metodas.
     * @param g Grafinis kontekstas (Graphics objektas).
     */
    @Override
    public void draw(Graphics g){
        for (Shape shape : shapes){
            shape.draw(g);
        }
    }
}