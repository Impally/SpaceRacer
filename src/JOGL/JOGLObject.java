package JOGL;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import objects.DrawPair;

import java.util.ArrayList;

/**
 * Created by rodge on 12/16/2015.
 */
public class JOGLObject {
    //public Position3f state;
    //public Position3f scale;
    protected ArrayList<DrawPair> drawPairs;

    public JOGLObject() {
//        this.state = new Position3f(0, 0, 0);
    }

    public JOGLObject(float x, float y, float z) {
  //      this.state = new Position3f(x, y, z);
    }

    //public JOGLObject(Position3f position) {
    //    this.state = position;
    //}

    protected void init() {
        drawPairs = new ArrayList<DrawPair>();
    //    scale = new Position3f(1, 1, 1);
    }

    public void update() {
        return;
    }

    public void draw(final GL2 gl) {
        if (drawPairs != null) {
            gl.glPushMatrix();
            gl.glTranslatef(50f, 0f, 50f);

            //gl.glRotatef(-NumbersUtil.toDeg(state.xViewAngle), 0, 1, 0);
            gl.glScalef(.1f, .1f, .1f);
            for(DrawPair pair : drawPairs)
            {
                Texture texture = pair.texture;
                if (texture != null) {
                    texture.enable(gl);
                    texture.bind(gl);
                }
                gl.glCallList(pair.drawId);
                if (texture != null) {
                    texture.disable(gl);
                }
            }
            gl.glPopMatrix();
        }
    }
}