package com.mygdx.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.access.InputFile;
import com.mygdx.game.objects.BlockType;
import com.mygdx.game.objects.GameBlock;
import com.mygdx.game.objects.SnakeDirection;
import com.mygdx.game.options.GameDifficulty;
import com.mygdx.game.options.MapSize;
import com.mygdx.game.sounds.Jukebox;

public class GameWorld 
{
	private float screenWidth;
	private float screenHeight;
	private int desktopWidth;
	private int desktopHeight;
	
	private List<GameBlock> blocks;
	private List<GameBlock> logo;
	private int[] logoBlocks;
	
	private Rectangle start;
	private Rectangle map1;
	private Rectangle map2;
	private Rectangle map3;
	private Rectangle difficulty1;
	private Rectangle difficulty2;
	private Rectangle difficulty3;
	private Rectangle textField;
	private Rectangle submit;
	private Rectangle exit1;
	private Rectangle exit2;
	private Rectangle playAgain;
	private Rectangle back1;
	private Rectangle back2;
	private Rectangle back3;
	private Rectangle back4;
	private Rectangle back5;
	private Rectangle instructions;
	private Rectangle highscore;
	private Rectangle begin;
	
	private int blockColumns; //ile kolumn ma plansza
	private int blockRows; //ile rzedow ma plansza
	private int blockSize; //dlugosc bloku
	private int blockSpace; //odstep miedzy blokami
	private int logoBlockSize;
	private int logoBlockColumns;
	
	private String gameText = new String("Innotech Labs");
	private String winText = new String("YOU WIN");
	private String loseText = new String("YOU LOSE");
	private String clickToPlay = new String("CLICK TO PLAY");
	
	private SnakeDirection lastDirection; //zmienna przechowujaca jaki byl kierunek weza podczas ostatniego wybrania kierunku
	private SnakeDirection currentDirection; //zmienna do przechowywania kierunku pobranego z klawiatury, ktora nie ma bezposredniego wplywu na zmiane kierunku
	private SnakeDirection direction; //przechowuje jaki jest faktyczny kierunek weza
	private boolean snakeMoved; //przechowuje stan czy snake juz sie ruszyl czy jeszcze nie
	
	private GameDifficulty currentDifficulty;
	private float timer; //okresla ile razy odswierzy sie gra zanim poruszy sie snake. Okresla tez poziom trudnosci. Im mniejsza wartosc tym szybciej sie waz porusza
	
	private MapSize mapSize;
	private MapSize	gameSize; //zmienna do zapisywania wielkosci mapy do highscore
	private float sizeMultiplier; //mnoznik przez ktory trzeba przemnozyc dlugosci wszystkich obiektow aby wygladaly na ekranie tak samo pomimo zmiany wielkosci planszy 
	
	private boolean end; //okresla czy gra sie skonczyla
	private boolean play; //okresla czy zostal wcisniety przycisk myszy aby rozpoczac poruszanie sie snake'a
	private boolean startWindow; //okresla czy wyswietlac okno startowe
	private boolean gameWindow; //okresla czy wyswietlac okno gry
	private boolean difficultyWindow; //okresla czy wyswietlac okno wyboru trudnosci
	private boolean mapWindow; //okresla czy wyswietlac okno wyboru wielkosci mapy
	private boolean scoreWindow; //okresla czy wyswietlac okno wynikow
	private boolean exitWindow; //okresla czy wyswietlac okno wyjscia/ponownej rozgrywki
	private boolean highscoreWindow; //okresla czy wyswietlic okno najlepszych wynikow
	private boolean instructionsWindow; //okresla czy wyswietlic okno instrukcji
	private boolean nameWindow; //okresla czy wyswietlic okno z wprwadzeniem imienia
	private boolean restart; //okresla czy wcisnelismy Play Again
	private boolean empty; //stan okreslajacy czy na planszy istnieja puste pola pozwalajacy okreslic czy gracz wygral
	private boolean winner; //stan bezposrednio zwiazany z empty. Jesli wszystkie pola gry sa zajete czyli empty jest false to nastepuje koniec gry
	private boolean pause; //okresla czy jest wlaczona pauza
	private boolean fruitSet; //okresla czy na planszy jest owoc
	private boolean superFruitSet; //stan okreslajacy czy super owoc znajduje sie na planszy
	private int fruitCollected; //ilosc zebranych owocow
	private int superFruitCollected; //ilosc zebranych super owocow
	private int outOfDate; //ilosc ruchow zanim super owoc zniknie
	private int wastedChances; //ilosc zmarnowanych sznas podniesienia super owocu
	private int coordinates; //miejsce w ktorym moze pojawic sie owoc
	private int points; //ilosc zdobyttych punktow
	private int slength; //dlugosc weza
	private int positionOfHead;
	private int delayCounter; //okresla ile czasu pozostalo do startu gry
	private long timeAtStart; //okresla jaki jest czas w momencie wlaczenia sie okna z odliczaniem czasu do rozpoczecia gry 
	private Random rng;
	
	private int preRow;
	private int preCol;
	
	private boolean emptyName;
	private boolean goodJob;
	
	private float dummyCounter; //licznik odswierzen programu
	
	private char name[];
	
	private InputFile load;
	private String file;
	private String names[];
	private String highscores[];
	private String size[];
	private String diff[];
	private boolean newHighscore;
	
