package xxx.rtin.ma.games;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.ships.Ship;
import xxx.rtin.ma.games.weapons.Weapon;

public class PlayerEntity extends GameEntity {

    private Weapon[] mWeapons = new Weapon[2]; 
    
    private float mGravityRadius = 100.0f;
    public PlayerEntity(World world, Ship ship, float x, float y, float angle) {
        super(world, ship, x, y, angle);
    }
    public void giveWeapon(int idx, Weapon weapon) {
        mWeapons[idx] = weapon;
    }
    private int mWeaponIndex = 0;
    public void selectWeapon(int idx) {
        //if(idx != mWeaponIndex) {
            GameEntity oldTarget = null;
            if(mWeapon != null) {
                oldTarget = mWeapon.getTarget();
            }
            mWeaponIndex = idx;
            setWeapon(mWeapons[idx]);

            setTarget(oldTarget);
        //}
    }
    public Weapon getWeapon(int idx) {
        return mWeapons[idx];
    }
    public int getWeaponIndex() { 
        return mWeaponIndex;
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);
        if(mPos.x < 0) {
            mPos.x = 0;
        }
        if(mPos.x > mWorld.getWidth()) {
            mPos.x = mWorld.getWidth();
        }
        if(mPos.y < 0) {
            mPos.y = 0;
        }
        if(mPos.y > mWorld.getHeight()) {
            mPos.y = mWorld.getHeight();
        }

    }
    @Override
    public int getTeam() {
        return 2;
    }
    private int mLevelCost = 10;
    private int mCoins = 0;
    private int mTotalCoins = 0;
    private int mLevel = 1;
    public void giveCoin() {
        ++mCoins;
        ++mTotalCoins;
        float newRadius = (float) (Math.sqrt(mTotalCoins/Math.PI) + 10);
        setRadius(newRadius);
        if(mCoins % mLevelCost == 0) {
            mCoins -= mLevelCost;
            levelUp();
        }
    }
    public int getCoins() { return mCoins; }
    public int getLevel() { return mLevel; }
    
    private void levelUp() {
        SoundCache.PLAYER_LEVELUP.play();
        ++mLevel;
        mLevelCost += 5;
        //heal!
        mMaxHealth += 5;
        mMaxShield += 5;
        mCurHealth = mMaxHealth;
        mCurShield = mMaxShield;
        //Explode!
        for(int i=0; i<20; ++i) {
            float rads = (float) ((i * Math.PI * 2) / 20.0f);
            mWorld.addParticle(new Particle(
                ShapeCache.SQUARE, Color.magenta, 2000, mPos.x, mPos.y,
                    (float)FastTrig.cos(rads) * 50 + mVel.x, (float)FastTrig.sin(rads) * 50 + mVel.y, 0,
                    mRandom.nextFloat() * 9 + 1, 180 - 360 * mRandom.nextFloat()));
        }
        mWorld.toast(mLevelMessages[mLevel%mLevelMessages.length], 3000);
    }
    
    String[] mLevelMessages = new String[]{
       "you are getting bigger",
       "your enemies run in fear",
       "you are getting smarter",
       "only the fittest survive",
       "you are getting stronger",
       "you are changing",
       "the world is yours",
       "you are getting wiser",
       "you feel a sense of purpose",
       "you feel at one with the universe",
       "you feel calmer",
       "you know things that you did not know before",
       
    };
    

    public void retarget() {
        GameEntity target = mWorld.getNearestEntity(this);
        setTarget(target);
    }

    public void drawTarget(Graphics g) {
        if(hasTarget()) {
            g.pushTransform();
            GameEntity target = mWeapon.getTarget();
            g.translate(target.getPos().x, target.getPos().y);
            g.scale(target.getRadius(), target.getRadius());
            g.setColor(StaticConfig.TARGET_COLOR);

            g.fillOval(-1, -1, 2f, 2.0f);
            
            g.popTransform();
        }
    }

    public GameEntity getTarget() {
        return mWeapon != null ? mWeapon.getTarget() : null;
    }

    public float getWeaponCooldownPercent() {
        if(mWeapon != null) {
            return 1.0f - (float) mWeapon.getCooldown() / (float)mWeapon.getTotalCooldown();
        }
        return 0;
    }
    public float getLevelPercent() {
        return (float)mCoins / mLevelCost;
    }


}
