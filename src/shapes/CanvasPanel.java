package shapes;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Group;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

/**
 * Pagrindinė grafinės drobės klasė, atsakinga už figūrų piešimą, atvaizdavimą bei 
 * vartotojo sąveikos (pelės paspaudimų, tempimų ir klaviatūros mygtukų) apdorojimą.
 * Paveldi {@link JPanel} klasę.
 */
public class CanvasPanel extends JPanel{

    /** Sąrašas, kuriame saugomos visos drobėje nupieštos figūros. */
    protected ArrayList<Shape> shapes = new ArrayList<>();
    
    /** Paskutinė užfiksuota pelės pozicija (naudojama figūrų tempimui). */
    private Point lastMousePoint = null; 
    
    /** Šiuo metu aktyvi (kuriama arba manipuliuojama) figūra. */
    protected Shape currShape = null;
    
    /** Dabartinė spalva, kuri bus taikoma naujoms ar pasirinktoms figūroms. */
    protected Color currentColor = Color.BLACK;
    
    /** Sąrašas figūrų, kurios yra pažymėtos (pasirinktos) vartotojo. */
    protected ArrayList<Shape> selectedShapes = new ArrayList<>();

    /**
     * Galimi įrankių tipai, nurodantys, kokia operacija šiuo metu bus atliekama drobėje.
     */
    protected enum ToolType{
        /** Pasirinkimo ir tempimo įrankis */ 
        SELECT, 
        /** Stačiakampio piešimo įrankis */ 
        RECTANGLE, 
        /** Apskritimo piešimo įrankis */ 
        CIRCLE, 
        /** Trikampio piešimo įrankis */ 
        TRIANGLE;
    }
    
    /** Šiuo metu pasirinktas įrankis. Pagal nutylėjimą - SELECT (pasirinkimas). */
    ToolType currTool = ToolType.SELECT;

