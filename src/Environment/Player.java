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
public class Player {
    public static GLModel Spaceship = null;
    public static Texture temp = null;

    public static Boolean loadModels(GL2 gl) {
        Spaceship = ModelLoaderOBJ.LoadModel(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Wraith_Raider_Starship.obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Wraith_Raider_Starship.mtl", gl);
        if (Spaceship == null) {
            return false;
        }
        return true;
    }

    public static void drawPlayer(GL2 gl){
        temp = TextureLoader.loadTexture(new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Maps\\door_mtl1_diffcol.jpg"));
        temp.enable(gl);
        temp.bind(gl);
        Spaceship.opengldraw(gl);
        temp.disable(gl);
    }
}
