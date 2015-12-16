package Utils;

import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader{
    //returns a texture to be used
    public static Texture loadTexture(File file){
        Texture texture = null;
        try {
            texture = TextureIO.newTexture(file, true);
            System.out.println("Loaded " + texture + " from file: " + file);
        } catch (GLException | IOException e) {
            System.err.println("Failed opening texture file: " + file);
            e.printStackTrace();
        }
        return texture;}
}