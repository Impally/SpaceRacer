package Environment;

import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by rodge on 12/16/2015.
 */
public class Planets {
    public static Texture sunTexture;
    public static GLUquadric Sun;
    public static int planet_scale_factor = 16;

    public static void loadSun(GLU glu) {
        sunTexture = TextureLoader.loadTexture(new File(Paths.get(".\\Textures").toAbsolutePath().normalize().toString() + "\\sunmap.jpg"));
        Sun = glu.gluNewQuadric();
    }

    public static void drawSun(GLU glu, GL2 gl){
        glu.gluQuadricTexture(Sun, true);
        int size = 6;
        glu.gluQuadricDrawStyle(Sun, GLU.GLU_FILL);
        glu.gluQuadricNormals(Sun, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(Sun, GLU.GLU_OUTSIDE);
        final float radius = (6.378f*planet_scale_factor)/size;
        final int slices = (int) (16*planet_scale_factor)/size;
        final int stacks = (int) (16*planet_scale_factor)/size;
        sunTexture.enable(gl);
        sunTexture.bind(gl);
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
        glu.gluSphere(Sun, radius, slices, stacks);
        glu.gluDeleteQuadric(Sun);
        sunTexture.disable(gl);
    }
}
