package JOGL;

import Environment.Skybox;
import Utils.TextureLoader;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import input.Keyboard.Keyboard;

/**
 * Created by rodge on 12/16/2015.
 */
class JOGLFrame implements GLEventListener {

    private static GLU glu = new GLU();
    //Window Variables
    private int windowWidth, windowHeight;
    int[] vPort = new int[4];
    //Class to load texture and texture for Environment.Skybox
    private TextureLoader texture_loader = null;
    private Skybox current_skybox = null;
    private final float skybox_size = 500.0f;
    private final String skybox_name = "";
    Keyboard keyboard = null;

    // Camera variables
    public static float pos_x = -20;
    public static float pos_y = -20;
    public static float pos_z = 10;
    public static float look_x = 0;
    public static float look_y = 0;
    public static float look_z = 0;
    public static float offset = 8.0f;
    private int mode = 0;

    //camera speed
    public final static float rot_speed = 128.0f;
    public final static float mv_speed = 0.4f;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
        gl.glColor3f( 1.0f, 1.0f, 1.0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glDepthFunc( GL.GL_LEQUAL );
        gl.glEnable( GL.GL_TEXTURE_2D );
        // Initialize the texture loader and skybox.
        texture_loader = new TextureLoader( gl );
        current_skybox = new Skybox( texture_loader, skybox_name);
        gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl.glLoadIdentity();
        keyboard = new Keyboard();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {

    }
}
