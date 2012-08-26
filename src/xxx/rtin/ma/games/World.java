package xxx.rtin.ma.games;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import xxx.rtin.ma.games.ships.Ship;

public class World {
    private static World sInstance;

    public static World GetInstance() {
        if (sInstance == null) {
            sInstance = new World();
        }
        return sInstance;
    }

    List<Wave> mWaves = new LinkedList<Wave>();

    public void addWave(Wave w) {
        mWaves.add(w);
    }

    private class Debris {
        public Debris(float x, float y, float angle, float rotationSpeed,
                float size, Shape shape) {
            this.x = x;
            this.y = y;
            this.angle = angle;
            this.size = size;
            this.shape = shape;
            this.rotationSpeed = rotationSpeed;
        }

        float    x;
        float    y;
        Vector2f pos;
        float    angle;
        float    rotationSpeed;
        float    size;
        Shape    shape;

        public void update(int delta) {
            angle += rotationSpeed * (delta / 1000.0f);
        }
    }

    Random      mRandom = new Random();
    Debris[]    mDebris;
    private int mWidth  = 4000;
    private int mHeight = 4000;

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    private World() {
        mDebris = new Debris[20];
        for (int i = 0; i < mDebris.length; ++i) {
            mDebris[i] = new Debris(mRandom.nextFloat() * 1600,
                    mRandom.nextFloat() * 1200, mRandom.nextFloat() * 360.0f,
                    45 - 90 * mRandom.nextFloat(),
                    5 + mRandom.nextFloat() * 20, ShapeCache.SQUARE);
        }
    }

    
    private String mToastMessage;
    private int    mToastDuration = 0;

    private class Toast {
        int       life;
        final int duration;
        String    message;
        Sound     sound;

        public Toast(String message, int duration, Sound sound) {
            this.message = message;
            this.duration = duration;
            this.life = duration;
            this.sound = sound;
        }
    }

    private List<Toast> mToasts = new LinkedList<Toast>();

    public void toast(String message, int duration, boolean front, Sound sound) {
        if(front) {
            mToasts.add(0, new Toast(message, duration, sound));
        } else {
            mToasts.add(new Toast(message, duration, sound));
        }
    }

    public void toast(String message, int duration) {
        toast(message, duration, false, null);
    }
/*
    public void toast(String message, int duration, Sound sound) {
        mToasts.add(new Toast(message, duration, sound));
    }*/

    private PlayerEntity mPlayer;
    private GameEntity mStation;
    
    public void setStation(GameEntity station) {
        mStation = station;
    }

    public GameEntity getStation() {
        return mStation;
    }
    
    public PlayerEntity getPlayer() {
        return mPlayer;
    }

    public void setPlayer(PlayerEntity player) {
        mPlayer = player;
    }

    private List<GameEntity> mEntities            = new ArrayList<GameEntity>();
    private List<GameEntity> mEntitiesToRemove    = new ArrayList<GameEntity>();
    private List<Projectile> mProjectiles         = new ArrayList<Projectile>();
    private List<Projectile> mProjectilesToRemove = new ArrayList<Projectile>();
    private List<Coin>       mCoins               = new ArrayList<Coin>();
    private List<Coin>       mCoinsToRemove       = new ArrayList<Coin>();

    private List<Particle>   mParticles           = new ArrayList<Particle>();
    private List<Particle>   mParticlesToRemove   = new ArrayList<Particle>();

    public void addEntity(GameEntity entity) {
        mEntities.add(entity);
    }

    public void removeEntity(GameEntity entity) {
        if (entity == mPlayer) {
            toast("resequencing...", 1000, true, null);
            toast("you are replaceable", 2000, true, null);
            mPlayer.setDeathCounter(3000);
        } else if(entity == mStation) {
            SoundCache.GAMEOVER.play();
            gameOver("the station is destroyed. hope is lost", 5000);
        } else {
            mEntitiesToRemove.add(entity);
            if (mPlayer.getTarget() == entity) {
                mPlayer.setTarget(null);
            }
        }
    }

    public void addCoin(Coin c) {
        mCoins.add(c);
    }

    public void removeCoin(Coin c) {
        mCoinsToRemove.add(c);
    }

    public void addProjectile(Projectile projectile) {
        mProjectiles.add(projectile);
    }

    public void removeProjectile(Projectile p) {
        mProjectilesToRemove.add(p);
    }

    public void addParticle(Particle p) {
        mParticles.add(p);
    }

