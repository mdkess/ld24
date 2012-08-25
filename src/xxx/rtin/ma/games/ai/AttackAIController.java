package xxx.rtin.ma.games.ai;

import org.newdawn.slick.geom.Vector2f;

import xxx.rtin.ma.games.GameEntity;
import xxx.rtin.ma.games.StaticUtil;
import xxx.rtin.ma.games.World;

//Similar to missile AI
//This AI attacks the player as follows:
//  1. Charge the player
//  2. Flee if health < 75% and shield < 50%;
//  3. Shoot if in range.
public class AttackAIController extends AIController {

    
    public AttackAIController(GameEntity owner) {
        super(owner, World.GetInstance().getPlayer(), 600, 400, 1000);

    }
    public void update(int delta) {
        updateState();
        
        Vector2f toTarget = new Vector2f(mTarget.getPos()).sub(mOwner.getPos());
        float targetAngle = (float) toTarget.getTheta();
        
        
        float angleDifference;
        switch(mState) {
        case ATTACK:
            //Turn toward target
            mOwner.turnTowardTarget(delta, targetAngle);
            mOwner.setThrust();
            angleDifference = StaticUtil.AngleBetween(targetAngle, mOwner.getAngle());
            if(Math.abs(angleDifference) < 18) {
                mOwner.fire();
            }
            break;
        case CHARGE:
            mOwner.turnTowardTarget(delta, targetAngle);
            mOwner.setThrust();
            break;
        case FLEE:
            angleDifference = StaticUtil.AngleBetween(targetAngle, mOwner.getAngle());
            //Turn away from the target.
            if(Math.abs(angleDifference) < 90) {
                //if the difference means we'll move away, rotate first
                if(angleDifference > 0) {
                    mOwner.rotateCCW(delta);
                } else {
                    mOwner.rotateCW(delta);
                }
                //recalculate
                angleDifference = StaticUtil.AngleBetween(targetAngle, mOwner.getAngle());
            }
            if(Math.abs(angleDifference) > 90) {
                //if we're away, flee
                mOwner.setThrust();
            }
            
            break;
        case NONE:
            //Just sit there idly
            mOwner.stop();
            break;
        }
        
    }
    

}
