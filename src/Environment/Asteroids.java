package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import com.jogamp.opengl.GL2;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by rodge on 12/16/2015.
 */
public class Asteroids {

    public static File myFile;
    public static GLModel astOBJ = null;
    public static Boolean loadModels(GL2 gl) {

        myFile = new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Asteroid.obj");
        String objPath = myFile.toString();
        myFile = new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() + "\\Asteroid.mtl");
        String mtlPath = myFile.toString();


        System.out.println(objPath);
        System.out.println(mtlPath);

        astOBJ = ModelLoaderOBJ.LoadModel(objPath,mtlPath,gl);
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
