package input.state;

/**
 * Created by rodge on 12/16/2015.
 */
public class Vertex3f {
    public float x;
    public float y;
    public float z;

    public Vertex3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex3f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public float[] toArray() {
        float[] array = { x, y, z, 1 };
        return array;
    }

}