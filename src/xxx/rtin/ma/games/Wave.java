package xxx.rtin.ma.games;

import java.util.PriorityQueue;

//A wave is a list of enemies
public class Wave {

    private String mDescription;
    public String getDescription() { return mDescription; }
    private int mElapsedTime = 0;
    private boolean mNew = true;
    public Wave(String description, int initialDelay) {
        mDescription = description;
        mElapsedTime = -initialDelay;
    }
    
    private PriorityQueue<SpawnInfo> mSpawns = new PriorityQueue<SpawnInfo>();
    
    private class SpawnInfo implements  Comparable<SpawnInfo>{

        public SpawnInfo(int delay, GameEntity entity) {
            this.delay = delay;
            this.entity = entity;
        }
        @Override
        public int compareTo(SpawnInfo e) {
            return delay == e.delay ? 0
                    : delay < e.delay ? -1 : 1;
        }
        GameEntity entity;
        int delay;
    }
    
    
    public void update(int delta) {
        if(mNew) {
            World.GetInstance().toast(mDescription, 2000);
            mNew = false;
        }
        mElapsedTime += delta;
        while(!mSpawns.isEmpty() && mSpawns.peek().delay < mElapsedTime) {
            World.GetInstance().addEntity(mSpawns.remove().entity);
        }
    }
    public boolean isDone() { return mSpawns.isEmpty(); }
    
    public void addEnemy(int delay, GameEntity entity) {
        mSpawns.add(new SpawnInfo(delay, entity));
    }
}
