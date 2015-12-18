package Environment;

import Utils.TextureLoader;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.util.texture.Texture;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by rodge on 12/17/2015.
 */
public class Hud {
    public static final String PATH_TO_TEXTURE = Paths.get(".\\Textures").toAbsolutePath().normalize().toString() + "\\Hud.png";
    private static Texture hudTexture;

    public static void  init(){
        hudTexture = TextureLoader.loadTexture(new File(PATH_TO_TEXTURE));
    }

    public static void drawHud(GL2 gl, int width, int height){
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        hudTexture.enable(gl);
        hudTexture.bind(gl);
        gl.glBegin(GL2GL3.GL_QUADS);
        //Top Left
        gl.glTexCoord2f(0,1);
        gl.glVertex2f(0, height);
        //Top Right
        gl.glTexCoord2f(1,1);
        gl.glVertex2f(width,height);
        //Bottom Right
        gl.glTexCoord2f(1,0);
        gl.glVertex2f(width, 0);
        //Bottom Left
        gl.glTexCoord2f(0,0);
        gl.glVertex2f(0,0);
        gl.glEnd();
        hudTexture.disable(gl);
    }
}

