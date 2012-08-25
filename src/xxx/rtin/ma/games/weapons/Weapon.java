package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.World;

public abstract class Weapon {
    protected final int mTotalCooldown;
    protected World mWorld;
    protected GameEntity mOwner;
    protected int mCooldown;
    protected boolean mReady = true;
    
    public Weapon(World world, GameEntity owner, int cooldown) {
        mTotalCooldown = cooldown;
        mOwner = owner;
        mWorld = world;
        mCooldown = 0;
    }
    
    protected abstract void fireWeapon();
    
    public void fire() {
        if(mReady) {
            if(mOwner.hasTarget()) {
                fireWeapon();

                mCooldown = mTotalCooldown;
                mReady = false;
            }
        }
    }

    public void update(int delta) {
        if(!mReady) {
            mCooldown -= delta;
            if(mCooldown <= 0) {
                mReady = true;
            }
        }
    }
}
