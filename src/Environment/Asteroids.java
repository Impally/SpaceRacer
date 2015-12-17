package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by rodge on 12/16/2015.
 */
public class Asteroids {



    private static Texture temp = null;
    private static Asteroids[] asteroids;
    private static int maxAsteroids;
    private float posX, posY, posZ, vecX,vecY,vecZ,rotX,rotY,rotZ;
    private GLModel model;

    Asteroids(float posX, float posY,float posZ,float vecX,float vecY,float vecZ,float rotX,float rotY,float rotZ, GLModel model)
    {
        this.posX=posX; this.posY=posY; this.posZ=posZ;
        this.vecX=vecX; this.vecY=vecY; this.vecZ=vecZ;
        this.rotX=rotX; this.rotY=rotY; this.rotZ=rotZ;
        this.model=model;
    }

    public static void initAsteroids(GL2 gl, int numAsteroids){
        loadTextures();
        maxAsteroids = numAsteroids;
        asteroids = new Asteroids[maxAsteroids];
        int max = 15;
        int min = 1;
        Random rand1 = new Random();
        Random rand2 = new Random();

        for(int i = 0; i < maxAsteroids; i++){
            float randX = rand1.nextInt(2*max)-max;
            float randY = rand1.nextInt(2*max)-max;
            float randZ = rand1.nextInt(2*max)-max;
            int randModel = rand2.nextInt(5)+1;
            GLModel tempModel= randomModel(gl, randModel);
            asteroids[i] = new Asteroids(
                    randX,randY,randZ,
                    0,0,0,
                    0,0,0,
                    tempModel);
        }
        int j = 0;

    }


    public static void  loadTextures() {
        temp = TextureLoader.loadTexture(
                new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() +
                        "\\Maps\\PerforatedRock.jpg"));
    }

    public static GLModel randomModel(GL2 gl, int rand)
    {
        return ModelLoaderOBJ.LoadModel(
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Asteroid\\Asteroid_"+rand+".obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Asteroid\\Asteroid_"+rand+".mtl",gl);

    }


    public static void drawAsteroidField(GL2 gl){

        temp.enable(gl);
        temp.bind(gl);
        for(int i = 0; i < maxAsteroids; i++) {
            gl.glTranslatef(asteroids[i].posX,asteroids[i].posY,asteroids[i].posZ);
            asteroids[i].model.opengldraw(gl);
            gl.glTranslatef(-asteroids[i].posX,-asteroids[i].posY,-asteroids[i].posZ);
        }
        temp.disable(gl);
    }
}
