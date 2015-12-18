package JOGL;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;

import Environment.*;
import Utils.TextureLoader;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import input.Keyboard.Keyboard;

public class JoglEventListener implements GLEventListener{
    //Window Variables
    private int windowWidth, windowHeight;
    int[] vPort = new int[4];
    //Class to load texture and texture for Environment.Skybox
    private Skybox current_skybox = null;
    private final float skybox_size = 1000.0f;
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
    public static int mode = 0;
    //camera speed
    public final static float rot_speed = 128.0f;
    public final static float mv_speed = 0.4f;

    private long start_time = System.currentTimeMillis();

    private GLU glu = new GLU();
    Texture temp = null;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glDepthFunc( GL.GL_LEQUAL );
        gl.glEnable( GL.GL_TEXTURE_2D );
        // Initialize the texture loader and skybox.
        current_skybox = new Skybox(skybox_name);
        gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl.glLoadIdentity();
        loadObjects(gl);
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
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl.glPushMatrix();
        gl.glLoadIdentity();
        long current_time = System.currentTimeMillis()-start_time;
        int tenth = (int) (current_time%1000)/100;
        int second = (int) current_time/1000;


        // Update the camera state.
        if ( keyboard.keys[KeyEvent.VK_W] || keyboard.keys[KeyEvent.VK_S] ) {
            float normxy = (float) Math.sqrt( look_x * look_x + look_y * look_y );
            float multiplier = keyboard.keys[KeyEvent.VK_W] ? 1.0f : -1.0f;
            pos_x += look_x / normxy * mv_speed * multiplier;
            pos_y += look_y / normxy * mv_speed * multiplier;
        }

        if ( keyboard.keys[KeyEvent.VK_R] ) {
            pos_z += mv_speed;
            if (pos_z > 50) pos_z = 50;
        } else if ( keyboard.keys[KeyEvent.VK_F] ) {
            pos_z -= mv_speed;
            if (pos_z < 0.1) pos_z = 0.1f;
        }

        if ( keyboard.keys[KeyEvent.VK_A] || keyboard.keys[KeyEvent.VK_D] ) {
            float theta = (float) Math.atan2( look_y, look_x );
            float phi = (float) Math.acos( look_z );

            if ( keyboard.keys[KeyEvent.VK_A] )
                theta += Math.PI / 2.0;
            else if ( keyboard.keys[KeyEvent.VK_D] )
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
        gl.glTranslatef(0,0,-1);
        gl.glScaled(0.01f,0.01f,0.01f);
        gl.glRotatef(90f, 1f, 0f, 0f);
        Player.drawPlayer(gl);
        gl.glRotatef(-90f, 1f, 0f, 0f);
        gl.glScaled(100f,100f,100f);
        Asteroids.drawAsteroidField(gl);
        gl.glTranslatef(100, 50, 0);
        Planets.drawSun(glu, gl);
        Track.drawTrack(gl);
        gl.glTranslatef(-100,-50,0);
        gl.glTranslatef(pos_x, pos_y, 1);
        current_skybox.draw(gl, skybox_size);
        gl.glPopMatrix();
        renderText(gl, second + "." + tenth);
    }

    void glEnable2D(GL2 gl)
    {

        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glOrtho(0, vPort[2], 0, vPort[3], -1, 1);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
    }

    public void renderText(GL2 gl, String Message){
        TextRenderer renderer3 = new TextRenderer(new Font("Agency FB", Font.PLAIN, 26), true, true);
        renderer3.beginRendering(windowWidth-200, windowHeight-60);
        gl.glPushMatrix();
        renderer3.setColor(1.0f, 1.0f, 1.0f, 1);
        renderer3.draw(Message, 75, 60);
        gl.glFlush();
        gl.glPopMatrix();
        renderer3.endRendering();
    }
    void glDisable2D(GL2 gl)
    {
        gl.glMatrixMode(gl.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {}

    private void loadObjects(GL2 gl) {
        Player.loadModels(gl);
        Asteroids.initAsteroids(gl, 15);
        Lights.initLight(gl);
        Track.initTrack(gl, 30);
        Planets.loadSun(glu);
    }
}
