package xxx.rtin.ma.games;

import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

public class SoundCache {
    public static Sound MISSILE_FIRE;
    public static Sound MISSILE_HIT_SHIELD;
    public static Sound MISSILE_HIT_NOSHIELD;
    public static Sound MISSILE_MISS;
    public static Sound LASER_FIRE;
    public static Sound COLLECT_COIN;
    public static Sound PLAYER_LEVELUP;
    public static Sound WEAPON_SELECT;
    public static Sound INTRO;
    public static Sound[] EXPLOSIONS;
    public static Music SONG1;
    
    private static final Random sRandom = new Random();
    public static Sound RandomExplosion() {
        return EXPLOSIONS[sRandom.nextInt(EXPLOSIONS.length)];
    }
    
    static {
        try {
            MISSILE_FIRE = new Sound("res/missile_fire.wav"); 
            MISSILE_HIT_SHIELD = new Sound("res/missile_shield.wav");
            MISSILE_HIT_NOSHIELD = new Sound("res/missile_noshield.wav");
            MISSILE_MISS = new Sound("res/missile_miss.wav");
            WEAPON_SELECT = new Sound("res/weapon_select.wav");
            LASER_FIRE = new Sound("res/laser_fire.wav");
            COLLECT_COIN = new Sound("res/coin.wav");
            INTRO = new Sound("res/intro.wav");
            PLAYER_LEVELUP = new Sound("res/player_levelup.wav");
            SONG1 = new Music("res/song1.wav");
            
            EXPLOSIONS = new Sound[] {
                new Sound("res/explosion1.wav"),
                new Sound("res/explosion2.wav"),
                new Sound("res/explosion3.wav"),
            };
        } catch(Exception e) {
            
        }
    }
}
