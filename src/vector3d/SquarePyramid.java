package vector3d;

public class SquarePyramid extends Vector3d {
    public double height, baseEdge;

    public SquarePyramid(double x, double y, double z, Color color, double height, double baseEdge)
            throws InvalidDimensionException {
        super(x, y, z, color);
        if (height < 0) {
            throw new InvalidDimensionException("Height cannot be negative!", height);
        }
        if (baseEdge < 0) {
            throw new InvalidDimensionException("Base edge cannot be negative", baseEdge);
        }
        this.height = height;
        this.baseEdge = baseEdge;
    }

    @Override
    public double calculateArea() {
        return (this.baseEdge * this.baseEdge) + (2 * this.height * this.baseEdge);
    }

    @Override
    public double calculateVolume() {
        return (this.baseEdge * this.baseEdge * this.height) / 3;
    }

    @Override
    public String toString() {
        return "Coordinates:" + super.toString() + ", Base Edge:" + this.baseEdge + ", Height:" + this.height;
    }

    @Override
    public void add(double value) {
        this.x += value * 2;
        this.y += value * 2;
        this.z += value * 2;
    }

}