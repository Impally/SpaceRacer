/**
 * Created by rodge on 12/16/2015.
 */

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.nio.file.Paths;

import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import javax.swing.JOptionPane;

public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{

    //Window Variables
    private int windowWidth, windowHeight;
    int[] vPort = new int[4];
    //Class to load texture and texture for Skybox
    private TextureLoader texture_loader = null;
    private Skybox current_skybox = null;
    private final float skybox_size = 500.0f;
    // Camera variables
    private float pos_x = -20;
    private float pos_y = -20;
    private float pos_z = 10;
    private float look_x = 0;
    private float look_y = 0;
    private float look_z = 0;
    private float offset = 8.0f;
    private int mode = 0;
    //camera speed
    final float rot_speed = 128.0f;
    final float mv_speed = 0.4f;
    //mouse variables
    private int mouse_x0 = 0;
    private int mouse_y0 = 0;
    private boolean[] keys = new boolean[256];
    private GLU glu = new GLU();

    public void displayChanged( GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) { }

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
        current_skybox = new Skybox( texture_loader, "");
        gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl.glPushMatrix();
        gl.glLoadIdentity();


        // Update the camera state.
        if ( keys[KeyEvent.VK_W] || keys[KeyEvent.VK_S] ) {
            float normxy = (float) Math.sqrt( look_x * look_x + look_y * look_y );
            float multiplier = keys[KeyEvent.VK_W] ? 1.0f : -1.0f;
            pos_x += look_x / normxy * mv_speed * multiplier;
            pos_y += look_y / normxy * mv_speed * multiplier;
        }

        if ( keys[KeyEvent.VK_R] ) {
            pos_z += mv_speed;
            if (pos_z > 50) pos_z = 50;
        } else if ( keys[KeyEvent.VK_F] ) {
            pos_z -= mv_speed;
            if (pos_z < 0.1) pos_z = 0.1f;
        }

        if ( keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D] ) {
            float theta = (float) Math.atan2( look_y, look_x );
            float phi = (float) Math.acos( look_z );

            if ( keys[KeyEvent.VK_A] )
                theta += Math.PI / 2.0;
            else if ( keys[KeyEvent.VK_D] )
                theta -= Math.PI / 2.0;

            float strafe_x = (float)( Math.cos( theta ) * Math.sin( phi ) );
            float strafe_y = (float)( Math.sin( theta ) * Math.sin( phi ) );
            float normxy = (float) Math.sqrt( strafe_x * strafe_x + strafe_y * strafe_y );

            pos_x += strafe_x / normxy * mv_speed;
            pos_y += strafe_y / normxy * mv_speed;
        }

        glu.gluLookAt( pos_x, pos_y, pos_z,
                pos_x + look_x, pos_y + look_y, pos_z + look_z,
                0.0f, 0.0f, 1.0f );
        gl.glTranslatef(pos_x, pos_y, 0);
        current_skybox.draw(gl, skybox_size);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        windowWidth = width;
        windowHeight = height > 0 ? height : 1;
        vPort[0] = 0;
        vPort[1] = 0;
        vPort[2] = windowWidth;
        vPort[3] = windowHeight;
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GLMatrixFunc.GL_PROJECTION );
        gl.glLoadIdentity();
        glu.gluPerspective( 60.0f, (float) windowWidth / windowHeight, 0.1f, skybox_size * (float) Math.sqrt( 3.0 ) / 2.0f );
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.VK_ESCAPE == e.getKeyChar()) {System.exit(0);}
        //Key was pressed, set to true
        keys[ e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //key was released, set to false
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouse_x0 = e.getX();
        mouse_y0 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        float dX = x - mouse_x0;
        float dY = y - mouse_y0;
        float phi = (float) Math.acos( look_z );
        float theta = (float) Math.atan2( look_y, look_x );

        theta -= dX / rot_speed;
        phi += dY / rot_speed;

        if ( theta >= Math.PI * 2.0 )
            theta -= Math.PI * 2.0;
        else if ( theta < 0 )
            theta += Math.PI * 2.0;

        if ( phi > Math.PI - 0.1 )
            phi = (float)( Math.PI - 0.1 );
        else if ( phi < 0.1f )
            phi = 0.1f;

        look_x = (float)( Math.cos( theta ) * Math.sin( phi ) );
        look_y = (float)( Math.sin( theta ) * Math.sin( phi ) );
        look_z = (float)( Math.cos( phi ) );

        mouse_x0 = x;
        mouse_y0 = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
