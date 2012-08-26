package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.ShapeCache;
import xxx.rtin.ma.games.StaticConfig;

public class BlasterShip extends Ship {

    public BlasterShip() {
        super("Blaster", 180, 300, 100);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void render(Graphics g, boolean thrust) {
        g.setColor(StaticConfig.CANNON_COLOR);
        g.fill(ShapeCache.SQUARE);
    }

}
