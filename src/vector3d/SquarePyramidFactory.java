package vector3d;

public class SquarePyramidFactory extends ShapeFactory {
    @Override
    public Vector3d createShape(double x, double y, double z, Color color, double... dimensions) 
            throws InvalidDimensionException {
        // Dimensions: height, baseEdge
        return new SquarePyramid(x, y, z, color, dimensions[0], dimensions[1]);
    }
}