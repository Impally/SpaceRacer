package objects;

import com.jogamp.opengl.util.texture.Texture;

public class DrawPair {
    public Texture texture;
    public int drawId;

    public DrawPair() {

    }

    public DrawPair(Texture texture, int drawId) {
        this.texture = texture;
        this.drawId = drawId;
    }

}