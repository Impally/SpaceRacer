/**
 * Created by Rodger on 11/3/2015.
 */

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

import com.jogamp.opengl.util.Animator;
import input.Keyboard.Keyboard;
import input.Mouse.Mouse;


public class Main extends JFrame{
    static private Animator animator = null;

    public Main(){
        super("CS335 Environment.Skybox");

        setDefaultCloseOperation( EXIT_ON_CLOSE);
        setSize(1068, 720);
        setVisible(true);
        setupJogl();
    }

    public static void main(String[] args){
        Main m = new Main();
        m.setVisible(true);
    }

    private void setupJogl(){
        GLCapabilities caps = new GLCapabilities(null);
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);

        GLCanvas canvas = new GLCanvas(caps);
        add(canvas);

        JoglEventListener jgl = new JoglEventListener();
        Keyboard keyboard = new Keyboard();
        canvas.addGLEventListener(jgl);
        canvas.addKeyListener(keyboard);
        canvas.addMouseListener(jgl);
        canvas.addMouseMotionListener(jgl);

        animator = new Animator(canvas);
        animator.start();
    }

}
