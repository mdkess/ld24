package xxx.rtin.ma.games;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.ships.Ship;

public abstract class Projectile extends GameEntity {

    private GameEntity mOwner;
    private float mDamage;
    
    public Projectile(World world, Ship ship, GameEntity owner, float x, float y, float angle, float damage) {
        super(world, ship, x, y, angle);
        mOwner = owner;
        mDamage = damage;
    }
    Random r = new Random();
    @Override
    public void onCollision(GameEntity other) {
        if(other.getTeam() == mOwner.getTeam()) { return; }
        if(other.hasShield()) {
            SoundCache.MISSILE_HIT_SHIELD.play();
        } else {
            SoundCache.MISSILE_HIT_NOSHIELD.play();
        }
        //Reflect velocity through perpendicular angle to target.
        float targetAngle = (float) new Vector2f(other.getPos()).sub(mPos).getTheta();
        targetAngle += 90;
        
        float difference = StaticUtil.AngleBetween(targetAngle, (float)mVel.getTheta());
        targetAngle -= difference;
        
        final float variance = 30;
        //Now, variance
        for(int i=0; i<20; ++i) {
            
            float angle = targetAngle - variance + 2*variance * r.nextFloat();
            float rads = (float) Math.toRadians(angle);
            float v = r.nextFloat() * 100;
            mWorld.addParticle(
                    new Particle(
                            ShapeCache.SQUARE, Color.white, 1000, mPos.x, mPos.y,
                            (float)FastTrig.cos(rads) * v, (float)FastTrig.sin(rads) * v, 0,
                            r.nextFloat() * 9 + 1, 180 - 360 * r.nextFloat()));
        }

        
        //Explode!
        //Add some particles.
        
        //Remove self.
        mWorld.removeProjectile(this);
        other.damage(mDamage);
    }
}
