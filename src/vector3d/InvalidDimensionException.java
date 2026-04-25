package vector3d;

public class InvalidDimensionException extends Vector3dException {
    double badValue;

    public InvalidDimensionException(String message, double badValue) {
        super(message);
        this.badValue = badValue;
    }

    public double getBadValue() {
        return this.badValue;
    }
}
