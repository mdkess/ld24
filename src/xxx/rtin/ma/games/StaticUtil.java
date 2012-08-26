package xxx.rtin.ma.games;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class StaticUtil {
    public static float AngleBetween(float angle1, float angle2) {
        while(angle1 < 0) {
            angle1 += 360;
        }
        while(angle2 < 0) {
            angle2 += 360;
        }
        while(angle1 > 360) {
            angle1 -= 360;
        }
        while(angle2 > 360) {
            angle2 -= 360;
        }
        
        float difference = (angle2 - angle1) % 360;
        if(difference > 180) {
            difference -= 360;
        }
        if(difference < -180) {
            difference += 360;
        }
        return difference;
    }

    public static Vector2f WorldSpace(GameEntity reference, Vector2f mOffset) {
        float radius = reference.getRadius();
        float radians = (float)Math.toRadians(reference.getAngle());
        float x = (float) (radius * mOffset.x * FastTrig.cos(radians));
        float y = (float) (radius * mOffset.y * FastTrig.sin(radians));

        return new Vector2f(x, y).add(reference.getPos());
    }
    
    public static Vector2f RotateVector(Vector2f v, float degrees) {
        //Slow rotate
        float length = v.length();
        float theta = (float)v.getTheta();
        float rads = (float) Math.toRadians(degrees+theta);
        return new Vector2f((float)(length * FastTrig.cos(rads)), (float)(length * FastTrig.sin(rads)));
    }
}
