package xxx.rtin.ma.games.weapons;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.Particle;
import xxx.rtin.ma.games.Projectile;
import xxx.rtin.ma.games.ShapeCache;
import xxx.rtin.ma.games.StaticConfig;
import xxx.rtin.ma.games.StaticUtil;
import xxx.rtin.ma.games.World;

public class Missile extends Projectile {
    
    private static final Shape sMissileTriangle;
    private static final Shape sThrustTriangle;
    static {
        sMissileTriangle = new Polygon(new float[] {-0.5f, -0.5f, 0.5f, -0.5f, 0, 0.5f});
        sThrustTriangle = new Polygon(new float[] { -0.5f, -0.6f, 0.5f, -0.6f, 0, -1.0f});
    }
    
    private GameEntity mOwner;
    private GameEntity mTarget;
    
    public Missile(World world, GameEntity owner, GameEntity target) {
        super(world, owner, owner.getPos().x, owner.getPos().y, owner.getAngle());

        mOwner = owner;
        mTarget = target;
        
        setTurnRate(90);
        setMaxSpeed(300);
        setThrustForce(150);
        setRadius(5);
        
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
        
        
        
        g.setColor(StaticConfig.MISSILE_COLOR);
        g.fill(sMissileTriangle);
        g.setColor(StaticConfig.SHIP_THRUST_COLOR);
        g.fill(sThrustTriangle);
        
        g.popTransform();
    }
    
}