    boolean mPaused = false;
    String mPauseMessage = "";
    public void pause(String message) {
        mPaused = true;
        mPauseMessage = message;
    }
    public void unpause() {
        mPaused = false;
    }
    public boolean isPaused() {
        return mPaused;
    }
    
    boolean mGameOver = false;
    String  mGameOverMessage ="";
    public void gameOver(String message, int delay) {
        if(delay == 0) delay = 1;
        mGameOverCounter = delay;
        mGameOverMessage = message;
    }
    public void gameOver() {
        mGameOver = true;
        if(SoundCache.SONG1.playing()) {
            SoundCache.SONG1.stop();
        }
        SoundCache.GAMEOVER.play();
    }
    public boolean isGameOver() { return mGameOver; }
    
    private int mGameOverCounter = 0; 
    private void updateGameOver(int delta) {
        if(mGameOverCounter != 0) {
            mGameOverCounter -= delta;
            if(mGameOverCounter <= 0) {
                gameOver();
            }
        }
    }
    
    public void update(int delta) {
        updateGameOver(delta);
        if(mPaused) { return; }
        if(mGameOver) { return; }
        if (!mWaves.isEmpty()) {
            mWaves.get(0).update(delta);
            if (mWaves.get(0).isDone() && mEntities.size() <= 2) { //hax - player + mothership
                mWaves.remove(0);
            }
        } else {
            gameOver("you awake from the dream. congratulations, you have won", 5000);
        }
        if(!SoundCache.SONG1.playing()) {
            SoundCache.SONG1.play();
        }

        mEntities.removeAll(mEntitiesToRemove);
        mEntitiesToRemove.clear();

        mProjectiles.removeAll(mProjectilesToRemove);
        mProjectilesToRemove.clear();
        handleCollisions();

        for (Debris d : mDebris) {
            d.update(delta);
        }

        for (Projectile entity : mProjectiles) {
            entity.update(delta);
        }
        for (Coin c : mCoins) {
            c.update(delta);
            if (!c.alive()) {
                mCoinsToRemove.add(c);
            }
        }
        mCoins.removeAll(mCoinsToRemove);
        mCoinsToRemove.clear();
        for (GameEntity entity : mEntities) {
            entity.update(delta);
        }

        for (Particle p : mParticles) {
            p.update(delta);
            if (!p.alive()) {
                mParticlesToRemove.add(p);
            }
        }
        mParticles.removeAll(mParticlesToRemove);
        mParticlesToRemove.clear();

        if (!mToasts.isEmpty()) {
            mToasts.get(0).life -= delta;
            if (mToasts.get(0).life < 0) {
                mToasts.remove(0);
            }
        }
    }
    
    private static final Color STATION_HOME_COLOR = new Color(1.0f, 0.5f, 0.7f, 0.1f);

    public void render(Graphics g) {
        g.pushTransform();
        float cameraX = 400 - mPlayer.getPos().x;
        float cameraY = 300 - mPlayer.getPos().y;
        g.translate(cameraX, cameraY);
        // g.translate(400, 300);
        // g.scale(mPlayer.getRadius()/10.0f,mPlayer.getRadius()/10.0f);

        drawBackground(g, cameraX, cameraY);
        g.setAntiAlias(true);
        for (GameEntity entity : mProjectiles) {
            entity.render(g);
        }
        for (Coin c : mCoins) {
            c.render(g);
        }
        for (Particle p : mParticles) {
            p.render(g);
        }
        for (GameEntity entity : mEntities) {
            entity.render(g);
        }

        mPlayer.drawTarget(g);
        //Draw line to base
        if(mStation != null) {
            g.setColor(STATION_HOME_COLOR);
            g.drawLine(mPlayer.getPos().x, mPlayer.getPos().y, mStation.getPos().x, mStation.getPos().y);
        }
        g.popTransform();
        drawHUD(g);
        
        if(mPaused) {
            g.setColor(Color.black);
            g.drawString(mPauseMessage, 399 - mPauseMessage.length()*4, 199);
            g.setColor(Color.white);
            g.drawString(mPauseMessage, 400 - mPauseMessage.length()*4, 200);
        } else if(mGameOver) {
            g.setColor(Color.black);
            g.drawString("[the end?]", 399 - 10*4, 179);
            g.drawString(mGameOverMessage, 399- mGameOverMessage.length()*4, 199);
            g.setColor(Color.white);
            g.drawString("[the end?]", 400 - 10*4, 180);
            g.drawString(mGameOverMessage, 400 - mGameOverMessage.length()*4, 200);
            
            String quitMessage = "(press Q or ESC to wake up)";
            g.setColor(Color.black);
            g.drawString(quitMessage, 399 - quitMessage.length()*4, 399);
            g.setColor(Color.white);
            g.drawString(quitMessage, 400 - quitMessage.length()*4, 400);
        } else {
            drawToast(g);
        }
    }

