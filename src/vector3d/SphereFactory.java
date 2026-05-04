package vector3d;

import java.io.Serializable;

public class SphereFactory extends ShapeFactory implements Serializable{
    @Override
    public Vector3d createShape(double x, double y, double z, Color color, double... dimensions) 
            throws InvalidDimensionException {
        // Dimensions[0] is the radius
        return new Sphere(x, y, z, color, dimensions[0]);
    }
}
