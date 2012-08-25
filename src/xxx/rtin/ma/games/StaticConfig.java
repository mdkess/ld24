package xxx.rtin.ma.games;

import org.newdawn.slick.Color;

public final class StaticConfig {
    public static final boolean DEBUG = false;
    public static final Color BACKGROUND_COLOR;
    public static final Color SHIP_INNER_COLOR;
    public static final Color SHIP_OUTER_COLOR;
    public static final Color TURRET_COLOR;
    public static final Color TURRET_GUN_COLOR;
    public static final Color SHIP_THRUST_COLOR;
    public static final Color CONTRAIL_COLOR;
    public static final Color MISSILE_COLOR;
    public static final Color TARGET_COLOR;

    //public static final Color LASER_COLOR;
    public static final Color CANNON_COLOR;
    //public static final Color LIGHTNING_COLOR;
    public static final int COIN_LIFE = 30000;
    public static final float COIN_SIZE = 5;
    
    static {
        BACKGROUND_COLOR = new Color(50f/255f, 50f/255f, 100f/255f);
        SHIP_OUTER_COLOR = new Color(0.9f, 0.9f, 1.0f);
        SHIP_INNER_COLOR = new Color(0.5f, 0.5f, 0.5f);
        TURRET_COLOR = new Color(0.3f, 0.3f, 0.3f);
        TURRET_GUN_COLOR = new Color(0.6f, 0.6f, 0.9f);
        SHIP_THRUST_COLOR = new Color(1.0f, 0.9f, 0.4f);
        CONTRAIL_COLOR = new Color(0.6f, 0.6f, 0.8f);
        MISSILE_COLOR = new Color(0.9f, 0.9f, 0.9f);
        CANNON_COLOR = new Color(1.0f, 1.0f, 0.3f);
        
        TARGET_COLOR = new Color(0.0f, 1.0f, 0.0f, 0.1f);
    }
    
    

}
