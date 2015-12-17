package Environment;

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
public class Asteroids {


    public static GLModel astOBJ = null;
    public static Texture temp = null;


    public static Boolean loadModels(GL2 gl) {
        temp = TextureLoader.loadTexture(
                new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() +
                        "\\Maps\\SmoothRock.jpg"));

        astOBJ = ModelLoaderOBJ.LoadModel(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Asteroid.obj",Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Asteroid.mtl",gl);
        if (astOBJ == null) {
            return false;
        }
        return true;
    }

    public static void drawAsteroid(GL2 gl, float r, int longs, int lats){

        temp.enable(gl);
        temp.bind(gl);
        astOBJ.opengldraw(gl);
        temp.disable(gl);
    }
}
