package Environment;

import Utils.GLModel;
import Utils.ModelLoaderOBJ;
import Utils.Rand;
import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
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

    public Ring(float posX, float posY, float posZ, GLModel model){
        this.posX=posX;
        this.posY=posY;
        this.posZ=posZ;
        this.model=model;
    }


    public static void loadTexture(){
        tex = TextureLoader.loadTexture(
                new File(Paths.get(".\\Models").toAbsolutePath().normalize().toString() +
                        "\\Maps\\ring_1.jpg"));
    }

    public static void initRings(GL2 gl, int num){

        loadTexture();
        numRings=num;
        rings = new Ring[num];
        GLModel tempModel = ModelLoaderOBJ.LoadModel(
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Ring.obj",
                Paths.get(".\\Models").toAbsolutePath().normalize().toString()
                        + "\\Ring.mtl", gl);
        for(int i = 0; i< numRings; i++){
            if(i==1)
                rings[i]=new Ring(0,i,0,tempModel);
            else
                rings[i]=new Ring(Rand.randInt(0,5),i,Rand.randInt(0,5),tempModel);
        }
    }

    public static void drawRing(int i, GL2 gl)
    {
        tex.enable(gl);
        tex.bind(gl);

        gl.glScalef(4f, 4f, 4f);
        gl.glTranslatef(rings[i].posX, -i * 20, rings[i].posZ);
        rings[0].model.opengldraw(gl);
        gl.glTranslatef(-rings[i].posX, i * 20, -rings[i].posZ);
        gl.glScalef(.25f, .25f, .25f);
        tex.disable(gl);
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


}
