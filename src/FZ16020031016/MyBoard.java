package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import java.util.ArrayList;
import java.util.Iterator;


public class MyBoard{
    private PieceColor[] my_board = new PieceColor[361];
    public MyBoard(Board board){
        for(int i=0;i<361;i++){
            my_board[i] = board.get(i);
        }
    }
    public PieceColor[] get_my_board(){
        return my_board;
    }
    public void set(int i, int j,PieceColor v,Board_Score BS) {
        if(i==j){
            System.out.println("cuowu ");
        }
        Board_Roads[][] br = BS.getBlackorwhite();
        //修改路表
        for(int q = 0;q<2;q++) {
            ArrayList<Road> ar;
            if(q==0){
                ar = Road.findRoads(my_board, i);
            }
            else{
                ar = Road.findRoads(my_board, j);
            }
            int count = ar.size();
            int nnn = 0;
            for (Road r : ar) {
                int ttt=nnn;
                int b = r.getBf();
                int w = r.getWf();
                int f = r.getFp();
                int jf = r.getJ();
                if (v == PieceColor.BLACK) {
                    b++;
                } else {
                    w++;
                }

                Road new_r = new Road(f, jf, w, b);
                ArrayList<Road> t_ar = br[b][w].getAllRoad();
                if(t_ar.size()==0){
                    BS.setRoad(new_r);
                    nnn++;
                }
                else {
                    Iterator<Road> iterator = t_ar.iterator();
                    //删除旧的，加新的
                    while (iterator.hasNext()) {
                        Road r1 = iterator.next();
                        if (b == r1.getBf() && w == r1.getWf() && f == r1.getFp() && jf == r1.getJ()) {
                            iterator.remove();
                            BS.setRoad(new_r);
                            nnn++;
                            break;
                        }
                    }
                    BS.setRoad(new_r);
                    nnn++;
                }
                if(nnn==ttt) {
                    System.out.println("7777777");
                }
            }

            if(q==0){
                //修改棋盘
                this.my_board[i] = v;
            }
            else{
                this.my_board[j] = v;
            }
        }

    }
    public void unset(int i,int j,Board_Score BS){
        Board_Roads[][] br = BS.getBlackorwhite();
        PieceColor v;
        //修改路表
        for(int q = 0;q<2;q++) {
            ArrayList<Road> ar;
            if(q==0){
                v = my_board[i];
                ar = Road.findRoads(my_board, i);
            }
            else{
                v = my_board[j];
                ar = Road.findRoads(my_board, j);
            }
            for (Road r : ar) {
                int b = r.getBf();
                int w = r.getWf();
                int f = r.getFp();
                int jf = r.getJ();

                ArrayList<Road> t_ar = br[b][w].getAllRoad();
                Iterator<Road> iterator = t_ar.iterator();
                //删除旧的，加新的
                while (iterator.hasNext()) {
                    Road r1 = iterator.next();
                    if (b == r1.getBf() && w == r1.getWf() && f == r1.getFp() && jf == r1.getJ()) {
                        iterator.remove();
                        if (v == PieceColor.BLACK) {
                            b--;
                        } else {
                            w--;
                        }
                        if(b==-1||w==-1){
                            String s = "b"+b+"w"+w+"f"+f+"jf"+jf;
                            System.out.println(s);
                            System.out.println("i"+i+"j"+j);
                            //draw();
                        }
                        Road new_r = new Road(f, jf, w, b);
                        BS.setRoad(new_r);
                        break;
                    }
                }
            }
            if(q==0){
                //修改棋盘
                this.my_board[i] = PieceColor.EMPTY;
            }
            else{
                this.my_board[j] = PieceColor.EMPTY;
            }
        }
    }
    public PieceColor get(int i){
        return my_board[i];
    }
    public String toString(boolean legend) {
        StringBuffer strBuff = new StringBuffer();
        strBuff.append("  ");

        int i;
        for(i = 0; i < 19; ++i) {
            strBuff.append((char)(65 + i)).append("  ");
        }

        strBuff.append("\n");

        for(i = 0; i < 361; ++i) {
            if (i % 19 == 0) {
                strBuff.append((char)(65 + i / 19) + " ");
            }

            if (this.my_board[i] == PieceColor.EMPTY) {
                strBuff.append("-").append("  ");
            } else if (this.my_board[i] == PieceColor.BLACK) {
                strBuff.append("x").append("  ");
            } else {
                strBuff.append("o").append("  ");
            }

            if ((i + 1) % 19 == 0) {
                strBuff.append("\n");
            }
        }

        return strBuff.toString();
    }
    public void draw() {
        System.out.print(this.toString(true));
    }
}
