package Utils;

import java.util.Random;

/**
 * Created by Marshall on 12/17/2015.
 */
public class Rand {

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static int randRange(int min, int max){
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        if(randInt(0,1)==0)
            randomNum*=-1;

        return randomNum;
    }
}
