package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.World;


public class MissileLauncher extends Weapon {
    public MissileLauncher(World world, GameEntity owner, int cooldown) {
        super(world, owner, cooldown);
    }

    @Override
    protected void fireWeapon() {
        SoundCache.MISSILE_FIRE.play();
        mWorld.addProjectile(new Missile(mWorld, mOwner, mOwner.getTarget()));
    }
}
