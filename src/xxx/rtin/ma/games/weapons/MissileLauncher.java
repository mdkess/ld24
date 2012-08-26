package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.World;


public class MissileLauncher extends Weapon {
    public MissileLauncher(World world, int cooldown, int damage) {
        super("Missile Launcher", world, cooldown, damage);
    }

    @Override
    protected boolean fireWeapon(GameEntity owner) {
        if(!hasTarget()) {
            return false;
        }
        SoundCache.MISSILE_FIRE.play();
        mWorld.addProjectile(new Missile(mWorld, owner, getTarget(), mDamage));
        return true;
    }
}
