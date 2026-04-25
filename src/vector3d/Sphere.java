package vector3d;

public class Sphere extends Vector3d {
    public double radius;

    public Sphere(double x, double y, double z, Color color, double radius) throws InvalidDimensionException {
        super(x, y, z, color);
        if (radius < 0) {
            throw new InvalidDimensionException("Radius cannot be negative!", radius);
        }
        this.radius = radius;
    }

    @Override
    public double calculateVolume() {

        return (4 * this.radius * this.radius * this.radius * Math.PI) / 3;
    }

    @Override
    public double calculateArea() {

        return 4 * Math.PI * this.radius * this.radius;
    }

    @Override
    public String toString() { // Method overriding
        return "Coordinates:" + super.toString() + ", Radius:" + this.radius;
    }

    @Override
    public void add(double value) {
        this.x += value * 2;
        this.y += value * 2;
        this.z += value * 2;
    }
}

// Calculatable interface
// Turis
// Išsives perimeter calculator arba advanced calculator (vienas turi, o kitas
// ir perimetrą)
// Vektor3d abstrakti klasė
// O išvestinės klasės žino kaip skaičiuoti plotus ir tūrius ir juos realizuos
// interface metodu