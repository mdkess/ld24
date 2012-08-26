package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import xxx.rtin.ma.games.StaticConfig;

public class MissileShip extends Ship {

    public MissileShip() {
        super("Missile", 90, 300, 150);

    }
    
    private static final Shape sMissileTriangle;
    private static final Shape sThrustTriangle;
    static {
        sMissileTriangle = new Polygon(new float[] {-0.5f, -0.5f, 0.5f, -0.5f, 0, 0.5f});
        sThrustTriangle = new Polygon(new float[] { -0.5f, -0.6f, 0.5f, -0.6f, 0, -1.0f});
    }
    @Override
    public void render(Graphics g, boolean thrust) {
        g.setColor(StaticConfig.MISSILE_COLOR);
        g.fill(sMissileTriangle);
        g.setColor(StaticConfig.SHIP_THRUST_COLOR);
        g.fill(sThrustTriangle);        
    }
}
