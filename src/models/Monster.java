package models;

/**
 * Created by Anas-kh on 26/02/2015.
 */
public class Monster {

    private int x;
    private int y;
    private int number;
    private Move move;

    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Monster(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
