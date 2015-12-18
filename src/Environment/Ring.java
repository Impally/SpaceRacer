package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import Utils.Rand;
import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by Marshall on 12/17/2015.
 */
public class Ring {

    private static Texture tex;
    private static Ring[] rings;

    private float posX,posY,posZ;
    private static int numRings;
    private GLModel model;
    private boolean draw;

    public Ring(float posX, float posY, float posZ, GLModel model, boolean draw){
        this.posX=posX;
        this.posY=posY;
        this.posZ=posZ;
        this.model=model;
        this.draw=draw;
    }


    public static void loadTexture(){
        tex = TextureLoader.loadTexture(
                new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() +
                        "\\Maps\\ring_1.jpg"));
    }

    public static void initRings(GL2 gl, int num){

        int randDist = 50;
        loadTexture();
        numRings=num;
        rings = new Ring[num];
        GLModel tempModel = ModelLoaderOBJ.LoadModel(
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Ring.obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Ring.mtl", gl);
        for(int i = 0; i< numRings; i++){
                rings[i]=new Ring(Rand.randInt(-randDist,randDist),
                        Rand.randInt(-randDist,randDist),
                        Rand.randInt(-randDist,randDist),tempModel,true);
        }
    }

    public static void drawRings(GL2 gl)
    {

        tex.enable(gl);
        tex.bind(gl);

        for(int i =0; i < numRings; i++) {
            if(rings[i].draw) {
                gl.glTranslatef(rings[i].posX, rings[i].posY, rings[i].posZ);
                rings[i].model.opengldraw(gl);
                gl.glTranslatef(-rings[i].posX, -rings[i].posY, -rings[i].posZ);
            }
        }


        tex.disable(gl);
    }

    public static boolean checkCollision(float camX, float camY, float camZ) {

        boolean collision = false;
        for(int i = 0; i < rings.length; i++) {
            if(rings[i].draw) {
                //check the X axis
                if (Math.abs(camX - rings[i].posX) < rings[i].model.getXWidth()) {
                    //check the Y axis
                    if (Math.abs(camY - rings[i].posY) < rings[i].model.getXWidth()) {
                        //check the Z axis
                        if (Math.abs(camZ - rings[i].posZ) < rings[i].model.getZDepth()) {
                            System.out.println("COLLISION DETECTED");
                            rings[i].draw = false;
                            collision = true;
                        }
                    }
                }
            }
        }
        return collision;

    }

    public static void drawRing(float x, float y, float z, GL2 gl, int num)
    {


        tex.enable(gl);
        tex.bind(gl);
        gl.glScalef(4f, 4f, 4f);
        gl.glTranslatef(rings[num].posX, -y, rings[num].posZ);
        rings[0].model.opengldraw(gl);
        gl.glTranslatef(-rings[num].posX, y, -rings[num].posX);
        tex.disable(gl);
        gl.glScalef(.25f, .25f,.25f);
    }

    public static int getNumRings(){
        return numRings;
    }

    public static Ring getRingByID(int i){
        return rings[i];
    }

    public static float getXCoord(int i ){
        return rings[i].posX;
    }
    public static float getYCoord(int i ){
        return rings[i].posY;
    }
    public static float getZCoord(int i ){
        return rings[i].posZ;
    }


}
