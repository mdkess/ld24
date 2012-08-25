package xxx.rtin.ma.games;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import xxx.rtin.ma.games.ships.Ship;

public class World {
    private static World sInstance; 
    private World() { }
    public static World GetInstance() {
        if(sInstance == null ) {
            sInstance = new World();
        }
        return sInstance;
    }
    
    private String mToastMessage;
    private int mToastDuration = 0;
    private class Toast {
        int life;
        final int duration;
        String message;
        public Toast(String message, int duration) {
            this.message = message;
            this.duration = duration;
            this.life = duration;
        }
    }
    
    private List<Toast> mToasts = new LinkedList<Toast>();
    
    
    public void toast(String message, int duration) {
        mToasts.add(new Toast(message, duration));
    }
    
    private PlayerEntity mPlayer;
    public PlayerEntity getPlayer() { return mPlayer; }
    public void setPlayer(PlayerEntity player) { mPlayer = player; }
    
    private List<GameEntity> mEntities = new ArrayList<GameEntity>();
    private List<Projectile> mProjectiles = new ArrayList<Projectile>();
    private List<Projectile> mProjectilesToRemove = new ArrayList<Projectile>();
    private List<Coin> mCoins = new ArrayList<Coin>();
    private List<Coin> mCoinsToRemove = new ArrayList<Coin>();
    
