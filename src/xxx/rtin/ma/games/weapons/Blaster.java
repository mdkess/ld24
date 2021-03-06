package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.SoundCache;
import xxx.rtin.ma.games.World;

public class Blaster extends Weapon {
    public Blaster(World world, int cooldown, int damage) {
        super("Mass Driver", world, cooldown, damage);
    }

    @Override
    protected boolean fireWeapon(GameEntity owner) {
        SoundCache.LASER_FIRE.play();
        mWorld.addProjectile(new Bullet(mWorld, owner, mDamage * owner.getDamageMultiplier()));
        return true;
    }

}
