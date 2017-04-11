package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.GameRenderer;
import com.mygdx.game.GameWorld;
import com.mygdx.game.input.InputHandler;

public class GameScreen implements Screen {
    
	private GameWorld world;
    private GameRenderer renderer;
    private int desktopWidth;
    private int desktopHeight;
    private float screenWidth = 180; //small=180, medium=240, large=360//640//690//720
    private float screenHeight = 120; //small=120, medium=160, large=240//480
    
    public GameScreen(int desktopWidth, int desktopHeight) {
    	
    	this.desktopHeight = desktopHeight;
        this.desktopWidth = desktopWidth;
        
        world = new GameWorld(screenWidth, screenHeight, desktopWidth ,desktopHeight);
        renderer = new GameRenderer(world);
        
        Gdx.input.setInputProcessor(new InputHandler(world));
    }

    @Override
    public void render(float delta) {
    	 world.update(delta);
         renderer.render();
         if(world.isRestart())
         {
        	 world = new GameWorld(screenWidth, screenHeight, desktopWidth ,desktopHeight);
        	 renderer = new GameRenderer(world);
             Gdx.input.setInputProcessor(new InputHandler(world));
         }
        
      // Gdx.app.log("GameScreen FPS", (1/delta) + "");
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");     
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
    	
    }

}