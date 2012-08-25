package xxx.rtin.ma.games;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import xxx.rtin.ma.games.ships.CircleShip;
import xxx.rtin.ma.games.ships.MotherShip;
import xxx.rtin.ma.games.ships.SquareShip;
import xxx.rtin.ma.games.ships.TriangleShip;
import xxx.rtin.ma.games.weapons.Blaster;
import xxx.rtin.ma.games.weapons.MissileLauncher;

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
	    
	    mWorld.setPlayer(new PlayerEntity(mWorld, new TriangleShip(), 400, 300, 0));
	    mWorld.addEntity(mWorld.getPlayer());
	    GameEntity t = new GameEntity(mWorld, new TriangleShip(), 200, 200, 45);
	    mWorld.getPlayer().setTarget(t);
	    mWorld.addEntity(t);
	    
	    mWorld.addEntity(new GameEntity(mWorld, new SquareShip(), 300, 300, 45));
	    mWorld.addEntity(new GameEntity(mWorld, new CircleShip(), 400, 400, 45));
	    GameEntity mothership = new GameEntity(mWorld, new MotherShip(), 400, 100, 45);
	    mothership.setRadius(100);
	    mWorld.addEntity(mothership);
	    
	    
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
	    if(!SoundCache.SONG1.playing()) {
	        SoundCache.SONG1.play();
	    }
	    
	    //TODO Move to World.
	    Input input = gc.getInput();
	    PlayerEntity player = mWorld.getPlayer();
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
        //TODO not new each time.
        //TODO wpn stats
        if(input.isKeyPressed(Input.KEY_1)) {
            player.setWeapon(new MissileLauncher(mWorld, 1000));
        } else if(input.isKeyPressed(Input.KEY_2)){
            player.setWeapon(new Blaster(mWorld, 500));
        }
        

	    
		mWorld.update(delta);
	}
	
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new GameMain("LD24 - S'lar!"));
		//app.setVSync(true);
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}
