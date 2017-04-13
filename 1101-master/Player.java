public class Player {

	private Direction dir;
	private int player;
	private int posX;
	private int posY;
	private int nextX;
	private int nextY;

	//constructor
	public Player(int playerNum) {
		this.player = playerNum;
		if (playerNum == 1) {
			dir = Direction.RIGHT;
			posX = 1;
			posY = 11;
		}
		else {
			dir = Direction.LEFT;
			posX = 20;
			posY = 11;
		}
		setNext();
	}

	public void setNext(){
		if(dir == Direction.UP){
			nextY = posY-1;
			nextX = posX;
		}else if(dir == Direction.DOWN){
			nextY = posY+1;
			nextX = posX;
		}else if(dir == Direction.RIGHT){
			nextY = posY;
			nextX = posX+1;
		}else if(dir == Direction.LEFT){
			nextY = posY;
			nextX = posX-1;
		}
	}
	public int getNextX(){
		return nextX;
	}
	public int getNextY(){
		return nextY;
	}

	//reset the player positions and directions
	public void reset(){
		if (player == 1) {
			dir = Direction.RIGHT;
			posX = 1;
			posY = 11;
		}
		else {
			dir = Direction.LEFT;
			posX = 20;
			posY = 11;
		}
		setNext();
	}


	public Direction getDir() {
		return dir;
	}

	public String getDirString(){
		String s = "";
		switch (dir){
			case UP: s = "Up";
				break;
			case DOWN: s = "Down";
				break;
			case LEFT: s = "Left";
				break;
			case RIGHT: s = "Right";
				break;
		}
		return s;
	}

	public void setDir(Direction dir) {
		if(dir == Direction.UP && this.dir == Direction.DOWN)
			this.dir = Direction.DOWN;

		else if (dir == Direction.RIGHT && this.dir == Direction.LEFT)
			this.dir = Direction.LEFT;

		else if (dir == Direction.LEFT && this.dir == Direction.RIGHT)
			this.dir = Direction.RIGHT;

		else if (dir == Direction.DOWN && this.dir == Direction.UP)
			this.dir = Direction.UP;

		else
			this.dir = dir;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}



	//custom enum thing
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}


}
