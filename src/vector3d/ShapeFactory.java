package vector3d;

public abstract class ShapeFactory{
    public abstract Vector3d createShape(double x, double y, double z, Color color, double... dimensions) throws InvalidDimensionException;
}

