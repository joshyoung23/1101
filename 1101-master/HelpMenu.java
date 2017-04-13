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

public class HelpMenu {
	
	private Stage window;  //passed in from Main.java (this is the main window)
    private Scene menu;    //passed in from Main.java (the main menu Stage)
	private Pane helpPane; //this pane holds the helpPane and the "back to main menu" button
	private Scene helpMenu;
	private Button button; //button that returns to the main menu
	private Font helpFont = new Font("Calibri", 20);
	private MediaPlayer select = new MediaPlayer(new Media(new File("select.wav").toURI().toString()));
   	private MediaPlayer click = new MediaPlayer(new Media(new File("click.wav").toURI().toString()));
   	private MediaPlayer pause = new MediaPlayer(new Media(new File("pause.wav").toURI().toString()));
   	private MediaPlayer resume = new MediaPlayer(new Media(new File("play.wav").toURI().toString()));
   	private boolean mute = false; 
	
	//Constructor 
    public HelpMenu(Stage stage, Scene scene){
    	 //Taken from Main.
    	 window = stage;
         menu = scene;
         
         //Create the base pane of the help menu.
         helpPane = new Pane();
    	
         DropShadow dshadow = new DropShadow();
         
         //create a new button that returns the user to the main menu
         button = new Button("Return");
         button.setTranslateX(360);
         button.setTranslateY(360);
         button.setOnMouseEntered( event -> select.play());
         button.setOnMouseExited(e -> select.stop());
 		 button.setEffect(dshadow);
 		 button.setStyle("-fx-font: 15 arial; -fx-base: #ed1c24;");
         button.setOnMousePressed(e -> click.play());
         button.setOnMouseReleased(e -> {
        	 window.setScene(menu);
        	 click.stop();
         });
         
         Text testText = new Text("Controls"
         		+ "\n\n Player 1:" + "\t\t\t Misc"
         		+ "\n\t W - UP" + " \t\t\t C - PAUSE"
         		+ "\n\t A - LEFT" + "\t\t\t V - RESUME"
         		+ "\n\t S - DOWN" + "\t\t B - MENU"
         		+ "\n\t D - RIGHT"
         		+ "\n\n Player 2:"
         		+ "\n\t I - UP"
         		+ "\n\t J - LEFT"
         		+ "\n\t K - DOWN"
         		+ "\n\t L - RIGHT ");
         testText.setFont(Font.font("Arial", 20));
         testText.setFill(Color.WHITE);
         
         //create a pane to hold the help text
         BorderPane helpTextPane1 = new BorderPane();
         helpTextPane1.setPrefSize(240,280);
         helpTextPane1.setAlignment(testText,Pos.TOP_LEFT);
         helpTextPane1.setCenter(testText);
         helpTextPane1.setTranslateX(50);
         helpTextPane1.setTranslateY(80);
         helpTextPane1.setVisible(true);
         
         
 		//create an image viewer for the background
 		ImageView viewer = new ImageView();
 		Image backGround = new Image(Main.class.getResourceAsStream("HelpMenuBackground.png"));
 		viewer.setImage(backGround);
 		viewer.setFitWidth(640); //Change this number as needed to reflect the preferred size set above. 
        viewer.setPreserveRatio(true);
        viewer.setSmooth(true);
        viewer.setCache(true);
 		
 		//Add to the helpPane
 		helpPane.getChildren().addAll(viewer, helpTextPane1, button);
           
 		
    }    
        
  //get method to return the "helpPane" to the main class so it can be displayed in a scene
    public Pane getPane(){
        return helpPane;
    } 
    
  //method that toggles mute on/off for the gameboard
    public void toggleMute(){
        if(!mute){
            mute = true;
            select.setMute(true);
            click.setMute(true);
        } else {
            mute = false;
            select.setMute(false);
            click.setMute(false);
        }      
    }
    
    
}
