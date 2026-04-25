package vector3d;

public abstract class Vector3d implements AdvancedCalculator, Cloneable {
    protected double x, y, z;
    protected static int counter = 0;
    protected Color color;

    public Vector3d(double x, double y, double z, Color color) { // Constructor
        // Coordinates
        this.x = x;
        this.y = y;
        this.z = z;

        // RGB colors
        this.color = color;

        ++counter;
    }

    public Vector3d() {
        // Change default color to black
        this(0.0, 0.0, 0.0, new Color(0, 0, 0));
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Color getColor() {
        return this.color;
    }

    public static int getCounter() {
        return counter;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // Cloning method
    public Object clone() throws CloneNotSupportedException {
        Vector3d clonedVector = (Vector3d) super.clone(); // Manual casting
        clonedVector.color = new Color(color.getR(), color.getG(), color.getB());
        return clonedVector;
    }

    public void add(double value) {
        this.x += value;
        this.y += value;
        this.z += value;
    }

    public void add(Vector3d v) {
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }

    public static final void printCounter() {
        System.out.println("Total Vectors Created: " + counter);
    }
}
