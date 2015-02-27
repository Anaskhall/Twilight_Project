package models;

/**
 * Created by Anas-kh on 26/02/2015.
 */
public class Move {

    private int startX;
    private int startY;
    private int number;
    private int finishX;
    private int finishY;

    public Move(int startX, int startY, int number, int finishX, int finishY) {
        this.startX = startX;
        this.startY = startY;
        this.number = number;
        this.finishX = finishX;
        this.finishY = finishY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFinishX() {
        return finishX;
    }

    public void setFinishX(int finishX) {
        this.finishX = finishX;
    }

    public int getFinishY() {
        return finishY;
    }

    public void setFinishY(int finishY) {
        this.finishY = finishY;
    }
}
