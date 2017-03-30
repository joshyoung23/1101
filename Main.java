import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;

public class Main extends Application {

	//initialize the global attributes
	private Player p1 = new Player(1);
	private Player p2 = new Player(2);
	private GameBoard gameboard;	//this class does all the updating game board stuff
	private Font titleFont = new Font("Calibri",30);
	private Scene mainMenu;
	private Scene gameArea;

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage window) throws Exception {

		//add title to the top of the game window
		window.setTitle("Tron game");
		
		//make the game window a fixed size
		window.setResizable(false);

		//create three buttons
		Button playButton = new Button("Play");
		Button exitButton = new Button("Exit");
		Button highScoreButton = new Button("High Scores");

		//create a title for the menu
		Text menuTitle = new Text("Main Menu");
		menuTitle.setFont(titleFont);


		//placeholder thing to display when they hit high score button
		//Text highScoreText = new Text("High score function not available yet...");
		//highScoreText.setVisible(false);

		//create a grid pane for the menu (probably shouldn't be a grid Pane tbh)
		GridPane menu = new GridPane();
		menu.setAlignment(Pos.CENTER);
		menu.setHgap(10);
		menu.setVgap(20);
		menu.setStyle("-fx-background-color: BEIGE");
		menu.add(playButton,3,3);
		menu.add(exitButton,3,4);
		Text highScoreText = new Text("Josh sucks at league of legends...");
		highScoreText.setVisible(false);
		menu.add(highScoreButton,3,5);
		menu.add(menuTitle,3,1);
		menu.add(highScoreText, 3, 6);

		//create the two scenes
		mainMenu = new Scene(menu, 440, 440, Color.LIGHTGRAY);

		gameboard = new GameBoard(p1,p2,window,mainMenu);
		gameArea = new Scene(gameboard.getPane(), 440,440,Color.LIGHTGRAY);

		//by default start by displaying the main menu
		window.setScene(mainMenu);

		//when they click the play button
		playButton.setOnAction(e ->{
			window.setScene(gameArea);  //switch to the gameArea scene
			gameboard.reset();			//reset the game board icons
			gameboard.start();			//start the timer
			highScoreText.setVisible(false); //re-hide the high score thing
		});

		//when they click exit
		exitButton.setOnAction(e ->{
			System.exit(0);		//exit program
		});

		//when they click high SCore button
		highScoreButton.setOnAction(e ->{
			highScoreText.setVisible(true);  //for now, display placeholder text
		});

		//while gameArea is being displayed .... update direction with W,A,S,D  and I,J,K,L
		//also, pause the game with C ,   resume with V,    return to main menu (and reset board) with B
		gameArea.setOnKeyPressed(e -> {
	        switch (e.getCode()){
				case W: p1.setDir(Player.Direction.UP);
					break;
				case A: p1.setDir(Player.Direction.LEFT);
					break;
				case S: p1.setDir(Player.Direction.DOWN);
					break;
				case D: p1.setDir(Player.Direction.RIGHT);
					break;
				case I: p2.setDir(Player.Direction.UP);
					break;
				case J: p2.setDir(Player.Direction.LEFT);
					break;
				case K: p2.setDir(Player.Direction.DOWN);
					break;
				case L: p2.setDir(Player.Direction.RIGHT);
					break;
				case C: gameboard.pause();
					break;
				case V: gameboard.play();
					break;
				case B: window.setScene(mainMenu);
						gameboard.reset();
					break;
			}
	    });

		//show the window (duh)
		window.show();
	}

}
