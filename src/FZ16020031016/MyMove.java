package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;

import static core.game.Move.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import static core.board.PieceColor.EMPTY;


public class MyMove {
    private ArrayList<Step> allStep = new ArrayList<>();
    private ArrayList<PosSore> myself = new ArrayList<PosSore>();
    private ArrayList<PosSore> stop = new ArrayList<PosSore>();
    private ArrayList<Integer> myself_pos = new ArrayList<>();
    private ArrayList<Integer> stop_pos = new ArrayList<>();
    private int[][]map = new int[19][19];
    String str1= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int[][] dir = {
            {0,1},  // 右
            {1,0},  // 下
            {-1,1}, // 右上
            {1,1}   // 右下
    };

    private void randomStep(MyBoard board){
        Random rand = new Random();
        while (true) {
            int index1 = rand.nextInt(SIDE * SIDE);
            int index2 = rand.nextInt(SIDE * SIDE);
            if (index1 != index2 && board.get(index1) == EMPTY && board.get(index2) == EMPTY){
                Step s = new Step(index1,index2);
                this.allStep.add(s);
                break;
            }
        }
    }

    private void operator(MyBoard board,PieceColor myChess){
        //沿着路的方向搜六步
        for(PosSore ps:myself){
            int way = ps.getWay()-1;
            int i = ps.getI();
            int j = ps.getJ();
            for (int k = 0 ; k < 6;k++){
                int y = i + dir[way][0] * k,x = j + dir[way][1] * k;
                if(y>=0&&y<19&&x>=0&&x<19 && board.get(y*19+x) == EMPTY){
                    if(myself_pos.size()>=6){
                        break;
                    }
                    if(!myself_pos.contains(y*19+x)) {
                        myself_pos.add(y * 19 + x);
                    }
                }
            }
        }
        for(PosSore ps:stop){
            int way = ps.getWay()-1;
            int i = ps.getI();
            int j = ps.getJ();

            for (int k = 0 ; k < 6;k++){
                int y = i + dir[way][0] * k,x = j + dir[way][1] * k;
                if(y>=0&&y<19&&x>=0&&x<19 && board.get(y*19+x) == EMPTY){
                    if(stop_pos.size()>=6){
                        break;
                    }
                    if(!stop_pos.contains(y*19+x)) {
                        stop_pos.add(y * 19 + x);
                    }
                    break;
                }
            }
        }
    }
    public void generateRoad(PieceColor myChess, MyBoard board,Board_Score BS){
        Board_Roads[][]br = BS.getBlackorwhite();
        ArrayList<Road> ar_all = new ArrayList<>();
        for(int i=1;i<6;i++){
            ar_all.addAll(br[0][i].getAllRoad());
            ar_all.addAll(br[i][0].getAllRoad());
        }
        PieceColor opponent;
        if(myChess==PieceColor.BLACK)
            opponent=PieceColor.WHITE;
        else
            opponent=PieceColor.BLACK;
        //x24+1
        int[] soreList = {0,1,25,601,14425,346201};
        for(Road road:ar_all){
            int mysore = 0,yousore = 0;
            int my = ChessCount.getMy(road,myChess);
            int your = ChessCount.getYour(road,myChess);
            //一条路上只有自己的子，才会加分
            if(your == 0)
                mysore += soreList[my];
            //一条路上只有对方的子，才会加分
            if(my == 0)
                yousore += soreList[your];
            if(yousore > 0)
                stop.add(new PosSore(road.getFp()/19,road.getFp()%19,road.getJ(),opponent,yousore));
            else if(mysore > 0)
                myself.add(new PosSore(road.getFp()/19,road.getFp()%19,road.getJ(), myChess,mysore));
        }

        //对可能要下的地方进行排序，分越高的，给自己的优势或者给对方造成的妨碍越大
        Collections.sort(myself, new SortBySore());
        Collections.sort(stop, new SortBySore());
        //开始下棋
        this.operator(board,myChess);
    }

    public void stopTwo(){
        int len = stop_pos.size();
        for(int i = 0;i<len;i++){
            for(int j = i+1;j<len;j++){
                if(stop_pos.get(i).intValue()!=stop_pos.get(j).intValue()) {
                    allStep.add(new Step(stop_pos.get(i), stop_pos.get(j)));
                }
            }
        }
    }
    public void stopOne(){
        int len = stop_pos.size();
        int len2 = myself_pos.size();
        for(int i = 0;i<len;i++){
            for(int j = 0; j<len2;j++){
                if(stop_pos.get(i).intValue()!=myself_pos.get(j).intValue()) {
                    allStep.add(new Step(stop_pos.get(i), myself_pos.get(j)));
                }
            }
        }
    }
    public void goodMyself(){
        int len = myself_pos.size();
        for(int i = 0;i<len;i++){
            for(int j = i+1;j<len;j++){
                if(myself_pos.get(i).intValue()!=myself_pos.get(j).intValue()) {
                    allStep.add(new Step(myself_pos.get(i), myself_pos.get(j)));
                }
            }
        }
    }
    public void generateStep(PieceColor myChess, MyBoard board, Board_Score BS){
        this.generateRoad(myChess,board,BS);
//        二子堵
        stopTwo();
//        一子堵
        stopOne();
//        自己好
        goodMyself();
//        随机？




    }

    public ArrayList<Step> getAllStep() {
        return allStep;
    }
}
