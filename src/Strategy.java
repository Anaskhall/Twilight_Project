import models.Map;
import models.Monster;
import models.Move;

import java.util.ArrayList;

/**
 * Created by Anas-kh on 26/02/2015.
 */
public class Strategy {

    public Move chooseMoveMonster(Map map, Monster monster){
        Move move = null;
        return move;
    }

    public ArrayList<Move> chooseMoves(Map map){
        ArrayList<Move> moves = new ArrayList<Move>();
        for (Monster monster : map.getArmy()){
            Move move = chooseMoveMonster(map, monster);
            if (move!=null){
                moves.add(move);
            }

        }
        return moves;
    }
}
