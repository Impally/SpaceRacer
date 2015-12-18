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
    public static boolean mode;
    //camera speed
    public final static float rot_speed = 128.0f;
    public final static float mv_speed = 0.001f;
    private static float accelx, accely, accelz;
    private long start_time = System.currentTimeMillis();
    private GLU glu = new GLU();
    private static int tenth, second;
    public static long current_time;
    private static int score;
    private boolean gameOver;

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
        loadObjects(gl);
        score = 0;
        gameOver = false;
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
        gl.glLoadIdentity();
        updateState();
        glu.gluLookAt( pos_x, pos_y, pos_z,
                pos_x + look_x, pos_y + look_y, pos_z + look_z,
                0.0f, 0.0f, 1.0f );
        if(!mode){
            gl.glTranslatef(0,0,-1);
            Asteroids.drawAsteroidField(gl);
            Ring.drawRings(gl);
            gl.glTranslatef(100, 50, 0);
            Planets.drawSun(glu, gl, 100f, 50f, 100f);
            gl.glTranslatef(-100,-50,0);
            gl.glTranslatef(pos_x, pos_y, pos_z);
            current_skybox.draw(gl, skybox_size);
            renderText(gl, second + "." + tenth, (int) (windowWidth*.78), (int) (windowHeight*.15) );
            renderText(gl, Integer.toString(score), (int) (windowWidth*.9), (int) (windowHeight*.85));
            glEnable2D(gl);
            Hud.drawHud(gl, windowWidth, windowHeight);
            glDisable2D(gl);
        }else{Player.drawPlayer(gl, pos_x, pos_y, pos_z);}
        gl.glPopMatrix();
        if(Asteroids.checkCollision(pos_x,pos_y,pos_z))
        {
            gameOver = true;
        }
        if(Ring.checkCollision(pos_x,pos_y,pos_z))
        {
            score += 1;
            if(score == 10)
            {
              gameOver = true;
            }
        }
        if(gameOver)
        {
            gameOver(gl);
        }

    }

    private void updateState() {
        // Update the camera state.
        float normxy = 1;
        if ( keyboard.keys[KeyEvent.VK_W] || keyboard.keys[KeyEvent.VK_S] ) {
            normxy = (float) Math.sqrt( look_x * look_x + look_y * look_y );
            float multiplier = keyboard.keys[KeyEvent.VK_W] ? 1.0f : -1.0f;
            accelx += multiplier;
            if(accelx > 100){accelx = 100;}
            if(accelx < -100){accelx = -100;}
        }
        pos_x += look_x / normxy * mv_speed * accelx;
        pos_y += look_y / normxy * mv_speed * accelx;

        if ( keyboard.keys[KeyEvent.VK_R] ) {
            accelz += 1;
            if(accelz > 100){accelz = 100;}
        } else if ( keyboard.keys[KeyEvent.VK_F] ) {
            accelz -=1;
            if(accelz < -100){accelz = -100;}
        }
        pos_z += mv_speed*accelz;

        if ( keyboard.keys[KeyEvent.VK_A] || keyboard.keys[KeyEvent.VK_D] ) {
            float theta = (float) Math.atan2( look_y, look_x );
            float phi = (float) Math.acos( look_z );

            if ( keyboard.keys[KeyEvent.VK_A] )
                theta += Math.PI / 2.0;
            else if ( keyboard.keys[KeyEvent.VK_D] )
                theta -= Math.PI / 2.0;

            float strafe_x = (float)( Math.cos( theta ) * Math.sin( phi ) );
            float strafe_y = (float)( Math.sin( theta ) * Math.sin( phi ) );
            normxy = (float) Math.sqrt( strafe_x * strafe_x + strafe_y * strafe_y );

            pos_x += strafe_x / normxy * mv_speed;
            pos_y += strafe_y / normxy * mv_speed;
        }

        current_time = System.currentTimeMillis()-start_time;
        tenth = (int) (current_time%1000)/100;
        second = (int) current_time/1000;
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

    public void renderText(GL2 gl, String Message, int x, int y){
        TextRenderer renderer3 = new TextRenderer(new Font("Agency FB", Font.PLAIN, 50), true, true);
        renderer3.beginRendering(windowWidth, windowHeight);
        gl.glPushMatrix();
        renderer3.setColor(.2352f, .53725f, .76078f, 1);
        renderer3.draw(Message, x, y);
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
        Lights.initLight(gl);
        Ring.initRings(gl, 30);
        Asteroids.initAsteroids(gl, 45);
        Planets.loadSun(glu);
        Hud.init();
    }

    private void gameOver(GL2 gl) {
        if(score < 10)
        {
            TextRenderer renderer3 = new TextRenderer(new Font("Agency FB", Font.PLAIN, 100), true, true);
            renderer3.beginRendering(windowWidth, windowHeight);
            gl.glPushMatrix();
            renderer3.setColor(.2352f, .53725f, .76078f, 1);
            renderer3.draw("Get Rekt", 0, windowHeight/2);
            gl.glFlush();
            gl.glPopMatrix();
            renderer3.endRendering();
        }else
        {
            TextRenderer renderer3 = new TextRenderer(new Font("Agency FB", Font.PLAIN, 100), true, true);
            renderer3.beginRendering(windowWidth, windowHeight);
            gl.glPushMatrix();
            renderer3.setColor(.2352f, .53725f, .76078f, 1);
            renderer3.draw("Victory is yours!  Escape to Quit", 0, windowHeight/2);
            gl.glFlush();
            gl.glPopMatrix();
            renderer3.endRendering();
        }
    }
}
