import models.Map;
import models.Move;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Anas-kh on 26/02/2015.
 */
public class Game {

    private static Socket socket;
    private static InputStream input;
    private static OutputStream output;

    private static String host = "127.0.0.1";
    private static int port = 5555;
    private static String team = "Twilight";
    private Strategy strategy = new Strategy();
    private Map game_map;

    int test = 0;

    public byte[] receiveTrame(int size) throws Exception {
        byte[] trame = new byte[size];
        int nbBytesLus = input.read(trame, 0, size);
        if (nbBytesLus != size) {
            throw new Exception("Cannot read trame type");
        }
        return trame;
    }
    public boolean receiveData() throws Exception{
        byte[] trame =receiveTrame(3);
        String typeTrame = new String(trame, "ASCII");
        System.out.println("Trame received : " + typeTrame);
        if (typeTrame.equalsIgnoreCase("SET")) {
            processTrameSET();
        } else if (typeTrame.equalsIgnoreCase("HUM")) {
            processTrameHUM();
        } else if (typeTrame.equalsIgnoreCase("HME")) {
            processTrameHME();
        } else if (typeTrame.equalsIgnoreCase("MAP")) {
            processTrameMAP();
        } else if (typeTrame.equalsIgnoreCase("UPD")) {
            processTrameUPD();
        } else if (typeTrame.equalsIgnoreCase("END")) {
            return false; // delete used resources
        } else if (typeTrame.equalsIgnoreCase("BYE")) {
            return true;
        } else {
            throw new Exception("Unknown trame");
        }
        return false;
    }

    private void processTrameSET() throws Exception {
        byte[] trame = receiveTrame(2);
        int lines = (int) trame[0] & 0xff;
        int columns = (int) trame[1] & 0xff;
        System.out.println("SET " + lines + "   " + columns);
        game_map = new Map(lines,columns);
    }

    private void processTrameHUM() throws Exception {
        byte[] trame = receiveTrame(1);
        int N = (int) trame[0] & 0xff;
        trame = receiveTrame(2*N);
        for (int i=0; i<N; i++){
            int x = (int) trame[2*i] & 0xff;
            int y = (int) trame[2*i +1] & 0xff;
            System.out.println("HUM " + x + "   " + y);
            game_map.addHuman(x, y);
        }
    }

    private void processTrameHME() throws Exception {
        byte[] trame = receiveTrame(2);
        int x = (int) trame[0] & 0xff;
        int y = (int) trame[1] & 0xff;
        System.out.println("HME " + x + "   " + y);
        game_map.addArmy(x, y);
    }

    private void processTrameMAP() throws Exception {
        byte[] trame = receiveTrame(1);
        int N = (int) trame[0] & 0xff;
        trame = receiveTrame(5*N);
        for (int i=0; i<N; i++){
            int x = (int) trame[5 * i] & 0xff;
            int y = (int) trame[5 * i +1] & 0xff;
            int humans = (int) trame[5 * i +2] & 0xff;
            int vampires = (int) trame[5 * i +3] & 0xff;
            int werewolves = (int) trame[5 * i +4] & 0xff;
            System.out.println("MAP " + x + " " + y + " " + humans+ " " + vampires+ " " + werewolves);
            game_map.initialize(x, y, humans, vampires, werewolves);
        }
    }

    private void processTrameUPD() throws Exception {
        byte[] trame = receiveTrame(1);
        int N = (int) trame[0] & 0xff;
        trame = receiveTrame(5*N);
        for (int i=0; i<N; i++){
            int x = (int) trame[5 * i] & 0xff;
            int y = (int) trame[5 * i +1] & 0xff;
            int humans = (int) trame[5 * i +2] & 0xff;
            int vampires = (int) trame[5 * i +3] & 0xff;
            int werewolves = (int) trame[5 * i +4] & 0xff;
            System.out.println("UPD " + x + " " + y + " " + humans+ " " + vampires+ " " + werewolves);
            game_map.update(x, y, humans, vampires, werewolves);
        }
        //ArrayList<Move> moves = strategy.chooseMoves(game_map);
        Move move = new Move(4,3,2,5,4);
        Move move2 = new Move(4,3,2,3,2);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(move);
        moves.add(move2);

        if (test==0){
            System.out.println("Sending MOV");
            sendMOV(moves);
            test++;
        }

    }

    public void sendNME(){
        int size = team.length();
        byte[] trame = new byte[4+size];

        trame[0] = 'N';
        trame[1] = 'M';
        trame[2] = 'E';
        trame[3] = (byte) size;
        for (int i=0; i<size; i++){
            trame[i+4] = (byte) team.charAt(i);
        }
        try {
            output.write(trame, 0, trame.length);
        } catch (IOException e) {
            System.out.println("Cannot send NME");
        }
    }

    public void sendMOV(ArrayList<Move> moves){
        int size = moves.size();
        byte[] trame = new byte[4+5*size];
        trame[0] = 'M';
        trame[1] = 'O';
        trame[2] = 'V';
        trame[3] = (byte) size;

        for (int i=0; i<size; i++){
            Move move = moves.get(i);
            trame[4+i]= (byte) move.getStartX();
            trame[4+i+1]= (byte) move.getStartY();
            trame[4+i+2]= (byte) move.getNumber();
            trame[4+i+3]= (byte) move.getFinishX();
            trame[4+i+4]= (byte) move.getFinishY();
        }
        try {
            output.write(trame, 0, trame.length);
        } catch (IOException e) {
            System.out.println("Cannot send MOV");
        }
    }


    public static void main(String[] args) throws Exception {

        Game game = new Game();
        System.out.println("Establishing connection");
        try {
            socket = new Socket(host, port);
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Closing Program");
            return;
        }
        System.out.println("Connexion established");

        game.sendNME();
        System.out.println("NME sent");

        while (true){
            boolean isClosing = game.receiveData();
            if (isClosing){
                System.out.println("Closing Program");
                break;
            }
        }
        socket.close();
        input.close();
        output.close();
    }
}
