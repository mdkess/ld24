package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.World;

public class Blaster extends Weapon {
    public Blaster(World world, int cooldown) {
        super(world, cooldown);
    }

    @Override
    protected boolean fireWeapon(GameEntity owner) {
        SoundCache.LASER_FIRE.play();
        mWorld.addProjectile(new Bullet(mWorld, owner));
        return true;
    }

}
