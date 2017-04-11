import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;

import java.io.File;

public class GameBoard  {

    //declare the global variables
    private Stage window;  //passed in from Main.java (this is the main window)
    private Scene menu;    //passed in from Main.java (the main menu Stage)
    private Player p1;     //passed in from Main.java
    private Player p2;     //passed in from Main.java
    private Timeline time; //runs every X seconds, and renders / redraws the players
    private GridPane pane; //this is the pane that stores and displays the player icons
    private Pane realGameArea; //this pane holds the GridPane and the "back to main menu" button
    private Cell[][] cells;//2D array of "cells"(basically images dictating whether something is a player, wall, empty)
    private Button button; //button that returns to the main menu, (only appears when a player loses)
    private int count = 1; //count variable, used so that the "return to menu" button only appears once
    private Label winner;  //displays which player was the winner, only visible after someone wins
    private VBox winBox;   //holds the button and the label, this is what appears on game ending
    private Font winFont = new Font("Impact",40); //call of duty font L M A O
    Media select = new Media(new File("select.wav").toURI().toString());
    Media click = new Media(new File("click.wav").toURI().toString());
    Media crash = new Media(new File("crash.wav").toURI().toString());
    Media song = new Media(new File("song.wav").toURI().toString());
    Media win = new Media(new File("winner.wav").toURI().toString());


    MediaPlayer mediaPlayer = new MediaPlayer(song);



