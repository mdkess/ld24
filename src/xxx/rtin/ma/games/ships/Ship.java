package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;

import xxx.rtin.ma.games.GameEntity;


public abstract class Ship {
    protected GameEntity mOwner;
    private float mTurnRate;
    private float mMaxSpeed;
    private float mThrust;
    private String mName;
    public Ship(String name, float turnRate, float maxSpeed, float thrust) {
        mTurnRate = turnRate;
        mMaxSpeed = maxSpeed;
        mThrust = thrust;
        mName = name;
    }
    public String getName() { return mName; }
    public float getTurnRate() { return mTurnRate; }
    public float getMaxSpeed() { return mMaxSpeed; }
    public float getThrust() { return mThrust; }
    
    public void setOwner(GameEntity entity) { mOwner = entity; }
    public GameEntity getOwner() { return mOwner; }
    public abstract void render(Graphics g, boolean thrust);
    
    public void update(int delta) {
        
    }
    
    public boolean inertialEngines() {
        return false;
    }
    
}
