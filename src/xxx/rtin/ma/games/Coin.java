package xxx.rtin.ma.games;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;

//basically a particle, but can be picked up
public class Coin extends Particle {

    public Coin(Shape shape, float x, float y, float vx, float vy, float damping, float rotation) {
        super(shape, Color.yellow, StaticConfig.COIN_LIFE, x, y, vx, vy, damping, StaticConfig.COIN_SIZE, rotation);
        //setFades(false);
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
    }

    
}
