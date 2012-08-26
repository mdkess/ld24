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
import xxx.rtin.ma.games.weapons.BlasterBattery;

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
	    

        mWorld.setPlayer(new PlayerEntity(mWorld, new TriangleShip(1.0f), mWorld.getWidth()/2, mWorld.getHeight() - 1250, 270));
        mWorld.getPlayer().setTeam(1);
        mWorld.addEntity(mWorld.getPlayer());
        mWorld.getPlayer().selectWeapon(0);

        GameEntity mothership = new GameEntity(mWorld, new MotherShip(), mWorld.getWidth()/2, mWorld.getHeight() - 1000, 0);
        mothership.setRadius(100);
        mothership.setTeam(1);
        mWorld.addEntity(mothership);
        mWorld.setStation(mothership);
        
        RocketBattery battery = new RocketBattery("Rocket Salvo", mWorld, 1000, 30);
        battery.addLauncher(0, -1, 270);
        battery.addLauncher(0,  1, 90);
        battery.addLauncher(-1, 0, 180);
        battery.addLauncher( 1, 0, 0);
        
        mothership.setShieldRegen(10);
        mothership.setHealthRegen(10);
        mothership.setMaxHealth(2000);
        mothership.setMaxShield(2000);
        mothership.setWeapon(battery);
        mothership.setController(new StationAIController(mothership));

	    
	    /*
	    Blaster singleBlaster = new Blaster(mWorld, 500, 20);
	    BlasterBattery doubleBlaster = new BlasterBattery("Double Blaster", mWorld, 750, 20);
	    doubleBlaster.addLauncher(0, -1, 0);
	    doubleBlaster.addLauncher(0, 1, 0);
	    
	    BlasterBattery tripleBlaster = new BlasterBattery("Blaster Battery", mWorld, 1000, 20);
	    tripleBlaster.addLauncher(0, -1, -10);
	    tripleBlaster.addLauncher(0, 0, 0);
	    tripleBlaster.addLauncher(0, 1, 10);

	    mWorld.setPlayer(new PlayerEntity(mWorld, new TriangleShip(1.0f), mWorld.getWidth()/2, mWorld.getHeight() - 1250, 270));
	    mWorld.getPlayer().setTeam(1);
        mWorld.addEntity(mWorld.getPlayer());

        mWorld.getPlayer().giveWeapon(singleBlaster);
        mWorld.getPlayer().giveWeapon(doubleBlaster);
        mWorld.getPlayer().giveWeapon(tripleBlaster);

        MissileLauncher singleRocket = new MissileLauncher(mWorld, 750, 40);
        
        RocketBattery doubleRocket = new RocketBattery("Double Rocket", mWorld, 1100, 40);
        doubleRocket.addLauncher(0, -1, 0);
        doubleRocket.addLauncher(0, 1, 0);
        
        
        RocketBattery tripleRocket = new RocketBattery("Triple Rocket", mWorld, 1500, 40);
        tripleRocket.addLauncher(0, -1, -90);
        tripleRocket.addLauncher(0, 0, 0);
        tripleRocket.addLauncher(0, 1, 90);
        
        mWorld.getPlayer().giveWeapon(singleRocket);
        mWorld.getPlayer().giveWeapon(doubleRocket);
        mWorld.getPlayer().giveWeapon(tripleRocket);
        
        */
        //The player must defend the mothership


        
        playIntro();
        
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
	    mWorld.toast("[kinetic dream]", 4000, false, SoundCache.INTRO);
	    mWorld.toast("", 1000);
	    mWorld.toast("Move: Arrow keys/WASD. Shoot: Space. Weapons: 1-9 Target: E Help: H Pause: P", 4000);
	    mWorld.toast("", 1000);
	    mWorld.toast("-- defend the bioforge --", 5000);
	}

	private void createWaves() {
	    Wave[] waves = new Wave[] {
	        new Wave("1. steps", 20000),
	        new Wave("2. knowledge", 0),
	        new Wave("3. evolution", 0),
	        new Wave("4. thought", 0),
	        new Wave("5. cycle", 0),
	        new Wave("6. destruction", 0),
	    };
	    
	    int[] numShips = new int[] {
	        10, 10, 10, 15, 15, 15
	    };
	    
	    int[] blasterLevel = new int[] {
	        0, 1, 1, 2, 2, 2
	    };
	    
	    int[] rocketLevel = new int[] {
	            -1, -1, 1, 1, 2, 2
        };
	    int[] saucerLevel = new int[] {
	            -1, -1, -1, 1, 1, 2
	    };
	    
	    float[] healthFactor = new float[] {
            1.0f, 1.25f, 1.5f, 1.75f, 2.0f, 2.25f
	    };
	    
	    float[] damageFactor = new float[] {
	        1.0f, 1.1f, 1.2f, 1.3f, 1.5f, 1.8f    
	    };
	    
	    float[] speedFactor = new float[] {
	        0.5f, 0.55f, 0.6f, 0.65f, 0.7f, 0.8f
	    };
	    
	    float[] coinFactor = new float[] {
	        1.0f, 1.5f, 2.0f, 2.5f, 3.0f, 3.5f
	    };
	    
	    //Generate the waves:
	    //Waves 1-3: Simple ship.
	    //Waves 4-6: Dual blaster.
	    for(int w =0; w < waves.length; ++w) {
	        //Add attack ships
	        
	        //Waves 1-3: Simple ship.
	        //Waves 4-6: Dual blaster.
	        if(blasterLevel[w] >= 0) {
    	        for(int i=0; i< numShips[w]; ++i) {
                    GameEntity e = new GameEntity(mWorld, new TriangleShip(speedFactor[w]), mWorld.getWidth()/2 - 100*i, -500, 0);
                    e.setMaxHealth(5 * healthFactor[w]);
                    e.setMaxShield(5 * healthFactor[w]);
                    e.setHealthRegen(1 * healthFactor[w]);
                    e.setShieldRegen(1 * healthFactor[w]);
                    e.setDamageMultiplier(damageFactor[w]);
                    if(blasterLevel[w] == 1) {
                        e.setWeapon(new Blaster(mWorld, 2000, 5));
                    } else if(blasterLevel[w] == 2) {
                        BlasterBattery battery =
                                new BlasterBattery("Dual Blaster", mWorld, 3000, 5);
                        battery.addLauncher(0, -1, 0);
                        battery.addLauncher(0,  1, 0);
                        e.setWeapon(battery);
                    }
                    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount((int)Math.ceil(3 * coinFactor[w]));
                    waves[w].addEnemy(0, e);
    	        }
	        }

	        if(rocketLevel[w] >= 0) {
                for(int i=0; i< numShips[w]; ++i) {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(speedFactor[w]),  mWorld.getWidth()/2 - 100*i, -400, 0);
                    e.setMaxHealth(10 * healthFactor[w]);
                    e.setMaxShield(5 * healthFactor[w]);
                    e.setHealthRegen(1 * healthFactor[w]);
                    e.setShieldRegen(1 * healthFactor[w]);
                    e.setDamageMultiplier(damageFactor[w]);
                    if(rocketLevel[w] == 1) {
                        e.setWeapon(new MissileLauncher(mWorld, 5000, 10));
                    } else if(rocketLevel[w] == 2) {
                        RocketBattery battery = new RocketBattery("Rockets", mWorld, 5000, 5);
                        battery.addLauncher(0, -1, 45);
                        battery.addLauncher(0,  0, -45);
                        e.setWeapon(battery);
                    }
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount((int)Math.ceil(5 * coinFactor[w]));
                    waves[w].addEnemy(0, e);
                }
	        }
	           //first wave is just some guys with no enemies
            if(saucerLevel[w] >= 0) {
                for(int i=0; i< numShips[w]; ++i) {
                    GameEntity e = new GameEntity(mWorld, new CircleShip(speedFactor[w]), mWorld.getWidth()/2 - 100*i, -300, 0);
                    e.setMaxHealth(5 * healthFactor[w]);
                    e.setMaxShield(20 * healthFactor[w]);
                    e.setHealthRegen(1);
                    e.setShieldRegen(10);
                    e.setDamageMultiplier(damageFactor[w]);
                    if(saucerLevel[w] == 1) {
                        BlasterBattery battery = new BlasterBattery("Triple Blaster", mWorld, 1500, 5);
                        battery.addLauncher(0, -1, 0);
                        battery.addLauncher(0,  1, 0);                        
                        
                        e.setWeapon(battery);
                    } else {
                        BlasterBattery battery = new BlasterBattery("Triple Blaster", mWorld, 2500, 5);
                        battery.addLauncher(0, -1, 0);
                        battery.addLauncher(1,  0, 0);
                        battery.addLauncher(0,  1, 0);
                        e.setWeapon(battery);
                    }
                    
                    e.setCoinCount((int)Math.ceil(10*coinFactor[w]));                
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    waves[w].addEnemy(0, e);
                }
            }
	        
	        mWorld.addWave(waves[w]);
	    }
	    
        GameEntity mothership = new GameEntity(mWorld, new MotherShip(), mWorld.getWidth()/2, 400, 0);
        mothership.setRadius(100);
        
        RocketBattery battery = new RocketBattery("Rocket Salvo", mWorld, 1000, 20);
        battery.addLauncher(0, -1, 270);
        battery.addLauncher(0,  1, 90);
        battery.addLauncher(-1, 0, 180);
        battery.addLauncher( 1, 0, 0);
        
        mothership.setDamageMultiplier(damageFactor[5]);
        mothership.setMaxHealth(2500);
        mothership.setMaxShield(2500);
        mothership.setWeapon(battery);
        mothership.setController(new StationAIController(mothership));
        waves[5].addEnemy(0, mothership);
	    
	    
	    /*
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
    	        e.setCoinCount(3);
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
                e.setCoinCount(3);
                w2.addEnemy(0, e);
            }
            mWorld.addWave(w2);
	    }
	    {
            Wave w3 = new Wave("(3) they evolved", 5000);
            for(int i=0; i < 5; ++i) {
                {
                    GameEntity e = new GameEntity(mWorld, new TriangleShip(0.5f), 100*i, 0, 0);
                    e.setMaxHealth(20);
                    e.setMaxShield(20);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new Blaster(mWorld, 2000, 5));
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount(3);
                    
                    w3.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 100*i, 100, 0);
                    e.setMaxHealth(30);
                    e.setMaxShield(10);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new MissileLauncher(mWorld, 2000, 10));
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount(5);
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
                    e.setMaxHealth(30);
                    e.setMaxShield(30);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    
                    BlasterBattery battery = new BlasterBattery("Dual Blaster", mWorld, 1000, 10);
                    battery.addLauncher(0, -1, 0);
                    battery.addLauncher(0,  1, 0);
                    
                    e.setWeapon(battery);
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount(5);
                    
                    w4.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 300, 300+300*i, 0);
                    e.setMaxHealth(60);
                    e.setMaxShield(20);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    e.setWeapon(new MissileLauncher(mWorld, 2000, 10));
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount(7);
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
                    e.setMaxHealth(30);
                    e.setMaxShield(30);
                    e.setHealthRegen(5);
                    e.setShieldRegen(5);
                    BlasterBattery battery = new BlasterBattery("Dual Blaster", mWorld, 1000, 10);
                    battery.addLauncher(0, -1, 0);
                    battery.addLauncher(0,  1, 0);
                    
                    e.setWeapon(battery);    
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    e.setCoinCount(5);
                    w5.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new SquareShip(0.5f), 300, 300+300*i, 0);
                    e.setMaxHealth(80);
                    e.setMaxShield(20);
                    e.setHealthRegen(1);
                    e.setShieldRegen(1);
                    RocketBattery battery = new RocketBattery("Rockets", mWorld, 1000, 5);
                    battery.addLauncher(0, -1, 45);
                    battery.addLauncher(0,  0, -45);
                    e.setWeapon(battery);
                    e.setCoinCount(7);
                    e.setController(new AttackAIController(e));
                    e.setTarget(mWorld.getPlayer());
                    w5.addEnemy(0, e);
                }
                {
                    GameEntity e = new GameEntity(mWorld, new CircleShip(0.8f), 300, 300+300*i, 0);
                    e.setMaxHealth(20);
                    e.setMaxShield(80);
                    e.setHealthRegen(1);
                    e.setShieldRegen(10);
                    BlasterBattery battery = new BlasterBattery("Triple Blaster", mWorld, 1000, 5);
                    battery.addLauncher(0, -1, 0);
                    battery.addLauncher(1,  0, 0);
                    battery.addLauncher(0,  1, 0);
                    
                    e.setWeapon(battery);                    e.setCoinCount(10);
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
        */
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {

	    
	    //TODO Move to World.
	    Input input = gc.getInput();
	    PlayerEntity player = mWorld.getPlayer();
	    if(!mWorld.isPaused() && !mWorld.isGameOver()) {
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
            if(input.isKeyPressed(Input.KEY_K)){
                //player.damage(1000);
                //player.damage(1000);
                mWorld.gameOver("you awake from the dream. congratulations, you have won", 3000);
            }
            if(input.isKeyPressed(Input.KEY_H)) {
                mWorld.toast("Move: Arrow keys/WASD. Shoot: Space. Target: E Help: H Pause: P", 1000);
            }
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
            } else if(input.isKeyPressed(Input.KEY_3)){
                if(player.getWeaponIndex() != 2) {
                    SoundCache.WEAPON_SELECT.play();
                    player.selectWeapon(2);
                }
            } else if(input.isKeyPressed(Input.KEY_4)){
                if(player.getWeaponIndex() != 3) {
                    SoundCache.WEAPON_SELECT.play();
                    player.selectWeapon(3);
                }
            }
            else if(input.isKeyPressed(Input.KEY_5)){
                if(player.getWeaponIndex() != 4) {
                    SoundCache.WEAPON_SELECT.play();
                    player.selectWeapon(4);
                }
            } else if(input.isKeyPressed(Input.KEY_6)){
                if(player.getWeaponIndex() != 5) {
                    SoundCache.WEAPON_SELECT.play();
                    player.selectWeapon(5);
                }
            }
	    }
	    if(input.isKeyPressed(Input.KEY_Q) || input.isKeyPressed(Input.KEY_ESCAPE)) {
	        gc.exit();
	    }
        if(input.isKeyPressed(Input.KEY_P)){
            if(!mWorld.isPaused()) {
                mWorld.pause("Dream paused. Press P to resume.");
            } else {
                mWorld.unpause();
            }
        }

        

	    
		mWorld.update(delta);
	}
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GameMain("[kinetic dream]"));
		//app.setVSync(true);
		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.start();
	}
}
