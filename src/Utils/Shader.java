package Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.glsl.ShaderCode;

/**
 * Created by rodge on 12/16/2015.
 */
public class Shader {
    String PATH_TO_SHADERS = Paths.get(".\\Shaders").toAbsolutePath().normalize().toString();
    int v, f;

    public void buildShaders(GL2 gl) throws IOException{
        int v = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        int f = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

        BufferedReader brv = new BufferedReader(new FileReader(PATH_TO_SHADERS + "\\Vertex_Shader.glsl"));
        String vsrc = "";
        String line;
        while ((line=brv.readLine()) != null) {
            vsrc += line + "\n";
        }
        //gl.glShaderSource(v, 1, vsrc, null);
        gl.glCompileShader(v);

        BufferedReader brf = new BufferedReader(new FileReader("fragmentshader.glsl"));
        String fsrc = "";
        while ((line=brf.readLine()) != null) {
            fsrc += line + "\n";
        }
      //  gl.glShaderSource(f, 1, fsrc, null);
        gl.glCompileShader(f);
    }


}
