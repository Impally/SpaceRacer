package Environment;

import JOGL.JoglEventListener;
import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by rodge on 12/16/2015.
 */
public class Player {
    public static GLModel Spaceship;
    public static Texture temp;
    public static float pos_x, pos_y, pos_z;

    public static Boolean loadModels(GL2 gl) {
        temp = TextureLoader.loadTexture(new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Maps\\door_mtl1_diffcol.jpg"));
        Spaceship = ModelLoaderOBJ.LoadModel(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Wraith_Raider_Starship.obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Wraith_Raider_Starship.mtl", gl);
        pos_x = JoglEventListener.pos_x;
        pos_y = JoglEventListener.pos_y;
        pos_z = JoglEventListener.pos_z;
        if (Spaceship == null) {
            return false;
        }
        return true;
    }

    public static void drawPlayer(GL2 gl, float x, float y, float z){
        gl.glPushMatrix();
        gl.glScaled(0.01f,0.01f,0.01f);
        gl.glRotatef(90f, 1f, 0f, 0f);

        gl.glTranslatef(pos_x - x,pos_y -y, pos_z - z);
        temp.enable(gl);
        temp.bind(gl);
        Spaceship.opengldraw(gl);
        temp.disable(gl);
        gl.glPopMatrix();
    }
}
