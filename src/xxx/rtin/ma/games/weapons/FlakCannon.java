package xxx.rtin.ma.games.weapons;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.World;

public class FlakCannon extends Weapon {

    public FlakCannon(String name, World world, int cooldown, int damage) {
        super(name, world, cooldown, damage);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean fireWeapon(GameEntity owner) {
        mWorld.addProjectile(new FlakBomb(mWorld, owner, mDamage * owner.getDamageMultiplier()));
        return true;
    }

}
