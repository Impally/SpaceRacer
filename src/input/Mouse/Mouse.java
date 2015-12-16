package input.Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import input.state.State;
import JOGL.JoglEventListener;
/**
 * Created by rodge on 12/16/2015.
 */
public class Mouse implements MouseListener, MouseMotionListener {

    private int mouse_x0 = 0;
    private int mouse_y0 = 0;

    public Mouse(){

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
        float phi = (float) Math.acos( JoglEventListener.look_z );
        float theta = (float) Math.atan2( JoglEventListener.look_y, JoglEventListener.look_x );

        theta -= dX / JoglEventListener.rot_speed;
        phi += dY / JoglEventListener.rot_speed;

        if ( theta >= Math.PI * 2.0 )
            theta -= Math.PI * 2.0;
        else if ( theta < 0 )
            theta += Math.PI * 2.0;

        if ( phi > Math.PI - 0.1 )
            phi = (float)( Math.PI - 0.1 );
        else if ( phi < 0.1f )
            phi = 0.1f;

        JoglEventListener.look_x = (float)( Math.cos( theta ) * Math.sin( phi ) );
        JoglEventListener.look_y = (float)( Math.sin( theta ) * Math.sin( phi ) );
        JoglEventListener.look_z = (float)( Math.cos( phi ) );

        mouse_x0 = x;
        mouse_y0 = y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