    private void drawToast(Graphics g) {
        if (!mToasts.isEmpty()) {
            Toast t = mToasts.get(0);
            if (t.sound != null) {
                t.sound.play();
                t.sound = null;
            }
            Color c1 = new Color(1, 1, 1, (float) t.life / t.duration);
            Color c2 = new Color(0, 0, 0, (float) t.life / t.duration);
            g.setColor(c2);
            g.drawString(t.message, 399 - t.message.length() * 4, 199);
            g.setColor(c1);
            g.drawString(t.message, 400 - t.message.length() * 4, 200);
        }
    }

    private static Color HUD_COLOR1            = new Color(0.5f, 0.5f, 0.5f,
                                                       0.5f);
    private static Color HUD_COLOR2            = new Color(0.5f, 0.5f, 0.5f,
                                                       0.7f);
    private static Color TARGET_COLOR          = new Color(0.5f, 1.0f, 0.5f,
                                                       0.5f);
    private static Color HEALTH_COLOR          = new Color(1.0f, 0.4f, 0.4f,
                                                       0.5f);
    private static Color SHIELD_COLOR          = new Color(0.6f, 0.6f, 1.0f,
                                                       0.5f);
    private static Color WEAPON_COOLDOWN_COLOR = new Color(0.8f, 0.8f, 0.2f,
                                                       0.5f);
    private static Color XP_COLOR              = new Color(0.4f, 1.0f, 0.4f,
                                                       0.5f);

    private void drawHUD(Graphics g) {
        GameEntity target = mPlayer.getTarget();
        if (target != null) {
            g.setColor(HUD_COLOR1);
            g.fillRect(800, 0, -200, 200);
            g.setColor(HUD_COLOR2);
            g.fillRect(610, 10, 75, 75);
            // Draw ship here
            g.pushTransform();
            Ship ship = target.getShip();
            g.translate(610 + 75 / 2, 10 + 75 / 2);
            g.scale(20, 20);
            ship.render(g, false);
            g.popTransform();
            g.setColor(Color.white);
            g.drawString(target.getShip().getName(), 690, 10);
            g.setColor(HUD_COLOR2);

            // Draw health
            g.fillRect(690, 30, 100, 25);
            float healthPct = target.getCurrentHealth()
                    / target.getTotalHealth();
            g.setColor(HEALTH_COLOR);
            g.fillRect(695, 35, 90 * healthPct, 15);

            g.setColor(HUD_COLOR2);
            // Draw shield
            g.fillRect(690, 60, 100, 25);
            float shieldPct = target.getCurrentShield()
                    / target.getTotalShield();
            g.setColor(SHIELD_COLOR);
            g.fillRect(695, 65, 90 * shieldPct, 15);

            g.setColor(TARGET_COLOR);
            // g.drawLine(mPlayer.getPos().x - target.getPos().x,
            // mPlayer.getPos().y - target.getPos().y, 600, 200);
            g.drawLine(target.getPos().x - mPlayer.getPos().x + 400,
                    target.getPos().y - mPlayer.getPos().y + 300, 600, 200);

        }


        // Now draw player stats on the bottom right.

        float playerHealthPct = mPlayer.getCurrentHealth()
                / mPlayer.getTotalHealth();
        float playerShieldPct = mPlayer.getCurrentShield()
                / mPlayer.getTotalShield();
        float playerWeaponPct = mPlayer.getWeaponCooldownPercent();
        float playerLevelPct = (float) mPlayer.getLevelPercent();
        g.setColor(HUD_COLOR2);
        // hp
        g.fillRect(760, 490, 30, 100);
        // power
        g.fillRect(720, 490, 30, 100);
        // weapon regen
        g.fillRect(720, 475, 70, 10);
        // xp
        g.fillRect(720, 460, 70, 10);

        g.setColor(HEALTH_COLOR);
        g.fillRect(765, 585, 20, -90 * playerHealthPct);
        g.setColor(SHIELD_COLOR);
        g.fillRect(725, 585, 20, -90 * playerShieldPct);
        g.setColor(WEAPON_COOLDOWN_COLOR);
        g.fillRect(722.5f, 477.5f, 65 * playerWeaponPct, 5);
        g.setColor(XP_COLOR);
        g.fillRect(722.5f, 462.5f, 65 * playerLevelPct, 5);

        g.setColor(Color.white);
        g.drawString("H: " + (int) mPlayer.getTotalHealth(), 720, 400);
        g.drawString("S: " + (int) mPlayer.getTotalShield(), 720, 415);
        g.drawString("L: " + (int) mPlayer.getLevel(), 720, 430);
        
        int n = mPlayer.getNumWeapons();
        int y0 = 580 - 15 * n;
        for (int i = 0; i < n ; ++i) {
            String text = mPlayer.getWeapon(i).getName();
            if (mPlayer.getWeaponIndex() == i) {
                g.drawString("*" + (i + 1) + ". " + text, 10, y0 + 15 * i);
            } else {
                g.drawString(" " + (i + 1) + ". " + text, 10, y0 + 15 * i);
            }
        }
        drawStarMap(g);
    }
    
