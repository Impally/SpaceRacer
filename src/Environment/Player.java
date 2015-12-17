package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import com.jogamp.opengl.GL2;

import java.nio.file.Paths;

/**
 * Created by rodge on 12/16/2015.
 */
public class Player {
    public static GLModel Spaceship = null;
    public static Boolean loadModels(GL2 gl) {
        Spaceship = ModelLoaderOBJ.LoadModel(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Wraith_Raider_Starship.obj",
                Paths.get("\\Models").toAbsolutePath().normalize().toString() + "\\Wraith_Raider_Starship.mtl", gl);
        if (Spaceship == null) {
            return false;
        }
        return true;
    }

    public static void drawPlayer(GL2 gl){
        Spaceship.opengldraw(gl);
        gl.glFlush();
    }
}
