package xxx.rtin.ma.games.ai;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.World;

public class StationAIController extends AIController {

    public StationAIController(GameEntity owner) {
        super(owner, null, 500, 500, 0);
    }

    @Override
    public void update(int delta) {
        mOwner.rotateCW(delta); //just rotate

        //Shoot at nearest target
        if(mOwner.canFire()) {
            mOwner.setTarget(World.GetInstance().getNearestEnemy(mOwner));
            mOwner.fire();
        }
    }

}
