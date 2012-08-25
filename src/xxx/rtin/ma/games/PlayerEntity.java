package xxx.rtin.ma.games;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.FastTrig;

import xxx.rtin.ma.games.ships.Ship;

public class PlayerEntity extends GameEntity {

    private float mGravityRadius = 100.0f;
    public PlayerEntity(World world, Ship ship, float x, float y, float angle) {
        super(world, ship, x, y, angle);
        
    }
    
    @Override
    public void update(int delta) {
        super.update(delta);

    }
    @Override
    public int getTeam() {
        return 2;
    }
    private int mLevelCost = 10;
    private int mCoins = 0;
    private int mTotalCoins = 0;
    private int mLevel = 0;
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
        mWorld.toast("LEVEL UP!", 1000);
    }
    

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
