import models.Map;
import models.Monster;
import models.Move;

import java.util.*;

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

    public boolean currentPlayer;

    public float rating(Map map){
        return 0;// TODO
    }

    public List<Map> possibleMaps(Map map){
        List maps = new ArrayList<Map>();
        return maps; //TODO
    }

    public float alphaBeta(Map map, int depth, float alpha, float beta, boolean currentPlayer) {

        if (depth <= 0) {
            return rating(map); //rate a map
        }

        List<Map> children = possibleMaps(map); // generates list of possible maps

        if (currentPlayer) { // ai tries to maximize the score
            for (Map child : children) {
                alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta, !currentPlayer));

                if (beta <= alpha) {
                    break; // cutoff
                }
            }
            return alpha;

        } else { // enemy tries to minimize the score
            for (Map child : children) {
                beta = Math.min(beta, alphaBeta(map, depth - 1, alpha, beta, !currentPlayer));
                if (beta <= alpha) {
                    break; // cutoff
                }
            }
            return beta;
        }
    }
    
}