    private void drawStarMap(Graphics g) {
        //Draw a mini map in the top left
        g.setColor(HUD_COLOR1);
        g.fillRect(0, 0, 200, 200);
        
        //Draw enemies
        g.setColor(Color.red);
        for(GameEntity entity : mEntities) {
            float x = entity.getPos().x;
            float y = entity.getPos().y;
            if(x >= 0 && x <= mWidth && y >= 0 && y <= mWidth) {
                if(entity.getRadius() > 50) {
                    g.fillOval(200*(x/4000)-2, 200*(y/4000)-2, 8, 8);

                } else {
                    g.fillRect(200*(x/4000), 200*(y/4000), 2, 2);
                }
            }
        }
        //draw player
        {
            g.setColor(Color.green);
            float x = mPlayer.getPos().x;
            float y = mPlayer.getPos().y;
            if(x >= 0 && x <= mWidth && y >= 0 && y <= mWidth) {
                g.fillRect(200*(x/4000), 200*(y/4000), 2, 2);
            }
        }
        //draw station
        if(mStation != null) {
            g.setColor(Color.pink);
            float x = mStation.getPos().x;
            float y = mStation.getPos().y;
            if(x >= 0 && x <= mWidth && y >= 0 && y <= mWidth) {
                g.fillOval(200*(x/4000)-2, 200*(y/4000)-2, 8, 8);
            }
        }
        
        
    }

    public GameEntity getNearestEnemy(GameEntity src) {
	    GameEntity closest = null;
	    float bestDistanceSq = Float.MAX_VALUE;
	    for(GameEntity entity : mEntities) {
	        if(entity.getTeam() != src.getTeam()) {
	            float d2 = entity.getPos().distanceSquared(src.getPos());
	            if(d2 < bestDistanceSq) {
	                bestDistanceSq = d2;
	                closest = entity;
	            }
	        }
	    }
	    return closest;
	}

    private void handleCollisions() {
        for (Projectile p : mProjectiles) {
            for (GameEntity entity : mEntities) {
                if (collides(p, entity)) {
                    p.onCollision(entity);
                }
            }
        }

        for (int i = 0; i < mEntities.size() - 1; ++i) {
            for (int j = i + 1; j < mEntities.size(); ++j) {
                GameEntity e1 = mEntities.get(i);
                GameEntity e2 = mEntities.get(j);
                if (collides(e1, e2)) {
                    resolveCollision(e1, e2);
                }
            }
        }

        if (mPlayer != null) {
            for (Coin c : mCoins) {
                if (collides(mPlayer, c)) {
                    mPlayer.giveCoin();
                    removeCoin(c);
                    SoundCache.COLLECT_COIN.play(1, 0.3f);
                }
            }
        }
    }

