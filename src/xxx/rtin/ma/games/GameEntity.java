package xxx.rtin.ma.games;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.ai.AIController;
import xxx.rtin.ma.games.ships.Ship;
import xxx.rtin.ma.games.weapons.MissileLauncher;
import xxx.rtin.ma.games.weapons.Weapon;

public class GameEntity {

    protected Ship mShip;
    
    protected World mWorld;
    
    protected Vector2f mPos;
    protected Vector2f mVel;
    protected float mAngle;
    protected float mStoppingForce;
    protected float mMass;
    protected float mRadius;
    private Contrail mContrail;
    
    public boolean hasTarget() { return mWeapon != null && mWeapon.hasTarget(); }
    public void setTarget(GameEntity target) {
        if(mWeapon != null) {
            mWeapon.setTarget(target);
        }
    }
    
    public float getRadius() { return mRadius; }
    public float getHitRadius() {
        if(mCurShield > 0) {
            return 2*mRadius;
        } else {
            return mRadius;
        }
    }
    public boolean hasShield() { return mCurShield > 0; }
    public void setRadius(float radius) { mRadius = radius; }
    
    //stats
    protected float mMaxShield;
    protected float mCurShield;
    protected float mShieldRegen;
    protected float mMaxHealth;
    protected float mCurHealth;
    protected float mHealthRegen;
    
    //no effect on stats
    protected float mShieldDamageCharge; //The shield "charges" when hit, and slowly drains.
    protected float mShieldChargeDissipateRate;
    
    private float mTimeBetweenExhaust;
    private float mLastExhaust;
    
    public float getTotalHealth() { return mMaxHealth; }
    public float getCurrentHealth() { return mCurHealth; }
    public float getTotalShield() { return mMaxShield; }
    public float getCurrentShield() { return mCurShield; }
    
    public Ship getShip() { return mShip; }
    
    protected AIController mController;
    
    public GameEntity(World world, Ship ship, float x, float y, float angle) {
        mWorld = world;
        mPos = new Vector2f(x, y);
        mVel = new Vector2f();
        mAngle = angle;
        mStoppingForce = 100;
        mMass = 1.0f;
        mRadius = 10;
        
        //mContrail = new Contrail(this, 40, 20);
        
        mMaxShield = 100;
        mCurShield = 20;
        mShieldRegen = 0
                ;
        mMaxHealth = 100;
        mCurHealth = 100;
        mHealthRegen = 5;
        
        mShieldDamageCharge = 0;
        mShieldChargeDissipateRate = 10;
        
        mTimeBetweenExhaust = 50;
        mLastExhaust = 0;
        
        mShip = ship;
        mShip.setOwner(this);
        setWeapon(new MissileLauncher(mWorld, 1000));
    }
    
    public void setController(AIController controller) {
        mController = controller;
    }
    
    private void updateStats(int delta) {
        float df = (delta/1000.0f);
        mCurShield += mShieldRegen * df;
        if(mCurShield > mMaxShield) {
            mCurShield = mMaxShield;
        }
        
        mCurHealth += mHealthRegen * df;
        if(mCurHealth > mMaxHealth) {
            mCurHealth = mMaxHealth;
        }
        
        mShieldDamageCharge -= mShieldChargeDissipateRate * df;
        if(mShieldDamageCharge < 0) {
            mShieldDamageCharge = 0;
        }
    }
    public boolean shieldsUp() {
        return mCurShield > 0;
    }

    public int getTeam() {
        return 1;
    }
    
    public void onCollision(GameEntity other) {
        
    }

    public Vector2f getPos() {
        return mPos;
    }
    public Vector2f getVel() {
        return mVel;
    }
    public float getAngle() {
        return mAngle;
    }
    
    public void damage(int amount) {
        System.out.println("BAM! " + amount + " damage!");
        mShieldDamageCharge += amount;
        
        //shield blocks all damage
        if(mCurShield > 0) {
            mCurShield -= amount;
            if(mCurShield < 0) {
                mCurShield = 0;
            }
        } else {
            mCurHealth -= amount;
            if(mCurHealth < 0) {
                System.out.println("DEAD");
            }
        }
        
    }
    
    private boolean mStop = false;
    private boolean mThrust = false;
    private boolean mDrawThrust = false;
    public void setThrust() {
        mThrust = true;
    }
    public void stop() {
        mStop = true;
    }
    
    public void rotateCW(int delta) {
        mAngle += (delta/1000.0f) * mShip.getTurnRate();
        if(mAngle > 360) { mAngle -= 360; }
    }
    
