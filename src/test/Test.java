package test;
 
import vector3d.*;

public class Test {
    public static void main(String[] args) {
        Vector3d v1 = null;
        Vector3d v2 = null;
        Vector3d v3 = null;
        Color red = new Color(255, 0, 0);
        try {

            ShapeFactory sphereFactory = new SphereFactory();
            ShapeFactory pyramidFactory = new SquarePyramidFactory();
            ShapeFactory prismFactory = new RectangularPrismFactory();

            v1 = sphereFactory.createShape(5, 5, 5, red, 5.0);
            v2 = pyramidFactory.createShape(5, 5, 5, red, 5.0, 5.0);
            v3 = prismFactory.createShape(5, 5, 5, red, 5.0, 5.0, 5.0);

            Vector3d v1_copy = (Vector3d) v1.clone();

            v1_copy.getColor().setG(200);

            System.out.println("Original color: " + v1.getColor().getR() + " " + v1.getColor().getG() + " "
                    + v1.getColor().getB());
            System.out.println("Clone color: " + v1_copy.getColor().getR() + " " + v1_copy.getColor().getG() + " "
                    + v1_copy.getColor().getB() + "\n");

            System.out.println("Original address: " + System.identityHashCode(v1.getColor()));
            System.out.println("Clone address: " + System.identityHashCode(v1_copy.getColor()) + "\n");

            // System.out.println(
            // "Sphere: " + v1.toString() +
            // "\nArea: " + v1.calculateArea() +
            // "\nVolume: " + v1.calculateVolume() + "\n");
            // System.out.println(
            // "Square Pyramid: " + v2.toString() +
            // "\nArea: " + v2.calculateArea() +
            // "\nVolume: " + v2.calculateVolume() + "\n");

            // System.out.println(
            // "Rectangular Prism: " + v3.toString() +
            // "\nArea: " + v3.calculateArea() +
            // "\nVolume: " + v3.calculateVolume() + "\n");

            Vector3d badSphere = sphereFactory.createShape(5, 5, 5, red, -10.0);

        } catch (InvalidDimensionException e) {
            System.err.println("Error: " + e.getMessage() + " Wrong number: " + e.getBadValue());
            // Dont do anythng
        } catch (Exception e) {
            System.err.println("Something went wrong" + e);
        } finally {
            System.out.println("The process of creating figures is finished");
        }

    }
}