    private void resolveCollision(GameEntity e1, GameEntity e2) {
        // smallest radius defers
        float r1 = e1.getHitRadius();
        float r2 = e2.getHitRadius();
        if (r1 < r2) {
            // e1 smallest
            Vector2f toTarget = new Vector2f(e1.getPos()).sub(e2.getPos());
            // move r1 + r2 along toTarget;
            toTarget.normalise();
            toTarget.scale(r1 + r2);
            e1.getPos().set(toTarget.add(e2.getPos()));
            /*
             * //reflect through perp. toTarget.negateLocal(); float targetAngle
             * = (float) toTarget.getTheta(); targetAngle += 90;
             * 
             * float difference = StaticUtil.AngleBetween(targetAngle,
             * (float)e1.getVel().getTheta()); targetAngle -= difference;
             * 
             * float v = e1.getVel().length(); float rads = (float)
             * Math.toRadians(targetAngle);
             * 
             * e1.getVel().set((float)(v * FastTrig.cos(rads)), (float)(v *
             * FastTrig.sin(rads)));
             */
        } else {
            // e2 smallest
            Vector2f toTarget = new Vector2f(e2.getPos()).sub(e1.getPos());
            // move r1 + r2 along toTarget;
            toTarget.normalise();
            toTarget.scale(r1 + r2);
            e2.getPos().set(toTarget.add(e1.getPos()));

            // reflect e2's velocity through toTarget's tangen
            // e2.getVel().set(0,0);
            /*
             * float targetAngle = (float) toTarget.getTheta(); targetAngle +=
             * 90;
             * 
             * float difference = StaticUtil.AngleBetween(targetAngle,
             * (float)e2.getVel().getTheta()); targetAngle += difference;
             * 
             * float v = e2.getVel().length(); float rads = (float)
             * Math.toRadians(targetAngle);
             * 
             * e2.getVel().set((float)(v * FastTrig.cos(rads)), (float)(v *
             * FastTrig.sin(rads)));
             */
        }
    }

    private boolean collides(GameEntity a, GameEntity b) {
        Vector2f toTarget = new Vector2f(a.getPos()).sub(b.getPos());
        return (toTarget.length() < a.getHitRadius() + b.getHitRadius());
    }

    private boolean collides(GameEntity a, Coin b) {
        Vector2f toTarget = new Vector2f(a.getPos()).sub(b.getPos());
        return (toTarget.length() < a.getHitRadius() + 5);
    }

    private static final Color BACKGROUND_COLOR = new Color(1.0f, 1.0f, 1.0f,
                                                        0.1f);

    private void drawBackground(Graphics g, float cameraX, float cameraY) {
        g.setColor(BACKGROUND_COLOR);
        g.pushTransform();
        {
            float xf = (int) ((-cameraX) / (1600));
            float yf = (int) ((-cameraY) / (1200));
            if (cameraX > 0) {
                xf -= 1;
            }
            if (cameraY > 0) {
                yf -= 1;
            }

            g.translate(xf * 1600, yf * 1200);

            for (Debris v : mDebris) {
                g.pushTransform();
                {
                    g.translate(v.x, v.y);
                    g.rotate(0, 0, v.angle);
                    g.scale(v.size, v.size);
                    g.draw(v.shape);
                }
                g.popTransform();

                g.pushTransform();
                {
                    g.translate(v.x + 1600, v.y);
                    g.rotate(0, 0, v.angle);
                    g.scale(v.size, v.size);
                    g.draw(v.shape);
                }
                g.popTransform();

                g.pushTransform();
                {
                    g.translate(v.x + 1600, v.y + 1200);
                    g.rotate(0, 0, v.angle);
                    g.scale(v.size, v.size);
                    g.draw(v.shape);
                }
                g.popTransform();

                g.pushTransform();
                {
                    g.translate(v.x, v.y + 1200);
                    g.rotate(0, 0, v.angle);
                    g.scale(v.size, v.size);
                    g.draw(v.shape);
                }
                g.popTransform();

                /*
                 * 
                 * g.drawString("*", v.x+1600, v.y+1200); g.drawString("*", v.x,
                 * v.y+1200); g.drawString("*", v.x+1600, v.y);
                 */
            }
        }
        g.popTransform();

        /*
         * //which multiple of 1600 we have so far float xf = (int)((-cameraX) /
         * (1600)); float yf = (int)((-cameraY) / (1200)); if(cameraX > 0) { xf
         * -= 1; } if(cameraY > 0) { yf -= 1; }
         * 
         * 
         * for(Vector2f v : mDebris) { g.drawString("*", 1600*xf + v.x, 1200*yf
         * + v.y); g.drawString("*", 1600*(xf+1) + v.x , 1200*yf + v.y);
         * g.drawString("*", 1600*xf + v.x, 1200*(yf+1) + v.y);
         * g.drawString("*", 1600*(xf+1) + v.x, 1200*(yf+1) + v.y); }
         */
    }

    public List<Coin> getCoins() {
        return mCoins;
    }
}
