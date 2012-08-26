package xxx.rtin.ma.games.ai;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.World;

public class StationAIController extends AIController {

    private int mRetargetTimer = 5000;
    
    public StationAIController(GameEntity owner) {
        super(owner, null, 500, 500, 0);
    }

    @Override
    public void update(int delta) {
        mRetargetTimer -= delta;
        if(mRetargetTimer < 0) {
            mOwner.setTarget(World.GetInstance().getNearestEnemy(mOwner));
            mRetargetTimer = 5000;
        }
        
        mOwner.rotateCW(delta); //just rotate

        //Shoot at nearest target
        if(mOwner.canFire() && mOwner.targetInRange(1000*1000)) {
            mOwner.fire();
        }
    }

}
