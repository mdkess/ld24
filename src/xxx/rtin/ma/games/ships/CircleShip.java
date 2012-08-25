package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;

import xxx.rtin.ma.games.StaticConfig;

public class CircleShip extends Ship {

    public CircleShip() {
        super(360, 100, 100);
    }
    @Override
    public void render(Graphics g, boolean thrust) {
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-1, -1, 2, 2);
        g.setColor(StaticConfig.SHIP_INNER_COLOR);
        g.fillOval(-0.50f, -0.25f, 1, 1);
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-0.25f, 0.125f, 0.5f, 0.5f);
    }
    @Override
    public boolean inertialEngines() {
        return true;
    }

}
