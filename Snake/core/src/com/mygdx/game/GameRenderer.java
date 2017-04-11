package com.mygdx.game;

import java.awt.Font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


import com.mygdx.game.objects.GameBlock;



public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batch;
	private BitmapFont redFont;
    private BitmapFont yellowFont;
    private BitmapFont blueFont;
    private BitmapFont greenFont;
    private BitmapFont bigYellowFont;
    private BitmapFont font;
	private BitmapFont font2;
	private GlyphLayout glyphLayout;
	private Font fonts;
	private FreeTypeFontGenerator gen;
	private FreeTypeFontParameter parameter;
	
	public GameRenderer(GameWorld world) {
		myWorld = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, myWorld.getScreenWidth(), myWorld.getScreenHeight());

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		gen = new FreeTypeFontGenerator(Gdx.files.internal("Consolas Bold.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 15;
		glyphLayout = new GlyphLayout();
        
		batch = new SpriteBatch();    
        redFont = new BitmapFont();
        redFont = gen.generateFont(parameter);
        redFont.setColor(1.0f, 0.0f, 0.0f, 1);
        yellowFont = new BitmapFont();
		yellowFont = gen.generateFont(parameter);
		yellowFont.setColor(Color.YELLOW);
		blueFont = new BitmapFont();
        blueFont = gen.generateFont(parameter);
        blueFont.setColor(0.2f, 0.2f, 1.0f, 1);
        greenFont = new BitmapFont();
        greenFont = gen.generateFont(parameter);
        greenFont.setColor(0.0f, 0.8f, 0.0f, 1);
        parameter.size = 50;
        bigYellowFont = new BitmapFont();
        bigYellowFont = gen.generateFont(parameter);
        bigYellowFont.setColor(0.2f, 0.2f, 1.0f, 1);
        gen.dispose();
	}

	public void render() 
	{
		// Gdx.app.log("GameRenderer", "render");

		// Rysowanie tla

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(myWorld.getStartWindow())
		{
			for (GameBlock logo : myWorld.getLogo()) 
			{
				shapeRenderer.begin(ShapeType.Filled);

				switch(logo.getType())
				{
					case EMPTY:
						shapeRenderer.setColor(0.6f, 1.0f, 0.0f, 1);
						break;
					case SNAKE_BODY:
						shapeRenderer.setColor(Color.MAGENTA.r, Color.MAGENTA.g, Color.MAGENTA.b, 1);
						break;
					case SNAKE_BACK:
						shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 1);
						break;
					case SNAKE_HEAD:
						shapeRenderer.setColor(Color.RED.r, Color.RED.g, Color.RED.b, 1);
						break;
					case FRUIT:
						shapeRenderer.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, 1);
						break;
					case SUPER_FRUIT:
						shapeRenderer.setColor(Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b, 1);
						break;
					default:
						break;
						
				}
			

				shapeRenderer.rect(logo.getRectangle().x * myWorld.getSizeMultiplier(), logo.getRectangle().y * myWorld.getSizeMultiplier(), 
							logo.getRectangle().width * myWorld.getSizeMultiplier(), logo.getRectangle().height * myWorld.getSizeMultiplier());
				shapeRenderer.end();
				
			}
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getStart().x * myWorld.getSizeMultiplier(), myWorld.getStart().y * myWorld.getSizeMultiplier(),
						myWorld.getStart().width * myWorld.getSizeMultiplier(), myWorld.getStart().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "NEW GAME");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getStart().x + myWorld.getStart().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getStart().y + myWorld.getStart().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getHighscore().x * myWorld.getSizeMultiplier(), myWorld.getHighscore().y * myWorld.getSizeMultiplier(),
						myWorld.getHighscore().width * myWorld.getSizeMultiplier(), myWorld.getHighscore().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, "HIGHSCORE");
			blueFont.draw(batch, glyphLayout, 
			(myWorld.getHighscore().x + myWorld.getHighscore().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getHighscore().y + myWorld.getHighscore().height /2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getInstructions().x * myWorld.getSizeMultiplier(), myWorld.getInstructions().y * myWorld.getSizeMultiplier(), 
						myWorld.getInstructions().width * myWorld.getSizeMultiplier(), myWorld.getInstructions().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "INSTRUCTIONS");
			greenFont.draw(batch, glyphLayout, 
			(myWorld.getInstructions().x + myWorld.getInstructions().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getInstructions().y + myWorld.getInstructions().height /2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getExit1().x * myWorld.getSizeMultiplier(), myWorld.getExit1().y * myWorld.getSizeMultiplier(), 
						myWorld.getExit1().width * myWorld.getSizeMultiplier(), myWorld.getExit1().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "EXIT");
			redFont.draw(batch, glyphLayout,
			(myWorld.getExit1().x + myWorld.getExit1().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getExit1().y + myWorld.getExit1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
		}
		else if(myWorld.getMapWindow())
		{	
			batch.begin();
			glyphLayout.setText(yellowFont, "WYBIERZ WIELKOSC PLANSZY:");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 2 - glyphLayout.width / 2,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 6 / 7);
			batch.end();

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getMap1().x * myWorld.getSizeMultiplier(), myWorld.getMap1().y * myWorld.getSizeMultiplier(), 
						myWorld.getMap1().width * myWorld.getSizeMultiplier(), myWorld.getMap1().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "SMALL");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getMap1().x + myWorld.getMap1().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getMap1().y + myWorld.getMap1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			
			glyphLayout.setText(yellowFont, "PLANSZA 11x18, 198 POL");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getMap1().x * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) + myWorld.getMap1().width * 5, 
			myWorld.getDesktopHeight() - (myWorld.getMap1().y + myWorld.getMap1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getMap2().x * myWorld.getSizeMultiplier(), myWorld.getMap2().y * myWorld.getSizeMultiplier(),
						myWorld.getMap2().width * myWorld.getSizeMultiplier(), myWorld.getMap2().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, "MEDIUM");
			blueFont.draw(batch, glyphLayout, 
			(myWorld.getMap2().x + myWorld.getMap2().width / 2) * (myWorld.getDesktopWidth()/myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getMap2().y + myWorld.getMap2().height / 2) * (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			
			glyphLayout.setText(yellowFont, "PLANSZA 15x24, 360 POL");
			yellowFont.draw(batch, glyphLayout,
			myWorld.getMap2().x * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) + myWorld.getMap2().width * 5, 
			myWorld.getDesktopHeight() - (myWorld.getMap2().y + myWorld.getMap2().height / 2) * (myWorld.getDesktopHeight()/myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getMap3().x * myWorld.getSizeMultiplier(), myWorld.getMap3().y * myWorld.getSizeMultiplier(), 
						myWorld.getMap3().width * myWorld.getSizeMultiplier(), myWorld.getMap3().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "LARGE");
			greenFont.draw(batch, glyphLayout,
			(myWorld.getMap3().x + myWorld.getMap3().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getMap3().y + myWorld.getMap3().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			
			glyphLayout.setText(yellowFont, "PLANSZA 23x36, 828 POL");
			yellowFont.draw(batch, glyphLayout,
			myWorld.getMap3().x * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) + myWorld.getMap3().width * 5, 
			myWorld.getDesktopHeight() - (myWorld.getMap3().y + myWorld.getMap3().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack3().x * myWorld.getSizeMultiplier(), myWorld.getBack3().y * myWorld.getSizeMultiplier(), 
						myWorld.getBack3().width * myWorld.getSizeMultiplier(), myWorld.getBack3().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout,
			(myWorld.getBack3().x + myWorld.getBack3().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack3().y + myWorld.getBack3().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
		
		}
		else if(myWorld.getDifficultyWindow())
		{
			batch.begin();
			glyphLayout.setText(yellowFont, "WYBIERZ POZIOM TRUDNOSCI:");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 2 - glyphLayout.width / 2,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 6 / 7); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1); 
			shapeRenderer.rect(myWorld.getDifficulty1().x * myWorld.getSizeMultiplier(), myWorld.getDifficulty1().y * myWorld.getSizeMultiplier(), 
						myWorld.getDifficulty1().width * myWorld.getSizeMultiplier(), myWorld.getDifficulty1().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "EASY");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getDifficulty1().x + myWorld.getDifficulty1().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getDifficulty1().y + myWorld.getDifficulty1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			
			glyphLayout.setText(yellowFont, "WAZ PORUSZA SIE Z SZYBKOSCIA OKOLO 2 KRATEK NA SEKUNDE");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getDifficulty1().x * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) + myWorld.getDifficulty1().width * 5, 
			myWorld.getDesktopHeight() - (myWorld.getDifficulty1().y + myWorld.getDifficulty1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getDifficulty2().x * myWorld.getSizeMultiplier(), myWorld.getDifficulty2().y * myWorld.getSizeMultiplier(),
						myWorld.getDifficulty2().width * myWorld.getSizeMultiplier(), myWorld.getDifficulty2().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, "MEDIUM");
			blueFont.draw(batch, glyphLayout,
			(myWorld.getDifficulty2().x + myWorld.getDifficulty2().width / 2) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getDifficulty2().y + myWorld.getDifficulty2().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			
			glyphLayout.setText(yellowFont, "WAZ PORUSZA SIE Z SZYBKOSCIA OKOLO 4 KRATEK NA SEKUNDE");
			yellowFont.draw(batch, glyphLayout,
			myWorld.getDifficulty2().x * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) + myWorld.getDifficulty2().width * 5, 
			myWorld.getDesktopHeight() - (myWorld.getDifficulty2().y + myWorld.getDifficulty2().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getDifficulty3().x * myWorld.getSizeMultiplier(), myWorld.getDifficulty3().y * myWorld.getSizeMultiplier(), 
						myWorld.getDifficulty3().width * myWorld.getSizeMultiplier(), myWorld.getDifficulty3().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "HARD");
			greenFont.draw(batch, glyphLayout,
			(myWorld.getDifficulty3().x + myWorld.getDifficulty3().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getDifficulty3().y + myWorld.getDifficulty3().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			
			glyphLayout.setText(yellowFont, "WAZ PORUSZA SIE Z SZYBKOSCIA OKOLO 6 KRATEK NA SEKUNDE");
			yellowFont.draw(batch, glyphLayout, 
			myWorld.getDifficulty3().x * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) + myWorld.getDifficulty3().width * 5, 
			myWorld.getDesktopHeight() - (myWorld.getDifficulty3().y + myWorld.getDifficulty3().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack2().x * myWorld.getSizeMultiplier(), myWorld.getBack2().y * myWorld.getSizeMultiplier(),
						myWorld.getBack2().width * myWorld.getSizeMultiplier(), myWorld.getBack2().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack2().x + myWorld.getBack2().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack2().y + myWorld.getBack2().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
		}
		else if(myWorld.getNameWindow())
		{
			if(myWorld.getEmptyName() == false)
			{
				batch.begin();
				glyphLayout.setText(yellowFont, "WPROWADZ SWOJE IMIE:");
				yellowFont.draw(batch, glyphLayout,
				(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
				myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height + 60);
				batch.end();
			}
			else if(myWorld.isNameEmpty() == false || myWorld.getEmptyName())
			{
				batch.begin();
				glyphLayout.setText(yellowFont, "WPROWADZ SWOJE IMIE KONIECZNIE:");
				yellowFont.draw(batch, glyphLayout,
				(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
				myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height + 60);
				batch.end();
			}
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getTextField().x * myWorld.getSizeMultiplier(), myWorld.getTextField().y * myWorld.getSizeMultiplier(),
						myWorld.getTextField().width * myWorld.getSizeMultiplier(), myWorld.getTextField().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, String.valueOf(myWorld.getName()));
			blueFont.draw(batch, glyphLayout,
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBegin().x * myWorld.getSizeMultiplier(), myWorld.getBegin().y * myWorld.getSizeMultiplier(), 
						myWorld.getBegin().width * myWorld.getSizeMultiplier(), myWorld.getBegin().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "PLAY!");
			greenFont.draw(batch, glyphLayout, 
			(myWorld.getBegin().x + myWorld.getBegin().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBegin().y + myWorld.getBegin().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack5().x * myWorld.getSizeMultiplier(), myWorld.getBack5().y * myWorld.getSizeMultiplier(),
						myWorld.getBack5().width * myWorld.getSizeMultiplier(), myWorld.getBack5().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack5().x + myWorld.getBack5().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack5().y + myWorld.getBack5().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);
			batch.end();
		}
		else if(myWorld.getScoreWindow())
		{						
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getSubmit().x * myWorld.getSizeMultiplier(), myWorld.getSubmit().y * myWorld.getSizeMultiplier(), 
						myWorld.getSubmit().width * myWorld.getSizeMultiplier(), myWorld.getSubmit().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "CONTINUE");
			greenFont.draw(batch, glyphLayout,
			(myWorld.getSubmit().x + myWorld.getSubmit().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getSubmit().y + myWorld.getSubmit().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			if(myWorld.isNewHighscore())
			{
				batch.begin();
				glyphLayout.setText(blueFont, "!!!NEW HIGHSCORE!!!");
				blueFont.draw(batch, glyphLayout, 
				(myWorld.getSubmit().x + myWorld.getSubmit().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
				myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) + myWorld.getTextField().height * 13); 
				batch.end();
			}
			
			batch.begin();
			glyphLayout.setText(yellowFont, "Difficulty: " + String.valueOf(myWorld.getCurrentDifficulty()));
			yellowFont.draw(batch, "Difficulty: " + String.valueOf(myWorld.getCurrentDifficulty()),
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height*11); 
			
			glyphLayout.setText(yellowFont, "Map size: " + String.valueOf(myWorld.getGameSize()));
			yellowFont.draw(batch, "Map size: " + String.valueOf(myWorld.getGameSize()), 
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height*9);
			
			glyphLayout.setText(yellowFont, "Points: " + String.valueOf(myWorld.getPoints()));
			yellowFont.draw(batch, "Points: " + String.valueOf(myWorld.getPoints()),
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height*7);
			
			glyphLayout.setText(yellowFont, "Fruits collected: " + String.valueOf(myWorld.getFruitCollected()));
			yellowFont.draw(batch, "Fruits collected: " + String.valueOf(myWorld.getFruitCollected()),
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height*5); 
			
			glyphLayout.setText(yellowFont, "Super fruits collected: " + String.valueOf(myWorld.getSuperFruitCollected()));
			yellowFont.draw(batch, "Super fruits collected: " + String.valueOf(myWorld.getSuperFruitCollected()), 
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight()-(myWorld.getTextField().y*(myWorld.getDesktopHeight()/myWorld.getScreenHeight()))+myWorld.getTextField().height*3); 
			
			glyphLayout.setText(yellowFont, "Good job " + String.valueOf(myWorld.goodJob()));
			yellowFont.draw(batch, glyphLayout,
			(myWorld.getTextField().x + myWorld.getTextField().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getTextField().y * (myWorld.getDesktopHeight() / myWorld.getScreenHeight())) - myWorld.getTextField().height);
			batch.end();
			
		}
		else if(myWorld.getExitWindow())
		{
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getPlayAgain().x * myWorld.getSizeMultiplier(), myWorld.getPlayAgain().y * myWorld.getSizeMultiplier(),
						myWorld.getPlayAgain().width * myWorld.getSizeMultiplier(), myWorld.getPlayAgain().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(greenFont, "PLAY AGAIN");
			greenFont.draw(batch, glyphLayout, 
			(myWorld.getPlayAgain().x + myWorld.getPlayAgain().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getPlayAgain().y + myWorld.getPlayAgain().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2); 
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getExit2().x * myWorld.getSizeMultiplier(), myWorld.getExit2().y * myWorld.getSizeMultiplier(),
						myWorld.getExit2().width * myWorld.getSizeMultiplier(), myWorld.getExit2().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "EXIT");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getExit2().x + myWorld.getExit2().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getExit2().y + myWorld.getExit2().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);  
			batch.end();
			
		}
		else if(myWorld.getInstructionsWindow())
		{
			batch.begin();
			yellowFont.draw(batch, "UZYJ STRZALEK DO ZMIANY KIERUNKU PORUSZANIA SIE WEZA.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 6 / 7);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "WCISNIJ 'P' W TAKCIE GRY ABY WLACZYC PAUSE. \nWCISNIJ 'P' PONOWNIE ABY WZNOWIC GRE.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 5 / 7);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "ZBIERAJ CZERWONE OWOCE ABY ZDOBYC PUNKTY. \nZEBRANIE POMARANCZOWYCH OWOCOW DA CI BONUSOWE PUNKTY.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 7);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "JESLI WAZ ZDERZY SIE ZE SCIANA LUB ZE SWOIM OGONEM, GRA SIE KONCZY.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 7);
			batch.end();
			
			batch.begin();
			yellowFont.draw(batch, "ZYCZE MILEJ ZABAWY.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 8,
			myWorld.getScreenHeight() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 2 / 7);
			batch.end();
			
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack4().x * myWorld.getSizeMultiplier(), myWorld.getBack4().y * myWorld.getSizeMultiplier(),
						myWorld.getBack4().width * myWorld.getSizeMultiplier(), myWorld.getBack4().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack4().x + myWorld.getBack4().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack4().y + myWorld.getBack4().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);  
			batch.end();
		}
		else if(myWorld.getHighscoreWindow())
		{
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
			shapeRenderer.rect(myWorld.getBack1().x * myWorld.getSizeMultiplier(), myWorld.getBack1().y * myWorld.getSizeMultiplier(),
						myWorld.getBack1().width * myWorld.getSizeMultiplier(), myWorld.getBack1().height * myWorld.getSizeMultiplier());
			shapeRenderer.end();
			
			batch.begin();
			glyphLayout.setText(redFont, "BACK");
			redFont.draw(batch, glyphLayout, 
			(myWorld.getBack1().x + myWorld.getBack1().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - (myWorld.getBack1().y + myWorld.getBack1().height / 2) * (myWorld.getDesktopHeight() / myWorld.getScreenHeight()) + glyphLayout.height / 2);  
			batch.end();
			
			batch.begin();
			glyphLayout.setText(blueFont, "HIGHSCORES");
			blueFont.draw(batch, glyphLayout,
			(myWorld.getBack1().x + myWorld.getBack1().width / 2 ) * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) - glyphLayout.width / 2, 
			myWorld.getDesktopHeight() - myWorld.getBack1().height);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "No.", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 6,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "POINTS", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 2 / 6,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "NAME", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 3 / 7,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "MAP SIZE", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 4 / 6,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			batch.begin();
			blueFont.draw(batch, "DIFFICULTY", 
			myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 5 / 6,
			myWorld.getDesktopHeight()-30);
			batch.end();
			
			for(int i  = 0; i < myWorld.getHighscores().length; i++)
			{	
				batch.begin();
				glyphLayout.setText(yellowFont, myWorld.showHighscore(i));
				yellowFont.draw(batch, glyphLayout, 
				myWorld.getScreenWidth() * (myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * 1 / 6,
				myWorld.getDesktopHeight() - 30 * (i + 2));
				batch.end();
			}
		}
		else if(myWorld.getGameWindow())
		{
	
			for (GameBlock block : myWorld.getBlocks()) 
			{
				shapeRenderer.begin(ShapeType.Filled);

				switch(block.getType())
				{
					case EMPTY:
						shapeRenderer.setColor(1.0f, 1.0f, 0.8f, 1);
						break;
					case SNAKE_BODY:
						shapeRenderer.setColor(0.73f, 0.73f, 0.73f, 1);
						break;
					case SNAKE_BACK:
						shapeRenderer.setColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 1);
						break;
					case SNAKE_HEAD:
						shapeRenderer.setColor(0.6f, 1.0f, 0.0f, 1);
						break;
					case FRUIT:
						shapeRenderer.setColor(Color.RED.r, Color.RED.g, Color.RED.b, 1);//shapeRenderer.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, 1);
						break;
					case SUPER_FRUIT:
						shapeRenderer.setColor(Color.ORANGE.r, Color.ORANGE.g, Color.ORANGE.b, 1);
						break;
					default:
						break;						
				}
				
				shapeRenderer.rect(block.getRectangle().x * myWorld.getSizeMultiplier(), block.getRectangle().y * myWorld.getSizeMultiplier(), 
							block.getRectangle().width * myWorld.getSizeMultiplier(), block.getRectangle().height * myWorld.getSizeMultiplier());

				shapeRenderer.end();
				
				if(myWorld.getPlay() == false)
				{
					batch.begin();
					glyphLayout.setText(bigYellowFont, String.valueOf(myWorld.getDelayCounter()));
					bigYellowFont.draw(batch, glyphLayout, 
					(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
					(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
					batch.end();
				}
			}
			
			batch.begin();
			
			redFont.draw(batch, myWorld.getGameText(), 10, 17);
			redFont.draw(batch, "Points: " + String.valueOf(myWorld.getPoints()), 150, 17);
			redFont.draw(batch, "Difficulty: " + String.valueOf(myWorld.getCurrentDifficulty()), 300, 17);
			redFont.draw(batch, "Map: " + String.valueOf(myWorld.getMapSize()), 450, 17);
			redFont.draw(batch, "Gra: " + String.valueOf(myWorld.getName()), 600, 17);
			
			if(myWorld.getWinner())
			{
				glyphLayout.setText(bigYellowFont, myWorld.getWinText());
				bigYellowFont.draw(batch, glyphLayout,
				(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
				(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
			}
			else if(myWorld.getEnd())
			{
				glyphLayout.setText(bigYellowFont, myWorld.getLoseText());
				bigYellowFont.draw(batch, glyphLayout, 
				(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
				(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
			}
			else if(myWorld.getPause())
			{
				glyphLayout.setText(bigYellowFont, "GAME PAUSED. \nPRESS 'P' TO CONTINUE");
				bigYellowFont.draw(batch, glyphLayout, 
				(myWorld.getDesktopWidth() / myWorld.getScreenWidth()) * (myWorld.getScreenWidth() / 2) - glyphLayout.width / 2,
				(myWorld.getDesktopHeight() / myWorld.getScreenHeight()) * (myWorld.getScreenHeight() / 2 + glyphLayout.height / 2));
			}
			batch.end();
		}	
	}
}
