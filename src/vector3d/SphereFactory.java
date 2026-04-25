package vector3d;

public class SphereFactory extends ShapeFactory {
    @Override
    public Vector3d createShape(double x, double y, double z, Color color, double... dimensions) 
            throws InvalidDimensionException {
        // Dimensions[0] is the radius
        return new Sphere(x, y, z, color, dimensions[0]);
    }
}
