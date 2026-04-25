package vector3d;

public class Color {
    int r, g, b;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return this.r;
    }

    public int getG() {
        return this.g;
    }

    public int getB() {
        return this.b;
    }

    public void setR(int r) {

        if (r >= 0 && r <= 255) {
            this.r = r;
        } else {
            System.err.println("Range of red color should be 0 - 255");
        }
    }

    public void setG(int g) {

        if (g >= 0 && g <= 255) {
            this.g = g;
        } else {
            System.err.println("Range of green color should be 0 - 255");
        }
    }

    public void setB(int b) {
        if (b >= 0 && b <= 255) {
            this.b = b;
        } else {
            System.err.println("Range of blue color should be 0 - 255");
        }
    }

}
