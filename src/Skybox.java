import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;

import java.nio.file.Paths;

public class Skybox {
    public static final int NUM_FACES = 6;
    public static final String PATH_TO_TEXTURES = Paths.get(".\\Textures").toAbsolutePath().normalize().toString();
    public static final String[] SKYBOX_SUFFIXES = {
            "zpos.png", "zneg.png",
            "xpos.png", "xneg.png",
            "ypos.png", "yneg.png"
    };
    protected TextureLoader texture_loader = null;
    private String skybox_name = null;
    private int[] textures = new int[ NUM_FACES ];

    public Skybox( TextureLoader texture_loader, String skybox_name ) {
        this.texture_loader = texture_loader;
        this.skybox_name = skybox_name;
        loadTextures();
    }

    protected void loadTextures() {
        String skybox_name = getSkyboxName();

        for ( int i = 0; i < NUM_FACES; ++i ) {
            textures[ i ] = texture_loader.generateTexture();

            try {
                texture_loader.loadTexture( textures[ i ], PATH_TO_TEXTURES + "\\" + skybox_name + SKYBOX_SUFFIXES[ i ], false);
            } catch ( Exception e ) {
                System.err.println( "Unable to load texture: " + e.getMessage() );
            }
        }
    }

    public String getSkyboxName() {
        return skybox_name;
    }

    public void draw( GL2 gl, float size ) {
        final float d = size/2;

        // Front
        gl.glBindTexture( GL.GL_TEXTURE_2D, textures[ 0 ] );
        gl.glBegin( GL2GL3.GL_QUADS );

        gl.glTexCoord2f( 0.0f, 1.0f );
        gl.glVertex3f( d, -d, d );

        gl.glTexCoord2f( 0.0f, 0.0f );
        gl.glVertex3f( d, -d, -d );

        gl.glTexCoord2f( 1.0f, 0.0f );
        gl.glVertex3f( d, d, -d );

        gl.glTexCoord2f( 1.0f, 1.0f );
        gl.glVertex3f( d, d, d );

        gl.glEnd();

        // Back
        gl.glBindTexture( GL.GL_TEXTURE_2D, textures[ 1 ] );
        gl.glBegin( GL2GL3.GL_QUADS );

        gl.glTexCoord2f( 0.0f, 1.0f );
        gl.glVertex3f( -d, d, d );

        gl.glTexCoord2f( 0.0f, 0.0f );
        gl.glVertex3f( -d, d, -d );

        gl.glTexCoord2f( 1.0f, 0.0f );
        gl.glVertex3f( -d, -d, -d );

        gl.glTexCoord2f( 1.0f, 1.0f );
        gl.glVertex3f( -d, -d, d );

        gl.glEnd();

        // Left
        gl.glBindTexture( GL.GL_TEXTURE_2D, textures[ 2 ] );
        gl.glBegin( GL2GL3.GL_QUADS );

        gl.glTexCoord2f( 0.0f, 1.0f );
        gl.glVertex3f( d, d, d );

        gl.glTexCoord2f( 0.0f, 0.0f );
        gl.glVertex3f( d, d, -d );

        gl.glTexCoord2f( 1.0f, 0.0f );
        gl.glVertex3f( -d, d, -d );

        gl.glTexCoord2f( 1.0f, 1.0f );
        gl.glVertex3f( -d, d, d );

        gl.glEnd();

        // Right
        gl.glBindTexture( GL.GL_TEXTURE_2D, textures[ 3 ] );
        gl.glBegin( GL2GL3.GL_QUADS );

        gl.glTexCoord2f( 0.0f, 1.0f );
        gl.glVertex3f( -d, -d, d );

        gl.glTexCoord2f( 0.0f, 0.0f );
        gl.glVertex3f( -d, -d, -d );

        gl.glTexCoord2f( 1.0f, 0.0f );
        gl.glVertex3f( d, -d, -d );

        gl.glTexCoord2f( 1.0f, 1.0f );
        gl.glVertex3f( d, -d, d );

        gl.glEnd();

        // Up
        gl.glBindTexture( GL.GL_TEXTURE_2D, textures[ 4 ] );
        gl.glBegin( GL2GL3.GL_QUADS );

        gl.glTexCoord2f( 0.0f, 1.0f );
        gl.glVertex3f( -d, -d, d );

        gl.glTexCoord2f( 0.0f, 0.0f );
        gl.glVertex3f( d, -d, d );

        gl.glTexCoord2f( 1.0f, 0.0f );
        gl.glVertex3f( d, d, d );

        gl.glTexCoord2f( 1.0f, 1.0f );
        gl.glVertex3f( -d, d, d );

        gl.glEnd();

        // Down
        gl.glBindTexture( GL.GL_TEXTURE_2D, textures[ 5 ] );
        gl.glBegin( GL2GL3.GL_QUADS );

        gl.glTexCoord2f( 0.0f, 1.0f );
        gl.glVertex3f( d, -d, -d );

        gl.glTexCoord2f( 0.0f, 0.0f );
        gl.glVertex3f( -d, -d, -d );

        gl.glTexCoord2f( 1.0f, 0.0f );
        gl.glVertex3f( -d, d, -d );

        gl.glTexCoord2f( 1.0f, 1.0f );
        gl.glVertex3f( d, d, -d );

        gl.glEnd();
    }
}
