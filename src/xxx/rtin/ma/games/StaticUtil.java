package xxx.rtin.ma.games;

public class StaticUtil {
    public static float AngleBetween(float angle1, float angle2) {

        float difference = (angle2 - angle1) % 360;
        if(difference > 180) {
            difference = difference - 360;
        }
        return difference;
    }
}