    //constructor that fills the grid pane with "walls" and "empty cells"
    //it also initializes all of the global variables
    public GameBoard(Player p1, Player p2, Stage stage, Scene scene){
        //take in the players, Stage and Scene from Main.java
        this.p1 = p1;
        this.p2 = p2;
        window = stage;
        menu = scene;

        //create the base pane of the game area
        realGameArea = new Pane();

        //create a new button that returns the user to the main menu
        button = new Button("Game Over, Return to Menu");
        button.setOnMouseEntered( event -> {
            MediaPlayer mediaPlayer = new MediaPlayer(select);
            mediaPlayer.play();
        });
        button.setOnAction(e -> {
            window.setScene(menu);
            MediaPlayer mediaPlayer = new MediaPlayer(click);
            mediaPlayer.play();
        });

        //create the label that displays the winner
        winner = new Label();
        winner.setFont(winFont);

        //put the label and the button into a vertical box
        winBox = new VBox(winner,button);
        winBox.setPadding(new Insets(20,20,20,20));
        winBox.setSpacing(20);
        winBox.setVisible(false);  //hide the box by default
        winBox.setAlignment(Pos.CENTER);
        winBox.setTranslateX(110);
        winBox.setTranslateY(110);

        winBox.setStyle("-fx-background-color: WHITE");
        //create the GridPane which holds the images for EMPTY, PLAYER, or WALL cells.
        this.pane = new GridPane();
        //create the Cell 2D array and fill it with EMPTY cells
        cells = new Cell[22][22];
        for(int i = 0 ; i < 22 ; i++){
            for(int j = 0 ; j < 22 ; j++){
                cells[i][j] = new Cell(Cell.Content.EMPTY);
                pane.add(cells[i][j].getView(),i,j);
            }
        }
        //fill the sides of the array with WALL cells instead
        for(int i = 0 ; i < 22 ; i++){
            cells[i][0].setContent(Cell.Content.WALL);
            pane.add(cells[i][0].getView(),i,0);

            cells[0][i].setContent(Cell.Content.WALL);
            pane.add(cells[0][i].getView(),0,i);

            cells[21][i].setContent(Cell.Content.WALL);
            pane.add(cells[21][i].getView(),21,i);

            cells[i][21].setContent(Cell.Content.WALL);
            pane.add(cells[i][21].getView(),i,21);
        }
        //draw the player icons on the GridPane in their starting positions
        drawIcons();


        //create the timeline which renders and redraws the player icons as long as there is no winner
        time = new Timeline(new KeyFrame(Duration.millis(150), e-> { //<<<- duration is how quickly the game renders()

            if(winner() && count == 1){     //if there IS a winner, make the "return to main menu" button appear
                count = 0;                  //set count to 0 so that the button only appears once
                winBox.setVisible(true);
                mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(crash);
                mediaPlayer.play();
                mediaPlayer.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        mediaPlayer = new MediaPlayer(win);
                        mediaPlayer.play();
                    }
                });

            }else if(!winner()) {
                render();
                drawIcons();
            }
        }));
        time.setCycleCount(Timeline.INDEFINITE); //this means the timeline runs forever unless stopped

        //add the GridPane and the invisible button to the "realGameArea"
        realGameArea.getChildren().addAll(pane,winBox);
    }

    //start the timer again from the beginning
    public void start(){
        time.playFromStart();
        mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }

    //un-pause the timer
    public void play(){
        time.play();
        mediaPlayer.play();
    }

    //pause the timer
    public void pause(){
        time.pause();
        mediaPlayer.pause();
    }

    //get method to return the "realGameArea" to the main class so it can be displayed in a scene
    public Pane getPane(){
        return realGameArea;
    }

    //stop the timer, reset each player and re-fill the game board with EMPTY and WALL cells
    public void reset(){
        time.stop();
        p1.reset();
        p2.reset();
        //fill the array with EMPTY cells
        for(int i = 0 ; i < 22 ; i++){
            for(int j = 0 ; j < 22 ; j++){
                cells[i][j] = new Cell(Cell.Content.EMPTY);
                pane.add(cells[i][j].getView(),i,j);
            }
        }
        //fill the edges of the array with WALL cells
        for(int i = 0 ; i < 22 ; i++){
            cells[i][0].setContent(Cell.Content.WALL);
            pane.add(cells[i][0].getView(),i,0);

            cells[0][i].setContent(Cell.Content.WALL);
            pane.add(cells[0][i].getView(),0,i);

            cells[21][i].setContent(Cell.Content.WALL);
            pane.add(cells[21][i].getView(),21,i);

            cells[i][21].setContent(Cell.Content.WALL);
            pane.add(cells[i][21].getView(),i,21);
        }
        //redraw the players in the start positions
        drawIcons();

        //set count back to 1
        count = 1;
        //hide the "return to main menu" button again
        winBox.setVisible(false);
    }

    //redraw the game board to display the new player locations
    public void drawIcons(){
        if(     p1.getPosX() > 0 && p1.getPosX() < 21 && p1.getPosY() > 0 && p1.getPosY() < 21 &&
                p2.getPosX() > 0 && p2.getPosX() < 21 && p2.getPosY() > 0 && p2.getPosY() < 21    ) {
            //set the cell at the current player positions equal to PLAYER1 and PLAYER2
            cells[p1.getPosY()][p1.getPosX()].setContent(Cell.Content.PLAYER1);  //player 1
            cells[p2.getPosY()][p2.getPosX()].setContent(Cell.Content.PLAYER2);  //player 2

            //add the images for player 1 and player 2 to the current location in the GridPane

            pane.add(cells[p1.getPosY()][p1.getPosX()].getView(), p1.getPosX(), p1.getPosY()); //player 1
            pane.add(cells[p2.getPosY()][p2.getPosX()].getView(), p2.getPosX(), p2.getPosY()); //player 2
        }
    }

    //update the location of the players based on their direction and updates the "next location" as well
    public void render(){
        //move the current location of player one depending on direction
        switch (p1.getDir()){
            case UP:    p1.setPosY(p1.getPosY() - 1);
                break;
            case DOWN:  p1.setPosY(p1.getPosY() + 1);
                break;
            case LEFT:  p1.setPosX(p1.getPosX() - 1);
                break;
            case RIGHT: p1.setPosX(p1.getPosX() + 1);
                break;
        }
        //move the current location of player two depending on direction
        switch (p2.getDir()){
            case UP:    p2.setPosY(p2.getPosY() - 1);
                break;
            case DOWN:  p2.setPosY(p2.getPosY() + 1);
                break;
            case LEFT:  p2.setPosX(p2.getPosX() - 1);
                break;
            case RIGHT: p2.setPosX(p2.getPosX() + 1);
                break;
        }

        //after updating current position, update the next positions of both as well
        p1.setNext();
        p2.setNext();
    }

    //check if the next position of each player is an EMPTY cell, if not then return false;
    public boolean winner() {

        if(p1.getPosX() < 1 || p1.getPosX() > 20 || p1.getPosY() < 1 || p1.getPosY() > 20){
            winner.setText("Player 2 wins!");
            return true;
        }
        if(p2.getPosX() < 1 || p2.getPosX() > 20 || p2.getPosY() < 1 || p2.getPosY() > 20){
            winner.setText("Player 1 wins!");
            return true;
        }

        //if the cell that players are moving into is NOT EMPTY, then game over
        if(     cells[p1.getNextY()][p1.getNextX()].getContent() != Cell.Content.EMPTY &&
                cells[p2.getNextY()][p2.getNextX()].getContent() != Cell.Content.EMPTY   ) {
            winner.setText("It's a tie!");
            return true;
        }else if(cells[p1.getNextY()][p1.getNextX()].getContent() != Cell.Content.EMPTY) {
            winner.setText("Player 2 wins!");
            return true;
        }else if(cells[p2.getNextY()][p2.getNextX()].getContent() != Cell.Content.EMPTY) {
            winner.setText("Player 1 wins!");
            return true;
        }


        //by default return false;
        return false;
    }
}