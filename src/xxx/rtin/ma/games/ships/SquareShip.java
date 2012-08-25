package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import xxx.rtin.ma.games.StaticConfig;

public class SquareShip extends Ship {
    private static final Shape sOuterSquare;
    private static final Shape sInnerSquare;
    static {
        sOuterSquare = new Polygon(new float[] { -1, -1, 1, -1, 1, 1, -1, 1});
        sInnerSquare = new Polygon(new float[] { -0.75f, -0.75f, 0.5f, -0.75f, 0.5f, 0.5f, -0.75f, 0.5f});
    }
    
    public SquareShip() {
        super(100, 100, 100);
    }
    
    @Override
    public void render(Graphics g, boolean thrust) {
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fill(sOuterSquare);
        g.setColor(StaticConfig.SHIP_INNER_COLOR);
        g.fill(sInnerSquare);
    }

}
