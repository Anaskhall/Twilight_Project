package models;

import java.util.ArrayList;

/**
 * Created by Anas-kh on 26/02/2015.
 */
public class Map {

    private int lines;
    private int columns;
    private ArrayList<Human> humans;
    private ArrayList<Monster> monsters;
    private ArrayList<Monster> army;
    private String armyType;
    private String monstersType;

    public Map(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.humans = new ArrayList<Human>();
        this.monsters = new ArrayList<Monster>();
        this.army = new ArrayList<Monster>();
    }

    public void addHuman(int x, int y){
        this.humans.add( new Human(x,y));
    }

    public void addArmy(int x, int y){
        this.army.add(new Monster(x,y));
    }

    public void initialize(int x, int y, int humans, int vampires, int werewolves){
        if (humans>0){
            Human human = new Human(x,y);
            int index = getHumanBySquare(x,y);
            this.humans.get(index).setNumber(humans);
        }
        if (vampires >0){
            if (this.army.get(0).getX() == x && this.army.get(0).getY() == y){
                this.armyType = "Vampire";
                this.army.get(0).setNumber(vampires);
            }
            else {
                this.monstersType = "Vampire";
                this.army.get(0).setNumber(vampires);
            }
        }
        if (werewolves >0){
            if (this.army.get(0).getX() == x && this.army.get(0).getY() == y){
                this.armyType = "Werewolf";
                this.army.get(0).setNumber(werewolves);
            }
            else {
                this.monstersType = "Werewolf";
                this.army.get(0).setNumber(werewolves);
            }
        }
    }

    public void update(int x, int y, int humans, int vampires, int werewolves){
        updateHuman(x, y, humans);
        if (this.monstersType.equals("Vampire")){
            updateMonster(x, y, vampires);
            updateArmy(x, y, werewolves);
        }
        else{
            updateMonster(x, y, werewolves);
            updateArmy(x, y, vampires);
        }
    }

    public void updateHuman(int x, int y, int humans){
        int posHuman = getHumanBySquare(x,y);
        if (posHuman>=0){
            if (humans==0){
                this.humans.remove(posHuman);
            }
            else{
                this.humans.get(posHuman).setNumber(humans);
            }
        }
        else if (posHuman ==-1 && humans>0){
            this.humans.add(new Human(x , y, humans));
        }
    }

    public void updateMonster(int x, int y, int monsters){
        int posMonster = getMonsterBySquare(x, y);
        if (posMonster>=0){
            if (monsters==0){
                this.monsters.remove(posMonster);
            }
            else{
                this.monsters.get(posMonster).setNumber(monsters);
            }
        }
        else if (posMonster ==-1 && monsters>0){
            this.monsters.add(new Monster(x , y, monsters));
        }
    }

    public void updateArmy(int x, int y, int army){
        int posArmy = getMonsterBySquare(x, y);
        if (posArmy>=0){
            if (army==0){
                this.army.remove(posArmy);
            }
            else{
                this.army.get(posArmy).setNumber(army);
            }
        }
        else if (posArmy ==-1 && posArmy>0){
            this.army.add(new Monster(x , y, army));
        }
    }

    public int getHumanBySquare(int x, int y){
        for (Human human : this.humans){
            if (human.getX()==x && human.getY()==y){
                return this.humans.indexOf(human);
            }
        }
        return -1;
    }

    public int getMonsterBySquare(int x, int y){
        for (Monster monster : this.monsters){
            if (monster.getX()==x && monster.getY()==y){
                return this.monsters.indexOf(monster);
            }
        }
        return -1;
    }

    public int getArmyBySquare(int x, int y){
        for (Monster monster : this.army){
            if (monster.getX()==x && monster.getY()==y){
                return this.army.indexOf(monster);
            }
        }
        return -1;
    }

    public void addMonster(int x, int y){
        this.monsters.add( new Monster(x,y));
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Monster> getArmy() {
        return army;
    }

    public void setArmy(ArrayList<Monster> army) {
        this.army = army;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}
