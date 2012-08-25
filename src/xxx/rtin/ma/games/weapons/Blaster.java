package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.World;

public class Blaster extends Weapon {
    public Blaster(World world, GameEntity owner, int cooldown) {
        super(world, owner, cooldown);
    }

    @Override
    protected void fireWeapon() {
        SoundCache.LASER_FIRE.play();
        mWorld.addProjectile(new Bullet(mWorld, mOwner));
    }

}
