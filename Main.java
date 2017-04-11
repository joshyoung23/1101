import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import java.io.File;

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
		window.getIcons().add(new Image("file:MenuBackGround2.png"));
		
		//make the game window a fixed size
		window.setResizable(false);

		Media select = new Media(new File("select.wav").toURI().toString());
		Media click = new Media(new File("click.wav").toURI().toString());
		Media pause = new Media(new File("pause.wav").toURI().toString());
		Media play = new Media(new File("play.wav").toURI().toString());

		DropShadow dshadow = new DropShadow();
		
		//create three buttons (play, exit, options)
		
		Button playButton = new Button("Play");
			playButton.setEffect(dshadow);
			playButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
			
			playButton.setOnMouseEntered(event -> {
				MediaPlayer mediaPlayer = new MediaPlayer(select);
				mediaPlayer.play();
			});

		Button exitButton = new Button("Exit");
			exitButton.setEffect(dshadow);
			exitButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
			
			exitButton.setOnMouseEntered(event -> {
				MediaPlayer mediaPlayer = new MediaPlayer(select);
				mediaPlayer.play();
			});

		Button optionsButton = new Button("Options");
			optionsButton.setEffect(dshadow);
			optionsButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
			
			optionsButton.setOnMouseEntered(event -> {
				MediaPlayer mediaPlayer = new MediaPlayer(select);
				mediaPlayer.play();
			});

		//create a title for the menu
		Text menuTitle = new Text("Main Menu");
			menuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 25));
      			menuTitle.setFill(Color.BLUE);
		
      		Text gameTitle = new Text("Knights vs Dragons");
      			gameTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
      			gameTitle.setFill(Color.RED);

		//create a border pane for the menu
  		BorderPane menu = new BorderPane();
			//create an image viewer for the background
			ImageView viewer = new ImageView();
			Image backGround = new Image(Main.class.getResourceAsStream("MenuBackground2.png"));
			viewer.setImage(backGround);

			//create HBox for the gameTitle held in the top panel
			HBox hbox = new HBox();
			hbox.setPadding(new Insets(15, 85, 15, 85));
			hbox.setSpacing(50);
			hbox.setStyle("fx-background-color: #336699;");//i dont really know what this does ¯\_(..)_/¯

			//create Vbox for the buttons and menuTitle
			VBox vbox = new VBox();
			vbox.setPadding(new Insets(180));//side lengths
			vbox.setSpacing(10);//gaps

			//add viewer to the border pane
			menu.getChildren().add(viewer);
			// add titles and buttons to the hbx/vbox
			hbox.getChildren().add(gameTitle);
			vbox.getChildren().addAll(menuTitle, playButton, exitButton, optionsButton);
		
			//sets where the vbox and hbox should be
     			menu.setCenter(vbox);
     			menu.setTop(hbox);
		
		//create the two scenes
		mainMenu = new Scene(menu, 440, 440, Color.LIGHTGRAY);

		gameboard = new GameBoard(p1,p2,window,mainMenu);
		gameArea = new Scene(gameboard.getPane(), 440,440,Color.LIGHTGRAY);

		//by default start by displaying the main menu
		window.setScene(mainMenu);

		//when they click the play button
		playButton.setOnAction(e ->{
			MediaPlayer mediaPlayer = new MediaPlayer(click);
			mediaPlayer.play();
			window.setScene(gameArea);  //switch to the gameArea scene
			gameboard.reset();	//reset the game board icons
			gameboard.start();	//start the timer
		});

		//when they click exit
		exitButton.setOnAction(e ->{
			MediaPlayer mediaPlayer = new MediaPlayer(click);
			mediaPlayer.play();
			System.exit(0);		//exit program
		});

		//when they click high SCore button
		optionsButton.setOnAction(e ->{
			MediaPlayer mediaPlayer = new MediaPlayer(click);
			mediaPlayer.play();
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
					MediaPlayer mediaPlayer = new MediaPlayer(pause);
					mediaPlayer.play();
					break;
				case V: gameboard.play();
					mediaPlayer = new MediaPlayer(play);
					mediaPlayer.play();
					break;
				case B: window.setScene(mainMenu);
					gameboard.reset();
					mediaPlayer = new MediaPlayer(select);
					mediaPlayer.play();
					break;
			}
	    	});
		
		//show the window (duh)
		window.show();
		
	}//end start method

}//end class
