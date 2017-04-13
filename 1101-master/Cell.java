import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Cell {

    //declare global attributes
    private Content content;
    private ImageView view;
    private Image image;

    //constructor that takes in a value (empty, player1, player2, wall) and sets the image depending on the value
    public Cell(Content x){
        if(x == Content.EMPTY) {
            content = Content.EMPTY;
            image = new Image("emptyCell.png");
        }else if(x == Content.PLAYER1){
            content = Content.PLAYER1;
            image = new Image("Player1Head.png");
        }else if(x == Content.PLAYER2){
            content = Content.PLAYER2;
            image = new Image("Player2Head.png");
        }else{
            content = Content.WALL;
            image = new Image("wallCell.png");
        }
        view = new ImageView(image);
    }

    //basically the exact same as the constructor, to reset existing cell values
    public void setContent(Content x){
        if(x == Content.EMPTY) {
            content = Content.EMPTY;
            image = new Image("emptyCell.png");
        }else if(x == Content.PLAYER1){
            content = Content.PLAYER1;
            image = new Image("Player1Head.png");
        }else if(x == Content.PLAYER2){
            content = Content.PLAYER2;
            image = new Image("Player2Head.png");
        }else{
            content = Content.WALL;
            image = new Image("wallCell.png");
        }
        view = new ImageView(image);
    }

    //get content returns the content stored inside
    public Content getContent(){
        return content;
    }

    //get View method to return the current image to the game board class to add it to the gridPane
    public ImageView getView(){
        return view;
    }

    //enum's
    public enum Content {
        EMPTY, PLAYER1, PLAYER2, WALL
    }
}
