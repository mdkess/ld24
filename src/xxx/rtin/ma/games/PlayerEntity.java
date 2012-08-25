package xxx.rtin.ma.games;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

public class PlayerEntity extends GameEntity {

    private float mGravityRadius = 100.0f;
    public PlayerEntity(World world, float x, float y, float angle) {
        super(world, x, y, angle);
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);

    }


}
