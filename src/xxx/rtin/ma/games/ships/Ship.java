package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;

import xxx.rtin.ma.games.GameEntity;


public abstract class Ship {
    protected GameEntity mOwner;
    public Ship() {
    }
    public void setOwner(GameEntity entity) { mOwner = entity; }
    public GameEntity getOwner() { return mOwner; }
    public abstract void render(Graphics g, boolean thrust);
    
    public void update(int delta) {
        
    }
    
}
