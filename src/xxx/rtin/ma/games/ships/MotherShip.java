package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.StaticConfig;
import xxx.rtin.ma.games.World;
import xxx.rtin.ma.games.weapons.MissileLauncher;

public class MotherShip extends Ship {

    Turret[] mTurrets = new Turret[4];
    
    public MotherShip() {
        super("Station", 25, 0, 0);
    }
    
    //hax!
    @Override
    public void setOwner(GameEntity owner) {
        super.setOwner(owner);
        mTurrets[0] = new Turret(this, 0, 1, 200, new MissileLauncher(World.GetInstance(), 3000, 10));
        mTurrets[1] = new Turret(this, 1, 0, 200, new MissileLauncher(World.GetInstance(), 3000, 10));
        mTurrets[2] = new Turret(this, 0, -1, 200, new MissileLauncher(World.GetInstance(), 3000, 10));
        mTurrets[3] = new Turret(this, -1, 0, 200, new MissileLauncher(World.GetInstance(), 3000, 10));
    }

    @Override
    public void render(Graphics g, boolean thrust) {
        
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-1, -1, 2, 2);
        g.setColor(StaticConfig.SHIP_INNER_COLOR);
        g.fillOval(-0.75f, -0.75f, 1.5f, 1.5f);
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-0.25f, -0.25f, 0.5f, 0.5f);
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillRect(-1, -0.05f, 2, 0.1f);
        g.fillRect(-0.05f, -1, 0.1f, 2);
        for(Turret t : mTurrets) {
            t.render(g);
        }
    }
    
    @Override
    public void update(int delta) {
        for(Turret t : mTurrets) {
            t.update(delta);
        }
    }

}
