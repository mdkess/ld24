package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.World;

public abstract class Weapon {
    protected final int mTotalCooldown;
    protected World mWorld;
    protected GameEntity mOwner;
    protected int mCooldown;
    protected boolean mReady = true;
    protected GameEntity mTarget;
    
    public Weapon(World world, int cooldown) {
        mTotalCooldown = cooldown;
        mWorld = world;
        mCooldown = 0;
    }
    
    public void setTarget(GameEntity target) {
        mTarget = target;
    }
    public boolean hasTarget() {
        return mTarget != null;
    }
    public GameEntity getTarget() { return mTarget; }
    
    protected abstract boolean fireWeapon(GameEntity owner);
    
    public void fire(GameEntity owner) {
        if(mReady) {
            if(fireWeapon(owner)) {

                mCooldown = mTotalCooldown;
                mReady = false;
            }
        }
    }

    public void update(int delta) {
        if(!mReady) {
            mCooldown -= delta;
            if(mCooldown <= 0) {
                mCooldown = 0;
                mReady = true;
            }
        }
    }
    public int getCooldown() {
        return mCooldown;
    }
    public int getTotalCooldown() {
        return mTotalCooldown;
    }
}
