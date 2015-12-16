/**
 * Created by Rodger on 11/3/2015.
 */
import Utils.TextureLoader;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;

import java.nio.file.Paths;


public class Compass {
    public static final String PATH_TO_TEXTURE = Paths.get(".\\Textures").toAbsolutePath().normalize().toString() + "\\Compass.png";
    protected TextureLoader loader = null;
    private int[] textures = new int[1];

    //Gives a texture loader and a name for the ground
    public Compass(TextureLoader texture_loader)
    {
        this.loader = texture_loader;
        loadTexture();
    }

    protected void loadTexture()
    {
        textures[0] = loader.generateTexture();
        try{
            loader.loadTexture(textures[0], PATH_TO_TEXTURE, false);
        }catch(Exception e)
        {
            System.err.println("Unable to load texture: " + e.getMessage());
        }
    }

    public void draw(GL2 gl, int width, int height)
    {
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
        gl.glBegin(GL2GL3.GL_QUADS);
        gl.glTexCoord2f(0,1);
        gl.glVertex2f(width-100,height-28);

        gl.glTexCoord2f(1,1);
        gl.glVertex2f(width-28,height-28);

        gl.glTexCoord2f(1,0);
        gl.glVertex2f(width-28, height-100);

        gl.glTexCoord2f(0,0);
        gl.glVertex2f(width-100, height-100);

        gl.glEnd();
        gl.glDisable(GL.GL_BLEND);
    }


}
