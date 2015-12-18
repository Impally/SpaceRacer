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

    static GLUquadric Sun;
    static Texture sunTexture;
    static float planet_scale_factor = 10;
    static float pX,pY,pZ,radius;
    public static void loadSun(GLU glu){
        sunTexture = TextureLoader.loadTexture(new File(Paths.get(".\\Textures").toAbsolutePath().normalize().toString() + "\\sunmap.jpg"));
        Sun = glu.gluNewQuadric();
    }

    public static void drawSun(GLU glu, GL2 gl,float x, float y, float z){
        pX=x;
        pY=y;
        pZ=z;
        gl.glTranslatef(x,y,z);
        glu.gluQuadricTexture(Sun, true);

        int size = 6;
        glu.gluQuadricDrawStyle(Sun, GLU.GLU_FILL);
        glu.gluQuadricNormals(Sun, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(Sun, GLU.GLU_OUTSIDE);
        sunTexture.enable(gl);
        sunTexture.bind(gl);
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
        radius = (6.378f*planet_scale_factor)/size;
        final int slices = (int) (16*planet_scale_factor)/size;
        final int stacks = (int) (16*planet_scale_factor)/size;
        glu.gluSphere(Sun, radius, slices, stacks);
        glu.gluDeleteQuadric(Sun);
        sunTexture.disable(gl);
        gl.glTranslatef(-x,-y,-z);
    }

    public static boolean checkCollision(float camX, float camY, float camZ) {

        boolean collision = false;
        if(true) {
            //check the X axis
            if (Math.abs(camX - pX) < radius) {
                //check the Y axis
                if (Math.abs(camY - pY) < radius) {
                    //check the Z axis
                    if (Math.abs(camZ -  pZ) < radius) {
                        System.out.println("COLLISION DETECTED");
                        collision = true;
                    }
                 }
            }
        }

        return collision;

    }
}
