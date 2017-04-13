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
	private MediaPlayer select = new MediaPlayer(new Media(new File("select.wav").toURI().toString()));
   	private MediaPlayer click = new MediaPlayer(new Media(new File("click.wav").toURI().toString()));
   	private MediaPlayer pause = new MediaPlayer(new Media(new File("pause.wav").toURI().toString()));
   	private MediaPlayer resume = new MediaPlayer(new Media(new File("play.wav").toURI().toString()));
	private boolean mute = false;
	
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

		DropShadow dshadow = new DropShadow();
		
		//create four buttons (play, exit, options, mute)
      
      		Button muteButton = new Button("Mute");
         		muteButton.setEffect(dshadow);
			muteButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
         		muteButton.setTranslateY(200);
         
         		muteButton.setOnMouseEntered(e -> select.play());
         		muteButton.setOnMouseExited(e -> select.stop());
		
		Button playButton = new Button("Play");
			playButton.setEffect(dshadow);
			playButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
			
			playButton.setOnMouseEntered(event -> select.play());
			playButton.setOnMouseExited(e -> select.stop());

		Button exitButton = new Button("Exit");
			exitButton.setEffect(dshadow);
			exitButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
			
			exitButton.setOnMouseEntered(event -> select.play());
			exitButton.setOnMouseExited(e -> select.stop());

		Button optionsButton = new Button("Options");
			optionsButton.setEffect(dshadow);
			optionsButton.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
			
			optionsButton.setOnMouseEntered(event -> select.play());
			optionsButton.setOnMouseExited(e -> select.stop());

		//create a title for the menu and for the Game
		Text menuTitle = new Text("Main Menu");
			menuTitle.setFont(Font.font("Arial", FontWeight.BOLD, 35));
      			menuTitle.setFill(Color.DARKBLUE);
		
      		Text gameTitle = new Text("Knights vs Dragons");
      			gameTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
      			gameTitle.setFill(Color.RED);

		//create a border pane for the menu scene
  		BorderPane menu = new BorderPane();
			//set the prefered size of the menu
			menu.setPrefSize(440,440);
		
			//create an image viewer for the background
			ImageView viewer = new ImageView();
			Image backGround = new Image(Main.class.getResourceAsStream("MenuBackground2.png"));
			viewer.setImage(backGround);
			//add viewer to the border pane
			menu.getChildren().add(viewer);
		
			//create HBox for the gameTitle held in the top panel
			HBox hbox = new HBox();
			hbox.setPadding(new Insets(65,0,0,0)); //sets it 65 pixels down from the top
			hbox.setAlignment(Pos.CENTER); 
			hbox.getChildren().add(gameTitle);
		
			//create Vbox for the buttons and menuTitle
			VBox vbox = new VBox();
			vbox.setSpacing(10); //gaps between the nodes
			vbox.setAlignment(Pos.CENTER); //align the buttons in the centre of the box
			vbox.getChildren().addAll(menuTitle, playButton, exitButton, optionsButton);
		
			//sets where the vbox and hbox should be 
     			menu.setCenter(vbox);
     			menu.setTop(hbox);
			menu.setRight(muteButton);
		
		//create the two scenes (one for the menu, one for the gameArea)
		mainMenu = new Scene(menu, 440, 440, Color.LIGHTGRAY);

		gameboard = new GameBoard(p1,p2,window,mainMenu);
		gameArea = new Scene(gameboard.getPane(), 440,440,Color.LIGHTGRAY);

		//by default start by displaying the main menu
		window.setScene(mainMenu);

		//when they click the mute Button
      		muteButton.setOnMousePressed(e -> click.play());
      		muteButton.setOnMouseReleased(e -> {
         		click.stop();
         		if(!mute){
				select.setMute(true);
				click.setMute(true);
				pause.setMute(true);
				resume.setMute(true);
				mute = true;
				gameboard.toggleMute();
				muteButton.setText("UnMute");
			 } else {
				select.setMute(false);
				click.setMute(false);
				pause.setMute(false);
				resume.setMute(false);
				mute = false;
				gameboard.toggleMute();
				muteButton.setText("Mute");
			 } 
		});   
		
		//when they click the play button
      		playButton.setOnMousePressed(e -> click.play());
		playButton.setOnMouseReleased(e ->{
			click.stop();
			window.setScene(gameArea);  //switch to the gameArea scene
			gameboard.reset();	//reset the game board icons
			gameboard.start();	//start the timer
		});

		//when they click exit
		exitButton.setOnMousePressed(e -> click.play());
      		exitButton.setOnMouseReleased(e -> System.exit(0));


		//when they click the options button
		optionsButton.setOnMousePressed(e -> click.play());
      		optionsButton.setOnMouseReleased(e -> click.stop());
		
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
					pause.play();	//play the pause sound
					break;
				case V: gameboard.play();
					resume.play(); //play the "resume" sound
					break;
				case B: window.setScene(mainMenu);
					gameboard.reset();
					select.play();
					break;
			}
	    	});
		gameArea.setOnKeyReleased(e -> {
			switch(e.getCode()){
				case C: pause.stop(); //stop the pause sound
					break;
				case V: resume.stop(); //stop the resume sound
					break;
				case B: select.stop(); //stop the select sound
					break;
			}
		});
		
		//show the window (duh)
		window.show();
		
	}//end start method

}//end class
