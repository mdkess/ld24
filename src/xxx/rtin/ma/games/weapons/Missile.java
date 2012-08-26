package xxx.rtin.ma.games.weapons;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.Particle;
import xxx.rtin.ma.games.Projectile;
import xxx.rtin.ma.games.ShapeCache;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.StaticConfig;
import xxx.rtin.ma.games.StaticUtil;
import xxx.rtin.ma.games.World;
import xxx.rtin.ma.games.ships.MissileShip;

public class Missile extends Projectile {
       
    private GameEntity mOwner;
    private GameEntity mTarget;
    
    private int mLife = 3000; //missiles last three seconds.
    
    public Missile(World world, GameEntity owner, GameEntity target, int damage) {
        this(world, owner, target, owner.getPos().x, owner.getPos().y, owner.getAngle(), damage);
    }
    public Missile(World world, GameEntity owner, GameEntity target, float x, float y, float angle, int damage) {
        super(world, new MissileShip(), owner, x, y, angle, damage);

        mOwner = owner;
        mTarget = target;
        

        setRadius(5);
        
        mVel.set(owner.getVel());
    }

    
    boolean mNear = false;
    private float mHeading;
    @Override
    public void update(int delta) {
        setThrust();
        
        
        //The goal of the missile is to make its velocity vector point at the target. It does this by overshooting the target
        Vector2f toTarget = new Vector2f(mTarget.getPos()).sub(mPos);
        float targetAngle = (float) toTarget.getTheta();

        float velocityAngle = (float) mVel.getTheta();
        
        float difference = StaticUtil.AngleBetween(velocityAngle, targetAngle);
        if(mVel.lengthSquared() > 1 && Math.abs(difference) < 60) {
            turnTowardTarget(delta, targetAngle + difference);
            mHeading = targetAngle + difference;
            mNear = true;
        } else {
            turnTowardTarget(delta, targetAngle);
            mHeading= targetAngle;
            mNear = false;
        }
        
        super.update(delta);
        mLife -= delta;
        if(mLife <= 0) {
            explode();
        }
    }
    
    private void explode() {
        mWorld.removeProjectile(this);
        
        SoundCache.MISSILE_MISS.play();
        //Explode!
        for(int i=0; i<10; ++i) {
            float rads = (float) ((i * Math.PI * 2) / 10.0f);
            float v = 30 * mRandom.nextFloat();
            mWorld.addParticle(new Particle(
                ShapeCache.SQUARE, Color.white, 1000, mPos.x, mPos.y,
                    (float)FastTrig.cos(rads) * v + mVel.x, (float)FastTrig.sin(rads) * v + mVel.y, 0,
                    mRandom.nextFloat() * 9 + 1, 180 - 360 * mRandom.nextFloat()));
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.pushTransform();
        
        renderContrail(g);
        
        if(StaticConfig.DEBUG) {
            g.drawOval(mPos.x - mRadius, mPos.y - mRadius, 2*mRadius, 2*mRadius);
        }

        g.translate(mPos.x, mPos.y);
        
        if(StaticConfig.DEBUG) {
            g.setColor(Color.pink);
            g.drawLine(0, 0, mVel.x, mVel.y);
            if(mNear) {
                g.setColor(Color.green);
                float rad = (float) Math.toRadians(mHeading);
                float len = new Vector2f(mTarget.getPos()).sub(mPos).length();
                g.drawLine(0, 0, (float)(len * FastTrig.cos(rad)), (float)(len * FastTrig.sin(rad)));
            }
        }
        g.rotate(0, 0, getAngle() - 90);
        g.scale(10, 10);
        
        mShip.render(g,  true);
        
        g.popTransform();
    }
    
}
