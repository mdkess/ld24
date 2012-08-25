package xxx.rtin.ma.games;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class World {
    private static World sInstance; 
    private World() { }
    public static World GetInstance() {
        if(sInstance == null ) {
            sInstance = new World();
        }
        return sInstance;
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
    }
	public void render(Graphics g) {
		drawBackground(g);
		g.setAntiAlias(true);
		for(GameEntity entity : mProjectiles) {
            entity.render(g);
        }
		for(Coin c : mCoins) {
		    c.render(g);
		}
		for(GameEntity entity : mEntities) {
		    entity.render(g);
		}
		for(Particle p : mParticles) {
		    p.render(g);
		}
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
    	            //mPlayer.giveCoin();
    	            removeCoin(c);
    	            SoundCache.COLLECT_COIN.play(1, 0.3f);
    	        }
    	    }
	    }
	}
    private boolean collides(GameEntity a, GameEntity b) {
        Vector2f toTarget = new Vector2f(a.getPos()).sub(b.getPos());
        return (toTarget.length() < a.getRadius() + b.getRadius());
    }
    private boolean collides(GameEntity a, Coin b) {
        Vector2f toTarget = new Vector2f(a.getPos()).sub(b.getPos());
        return (toTarget.length() < a.getRadius() + 5);
    }
	private void drawBackground(Graphics g) {
	}
    public List<Coin> getCoins() {
        return mCoins;
    }
}
