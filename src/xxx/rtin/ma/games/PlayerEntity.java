package xxx.rtin.ma.games;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.ships.Ship;
import xxx.rtin.ma.games.ships.TriangleShip;
import xxx.rtin.ma.games.weapons.Blaster;
import xxx.rtin.ma.games.weapons.BlasterBattery;
import xxx.rtin.ma.games.weapons.MissileLauncher;
import xxx.rtin.ma.games.weapons.RocketBattery;
import xxx.rtin.ma.games.weapons.Weapon;

public class PlayerEntity extends GameEntity {

    private List<Weapon> mWeapons = new ArrayList<Weapon>();
    private List<Weapon> mLevelUpWeapons = new ArrayList<Weapon>();
    
    private float mGravityRadius = 100.0f;
    public PlayerEntity(World world, Ship ship, float x, float y, float angle) {
        super(world, ship, x, y, angle);
        
        Blaster singleBlaster = new Blaster(mWorld, 500, 20);
        BlasterBattery doubleBlaster = new BlasterBattery("Double Blaster", mWorld, 600, 20);
        doubleBlaster.addLauncher(0, -1, 0);
        doubleBlaster.addLauncher(0, 1, 0);
        
        BlasterBattery tripleBlaster = new BlasterBattery("Blaster Battery", mWorld, 750, 20);
        tripleBlaster.addLauncher(0, -1, -10);
        tripleBlaster.addLauncher(0, 0, 0);
        tripleBlaster.addLauncher(0, 1, 10);


        mLevelUpWeapons.add(singleBlaster);
        mLevelUpWeapons.add(doubleBlaster);
        mLevelUpWeapons.add(tripleBlaster);
        

        MissileLauncher singleRocket = new MissileLauncher(mWorld, 750, 40);
        
        RocketBattery doubleRocket = new RocketBattery("Double Rocket", mWorld, 1100, 40);
        doubleRocket.addLauncher(0, -1, 0);
        doubleRocket.addLauncher(0, 1, 0);
        
        
        RocketBattery tripleRocket = new RocketBattery("Triple Rocket", mWorld, 1500, 40);
        tripleRocket.addLauncher(0, -1, -45);
        tripleRocket.addLauncher(0, 0, 0);
        tripleRocket.addLauncher(0, 1, 45);
        
        mLevelUpWeapons.add(singleRocket);
        mLevelUpWeapons.add(doubleRocket);
        mLevelUpWeapons.add(tripleRocket);
        
        giveWeapon(mLevelUpWeapons.get(0));
    }
    public void giveWeapon(Weapon weapon) {
        mWeapons.add(weapon);
    }
    private int mWeaponIndex = 0;
    public void selectWeapon(int idx) {
        if(idx < mWeapons.size()) {
        //if(idx != mWeaponIndex) {
            GameEntity oldTarget = null;
            if(mWeapon != null) {
                oldTarget = mWeapon.getTarget();
            }
            mWeaponIndex = idx;
            setWeapon(mWeapons.get(idx));

            setTarget(oldTarget);
        }
    }
    public Weapon getWeapon(int idx) {
        return mWeapons.get(idx);
    }
    public int getWeaponIndex() { 
        return mWeaponIndex;
    }
    
    @Override
    public void render(Graphics g) {
        if(mDeathCounter > 0) return;
        super.render(g);

    }
    @Override
    public void update(int delta) {
        if(mDeathCounter > 0) {
            mDeathCounter -= delta;
            if(mDeathCounter <= 0) {
                mDeathCounter = 0;
                mDead = false;
                mPos.set(mWorld.getStation().getPos().x, mWorld.getStation().getPos().y - 300);
                mVel.set(0,0);
                mAngle = 270;
                SoundCache.RESPAWN.play();
                mCurHealth = mMaxHealth;
                mCurShield = mMaxShield;
            } else {
                return;
            }
        }
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

    private int mLevelCost = 10;
    private int mCoins = 0;
    private int mTotalCoins = 0;
    private int mLevel = 1;
    public void giveCoin() {
        ++mCoins;
        ++mTotalCoins;
        float newRadius = (float) (Math.sqrt(mTotalCoins/(2*Math.PI)) + 10);
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
        mLevelCost += 10;
        //heal!
        mMaxHealth += 20;
        mMaxShield += 20;
        mCurHealth = mMaxHealth;
        mCurShield = mMaxShield;
        mHealthRegen += 0.2f;
        mShieldRegen += 0.2f;
        mDamageMultiplier += 0.1f;
        
        //If the player is level 3, 5, 7, 9 or 11, give them a new weapon
        if(mLevel % 2 == 1 && mLevel <= 11) {
            giveWeapon(mLevelUpWeapons.get((int)(mLevel/2)));
            mWorld.toast("a new weapon to kill with", 5000);
        }
        
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
       "we are not so different",
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

    public int mDeathCounter = 0;
    public void setDeathCounter(int counter) {
        mDeathCounter = counter;
    }
    public int getNumWeapons() {
        return mWeapons.size();
    }


}
