package xxx.rtin.ma.games.weapons;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.StaticUtil;
import xxx.rtin.ma.games.World;

public class RocketBattery extends Weapon {

    public RocketBattery(String name, World world, int cooldown, int damage) {
        super(name, world, cooldown, damage);
    }

    private class Offset {
        public Offset(float x, float y, float angle) {
            this.pos = new Vector2f(x, y);
            this.angle = angle;
        }
        Vector2f pos; float angle;
    }
    private List<Offset> mOffset = new ArrayList<Offset>();
    public void addLauncher(float offX, float offY, float angle) {
        mOffset.add(new Offset(offX, offY, angle));
    }
    
    @Override
    protected boolean fireWeapon(GameEntity owner) {
        if(mTarget == null) { return false; }
        SoundCache.MISSILE_FIRE.play();

        for(Offset offset : mOffset) {
            Vector2f p = StaticUtil.RotateVector(offset.pos, owner.getAngle()).scale(owner.getRadius()).add(owner.getPos());
            mWorld.addProjectile(new Missile(mWorld, owner, getTarget(),p.x, p.y, owner.getAngle() + offset.angle, mDamage));

        }
        return true;
    }
}
