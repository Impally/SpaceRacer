package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import Utils.Rand;
import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import javafx.scene.Camera;

import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by rodge on 12/16/2015.
 */
public class Asteroids {


    private static Texture astTex;
    private static Asteroids[] asteroids;
    private static int maxAsteroids;
    private float posX, posY, posZ, vecX, vecY, vecZ, rotX, rotY, rotZ;
    private GLModel model;
    private Texture tex;

    Asteroids(float posX, float posY, float posZ, float vecX, float vecY, float vecZ, float rotX, float rotY, float rotZ, GLModel model, Texture tex) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.vecX = vecX;
        this.vecY = vecY;
        this.vecZ = vecZ;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.model = model;
        this.tex = tex;
    }

    public static void initAsteroids(GL2 gl, int numAsteroids) {
        loadTextures();
        maxAsteroids = numAsteroids;
        asteroids = new Asteroids[maxAsteroids];


        for (int i = 0; i < maxAsteroids; i++) {


            asteroids[i] = new Asteroids(
                    Rand.randInt(0, 15), Rand.randInt(0, 15), Rand.randInt(0, 15),
                    0, 0, 0,
                    Rand.randInt(0, 5), Rand.randInt(0, 5), Rand.randInt(0, 5),
                    randomModel(gl), loadTextures());
        }

    }


    public static Texture loadTextures() {

        return TextureLoader.loadTexture(
                new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() +
                        "\\Maps\\AsteroidTex_" + Rand.randInt(0, 2) + ".jpg"));

    }

    public static GLModel randomModel(GL2 gl) {
        return ModelLoaderOBJ.LoadModel(
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Asteroid\\Asteroid_" + Rand.randInt(1, 6) + ".obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Asteroid\\Asteroid_" + Rand.randInt(1, 6) + ".mtl", gl);

    }


    public static void drawAsteroidField(GL2 gl) {


        for (int i = 0; i < maxAsteroids; i++) {
            asteroids[i].tex.enable(gl);
            asteroids[i].tex.bind(gl);
            gl.glTranslatef(asteroids[i].posX, asteroids[i].posY, asteroids[i].posZ);

            asteroids[i].model.opengldraw(gl);

            gl.glTranslatef(-asteroids[i].posX, -asteroids[i].posY, -asteroids[i].posZ);
            asteroids[i].tex.disable(gl);
        }
    }

    public static boolean checkCollision(float camX, float camY, float camZ) {

        boolean collision = false;
        for(int i = 0; i < asteroids.length; i++) {
            //check the X axis
            if (Math.abs(camX - asteroids[i].posX) <  asteroids[i].model.getXWidth()) {
                //check the Y axis
                if (Math.abs(camY - asteroids[i].posY) <  asteroids[i].model.getYHeight()) {
                    //check the Z axis
                    if (Math.abs(camZ - asteroids[i].posZ) <  asteroids[i].model.getZDepth()) {
                        System.out.println("COLLISION DETECTED");
                        collision =  true;
                    }
                }
            }
        }
        return collision;

    }


}