	public GameWorld(float screenWidth, float screenHeight, int desktopWidth, int desktopHeight) 
	{		
		this.setScreenHeight(screenHeight);
		this.setScreenWidth(screenWidth);
		this.setDesktopHeight(desktopHeight);
		this.setDesktopWidth(desktopWidth);
		
		blockColumns = 0;
		blockRows = 0;
		blockSize = 9;
		blockSpace = 1;
		logoBlockSize = 6;
		logoBlockColumns = 21;
		
		logoBlocks = new int[] {4, 6, 7, 9, 13, 15, 16, 18, 23, 24, 25, 28, 30, 32, 34, 36, 38, 39, 41, 42, 46, 51, 55, 58, 59, 60, 63,
								64, 65, 67, 69, 72, 74, 76, 78, 80, 81, 83, 84, 88, 90, 91, 93, 95, 97, 99, 100, 102};
		
		lastDirection = SnakeDirection.RIGHT; 
		currentDirection = SnakeDirection.RIGHT; 
		direction = SnakeDirection.RIGHT; 
		snakeMoved = false; 
		
		timer = 10;
		
		mapSize = MapSize.SMALL;
		sizeMultiplier = 1;
		
		end = false; 
		play = false; 
		startWindow = true; 
		gameWindow = false; 
		difficultyWindow = false; 
		mapWindow = false; 
		scoreWindow = false; 
		exitWindow = false; 
		restart = false;
		empty = false; 
		winner = false;
		highscoreWindow = false;
		instructionsWindow = false;
		nameWindow = false;
		
		fruitSet = false;
		superFruitSet = false;
		fruitCollected = 0;
		superFruitCollected = 0;
		outOfDate = 0; 
		wastedChances = 0; 
 
		points = 0; 
		slength = 0;

		delayCounter = 3;
		dummyCounter = 0;
	
		goodJob = false;
		emptyName = false;
		rng = new Random();
		
		name = new char[10];// {' ', ' ', ' ', ' ', ' '};
		
		load = new InputFile(); //?
		newHighscore = false;
		names = new String[10];
		highscores = new String[10];
		size = new String[10];
		diff = new String[10];
				
		setupMapSize(); 
		initButtons();
		load();
		loadFile();
		initLogo();
	}
	
	public void initLogo() //tworzy logo w oknie startowym
	{		
		logo = new ArrayList<GameBlock>();
		int row = 0;
		int col = 0;
		
		for(int y = (logoBlockSize * 3); y < screenHeight - (logoBlockSize * 12); y+= logoBlockSize+blockSpace)
		{			
			row++;
			col = 0;
			for(int x = (logoBlockSize * 3); x < screenWidth - (logoBlockSize * 3); x+=logoBlockSize + blockSpace)
			{	
				col++;
				for(int i = 0; i < logoBlocks.length; i++)
				{
					if(((row - 1) * logoBlockColumns + col) == logoBlocks[i])
					{
						logo.add(new GameBlock(new Rectangle(x, y, logoBlockSize, logoBlockSize), BlockType.SNAKE_BACK));
						break;
					}
					else
					{
						logo.add(new GameBlock(new Rectangle(x, y, logoBlockSize, logoBlockSize), BlockType.EMPTY));
					}			
				}
			}
		}
	}
	
	public void initBlocks() //tworzy plansze i startowego weza
	{
		blocks = new ArrayList<GameBlock>();
					
		for(int y = 0; y < screenHeight - (blockSize * 2); y+= blockSize+blockSpace)
		{
			blockRows++;
			blockColumns = 0;
			
			for(int x = 0; x < screenWidth - blockSize; x+=blockSize + blockSpace)
			{
				blockColumns++;		
				
				if(x == screenWidth/2  && y == screenHeight/2)
				{
					blocks.add(new GameBlock(new Rectangle(x, y, blockSize, blockSize), BlockType.SNAKE_BACK, -1, -1));
				}
				else if(x >= screenWidth/2+blockSize && x <= (screenWidth/2)+2*blockSize && y == screenHeight/2)
				{
					blocks.add(new GameBlock(new Rectangle(x, y, blockSize, blockSize), BlockType.SNAKE_BODY, blockRows, blockColumns-1));	
				}
				else if(x >= screenWidth/2+2*blockSize && x <= (screenWidth/2)+3*blockSize && y == screenHeight/2)
				{
					blocks.add(new GameBlock(new Rectangle(x, y, blockSize, blockSize), BlockType.SNAKE_HEAD, blockRows, blockColumns-1));
				}
				else
				{
					blocks.add(new GameBlock(new Rectangle(x, y, blockSize, blockSize), BlockType.EMPTY));
				}
			}
		}
		direction = SnakeDirection.RIGHT;
	}

	public void clearAll()
	{
		//for(int i = blocks.size() ; i < block)
	}
	
