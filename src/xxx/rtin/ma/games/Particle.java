package xxx.rtin.ma.games;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

public class Particle {
    private Shape mShape;
    private Vector2f mPos;
    private Vector2f mVel;
    private final int mMaxLife;
    private int mLife;
    private float mSize;
    private final float mRotation;
    private float mAngle;
    private boolean mFades;
    private Color mColor;
    private float mDamping;
    private World mWorld; 
    
    public Vector2f getPos() { return mPos; }
    
    public Particle(Shape shape, Color color, int life, float x, float y, float vx, float vy, float damping, float size, float rotation) {
        mPos = new Vector2f(x, y);
        mVel = new Vector2f(vx, vy);
        mMaxLife = life;
        mLife = life;
        mShape = shape;
        mSize = size;
        mRotation = rotation;
        mAngle = 0;
        mFades = true;
        mColor = color;
        mDamping = damping;
        mWorld = World.GetInstance();
    }
    
    
    
    public void setFades(boolean fades) {
        mFades = fades;
    }
    
    
    public void update(int delta) {

        float dt = (delta/1000.0f);
        if(mDamping > 0 && mVel.lengthSquared() > 0.001f) {
            float rads = (float) Math.toRadians(mVel.getTheta());
            
            float vx = (float) (mVel.x - mDamping * FastTrig.cos(rads) * (dt));
            float vy = (float) (mVel.y - mDamping * FastTrig.sin(rads) * (dt));
            float len = vx * vx + vy*vy;
            if(len > mVel.lengthSquared()) {
                mVel.x = 0;
                mVel.y = 0;
            } else {
                mVel.x = vx;
                mVel.y = vy;
            }
        }

        mPos.x += mVel.x * dt;
        mPos.y += mVel.y * dt;
        
        mLife -= delta;
        mAngle += mRotation * (delta/1000.0f);
    }
    
    public void render(Graphics g) {
        g.pushTransform();
        
        if(mFades) {
            Color alpha = new Color(mColor.r, mColor.g, mColor.b, (float)mLife / mMaxLife);
            g.setColor(alpha);
        } else {
            g.setColor(mColor);
        }
        g.translate(mPos.x, mPos.y);
        g.rotate(0, 0, mAngle);
        g.scale(mSize, mSize);
        g.draw(mShape);
        
        g.popTransform();
    }
    
    public boolean alive() {
        return mLife > 0;
    }
}