    private List<Particle> mParticles = new ArrayList<Particle>();
    private List<Particle> mParticlesToRemove = new ArrayList<Particle>();
    public void addEntity(GameEntity entity) {
        mEntities.add(entity);
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

    
    public void update(int delta) {
        mProjectiles.removeAll(mProjectilesToRemove);
        mProjectilesToRemove.clear();
        handleCollisions();

        for(Projectile entity : mProjectiles) {
            entity.update(delta);
        }
        for(Coin c : mCoins) {
            c.update(delta);
            if(!c.alive()) {
                mCoinsToRemove.add(c);
            }
        }
        mCoins.removeAll(mCoinsToRemove);
        mCoinsToRemove.clear();
        for(GameEntity entity : mEntities) {
            entity.update(delta);
        }
        
        for(Particle p : mParticles) {
            p.update(delta);
            if(!p.alive()) {
                mParticlesToRemove.add(p);
            }
        }
        mParticles.removeAll(mParticlesToRemove);
        mParticlesToRemove.clear();
        
        if(!mToasts.isEmpty()) {
            mToasts.get(0).life -= delta;
            if(mToasts.get(0).life < 0) {
                mToasts.remove(0);
            }
        }
    }
	public void render(Graphics g) {
	    g.pushTransform();
	    g.translate(-mPlayer.getPos().x, -mPlayer.getPos().y);
	    g.translate(400, 300);
	    //g.scale(mPlayer.getRadius()/10.0f,mPlayer.getRadius()/10.0f);
	    
		drawBackground(g);
		g.setAntiAlias(true);
		for(GameEntity entity : mProjectiles) {
            entity.render(g);
        }
		for(Coin c : mCoins) {
		    c.render(g);
		}
		for(Particle p : mParticles) {
            p.render(g);
        }
		for(GameEntity entity : mEntities) {
		    entity.render(g);
		}
		
		mPlayer.drawTarget(g);
		g.popTransform();
		drawHUD(g);
		drawToast(g);
	}
	
	private void drawToast(Graphics g) {
	    if(!mToasts.isEmpty()) {
	        Toast t = mToasts.get(0);
	        Color c = new Color(1, 1, 1, (float)t.life / t.duration);
	        g.setColor(c);
	        g.drawString(t.message, 400 - t.message.length()*4, 200);
	    }
	}
	
	private static Color HUD_COLOR1 = new Color(0.5f, 0.5f, 0.5f, 0.5f);
	private static Color HUD_COLOR2 = new Color(0.5f, 0.5f, 0.5f, 0.7f);
	private static Color TARGET_COLOR = new Color(0.5f, 1.0f, 0.5f, 0.5f);
	private static Color HEALTH_COLOR = new Color(1.0f, 0.4f, 0.4f, 0.5f);
	private static Color SHIELD_COLOR = new Color(0.4f, 0.4f, 1.0f, 0.5f);
	private static Color WEAPON_COOLDOWN_COLOR = new Color(0.8f, 0.8f, 0.2f, 0.5f);
	private static Color XP_COLOR = new Color(0.4f, 1.0f, 0.4f, 0.5f);
	private void drawHUD(Graphics g) {
       GameEntity target = mPlayer.getTarget();
       if(target != null) {
    	    g.setColor(HUD_COLOR1);
    	    g.fillRect(800, 0, -200, 200);
    	    g.setColor(HUD_COLOR2);
    	    g.fillRect(610, 10, 75, 75);
    	    //Draw ship here
    	    g.pushTransform();
        	    Ship ship = target.getShip();
        	    g.translate(610 + 75/2, 10 + 75/2);
        	    g.scale(20, 20);
        	    ship.render(g, false);
    	    g.popTransform();
            g.setColor(Color.white);
            g.drawString(target.getClass().getSimpleName(), 690, 10);
            g.setColor(HUD_COLOR2);
            
            //Draw health
            g.fillRect(690, 30, 100, 25);
            float healthPct = target.getCurrentHealth() / target.getTotalHealth();
            g.setColor(HEALTH_COLOR);
            g.fillRect(695, 35, 90 * healthPct, 15);
            
            g.setColor(HUD_COLOR2);
            //Draw shield
            g.fillRect(690, 60, 100, 25);
            float shieldPct = target.getCurrentShield() / target.getTotalShield();
            g.setColor(SHIELD_COLOR);
    	    g.fillRect(695, 65, 90 * shieldPct, 15);
    	    
    	    g.setColor(TARGET_COLOR);
            //g.drawLine(mPlayer.getPos().x - target.getPos().x, mPlayer.getPos().y - target.getPos().y, 600, 200);
            g.drawLine(target.getPos().x - mPlayer.getPos().x + 400, target.getPos().y - mPlayer.getPos().y + 300, 600, 200);
  
       
       }
	   
       //Now draw player stats on the bottom right.
       
       float playerHealthPct = mPlayer.getCurrentHealth() / mPlayer.getTotalHealth();
       float playerShieldPct = mPlayer.getCurrentShield() / mPlayer.getTotalShield();
       float playerWeaponPct = mPlayer.getWeaponCooldownPercent();
       float playerLevelPct = (float)mPlayer.getLevelPercent();
       g.setColor(HUD_COLOR2);
       //hp
       g.fillRect(760, 490, 30, 100);
       //power
       g.fillRect(720, 490, 30, 100);
       //weapon regen
       g.fillRect(720, 475, 70, 10);
       //xp
       g.fillRect(720, 460, 70, 10);
       
       g.setColor(HEALTH_COLOR);
       g.fillRect(765, 585, 20, -90 * playerHealthPct);
       g.setColor(SHIELD_COLOR);
       g.fillRect(725, 585, 20, -90 * playerShieldPct);
       g.setColor(WEAPON_COOLDOWN_COLOR);
       g.fillRect(722.5f, 477.5f, 65 * playerWeaponPct, 5);
       g.setColor(XP_COLOR);
       g.fillRect(722.5f, 462.5f, 65 * playerLevelPct, 5);
       
	}
	
	public GameEntity getNearestEntity(GameEntity src) {
	    GameEntity closest = null;
	    float bestDistanceSq = 999999;
	    for(GameEntity entity : mEntities) {
	        if(entity != src) {
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
	    for(Projectile p : mProjectiles) {
	        for(GameEntity entity : mEntities) {
	            if(collides(p, entity)) {
	                p.onCollision(entity);
	            }
	        }
	    }
	    if(mPlayer != null) {
    	    for(Coin c : mCoins) {
    	        if(collides(mPlayer, c)) {
    	            mPlayer.giveCoin();
    	            removeCoin(c);
    	            SoundCache.COLLECT_COIN.play(1, 0.3f);
    	        }
    	    }
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
	private void drawBackground(Graphics g) {
	}
    public List<Coin> getCoins() {
        return mCoins;
    }
}
