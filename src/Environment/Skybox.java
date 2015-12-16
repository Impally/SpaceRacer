package Environment;

import Utils.TextureLoader;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.util.texture.Texture;

import java.io.File;
import java.nio.file.Paths;
import java.util.Hashtable;

public class Skybox {
    public static final int NUM_FACES = 6;
    public static final String PATH_TO_TEXTURES = Paths.get(".\\Textures").toAbsolutePath().normalize().toString();
    public static final String[] SKYBOX_SUFFIXES = {
            "zpos.png", "zneg.png",
            "xpos.png", "xneg.png",
            "ypos.png", "yneg.png"
    };
    protected Texture[] textures = new Texture[NUM_FACES];
    private String skybox_name = null;

    public Skybox(TextureLoader texture_loader, String skybox_name ) {
        this.skybox_name = skybox_name;
        loadTextures();
    }

    protected void loadTextures() {
        String skybox_name = getSkyboxName();

        for ( int i = 0; i < NUM_FACES; ++i ) {
            textures[i] = TextureLoader.loadTexture(new File(PATH_TO_TEXTURES + "\\" + skybox_name + SKYBOX_SUFFIXES[ i ]));
        }
    }

    public String getSkyboxName() {
        return skybox_name;
    }

    public void draw( GL2 gl, float size ) {
        final float d = size/2;
        float[][] texCoords ={{0.0f, 1.0f}, {0.0f,0.0f}, {1.0f, 0.0f}, {1.0f, 1.0f}};
        int[] keyValue = {  1, 0, 1,    // Front
                            1, 0, 0,    // Front
                            1, 1, 0,    // Front
                            1, 1, 1,    // Front
                            0, 1, 1,    // Back
                            0, 1, 0,    // Back
                            0, 0, 0,    // Back
                            0, 0, 1,    // Back
                            1, 1, 1,    // Left
                            1, 1, 0,    // Left
                            0, 1, 0,    // Left
                            0, 1, 1,    // Left
                            0, 0, 1,    // Right
                            0, 0, 0,    // Right
                            1, 0, 0,    // Right
                            1, 0, 1,    // Right
                            0, 0, 1,    // Up
                            1, 0, 1,    // Up
                            1, 1, 1,    // Up
                            0, 1, 1,    // Up
                            1, 0, 0,    // Down
                            0, 0, 0,    // Down
                            0, 1, 0,    // Down
                            1, 1, 0};   // Down


        drawFace(gl,texCoords, keyValue, d);
    }

    public void drawFace(GL2 gl, float[][] texCoords, int[] keys, float d){
        float[] depth = {-d, d};
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
    for(int j =0; j < 6; j++) {
        textures[j].enable(gl);
        textures[j].bind(gl);
        gl.glBegin(GL2GL3.GL_QUADS);
        for (int i = 0; i < 10; i += 3) {
            gl.glTexCoord2f(texCoords[(int) Math.floor(i / 3)][0], texCoords[(int) Math.floor(i / 3)][1]);
            gl.glVertex3f(depth[keys[j*12+i]], depth[keys[j*12+i + 1]], depth[keys[j*12+i + 2]]);
        }
        gl.glEnd();
    }
    }
}