    /**
     * Sukuria naują drobės (CanvasPanel) instanciją.
     * Inicializuoja klaviatūros ir pelės įvykių klausytojus (listeners) 
     * figūrų piešimui, pasirinkimui, perkėlimui bei trynimui.
     */
    public CanvasPanel(){
        this.setFocusable(true);
        
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // Patikrina, ar paspausti Backspace arba Delete klavišai
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE || 
                    e.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                    deleteSelectedShapes();
                }
            }
        });
        MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    requestFocusInWindow();
                    if (currTool == ToolType.SELECT){
                        findSelectedShape(e.getPoint());
                            if (currShape != null){
                                if (!e.isControlDown()){
                                    selectedShapes.clear();
                                    selectedShapes.add(currShape);
                                }
                                else if(selectedShapes.contains(currShape)){
                                    selectedShapes.remove(currShape);
                                }
                                else{
                                    selectedShapes.add(currShape);
                                }
                                lastMousePoint = e.getPoint(); // Prisimena pradinį tašką
                            }
                            else{
                                selectedShapes.clear();
                            }
                    }
                    else if (currTool == ToolType.RECTANGLE || currTool == ToolType.CIRCLE || currTool == ToolType.TRIANGLE){
                            currShape = createShape(currTool, e.getPoint());
                            if (currShape != null) {
                                addShape(currShape);
                                lastMousePoint = e.getPoint();
                            }
                        }
                }
                @Override
                public void mouseDragged(MouseEvent e){
                    if (currTool == ToolType.SELECT){
                        if (currShape != null && lastMousePoint != null){
        
                            Point currentPoint = e.getPoint();
        
                            int dx = currentPoint.x - lastMousePoint.x;
                            int dy = currentPoint.y - lastMousePoint.y;
                            
                            for (Shape s : selectedShapes){
                                s.moveBy(dx, dy);
                            }
        
                            lastMousePoint = currentPoint; // Atnaujina tašką
                            repaint();
                        }
                    }
                    else if (currTool == ToolType.RECTANGLE || currTool == ToolType.CIRCLE || currTool == ToolType.TRIANGLE){
                        if (currShape != null){

                            int width = e.getX() - lastMousePoint.x;
                            int height = e.getY() - lastMousePoint.y;

                            currShape.setSize(width, height);

                            repaint();

                        }
                }
            }
        };
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
    }

    /**
     * Nustato naują aktyvų įrankį drobėje ir atšaukia dabartinį figūrų pasirinkimą.
     * @param newTool Naujas įrankio tipas (pvz., RECTANGLE, SELECT).
     */
    public void setTool(ToolType newTool) {
        this.currTool = newTool;
        this.selectedShapes.clear(); // Geroji praktika atšaukti žymėjimą keičiant įrankį
        repaint();
    }
    
    /**
     * Gamyklos (Factory) metodas, sukuriantis naują figūrą pagal nurodytą įrankio tipą.
     * @param type Figūros tipas iš {@link ToolType}.
     * @param p    Pradinis taškas (koordinatės), kuriame figūra sukuriama.
     * @return Sukurtas {@link Shape} objektas arba null, jei tipas neatpažintas.
     */
    private Shape createShape(ToolType type, Point p) {
        switch (type) {
            case RECTANGLE: return new Rectangle(p, this.currentColor, 0, 0);
            case CIRCLE:    return new Circle(p, this.currentColor, 0);
            case TRIANGLE:  return new Triangle(p, this.currentColor, 0, 0);
            default: return null;
        }
    }

    /**
     * Išgrupuoja pasirinktą figūrų grupę.
     * Jei pasirinktas tik vienas elementas ir jis yra {@link GroupShape} tipo, 
     * jo vidinės figūros perkeliamos atgal į bendrą sąrašą, o pati grupė pašalinama.
     */
    public void ungroupSelectedShapes(){
        if (selectedShapes.size() == 1 && selectedShapes.get(0) instanceof GroupShape){
            
            GroupShape group = (GroupShape) selectedShapes.get(0); // Gauna grupę
            ArrayList<Shape> children = group.getShapes(); // Išpakuoja į ArrayList
            shapes.addAll(children); // Prideda jas atgal į drobę
            shapes.remove(group); // Pašalina grupės objektą
            selectedShapes.clear(); // Išvalo pasirinkimus

        }
    }

    /**
     * Apjungia visas šiuo metu pasirinktas figūras į vieną bendrą grupę (Composite pattern).
     * Sukuriama nauja {@link GroupShape}, į kurią perkeliamos figūros iš drobės.
     */
    public void groupSelectedShapes(){
        if (selectedShapes.size() > 1){

            Point originalPos = selectedShapes.get(0).getPoint(); // Prisimenama originali pozicija
            Point groupPos = new Point(originalPos.x, originalPos.y);

            GroupShape group = new GroupShape(groupPos, Color.BLACK); // Sukuriama grupė

            for (Shape s : selectedShapes){ // Perkelia figūras
                group.addShape(s);
            }
            shapes.removeAll(selectedShapes);
            shapes.add(group); // Prideda grupę kaip vieną objektą
            selectedShapes.clear(); // Išvalo pasirinkimus
            selectedShapes.add(group); // Pažymi naujai sukurtą grupę
            currShape = group;
            repaint();
        }
    }
    
    /**
     * Prideda nurodytą figūrą į drobės figūrų sąrašą ir atnaujina vaizdą.
     * @param s Pridedama figūra (objektas paveldimas iš {@link Shape}).
     */
    public void addShape(Shape s){
        shapes.add(s);
        repaint();
    }
    
    /**
     * Ištrina visas šiuo metu pasirinktas figūras iš drobės sąrašo ir atnaujina vaizdą.
     */
    public void deleteSelectedShapes() {
        this.shapes.removeAll(selectedShapes);
        this.selectedShapes.clear();
        this.currShape = null;
        this.repaint();
    }

    /**
     * Visiškai išvalo drobę – pašalina visas nupieštas figūras ir pasirinkimus.
     */
    public void clearCanvas() {
        this.shapes.clear();
        this.selectedShapes.clear();
        this.currShape = null;
        this.repaint();
    }

    /**
     * Tikrina figūrų sąrašą (nuo viršaus į apačią) ieškant figūros,
     * į kurios ribas pataiko duotas taškas. Rastą figūrą priskiria aktyviai.
     * @param p Taškas (paprastai pelės paspaudimo koordinatės).
     */
    public void findSelectedShape(Point p){
        for (int i = shapes.size() - 1; i >= 0; --i){
            Shape s = shapes.get(i);
            if (s.contains(p)){
                currShape = s;
                repaint();
                return;
            }
        }
        currShape = null;
    }

    /**
     * Pakeičia dabartinę piešimo spalvą ir priskiria ją visoms šiuo metu pasirinktoms figūroms.
     * @param newColor Nauja pasirinkta spalva.
     */
    public void changeColor(Color newColor) {
        this.currentColor = newColor;
        for (Shape s : selectedShapes) {
            s.setColor(newColor);
        }
        this.repaint();
    }

    /**
     * Naudojant Java objektų serializaciją, išsaugo visas drobės figūras į failą.
     * @param filepath Failo, į kurį bus išsaugota, kelias (pvz. "C:/.../file.bin").
     */
    public void saveToFile(String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(this.shapes); 
            System.out.println("Shapes successfully saved to " + filepath);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Naudojant Java objektų deserializaciją, užkrauna figūrų sąrašą iš išsaugoto failo.
     * Užkrovus išvalo ankstesnį drobės būvį.
     * @param filepath Išsaugoto failo kelias.
     */
    @SuppressWarnings("unchecked") // Nurodo Java pasitikėti, kad bus nuskaitytas ArrayList<Shape>
    public void loadFromFile(String filepath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            // Nuskaito objektą ir konvertuoja atgal į ArrayList<Shape>
            this.shapes = (ArrayList<Shape>) ois.readObject(); 
            
            // Išvalo drobės būseną
            this.selectedShapes.clear(); 
            this.currShape = null;
            this.currTool = ToolType.SELECT;
            
            this.repaint(); // Perpiešia naujai užkrautas figūras
            System.out.println("Shapes successfully loaded from " + filepath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    /**
     * Swing metodas, atsakingas už drobės perpiešimą. 
     * Nupiešia visas figūras ir apibrėžia pasirinktas figūras raudonu ribojamuoju rėmeliu (bounding box).
     * @param g Grafinis kontekstas (Graphics objektas).
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g); // Nupiešia foną
        for (Shape shape : shapes)
        {
            shape.draw(g);
        }
        Color oldColor = g.getColor();
        g.setColor(Color.RED);

        for (Shape s : selectedShapes){
            java.awt.Rectangle b = s.getBounds();
            g.drawRect(b.x, b.y, b.width, b.height);
        }
        g.setColor(oldColor);
    }
}