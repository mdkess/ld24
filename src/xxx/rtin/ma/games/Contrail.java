package xxx.rtin.ma.games;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Contrail {
    private float[][] mPoints;
    private int mIndex;
    
    private int mDelta;
    private int mTotalTime;
    
    private GameEntity mOwner;
    
    public Contrail(GameEntity owner, int length, int delta) {
        mIndex = 0;
        mPoints = new float[length][2];
        for(int i=0; i<length; ++i) {
            mPoints[i][0] = owner.getPos().x;
            mPoints[i][1] = owner.getPos().y;
        }
        mDelta = delta;
        mTotalTime = 0;
        
        mOwner = owner;
    }
     
    public void render(Graphics g) {
        
        //decrease alpha to length
        float x0 = mPoints[mIndex][0];
        float y0 = mPoints[mIndex][1];
        g.setColor(StaticConfig.CONTRAIL_COLOR);
        
        for(int i=0; i < mPoints.length -1; ++i) {
            int idx = mIndex -i;
            
            //TODO: ugly!
            if(idx < 0) idx += mPoints.length;
        
            float x1 = mPoints[idx][0];
            float y1 = mPoints[idx][1];
            g.drawLine(x0, y0, x1, y1);
            x0 = x1;
            y0 = y1;
        }
    }
    
    public void update(int delta) {
        mTotalTime += delta;
        while(mTotalTime > mDelta) {
            mTotalTime -= mDelta;
            mIndex = (mIndex +1) % mPoints.length;
            mPoints[mIndex][0] = mOwner.getPos().x;
            mPoints[mIndex][1] = mOwner.getPos().y;
        }
    }
}
