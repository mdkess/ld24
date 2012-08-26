package xxx.rtin.ma.games.ai;

import xxx.rtin.ma.games.GameEntity;

//mr. ai controller controls an entity by setting its thrusters
public abstract class AIController {
    protected enum State {
        ATTACK,
        CHARGE,
        FLEE,
        NONE
    };
    protected State mState = State.NONE;
    private float mChargeRange;
    private float mAttackRange;
    private float mSafeDistance;
    protected GameEntity mOwner;
    protected GameEntity mTarget;
    
    public AIController(GameEntity owner, GameEntity target, float chargeRange, float attackRange, float safeDistance) {
        mChargeRange = chargeRange;
        mAttackRange = attackRange;
        mSafeDistance = safeDistance;
        mOwner = owner;
        mTarget = target;
        
        mOwner.setTarget(mTarget);
    }
    
    public abstract void update(int delta);
    
    protected void updateState() {
        float distanceToTarget = mTarget.getPos().distance(mOwner.getPos());
        State oldState = mState;
        switch(mState) {
        case ATTACK:
            if(healthIsLow()) {
                mState = State.FLEE;
            } else if(distanceToTarget > mAttackRange) {
                mState = State.NONE;
            }
            
            break;
        case CHARGE:
            if(healthIsLow()) {
                mState = State.FLEE;
            } else if(distanceToTarget < mAttackRange) {
                mState = State.ATTACK;
            }
            break;
        case FLEE:
            if(distanceToTarget > mSafeDistance || !healthIsLow()) {
                mState = State.NONE;
            }
            break;
        case NONE:
            //dispatches to charge, flee and attack as appropriate
            
            if(healthIsLow()) {
                if(distanceToTarget < mSafeDistance) {
                    mState = State.FLEE;
                }
            //} else if(distanceToTarget < mChargeRange) {
            } else {
                mState = State.CHARGE;
            }
            break;
            
        }
        if(oldState != mState) {
            System.out.println("Changing from " + oldState + " to " + mState);
        }
    }
    
    protected boolean healthIsLow() {
        float healthPct = mOwner.getHealthPercent();
        float shieldPct = mOwner.getShieldPercent();
        return healthPct < 0.25f || healthPct < 0.75 && mOwner.hasShields() && shieldPct < 0.10f;
    }

}
