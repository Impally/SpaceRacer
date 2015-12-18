package Environment;
import com.jogamp.opengl.GL2;


/**
 * Created by Marshall on 12/17/2015.
 */
public class Track {
    private static int numRings = 20 ;
    private static Ring[] rings;

    public static void initTrack(GL2 gl, int num){
        Ring.initRings(gl,num);
        numRings=num;
    }

    public static void drawTrack(GL2 gl){


    }

}