	public void initButtons() //tworzy wszystkie przyciski w grze
	{
		
			start = new Rectangle(screenWidth*2/6-((blockSize+blockSpace)*3/2), screenHeight*3/5, (blockSize+blockSpace)*3, (blockSize+blockSpace)); // jest ulamkiem wiec dzielac przez niego tak naprawde zwiekszamy liczbe 
			highscore = new Rectangle(screenWidth*4/6-(blockSize+blockSpace)*3/2, screenHeight*3/5, (blockSize+blockSpace)*3, (blockSize+blockSpace));
			instructions = new Rectangle(screenWidth*2/6-((blockSize+blockSpace)*3/2), screenHeight*4/5, (blockSize+blockSpace)*3, (blockSize+blockSpace));
			exit1 = new Rectangle(screenWidth*4/6-(blockSize+blockSpace)*3/2, screenHeight*4/5, (blockSize+blockSpace)*3, (blockSize+blockSpace));
		
			back1 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			back4 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			
			map1 = new Rectangle(screenWidth/5-(blockSize+blockSpace), screenHeight/3, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			map2 = new Rectangle(screenWidth/5-(blockSize+blockSpace), screenHeight/3+(blockSize+blockSpace)*2, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			map3 = new Rectangle(screenWidth/5-(blockSize+blockSpace), screenHeight/3+(blockSize+blockSpace)*4, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			back3 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		
			difficulty1 = new Rectangle(screenWidth/5-(blockSize+blockSpace), screenHeight/3, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			difficulty2 = new Rectangle(screenWidth/5-(blockSize+blockSpace), screenHeight/3+(blockSize+blockSpace)*2, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			difficulty3 = new Rectangle(screenWidth/5-(blockSize+blockSpace), screenHeight/3+(blockSize+blockSpace)*4, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			back2 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		
			textField = new Rectangle(screenWidth/2-(blockSize+blockSpace) * 3/2, screenHeight/3, (blockSize+blockSpace)*3, (blockSize+blockSpace));
			begin = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight/2, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			back5 = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight*4/5, (blockSize+blockSpace)*2, (blockSize+blockSpace));
			
			submit = new Rectangle(screenWidth/2-(blockSize+blockSpace), screenHeight/2, (blockSize+blockSpace)*2, (blockSize+blockSpace));
		
			playAgain = new Rectangle(screenWidth/2-(blockSize+blockSpace)*3/2, screenHeight/3, (blockSize+blockSpace)*3, (blockSize+blockSpace));
			exit2 = new Rectangle(screenWidth/2-(blockSize+blockSpace)*3/2, screenHeight/2, (blockSize+blockSpace)*3, (blockSize+blockSpace));		
	}
	
	public boolean saveFileExists()
	{
		File f = new File("highscore.txt");
		return f.exists();
	}
	
	public void load() 
	{
		try
		{
			if(!saveFileExists())
			{
				initSaveFile();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	public void initSaveFile() //tworzy wyzerowany plik z highscore
	{
		String everything = "";
		for(int i = 0; i < 10; i++)
		{
			names[i] = "-----";
			highscores[i] = "0";
			size[i] = "---";
			diff[i] = "---";
			
			if(i == 9)
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + size[i] + "," + diff[i]);
			}
			else
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + size[i] + "," + diff[i] + ":");
			}	
		}
		load.writeFile("highscore.txt", everything);
	}
	
	public void saveToFile() //zapisuje wszystkie highscore do juz istniejacego pliku tekstowego
	{
		String everything = "";
		
		for(int i = 0; i < highscores.length; i++)
		{
			if(i == highscores.length - 1 )
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + size[i] + "," + diff[i]);
			}
			else
			{
				everything = everything + (names[i] + "," + highscores[i] + "," + size[i] + "," + diff[i] + ":");
			}
		}
		load.writeFile("highscore.txt", everything); 
	}
	
	public void loadFile() //zczytuje z pliku highscore
	{		
		file = load.readFile("highscore.txt");
		String[] splitFile = file.split(":");
				
		for(int i = 0; i < splitFile.length; i++)
		{
			String[] splitString = splitFile[i].split(",");
			names[i] = splitString[0];
			highscores[i] = splitString[1];
			size[i] = splitString[2];
			diff[i] = splitString[3];
		}
	}
	
	public String showHighscore(int i) //wyswietla ladnie uporzadkowane highscore 
	{	
		return String.format("%02d. %17d %12s %18s %16s", (i + 1), Integer.parseInt(highscores[i]), names[i], size[i], diff[i]);
	}
	
	public boolean isNewHighscore()
	{
		for(int i = highscores.length - 1; i >= 0; i--)
		{
			if(points >= Integer.parseInt(highscores[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	public void sortHighscore() //sortuje highscore. Jesli ta metoda zostaje wykonana to znaczy ze gracz zdobyl wystarczajaca ilosc punktow aby byc na 10 miejscu
								//dlatego juz na poczatku zapisuje punkty na pozycji 10 w highscore
	{
		String swapPoints;
		String swapNames;
		String swapDiff;
		String swapSize;
		
		highscores[highscores.length-1] = String.valueOf(points);
		names[names.length-1] = String.valueOf(getName());
		size[size.length-1] = String.valueOf(gameSize);
		diff[diff.length-1] = String.valueOf(currentDifficulty);
		
		for(int i = highscores.length - 1; i >= 1; i--)
		{	
		    if(points >= Integer.parseInt(highscores[i - 1]))
		    {
		    	swapPoints = highscores[i - 1];
				swapNames = names[i - 1];
				swapDiff = diff[i - 1];
				swapSize = size[i - 1];
				
				highscores[i - 1] = String.valueOf(points);
				names[i - 1] = String.valueOf(getName());
				size[i - 1] = String.valueOf(gameSize);
				diff[i - 1] = String.valueOf(currentDifficulty);
				
				highscores[i] = swapPoints;
				names[i] = swapNames;
				size[i] = swapSize;
				diff[i] = swapDiff;
			}
		}
	}
	
	public void insertName(char character) //sluzy do wpisywania imienia po zakonczeniu gry
	{
		for(int i = 0; i < name.length; i++)
		{
			//if sprawdzajacy czy pozycja do ktorej ma byc zapisany znak jest pusta oraz czy wcisniety klawisz to litera
			if(name[i] == 32 && (character  >= 65 && character <= 90 || character >= 97 && character <= 122) )
			{
				name[i] = character;
				break;
			}
			else if(i == name.length - 1 && character == 8)
			{
				name[i] = 32;
				break;
			}
			else if(character == 8 && name[i + 1] == 32)
			{
				name[i] = 32;
				break;
			}
		}
	}
	
	public void clearTextField()
	{
		for(int i = 0; i < name.length; i++)
		{
			name[i] = 32;
		}
	}
	
	public boolean isNameEmpty() //sprawdza czy zostal wprowadzony jakis znak do imienia
	{
		for(int i = 0; i < name.length; i++)
		{
			if( name[i] != 32) //name[i] != 62 &&
			{
				return true;
			}
		}
		return false;
	}
	
	public String goodJob()
	{
		for(int i = name.length - 1; i >= 0; i--)
		{
			if(i == name.length - 1 && name[i] != 32)
			{
				return String.valueOf(getName()) + "!";
			}
			else if(name[i] == 32 && name[i - 1] != 32 )
			{
				if(goodJob)
				{
					return String.valueOf(getName());
				}
				else
				{
					name[i] = '!';
					goodJob = true;
					return String.valueOf(getName());
				}
			}
		}
		return String.valueOf(getName()) + "";
	}
	
	public void callFruit() //tworzy owoce na planszy
	{
		int row;
		int col;
		
		do
		{
			row = rng.nextInt(blockRows) + 1;
			col = rng.nextInt(blockColumns) + 1;
		
			if(blocks.get((row - 1) * (blockColumns) + col - 1).getType() == BlockType.EMPTY)
			{
				blocks.get((row - 1) * (blockColumns) + col - 1).setType(BlockType.FRUIT);
				fruitSet = true;
			}
			
		}while(fruitSet == false);
	}
	
	public void callSuperFruit() //tworzy na planszy super owoce
	{
		int row;
		int col;
		outOfDate = 30;
		
		do
		{
			row = rng.nextInt(blockRows) + 1;
			col = rng.nextInt(blockColumns) + 1;
		
			if(blocks.get((row - 1) * (blockColumns) + col - 1).getType() == BlockType.EMPTY)
			{
				blocks.get((row - 1) * (blockColumns) + col - 1).setType(BlockType.SUPER_FRUIT);
				superFruitSet=true;
			}
			
		}while(superFruitSet == false);
		
		coordinates = (row - 1) * (blockColumns) + col - 1;
	}
	
	public void removeSuperFruit() //usuwa z planszy super owoce
	{
		blocks.get(coordinates).setType(BlockType.EMPTY);
	}
	
	public void maveRect(int keyNo) //pobiera wcisniete klawisze 
	{
		switch(keyNo)
		{
			case 22:
				currentDirection = SnakeDirection.RIGHT; 
				break;
			case 21:
				currentDirection = SnakeDirection.LEFT;
				break;
			case 19:
				currentDirection = SnakeDirection.UP;
				break;
			case 20:
				currentDirection = SnakeDirection.DOWN;
				break;
			default:
				break;
		}
	}
	
	public void startTimer() //wywoluje czas w momencie wejscia do okna wlasciwej gry. na potrzeby odliczania na poczatku gry
	{
		timeAtStart = System.nanoTime()/1000000000;
	}
	
	public void middleTimer() //wywoluje czas na potrzeby okreslenia czy minela kolejna sekunda od wywolania startTimer(). na potrzeby odliczania na poczatku gry
	{
		if((System.nanoTime()/1000000000) - timeAtStart >= 1)
		{
			delayCounter = delayCounter - 1;
			startTimer();
		}
	}
	
	public void superFruitTimer()
	{
		//superFruitTimer;
		//superFruitTimeAtStart;	
	}
	
	public void pause(char character) //wywoluje pause w grze
	{
		if(character == 'p' && pause)
		{
			pause = false;
		}
		else if(character == 'p' && pause == false)
		{
			pause = true;
		}
	}

	public void changecolor() //funkcja do sprawdzania czy gdzieœ komputer d³u¿ej nie szuka pustego miejsca na owoce
	{
		blocks.get(coordinates).setType(BlockType.SNAKE_BODY);
		fruitSet = false;
	}
	
	public void winner() //funkcja sprawdzajaca czy gracz przeszedl Snake'a czyli czy snake zajmuje wszystkie pola planszy 
	{
		empty = false;
		
		for(int row = 1; row <= blockRows; row++)
		{
			if(empty)
			{
				break;
			}
			
			for(int column = 1; column <= blockColumns; column++)
			{
				if(blocks.get((row - 1) * (blockColumns) + column - 1).getType() == BlockType.EMPTY) 
				{					//sprawdza czy blok jest pusty jesli tak to gra trwa nadal														
									//jesli nie to sprawdza czy jest to ostatni blok planszy														
									//jesli jest to ostatni blok planszy oznacza to, 														
									//ze wszystkie wczesniejsze pola sa juz zajete czyli na planszy nie ma juz miejsca 
					empty = true;
					break;
				}
				else if(((row - 1) * (blockColumns) + column - 1) == ((blockRows - 1) * (blockColumns) + blockColumns - 1))
				{
					winner = true;
					mapSize = MapSize.SMALL;
					
					Jukebox.play("win");
				}
			}
		}
	}

	public void setupDifficulty() //ustawia poziom trudnosci gry po wybraniu przez gracza ktorejs z dostepnych opcji
	{
		if(currentDifficulty == GameDifficulty.EASY)
		{
			timer = (float) 0.4;
		}
		else if(currentDifficulty == GameDifficulty.MEDIUM)
		{
			timer = (float) 0.2;
		}
		else if(currentDifficulty == GameDifficulty.HARD)
		{
			timer = (float) 0.1;
		}
	}
	
	public void checkDirection() //funkcja sprawdzajaca obecny i poprzedni kierunek aby zapobiec zmianie kierunku na przeciwny. Przy okazji zapobiega zacieciu sie snake'a przez zbyt szybkie wciskanie klawiszy
	{
		switch(currentDirection)
		{
			case RIGHT:
				if(lastDirection == SnakeDirection.LEFT) //te if-y zapobiegaja zmianie kierunku snake'a na przeciwny czyli zacieciu sie
				{
					direction = SnakeDirection.LEFT;
				}
				else
				{
					direction = SnakeDirection.RIGHT;
					lastDirection = direction;
				}
				break;
			case LEFT:
				if(lastDirection == SnakeDirection.RIGHT)
				{
					direction = SnakeDirection.RIGHT;
				}
				else
				{
					direction = SnakeDirection.LEFT;
					lastDirection = direction;
				}
				break;
			case UP:
				if(lastDirection == SnakeDirection.DOWN)
				{
					direction = SnakeDirection.DOWN;
				}
				else
				{
					direction = SnakeDirection.UP;
					lastDirection = direction;
				}
				break;
			case DOWN:
				if(lastDirection == SnakeDirection.UP)
				{
					direction = SnakeDirection.UP;
				}
				else
				{
					direction = SnakeDirection.DOWN;
					lastDirection = direction;
				}
				break;
			default:
				break;
		}
	}
	
	public int snakeLength() //liczy dlugosc snake'a na potrzeby sprawdzenia czy gracz wygral
	{
		int length = 1;
		int row = 0;
		int col = 0;
		
		while(blocks.get(positionOfHead).getType() != BlockType.SNAKE_BACK)
		{
			col = blocks.get(positionOfHead).getPreviousColumn();
			row = blocks.get(positionOfHead).getPreviousRow();
			positionOfHead = (row - 1) * (blockColumns) + col - 1;
			length = length + 1;
		}
		return length;
	}
	
	public void setupMapSize() //ustawia wielkosc planszy 
	{
		if(mapSize == MapSize.SMALL)
		{
			this.setScreenWidth(180);
			this.setScreenHeight(120);
			sizeMultiplier = 1;
		}
		else if(mapSize == MapSize.MEDIUM)
		{
			this.setScreenWidth(240);
			this.setScreenHeight(160);
			sizeMultiplier = (float) 0.75;
		}
		else if(mapSize == MapSize.LARGE)
		{
			this.setScreenWidth(360);
			this.setScreenHeight(240);
			sizeMultiplier = (float) 0.50;
		}
	}

	public void moveByMouse(int screenX, int screenY, int button)
	{
		if(startWindow  && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= start.x && (screenWidth/desktopWidth)*screenX <= start.x+start.width &&
				(screenWidth/desktopWidth)*screenY >= start.y && (screenWidth/desktopWidth)*screenY <= start.y+start.height)
			{
				startWindow = false;
				nameWindow = true;
				clearTextField();
			}
			else if((screenWidth/desktopWidth)*screenX >= highscore.x && (screenWidth/desktopWidth)*screenX <= highscore.x+highscore.width &&
					(screenWidth/desktopWidth)*screenY >= highscore.y && (screenWidth/desktopWidth)*screenY <= highscore.y+highscore.height)
			{
				startWindow = false;
				highscoreWindow = true;
			}
			else if((screenWidth/desktopWidth)*screenX >= instructions.x && (screenWidth/desktopWidth)*screenX <= instructions.x+instructions.width &&
					(screenWidth/desktopWidth)*screenY >= instructions.y && (screenWidth/desktopWidth)*screenY <= instructions.y+instructions.height)
			{
				startWindow = false;
				instructionsWindow = true;
			}
			else if((screenWidth/desktopWidth)*screenX >= exit1.x && (screenWidth/desktopWidth)*screenX <= exit1.x+exit1.width &&
					(screenWidth/desktopWidth)*screenY >= exit1.y && (screenWidth/desktopWidth)*screenY <= exit1.y+exit1.height)
			{
				Gdx.app.exit();
			}
		}
		else if(nameWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= begin.x && (screenWidth/desktopWidth)*screenX <= begin.x+begin.width &&
					(screenWidth/desktopWidth)*screenY >= begin.y && (screenWidth/desktopWidth)*screenY <= begin.y+begin.height)
			{
				if(isNameEmpty())
				{
					nameWindow = false;
					difficultyWindow = true;
				}	
				else
				{
					emptyName = true;
				}	
			}
			else if((screenWidth/desktopWidth)*screenX >= back5.x && (screenWidth/desktopWidth)*screenX <= back5.x+back5.width &&
					(screenWidth/desktopWidth)*screenY >= back5.y && (screenWidth/desktopWidth)*screenY <= back5.y+back5.height)
			{
				nameWindow = false;
				startWindow = true;
				emptyName = false;
			}
		}
		else if(difficultyWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= difficulty1.x && (screenWidth/desktopWidth)*screenX <= difficulty1.x+difficulty1.width &&
					(screenWidth/desktopWidth)*screenY >= difficulty1.y && (screenWidth/desktopWidth)*screenY <= difficulty1.y+difficulty1.height)
			{
				difficultyWindow = false;
				mapWindow  = true;
				currentDifficulty = GameDifficulty.EASY;
				setupDifficulty();
			}
			else if((screenWidth/desktopWidth)*screenX >= difficulty2.x && (screenWidth/desktopWidth)*screenX <= difficulty2.x+difficulty2.width &&
					(screenWidth/desktopWidth)*screenY >= difficulty2.y && (screenWidth/desktopWidth)*screenY <= difficulty2.y+difficulty2.height)
			{
				difficultyWindow = false;
				mapWindow  = true;
				currentDifficulty = GameDifficulty.MEDIUM;
				setupDifficulty();
			}
			else if((screenWidth/desktopWidth)*screenX >= difficulty3.x && (screenWidth/desktopWidth)*screenX <= difficulty3.x+difficulty3.width &&
					(screenWidth/desktopWidth)*screenY >= difficulty3.y && (screenWidth/desktopWidth)*screenY <= difficulty3.y+difficulty3.height)
			{
				difficultyWindow = false;
				mapWindow  = true;
				currentDifficulty = GameDifficulty.HARD;
				setupDifficulty();
			}	
			else if((screenWidth/desktopWidth)*screenX >= back2.x && (screenWidth/desktopWidth)*screenX <= back2.x+back2.width &&
					(screenWidth/desktopWidth)*screenY >= back2.y && (screenWidth/desktopWidth)*screenY <= back2.y+back2.height)
			{
				difficultyWindow = false;
				startWindow = true;
			}
		}
		else if(mapWindow && button == 0) 
		{
			if((screenWidth/desktopWidth)*screenX >= map1.x && (screenWidth/desktopWidth)*screenX <= map1.x+map1.width &&
					(screenWidth/desktopWidth)*screenY >= map1.y && (screenWidth/desktopWidth)*screenY <= map1.y+map1.height)
			{
				mapWindow = false;
				gameWindow = true;
				mapSize = MapSize.SMALL;
				gameSize = MapSize.SMALL;
				setupMapSize();
				initBlocks();
				startTimer();
			}
			else if((screenWidth/desktopWidth)*screenX >= map2.x && (screenWidth/desktopWidth)*screenX <= map2.x+map2.width &&
					(screenWidth/desktopWidth)*screenY >= map2.y && (screenWidth/desktopWidth)*screenY <= map2.y+map2.height)
			{
				mapWindow = false;
				gameWindow = true;
				mapSize = MapSize.MEDIUM;
				gameSize = MapSize.MEDIUM;
				setupMapSize();
				initBlocks();
				startTimer();
			}
			else if((screenWidth/desktopWidth)*screenX >= map3.x && (screenWidth/desktopWidth)*screenX <= map3.x+map3.width &&
					(screenWidth/desktopWidth)*screenY >= map3.y && (screenWidth/desktopWidth)*screenY <= map3.y+map3.height)
			{
				mapWindow = false;
				gameWindow = true;
				mapSize = MapSize.LARGE;
				gameSize = MapSize.LARGE;
				setupMapSize();
				initBlocks();
				startTimer();
			}
			else if((screenWidth/desktopWidth)*screenX >= back3.x && (screenWidth/desktopWidth)*screenX <= back3.x+back3.width &&
					(screenWidth/desktopWidth)*screenY >= back3.y && (screenWidth/desktopWidth)*screenY <= back3.y+back3.height)
			{
				mapWindow = false;
				difficultyWindow = true;
			}
		}
		else if(scoreWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= submit.x && (screenWidth/desktopWidth)*screenX <= submit.x+submit.width &&
					(screenWidth/desktopWidth)*screenY >= submit.y && (screenWidth/desktopWidth)*screenY <= submit.y+submit.height)
			{
				scoreWindow = false;
				exitWindow = true;	
			}
		}
		else if(exitWindow && button==0)
		{
			if((screenWidth/desktopWidth)*screenX >= playAgain.x && (screenWidth/desktopWidth)*screenX <= playAgain.x+playAgain.width &&
					(screenWidth/desktopWidth)*screenY >= playAgain.y && (screenWidth/desktopWidth)*screenY <= playAgain.y+playAgain.height)
			{	
				restart=true;
			}
			else if((screenWidth/desktopWidth)*screenX >= exit2.x && (screenWidth/desktopWidth)*screenX <= exit2.x+exit2.width &&
					(screenWidth/desktopWidth)*screenY >= exit2.y && (screenWidth/desktopWidth)*screenY <= exit2.y+exit2.height)
			{	
				Gdx.app.exit();
			}
		}
		else if(highscoreWindow && button == 0)
		{
			if((screenWidth/desktopWidth)*screenX >= back1.x && (screenWidth/desktopWidth)*screenX <= back1.x+back1.width &&
					(screenWidth/desktopWidth)*screenY >= back1.y && (screenWidth/desktopWidth)*screenY <= back1.y+back1.height)
			{
				highscoreWindow = false;
				startWindow = true;
			}
		}
		else if(instructionsWindow && button ==0)
		{
			if((screenWidth/desktopWidth)*screenX >= back4.x && (screenWidth/desktopWidth)*screenX <= back4.x+back4.width &&
					(screenWidth/desktopWidth)*screenY >= back4.y && (screenWidth/desktopWidth)*screenY <= back4.y+back4.height)
			{
				instructionsWindow = false;
				startWindow = true;
			}
		}
		/*else if(gameWindow && play == false && button == 0)
		{
			play = true;
		}*/
		else if(gameWindow && end && button == 0)
		{
			setupMapSize();
			gameWindow = false;
			scoreWindow = true;
			if(isNewHighscore())
			{
				loadFile();
				sortHighscore();
				saveToFile();
			}
		}
	}
	
	public void update(float delta) 
	{
		
		if(gameWindow)
		{
			if(play == false) //na potrzeby odliczania na poczatku gry
			{
				middleTimer();
				if(delayCounter < 0)
				{
					play = true;
				}
			}
			
			if(pause == false)
			{
				if(winner == false && end == false) //zakomentowaæ end == false jesli chce sie uzyskac nieœmiercionoœne sciany i ogon
				{
					
					if(fruitSet == false)
					{
						callFruit();
					}
					
					if(superFruitSet == false && slength > 10 * (superFruitCollected + 1 + wastedChances))
					{
						callSuperFruit();
					}
					
					if(superFruitSet && outOfDate == 0)
					{
						superFruitSet = false;
						removeSuperFruit();
						wastedChances = wastedChances + 1;
					}
						
					if(play)
					{
						if(dummyCounter >= timer)
						{
							checkDirection();
							updateSnakePosition();
							//changecolor();
							winner();
							dummyCounter = 0;
							outOfDate=outOfDate - 1;
							slength = snakeLength();
						}
						dummyCounter = dummyCounter + delta;
						
					}
				}
			}
		}
	}
	
	public void updateSnakePosition()
	{
		snakeMoved = false;
		
		for(int row = 1; row <= blockRows; row++)
		{
			if(snakeMoved)
			{
				return;
			}
			
			for(int column = 1; column <= blockColumns; column++)
			{
				GameBlock actualBlock = blocks.get(((row-1)*(blockColumns)+column-1));
				int newRow = 0; //zmienne wprowadzone aby ujednolicic kod bez nich kazdy case musial miec rozbudowanego ifa w ktorym byl zawarty algorytm obliczania nastepnego miejsca w zaleznosci od kierunku. Teraz dodaje sie po prostu do algorytmu wartosci pod newRow i newCol. Okreslaja one kierunek w ktorym przemieszcza sie waz
				int newCol = 0;
				
				if(actualBlock.getType() == BlockType.SNAKE_HEAD)
				{
					switch(direction)
					{
						case RIGHT:		
							if(column < blockColumns)
							{ 
								newRow = 0;
								newCol = 1;
							}
							else
							{
								end = true;
								mapSize = MapSize.SMALL;
								fruitSet = false;
								Jukebox.play("lose");
								return;
							}
							break;
						case LEFT:
							if(column > 1)
							{
								newRow = 0;
								newCol = -1;
							}
							else
							{
								end = true;
								mapSize = MapSize.SMALL;
								fruitSet = false;
								Jukebox.play("lose");
								return;
							}
							break;
						case UP:
							if(row > 1)
							{
								newRow = -1;
								newCol = 0;
							}
							else
							{
								end = true;
								mapSize = MapSize.SMALL;
								fruitSet = false;
								Jukebox.play("lose");
								return;
							}
							break;
						case DOWN:
							if(row < blockRows)
							{
								newRow = 1;
								newCol = 0;
							}
							else
							{
								end = true;
								mapSize = MapSize.SMALL;
								fruitSet = false;
								Jukebox.play("lose");
								return;
							}
							break;
						default:
							break;
					}
					
					GameBlock blockToGo = blocks.get(((row - 1 + newRow) * (blockColumns) + column - 1 + newCol)); //przechowuje block do ktorego ma sie przemiescic glowa
					
					if(blockToGo.getType() == BlockType.SNAKE_BACK || blockToGo.getType() == BlockType.SNAKE_BODY)
					{
						end = true;
						mapSize = MapSize.SMALL;
						fruitSet = false;
						Jukebox.play("lose");
						return;
					}
					else if(blockToGo.getType() == BlockType.FRUIT || blockToGo.getType() == BlockType.SUPER_FRUIT)
					{
						fruitSet = false;
						fruitCollected = fruitCollected + 1;
						
						if(blockToGo.getType() == BlockType.SUPER_FRUIT)
						{
							superFruitSet = false;
							superFruitCollected = superFruitCollected + 1;
							fruitCollected = fruitCollected - 1;
							fruitSet = true;
							points = points + 10;
							Jukebox.play("eat");
						}	
						
							points = points + 5;
							Jukebox.play("eat");
							blocks.get(((row - 1 + newRow) * (blockColumns) + column - 1 + newCol)).setType(BlockType.SNAKE_HEAD);
							
							//jesli tutaj postawimy if ktory sprawdzi czy pozycja na ktorej ma sie znalezc glowa to takze pozycja owocu
							//to przesuwamy tylko glowe i na jej miejsce wstawiamy cialo i to wszystko
							//zamieniamy stara glowe na cialo
							blocks.get(((row - 1) * (blockColumns) + column - 1)).setType(BlockType.SNAKE_BODY);
							
							blocks.get((row - 1 + newRow) * (blockColumns) + column - 1 + newCol).setPreviousColumn(column); //wpisujemy do nowej glowy o pixel na prawo obecna pozycje
							blocks.get((row - 1 + newRow) * (blockColumns) + column - 1 + newCol).setPreviousRow(row);			
							positionOfHead = (row - 1 + newRow) * (blockColumns) + column - 1 + newCol;
					}
					else
					{
						blocks.get(((row - 1 + newRow) * (blockColumns) + column - 1 + newCol)).setType(BlockType.SNAKE_HEAD);
						
						blocks.get(((row - 1) * (blockColumns) + column - 1)).setType(BlockType.SNAKE_BODY);
						
						blocks.get((row-1 + newRow) * (blockColumns) + column - 1 + newCol).setPreviousColumn(column); //wpisujemy do nowej glowy o pixel na prawo obecna pozycje
						blocks.get((row-1 + newRow) * (blockColumns) + column - 1 + newCol).setPreviousRow(row);
						positionOfHead = (row - 1 + newRow) * (blockColumns) + column - 1 + newCol;
						preRow = blocks.get((row - 1) * (blockColumns) + column - 1).getPreviousRow();
						preCol = blocks.get((row - 1) * (blockColumns) + column - 1).getPreviousColumn();
						
						do
						{
							row = preRow;//blocks.get((preRow-1)*(blockColumns)+preCol-1).getPreviousRow();
							column = preCol;//blocks.get((preRow-1)*(blockColumns)+preCol-1).getPreviousColumn();
							
							preRow = blocks.get((row - 1) * (blockColumns) + column - 1).getPreviousRow();
							preCol = blocks.get((row - 1) * (blockColumns) + column - 1).getPreviousColumn();
								
							if(blocks.get((preRow - 1) * (blockColumns) + preCol - 1).getType() == BlockType.SNAKE_BACK)
							{		
								blocks.get((row - 1) * (blockColumns) + column - 1).setType(BlockType.SNAKE_BACK);
								blocks.get((preRow - 1) * (blockColumns) + preCol - 1).setType(BlockType.EMPTY);	
							}	
							
						}while(blocks.get((preRow - 1) * (blockColumns) + preCol - 1).getType() != BlockType.EMPTY);
						
					}
					snakeMoved = true;
					Jukebox.play("move");
				}
				
				if(snakeMoved)
				{
					return;
				}
			}
		}
	}
	
	public List<GameBlock> getBlocks() 
	{
		return blocks;
	}

	public int getPoints()
	{
		return points;
	}

	public int getDesktopWidth() 
	{
		return desktopWidth;
	}

	public void setDesktopWidth(int desktopWidth) 
	{
		this.desktopWidth = desktopWidth;
	}

	public int getDesktopHeight() 
	{
		return desktopHeight;
	}

	public void setDesktopHeight(int desktopHeight) 
	{
		this.desktopHeight = desktopHeight;
	}

	public float getScreenWidth()
	{
		return screenWidth;
	}

	public void setScreenWidth(float screenWidth)
	{
		this.screenWidth = screenWidth;
	}

	public float getScreenHeight() 
	{
		return screenHeight;
	}

	public void setScreenHeight(float screenHeight) 
	{
		this.screenHeight = screenHeight;
	}

	public String getGameText() 
	{
		return gameText;
	}

	public void setGameText(String gameText) 
	{
		this.gameText = gameText;
	}
	
	public boolean getWinner()
	{
		return winner;
	}
	
	public boolean getStartWindow()
	{
		return startWindow;
	}
	
	public boolean getDifficultyWindow()
	{
		return difficultyWindow;
	}
	
	public boolean getMapWindow()
	{
		return mapWindow;
	}
	
	public boolean getNameWindow()
	{
		return nameWindow;
	}
	
	public boolean getScoreWindow()
	{
		return scoreWindow;
	}
	
	public boolean getExitWindow()
	{
		return exitWindow;
	}
	
	public boolean getGameWindow()
	{
		return gameWindow;
	}
	
	public boolean getInstructionsWindow()
	{
		return instructionsWindow;
	}
	
	public Rectangle getStart() 
	{
		return start;
	}
	
	public Rectangle getMap1() 
	{
		return map1;
	}
	
	public Rectangle getMap2() 
	{
		return map2;
	}
	
	public Rectangle getMap3() 
	{
		return map3;
	}
	
	public Rectangle getDifficulty1() 
	{
		return difficulty1;
	}
	
	public Rectangle getDifficulty2() 
	{
		return difficulty2;
	}
	
	public Rectangle getDifficulty3() 
	{
		return difficulty3;
	}
	
	public Rectangle getBegin()
	{
		return begin;
	}
	
	public Rectangle getTextField() 
	{
		return textField;
	}
	
	public Rectangle getSubmit() 
	{
		return submit;
	}
	
	public Rectangle getExit1() 
	{
		return exit1;
	}
	
	public Rectangle getExit2() 
	{
		return exit2;
	}
	
	public Rectangle getPlayAgain() 
	{
		return playAgain;
	}
	
	public int getBlockSize()
	{
		return blockSize;
	}
	
	public int getBlockSpace()
	{
		return blockSpace;
	}
	
	public boolean getEnd()
	{
		return end;
	}
	
	public String getWinText()
	{
		return winText;
	}
	
	public String getLoseText()
	{
		return loseText;
	}
	
	public GameDifficulty getCurrentDifficulty()
	{
		return currentDifficulty;
	}
	
	public boolean getPlay()
	{
		return play;
	}
	
	public String getClickToPlay()
	{
		return clickToPlay;
	}
	
	public float getSizeMultiplier()
	{
		return sizeMultiplier;
	}
	
	public MapSize getMapSize()
	{
		return mapSize;
	}
	
	public MapSize getGameSize()
	{
		return gameSize;
	}
	
	public boolean isRestart()
	{
		return restart;
	}
	
	public char[] getName()
	{
		return name;
	}
	
	public int getFruitCollected()
	{
		return fruitCollected;
	}
	
	public int getSuperFruitCollected()
	{
		return superFruitCollected;
	}
	
	public Rectangle getBack1()
	{
		return back1;
	}
	
	public Rectangle getBack2()
	{
		return back2;
	}
	
	public Rectangle getBack3()
	{
		return back3;
	}
	
	public Rectangle getBack4()
	{
		return back4;
	}
	
	public Rectangle getBack5()
	{
		return back5;
	}
	
	public Rectangle getHighscore()
	{
		return highscore;
	}
	
	public Rectangle getInstructions()
	{
		return instructions;
	}
	
	public boolean getHighscoreWindow()
	{
		return highscoreWindow;
	}
	
	public String[] getHighscores()
	{
		return highscores;
	}
	
	public List<GameBlock> getLogo()
	{
		return logo;
	}
	
	public int getLogoBlockSize()
	{
		return logoBlockSize;
	}
	
	public boolean getPause()
	{
		return pause;
	}
	
	public int getDelayCounter()
	{
		return delayCounter;
	}
	
	public boolean getEmptyName()
	{
		return emptyName;
	}
}
