package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import s2017666.AI;

import java.util.ArrayList;

import static core.board.PieceColor.*;

public class Search {
    private ArrayList<Step> AS =  new ArrayList<>();

    /**
     * 必胜格局搜索
     * @param board
     * @param myChess
     * @return
     */

    private int[][] dir = {
            {0,1},  // 右
            {1,0},  // 下
            {-1,1}, // 右上
            {1,1}   // 右下
    };
    private void operator(int i, int j ,MyBoard board,Road road){
        //沿着路的方向搜六步
        int d = road.getJ() - 1;//方向坐标
        ArrayList<int[]> a = new ArrayList<int[]>();
        for (int k = 0 ; k < 6;k++){
            int y = i + dir[d][0] * k,x = j + dir[d][1] * k;

            if(y>=0&&y<19&&x>=0&&x<19 && board.get(y*19+x) == EMPTY){
                int[] arr = {y,x};
                a.add(arr);
            }
        }
        //必胜策略的话，当只要下一步的时候就能结束，另一个随意找个空的地方下就行
        if(a.size() == 1){
            for (int ii = 0; ii < 19 ; ii++){
                for (int kk = 0 ; kk < 19 ; kk++){
                    if(board.get(ii*19+kk) == EMPTY){
                        int[] arr = {ii,kk};
                        a.add(arr);
                        kk = 20;ii = 20;//跳出两层循环
                    }
                }
            }
        }
        //添加到step,a也不懂为什么会越界
        if (a.size() != 0)
            AS.add( new Step(a.get(0),a.get(1)));
    }


    public Step mustWin(MyBoard board, PieceColor myChess){
        this.AS.clear();
        for(int i = 0;i<19;i++){
            for(int j=0;j<19;j++){
                int index = i*19+j;
                ArrayList<Road> roads = Road.getRoads(board,index);
                for (Road road : roads) {
                    int my = ChessCount.getMy(road,myChess);
                    int your = ChessCount.getYour(road,myChess);
                    if(your == 0 &&( my == 4 || my == 5)){
                        this.operator(road.getFp()/19,road.getFp()%19,board,road);
                    }
                }
            }
        }
        if(AS.size()!=0){
            return AS.get(0);
        }
        else{
            return new Step(new int[]{-1,-1},new int[]{-1,-1});
        }

    }
    public void operator_stop(int i, int j ,MyBoard board,Road road,PieceColor opponent){
        int d = road.getJ() - 1;//方向坐标
        ArrayList<int[]> a = new ArrayList<int[]>();
        int t_i=0,t_j=0;
        for (int k = 0 ; k < 6;k++){
            int y = i + dir[d][0] * k,x = j + dir[d][1] * k;
            if(y>=0&&y<19&&x>=0&&x<19 && board.get(y*19+x) == opponent){
                t_i = y;
                t_j = x;
                break;
            }
        }
        int flag1 = 0,flag2 = 0;
        for (int k = 1 ; k < 6;k++){
            int y = t_i + dir[d][0] * k,x = t_j + dir[d][1] * k;
            int y1 = t_i + dir[d][0] * (-1*k),x1 = t_j + dir[d][1] * (-1*k);
            if(flag1==0&&y>=0&&y<19&&x>=0&&x<19 && board.get(y*19+x) == EMPTY){
                int[] arr = {y,x};
                a.add(arr);
                flag1=1;
            }
            if(flag2==0&&y1>=0&&y1<19&&x1>=0&&x1<19 && board.get(y1*19+x1) == EMPTY){
                int[] arr = {y1,x1};
                a.add(arr);
                flag2=1;
            }
        }
        if(a.size() == 1){
            for (int ii = 0; ii < 19 ; ii++){
                for (int kk = 0 ; kk < 19 ; kk++){
                    if(board.get(ii*19+kk) == EMPTY){
                        int[] arr = {ii,kk};
                        a.add(arr);
                        kk = 20;ii = 20;//跳出两层循环
                    }
                }
            }
        }
        //添加到step
        if (a.size() != 0)
            AS.add( new Step(a.get(0),a.get(1)));
    }
    public Step mustStop(MyBoard board, PieceColor myChess){
        this.AS.clear();
        PieceColor opponent;
        if(myChess==WHITE){
            opponent=BLACK;
        }
        else{
            opponent=WHITE;
        }
        for(int i = 0;i<19;i++){
            for(int j=0;j<19;j++){
                int index = i*19+j;
                ArrayList<Road> roads = Road.getRoads(board,index);
                for (Road road : roads) {
                    int my = ChessCount.getMy(road,myChess);
                    int your = ChessCount.getYour(road,myChess);
                    if(my == 0 &&( your == 4 || your == 5)){
                        this.operator_stop(road.getFp()/19,road.getFp()%19,board,road,opponent);
                    }
                }
            }
        }
        if(AS.size()!=0){
            return AS.get(0);
        }
        else{
            return new Step(new int[]{-1,-1},new int[]{-1,-1});
        }
    }


}
