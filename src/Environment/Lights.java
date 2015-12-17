package Environment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * Created by Marshall on 12/17/2015.
 */
public class Lights {

    static float[] colorBlack  = {0.0f,0.0f,0.0f,1.0f};
    static float[] colorWhite  = {1.0f,1.0f,1.0f,1.0f};
    static float[] colorGray   = {0.6f,0.6f,0.6f,1.0f};
    static float[] colorRed    = {1.0f,0.0f,0.0f,1.0f};
    float[] colorBlue   = {0.0f,0.0f,0.1f,1.0f};
    float[] colorYellow = {1.0f,1.0f,0.0f,1.0f};
    float[] colorLightYellow = {.5f,.5f,0.0f,1.0f};

    public static void initLight( GL2 gl)
    {
        // First Switch the lights on.
        gl.glEnable( GL2.GL_LIGHTING );
        gl.glDisable( GL2.GL_LIGHT0 );
        gl.glEnable( GL2.GL_LIGHT1 );
        gl.glEnable( GL2.GL_LIGHT2 );


        //
        // Light 1.
        //
        // Position and direction (spotlight)
        float posLight1[] = { 1.0f, 1.f, 1.f, 0.0f };
        float spotDirection[] = { -1.0f, -1.0f, 0.f };

        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_POSITION, posLight1,1 );
        gl.glLightf( GL2.GL_LIGHT1, GL2.GL_SPOT_CUTOFF, 60.0F );
        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPOT_DIRECTION, spotDirection,1 );
        //
        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_AMBIENT, colorGray,1 );
        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_DIFFUSE, colorGray,1 );
        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPECULAR, colorWhite,1 );
        gl.glLightfv( GL2.GL_LIGHT1, GL2.GL_SPECULAR, colorRed,1 );
        //
        gl.glLightf( GL2.GL_LIGHT1, GL2.GL_CONSTANT_ATTENUATION, 0.2f );

        //
        // Light 2.
        //
        // Position and direction
        float posLight2[] = { .5f, 1.f, 3.f, 0.0f };
        gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_POSITION, posLight2,1 );
        //
        gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_AMBIENT, colorGray,1 );
        gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_DIFFUSE, colorGray,1 );
        gl.glLightfv( GL2.GL_LIGHT2, GL2.GL_SPECULAR, colorWhite,1 );
        //
        gl.glLightf( GL2.GL_LIGHT2, GL2.GL_CONSTANT_ATTENUATION, 0.8f );
    }


    Lights(GL2 gl){

        float posLight1[] = {1.0f, 1.f, 1.f, 0.0f };
        float posLight2[] = {0f, 0f, 0f, 0.0f };
        float[] colorGray   = {0.6f,0.6f,0.6f,1.0f};
        float[] colorWhite  = {1.0f,1.0f,1.0f,1.0f};

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posLight2, 1);
        gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, colorWhite,1 );
        gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_DIFFUSE, colorGray,1 );
        gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_SPECULAR, colorWhite, 1);
        gl.glLightf(GL2.GL_LIGHT0, GL2.GL_CONSTANT_ATTENUATION, 0.8f );
    }


}
