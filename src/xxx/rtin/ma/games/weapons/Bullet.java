package xxx.rtin.ma.games.weapons;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.Projectile;
import xxx.rtin.ma.games.World;
import xxx.rtin.ma.games.ships.BlasterShip;

//bullets don't accelerate.
public class Bullet extends Projectile {

    public Bullet(World world, GameEntity owner) {
        super(world, new BlasterShip(), owner, owner.getPos().x, owner.getPos().y, owner.getAngle());
        
        setRadius(5);
        setMaxSpeed(300);
        
        float r = (float) Math.toRadians(owner.getAngle());
        mVel.x = (float) (mMaxSpeed * FastTrig.cos(r));
        mVel.y = (float) (mMaxSpeed * FastTrig.sin(r));
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
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
