package xxx.rtin.ma.games;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import xxx.rtin.ma.games.ai.AttackAIController;
import xxx.rtin.ma.games.ai.StationAIController;
import xxx.rtin.ma.games.ships.CircleShip;
import xxx.rtin.ma.games.ships.MotherShip;
import xxx.rtin.ma.games.ships.SquareShip;
import xxx.rtin.ma.games.ships.TriangleShip;
import xxx.rtin.ma.games.weapons.Blaster;
import xxx.rtin.ma.games.weapons.MissileLauncher;
import xxx.rtin.ma.games.weapons.RocketBattery;
import xxx.rtin.ma.games.weapons.WeaponBattery;

public class GameMain extends BasicGame {

	private World mWorld;
    
	public GameMain(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		mWorld.render(g);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		mWorld = World.GetInstance();
		Graphics g = gc.getGraphics();
	    g.setBackground(StaticConfig.BACKGROUND_COLOR);
	    g.setAntiAlias(true);
	    
	    WeaponBattery b = new WeaponBattery("Blaster Battery", mWorld, 1000, 10);
	    b.addLauncher(0, -1, -10);
	    b.addLauncher(0, 0, 0);
	    b.addLauncher(0, 1, 10);

	    mWorld.setPlayer(new PlayerEntity(mWorld, new TriangleShip(1.0f), mWorld.getWidth()/2, mWorld.getHeight()/2, 270));
	    mWorld.getPlayer().setTeam(1);
        mWorld.addEntity(mWorld.getPlayer());
        //mWorld.getPlayer().giveWeapon(0, new Blaster(mWorld, 500));
        mWorld.getPlayer().giveWeapon(0, b);
        mWorld.getPlayer().giveWeapon(1, new MissileLauncher(mWorld, 1000, 20));
        mWorld.getPlayer().selectWeapon(0);
        
        //The player must defend the mothership

        GameEntity mothership = new GameEntity(mWorld, new MotherShip(), mWorld.getWidth()/2, mWorld.getHeight()/2 + 210, 0);
        mothership.setRadius(100);
        mothership.setTeam(1);
        mWorld.addEntity(mothership);
        mWorld.setStation(mothership);
        
        RocketBattery battery = new RocketBattery("Rocket Salvo", mWorld, 1000, 10);
        battery.addLauncher(0, -1, 270);
        battery.addLauncher(0,  1, 90);
        battery.addLauncher(-1, 0, 180);
        battery.addLauncher( 1, 0, 0);
        
        mothership.setWeapon(battery);
        mothership.setController(new StationAIController(mothership));

        
        //playIntro();
        
        createWaves();
        
	    /*
	    mWorld.setPlayer(new PlayerEntity(mWorld, new TriangleShip(), 400, 300, 0));
	    mWorld.addEntity(mWorld.getPlayer());
	    mWorld.getPlayer().giveWeapon(0, new Blaster(mWorld, 500));
	    mWorld.getPlayer().giveWeapon(1, new MissileLauncher(mWorld, 1000));
	    mWorld.getPlayer().selectWeapon(0);
	    GameEntity t = new GameEntity(mWorld, new TriangleShip(), 200, 200, 45);
	    mWorld.getPlayer().setTarget(t);
	    mWorld.addEntity(t);
	    //t.setController(new AttackAIController(t));
	    t.setWeapon(new Blaster(mWorld, 500));
	    
	    GameEntity s = new GameEntity(mWorld, new SquareShip(), 300, 300, 45);
	    mWorld.addEntity(s);
	    //s.setController(new AttackAIController(s));
	    s.setWeapon(new MissileLauncher(mWorld, 1000));
	    s.setTarget(mWorld.getPlayer());
	    
	    GameEntity c = new GameEntity(mWorld, new CircleShip(), 400, 400, 45);
	    mWorld.addEntity(c);
	    //c.setController(new AttackAIController(c));
        c.setWeapon(new Blaster(mWorld, 500));
        c.setTarget(mWorld.getPlayer());
        
        
	    GameEntity mothership = new GameEntity(mWorld, new MotherShip(), 400, 100, 45);
	    mothership.setRadius(100);
	    mWorld.addEntity(mothership);
	    */
	    
	}
	private void playIntro() {
	    mWorld.toast("", 1000);
	    mWorld.toast("Martin Kess Proudly Presents", 2000);
	    mWorld.toast("A Game For Ludum Dare 24", 2000);
	    mWorld.toast("", 1000);
	    mWorld.toast("[kinetic dream]", 4000, SoundCache.INTRO);
	    mWorld.toast("", 1000);
	    mWorld.toast("Move: Arrow keys/WASD. Shoot: Space. Weapons: 1-9 Target: E Help: H Pause: P", 4000);
	    mWorld.toast("", 1000);
	    mWorld.toast("-- defend the bioforge --", 5000);
	}

