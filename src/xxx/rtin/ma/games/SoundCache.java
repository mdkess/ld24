package xxx.rtin.ma.games;

import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

public class SoundCache {
    public static Sound MISSILE_FIRE;
    public static Sound MISSILE_HIT_SHIELD;
    public static Sound MISSILE_HIT_NOSHIELD;
    public static Sound LASER_FIRE;
    public static Sound COLLECT_COIN;
    
    public static Music SONG1;
    
    static {
        try {
            MISSILE_FIRE = new Sound("res/missile_fire.wav"); 
            MISSILE_HIT_SHIELD = new Sound("res/missile_shield.wav");
            MISSILE_HIT_NOSHIELD = new Sound("res/missile_noshield.wav");
            LASER_FIRE = new Sound("res/laser_fire.wav");
            COLLECT_COIN = new Sound("res/coin.wav");
            
            SONG1 = new Music("res/song1.wav");
        } catch(Exception e) {
            
        }
    }
}
