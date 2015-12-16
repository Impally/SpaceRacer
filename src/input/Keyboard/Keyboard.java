package input.Keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import input.state.State;

/**
 * Created by rodge on 12/16/2015.
 */
public class Keyboard implements KeyListener {
    private static final int KEY_COUNT = 256;
    public static boolean[] keys;
    private static State[] States;

    /**
     * Constructor
     */
    public Keyboard() {
        States = new State[KEY_COUNT];
        keys = new boolean[KEY_COUNT];

        for (int i = 0; i < KEY_COUNT; i++) {
            States[i] = State.RELEASED;
        }
    }

    /**
     * Only allow one thread to poll at once to be thread safe.
     */
    public synchronized void poll() {
        for (int i = 0; i < KEY_COUNT; i++) {
            if (keys[i]) {
                State currentState = States[i];
                if (currentState == State.RELEASED) {
                    States[i] = State.ONCE;
                } else {
                    States[i] = State.PRESSED;
                }
            } else {
                States[i] = State.RELEASED;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(KeyEvent.VK_ESCAPE == e.getKeyChar())
        {System.exit(0);}else if (keyCode > 0 && keyCode < KEY_COUNT) {keys[keyCode] = true;}

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode > 0 && keyCode < KEY_COUNT) {
            keys[keyCode] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     *
     * @param keyCode
     * @return boolean If key is set to PRESSED state (true) or not (false).
     */
    public boolean getPressed(int keyCode) {
        if (States[keyCode] == State.PRESSED) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param keyCode
     * @return boolean If key is set to RELEASED state (true) or not (false).
     */
    public boolean getReleased(int keyCode) {
        if (States[keyCode] == State.RELEASED) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param keyCode
     * @return boolean If key is set to ONCE state (true) or not (false).
     */
    public boolean getPressedOnce(int keyCode) {
        if (States[keyCode] == State.ONCE) {
            return true;
        }
        return false;
    }
}