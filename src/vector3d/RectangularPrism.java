package vector3d;

public class RectangularPrism extends Vector3d {
    public double length, width, height;

    public RectangularPrism(double x, double y, double z, Color color, double length, double width, double height)
            throws InvalidDimensionException {
        super(x, y, z, color);
        if (length < 0) {
            throw new InvalidDimensionException("Length cannot be negative!", length);
        }
        if (width < 0) {
            throw new InvalidDimensionException("Width cannot be negative!", width);
        }
        if (height < 0) {
            throw new InvalidDimensionException("Heights cannot be negative!", height);
        }
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {

        return 2 * ((this.length * this.width) + (this.length * this.height) + (this.width * this.height));
    }

    @Override
    public double calculateVolume() {
        return (this.length * this.height * this.width);
    }

    @Override
    public String toString() {
        return "Coordinates:" + super.toString() + ", Height:" + this.height + ", Width:" + this.width + ", Length:"
                + this.length;
    }

    @Override
    public void add(double value) {
        this.x += value * 2;
        this.y += value * 2;
        this.z += value * 2;
    }

}