	private void createWaves() {
	    {
	        Wave w1 = new Wave("(1) promethean space", 0);
    	    for(int i=0; i < 10; ++i) {
    	        GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 100*i, 0, 0);
    	        e.setMaxHealth(5);
    	        e.setMaxShield(5);
    	        e.setHealthRegen(1);
    	        e.setShieldRegen(1);
    	        e.setController(new AttackAIController(e));
    	        e.setTarget(mWorld.getPlayer());
    	        
    	        w1.addEnemy(0, e);
    	    }
    	    mWorld.addWave(w1);
	    }
	    {
    	    Wave w2 = new Wave("(2) knowledge", 5000);
            for(int i=0; i < 10; ++i) {
                GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 100*i, 0, 0);
                e.setMaxHealth(5);
                e.setMaxShield(5);
                e.setHealthRegen(1);
                e.setShieldRegen(1);
                e.setWeapon(new Blaster(mWorld, 2000, 5));
                e.setController(new AttackAIController(e));
                e.setTarget(mWorld.getPlayer());
                
                w2.addEnemy(0, e);
            }
            mWorld.addWave(w2);
	    }
	    {
            Wave w3 = new Wave("(3) they evolved", 5000);
            for(int i=0; i < 5; ++i) {
                {
                    GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 100*i, 0, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    
                    w3.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 100*i, 100, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new MissileLauncher(mWorld, 2000, 10));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w3.addEnemy(0, e);
                }
            }
            mWorld.addWave(w3);
        }
        
	    {
            Wave w4 = new Wave("(4) and so did you", 5000);
            for(int i=0; i < 10; ++i) {
                {
                    GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 300*i, 0, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    
                    w4.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 300, 300+300*i, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new MissileLauncher(mWorld, 2000, 10));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w4.addEnemy(0, e);
                }
            }
            mWorld.addWave(w4);
	    }
        {
            Wave w5 = new Wave("(5) something different", 5000);
            for(int i=0; i < 10; ++i) {
                {
                    GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 300*i, 0, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    
                    w5.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 300, 300+300*i, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new MissileLauncher(mWorld, 2000, 10));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w5.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new CircleShip(0.8f), 300, 300+300*i, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w5.addEnemy(0, e);
                }
            }
            mWorld.addWave(w5);
        }
        {
            Wave w6 = new Wave("(6) destroy the enemy station", 5000);
            
            GameEntity mothership = new GameEntity(mWorld, new MotherShip(), mWorld.getWidth()/2, 0, 0);
            mothership.setRadius(100);
            
            RocketBattery battery = new RocketBattery("Rocket Salvo", mWorld, 1000, 10);
            battery.addLauncher(0, -1, 270);
            battery.addLauncher(0,  1, 90);
            battery.addLauncher(-1, 0, 180);
            battery.addLauncher( 1, 0, 0);
            
            mothership.setWeapon(battery);
            mothership.setController(new StationAIController(mothership));
            w6.addEnemy(0, mothership);
            
            
            for(int i=0; i < 10; ++i) {
                {
                    GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 300*i, 0, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
    
                    //e.setController(new DefendAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    
                    w6.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 300, 300+300*i, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new MissileLauncher(mWorld, 2000, 10));
    
                    //e.setController(new DefendAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w6.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new CircleShip(0.8f), 300, 300+300*i, 0);
                    e.setMaxHealth(5);
                    e.setMaxShield(5);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w6.addEnemy(0, e);
                }        
            }
            mWorld.addWave(w6);
        }
        
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
	    if(!SoundCache.SONG1.playing()) {
	        SoundCache.SONG1.play();
	    }
	    
	    //TODO Move to World.
	    Input input = gc.getInput();
	    PlayerEntity player = mWorld.getPlayer();
	    if(!mWorld.isPaused()) {
    	    if(input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP)) {
    	        //thrust
    	        player.setThrust();
    	    }
    	    if(input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) {
                //rotate CCW
    	        player.rotateCCW(delta);
            }
            if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_X)) {
                //stop
                player.stop();
            }
            if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) {
                //rotate CW
                player.rotateCW(delta);
            }
            if(input.isKeyDown(Input.KEY_SPACE)) {
                player.fire();
            }
            if(input.isKeyPressed(Input.KEY_E)) {
                player.retarget();
            }
            if(input.isKeyPressed(Input.KEY_H)) {
                mWorld.toast("Move: Arrow keys/WASD. Shoot: Space. Target: E Help: H Pause: P", 1000);
            }
	    }
        if(input.isKeyPressed(Input.KEY_P)){
            if(!mWorld.isPaused()) {
                mWorld.pause("Dream paused. Press P to resume.");
            } else {
                mWorld.unpause();
            }
        }
        //TODO not new each time.
        //TODO wpn stats
        if(input.isKeyPressed(Input.KEY_1)) {
            if(player.getWeaponIndex() != 0) {
                SoundCache.WEAPON_SELECT.play();
                player.selectWeapon(0);
            }
        } else if(input.isKeyPressed(Input.KEY_2)){
            if(player.getWeaponIndex() != 1) {
                SoundCache.WEAPON_SELECT.play();
                player.selectWeapon(1);
            }
        }
        

	    
		mWorld.update(delta);
	}
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GameMain("[kinetic dream]"));
		//app.setVSync(true);
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}