    public void rotateCCW(int delta) {
        mAngle -= (delta/1000.0f) * mShip.getTurnRate();
        if(mAngle < 0) { mAngle += 360; }
    }
    
    public void turnTowardTarget(int delta, float targetAngle) {
        if(targetAngle < 0) targetAngle += 360;
        float difference = StaticUtil.AngleBetween(mAngle, targetAngle);
        float maxTurn = (delta/1000.0f) * mShip.getTurnRate();
        if(Math.abs(difference) <= maxTurn) {
            mAngle = targetAngle;
        } else if(difference > 0) {
            rotateCW(delta);
        } else {
            rotateCCW(delta);
        }
        
    }
    
    private void applyForce(float force, float angle, float dt, boolean clamp) {
        float acceleration = force / mMass;
        
        float vx = (float) (mVel.x + acceleration * FastTrig.cos(Math.toRadians(angle)) * (dt));
        float vy = (float) (mVel.y + acceleration * FastTrig.sin(Math.toRadians(angle)) * (dt));
        if(clamp) {
            float len = vx * vx + vy*vy;
            if(len > mVel.lengthSquared()) {
                vx = 0;
                vy = 0;
            }
        }
        
        mVel.x = vx;
        mVel.y = vy;
        
        if(mVel.length() > mShip.getMaxSpeed()) {
            mVel.scale(mShip.getMaxSpeed() / mVel.length());
        }        
    }

    protected final Random mRandom = new Random();
    public void update(int delta) {
        if(mController != null) {
            mController.update(delta);
        }
        if(mContrail != null) {
            mContrail.update(delta);
        }
        if(mWeapon != null) {
            mWeapon.update(delta);
        }
        if(mThrust) {
            mLastExhaust += delta;
            if(mLastExhaust > mTimeBetweenExhaust) {
                mLastExhaust -= mTimeBetweenExhaust;
                float rads = (float)Math.toRadians(mAngle -10 + 20 * mRandom.nextFloat());
                float vx = (float) (mShip.getThrust() * FastTrig.cos(rads));
                float vy = (float) (mShip.getThrust() * FastTrig.sin(rads));
                
                
                mWorld.addParticle(new Particle(ShapeCache.SQUARE, Color.white, 500, mPos.x, mPos.y, -vx, -vy, 0, 5, 180 - 360 * mRandom.nextFloat()));
            }
            if(mShip.inertialEngines()) {
                //set to max speed.
                float rads = (float)Math.toRadians(mAngle);
                mVel.x = (float) (mShip.getMaxSpeed() * FastTrig.cos(rads));
                mVel.y = (float) (mShip.getMaxSpeed() * FastTrig.sin(rads));
            } else {
                applyForce(mShip.getThrust(), mAngle, (delta/1000.0f), false);
            }
            
            mThrust = false;
            mDrawThrust = true;
        } else {
            mLastExhaust = 0;
            mDrawThrust = false;
            if(mShip.inertialEngines()) {
                mVel.x = 0;
                mVel.y = 0;
            }
        }
        if(mStop) {
            applyForce(-mStoppingForce, (float)mVel.getTheta(), (delta/1000.0f), true);

            mStop = false;
        }
        
        
        mPos.x += mVel.x * (delta/1000.0f);
        mPos.y += mVel.y * (delta/1000.0f);
        
        updateStats(delta);
    }
    public void renderContrail(Graphics g) {
        if(mContrail != null) {
            mContrail.render(g);
        }
    }
    
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
        }
        g.rotate(0, 0, mAngle - 90);
        g.scale(mRadius, mRadius);
        
        mShip.render(g, mDrawThrust);
        
        //draw shield;
        if(mCurShield > 0) {
            g.setLineWidth(3.0f);
            g.setColor(new Color(0.7f, 1.0f, 1.0f, 0.2f + 2.5f * (mShieldDamageCharge/mMaxShield)));
            g.drawOval(-2, -2, 4, 4);
            g.setLineWidth(1.0f);
        }
        
        g.popTransform();
    }
    
    
    protected Weapon mWeapon;
    public void setWeapon(Weapon weapon) {
        mWeapon = weapon;
    }
    
    public void fire() {
        if(mWeapon != null) {
            mWeapon.fire(this);
        }
        
    }
    public float getHealthPercent() {
        return mCurHealth / mMaxHealth;
    }
    public float getShieldPercent() {
        return mCurShield / mMaxShield;
    }
    public boolean hasShields() {
        return mMaxShield > 0;
    }
    
}

