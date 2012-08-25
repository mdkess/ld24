package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.StaticConfig;

public class CircleShip extends Ship {

    @Override
    public void render(Graphics g, boolean thrust) {
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-1, -1, 2, 2);
        g.setColor(StaticConfig.SHIP_INNER_COLOR);
        g.fillOval(-0.75f, -0.75f, 1, 1);
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-0.5f, -0.5f, 0.5f, 0.5f);
    }


}
