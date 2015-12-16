/**
 * Created by Rodger on 11/3/2015.
 */
import Environment.TextureLoader;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;

import java.nio.file.Paths;


public class Ground {
    public static final String PATH_TO_TEXTURE = Paths.get(".\\Textures").toAbsolutePath().normalize().toString() + "\\ground.png";
    protected TextureLoader loader = null;
    private int[] textures = new int[1];

    //Gives a texture loader and a name for the ground
    public Ground(TextureLoader texture_loader)
    {
        this.loader = texture_loader;
        loadTexture();
    }

    protected void loadTexture()
    {
        textures[0] = loader.generateTexture();
        try{
            loader.loadTexture(textures[0], PATH_TO_TEXTURE, true);
        }catch(Exception e)
        {
            System.err.println("Unable to load texture: " + e.getMessage());
        }
    }

    public void draw(GL2 gl, float size, float x, float y)
    {

        float d = size;

        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);
        gl.glBegin(GL2GL3.GL_QUADS);

        gl.glTexCoord2f(x, 4 + y);
        gl.glVertex3f(d, -d, 0f);

        gl.glTexCoord2f(x, y);
        gl.glVertex3f(-d, -d, 0f);

        gl.glTexCoord2f(4 + x, y);
        gl.glVertex3f(-d, d, 0f);

        gl.glTexCoord2f(4 + x, 4 + y);
        gl.glVertex3f(d, d, 0f);

        gl.glEnd();

    }


}
