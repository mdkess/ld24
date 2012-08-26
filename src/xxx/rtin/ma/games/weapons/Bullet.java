package xxx.rtin.ma.games.weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.Projectile;
import xxx.rtin.ma.games.World;
import xxx.rtin.ma.games.ships.BlasterShip;

//bullets don't accelerate.
public class Bullet extends Projectile {
    private int mLife = 3000; //missiles last three seconds.

    public Bullet(World world, GameEntity owner) {
        this(world, owner, owner.getPos().x, owner.getPos().y, owner.getAngle());
    }
    
    public Bullet(World world, GameEntity owner, float x, float y, float angle) {
        super(world, new BlasterShip(), owner, x, y, angle);
        
        setRadius(5);        
        float r = (float) Math.toRadians(angle);
        mVel.x = (float) (mShip.getMaxSpeed() * FastTrig.cos(r));
        mVel.y = (float) (mShip.getMaxSpeed() * FastTrig.sin(r));
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
        mLife -= delta;
        if(mLife < 0) {
            mWorld.removeProjectile(this);
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.pushTransform();

        g.translate(mPos.x, mPos.y);
        g.rotate(0, 0, mAngle);
        g.scale(mRadius, mRadius);

        mShip.render(g, false);
        
        g.popTransform();
    }

}
