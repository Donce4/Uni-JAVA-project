package vector3d;

public class RectangularPrismFactory extends ShapeFactory {
    @Override
    public Vector3d createShape(double x, double y, double z, Color color, double... dimensions) 
            throws InvalidDimensionException {
        // Dimensions: length, width, height
        return new RectangularPrism(x, y, z, color, dimensions[0], dimensions[1], dimensions[2]);
    }
}