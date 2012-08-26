package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import xxx.rtin.ma.games.StaticConfig;

public class TriangleShip extends Ship {
    
    private static final Shape sOuterTriangle;
    private static final Shape sInnerTriangle;
    private static final Shape sThrustTriangle;
    static {
        sOuterTriangle = new Polygon(new float[] { -1, -1, 1, -1, 0, 2});
        sInnerTriangle = new Polygon(new float[] { -0.5f, -0.5f, 0.5f, -0.5f, 0, 1.0f});
        sThrustTriangle = new Polygon(new float[] { -0.5f, -1.1f, 0.5f, -1.1f, 0, -1.5f});
    }
    
    public TriangleShip(float speedPct) {
        super(180, 200*speedPct, 200);
    }
    
    @Override
    public void render(Graphics g, boolean thrust) {
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fill(sOuterTriangle);
        g.setColor(StaticConfig.SHIP_INNER_COLOR);
        g.fill(sInnerTriangle);
        if(thrust) {
            g.setColor(StaticConfig.SHIP_THRUST_COLOR);
            g.fill(sThrustTriangle);
        }
    }
}
