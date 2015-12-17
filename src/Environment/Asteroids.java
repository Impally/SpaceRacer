package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import com.jogamp.opengl.GL2;

import java.nio.file.Paths;

/**
 * Created by rodge on 12/16/2015.
 */
public class Asteroids {
    public static GLModel astOBJ = null;
    public static Boolean loadModels(GL2 gl) {
        astOBJ = ModelLoaderOBJ.LoadModel(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Asteroid.obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Asteroid.mtl", gl);
        if (astOBJ == null) {
            return false;
        }
        return true;
    }

    public static void drawPlayer(GL2 gl){
        astOBJ.opengldraw(gl);
        gl.glFlush();
    }
}
