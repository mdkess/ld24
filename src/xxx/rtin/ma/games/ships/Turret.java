package xxx.rtin.ma.games.ships;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.StaticConfig;
import xxx.rtin.ma.games.StaticUtil;
import xxx.rtin.ma.games.World;
import xxx.rtin.ma.games.weapons.Weapon;

//turrets are simple creatures - they find a target, they shoot at the target.
public class Turret {
    private Vector2f mOffset; //relative to parent's RADIUS
    private float mRange;
    private Weapon mWeapon;
    private GameEntity mTarget;
    private float mAngle;
    private Ship mShip;
    
    public Turret(Ship ship, float offsetX, float offsetY, float range, Weapon weapon) {
        mShip = ship;
        mOffset = new Vector2f(offsetX, offsetY);
        mRange = range;
        mWeapon = weapon;
        mAngle = 0.0f;
    }
    
    public void render(Graphics g) {
        g.pushTransform();
        
        g.translate(mOffset.x, mOffset.y);
        g.rotate(0, 0, mAngle);
        g.scale(0.2f, 0.2f);
        g.setColor(StaticConfig.SHIP_INNER_COLOR);
        g.fillOval(-0.5f, -0.5f, 1.0f, 1.0f);
        g.setColor(StaticConfig.SHIP_OUTER_COLOR);
        g.fillOval(-0.25f, -0.25f, 0.5f, 0.5f);
        
        //g.fillRect(0, -0.05f, 1.0f, 0.1f);
        
        g.popTransform();
    }
    
    public void update(int delta) {
        Vector2f currentPos = StaticUtil.WorldSpace(mShip.getOwner(), mOffset);
        if(mTarget != null) {
            findTarget(currentPos);
        }
        if(mTarget != null) {
            mWeapon.update(delta);
            //mWeapon.fire();
        }
    }
    
    private void findTarget(Vector2f currentPos) {
        //See if the player is within range.
        GameEntity playerEntity = World.GetInstance().getPlayer();
        if(playerEntity.getPos().distance(currentPos) < mRange) {
            mTarget = playerEntity;
            System.out.println("Player is within range");
        }
    }

    
}
